//package com.example.redis.rpcutil.impl;
//
//
//import com.example.redis.rpcutil.*;
//import com.example.redis.rpcutil.util.MQRestClient;
//import com.example.redis.rpcutil.util.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.CacheMode;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.amqp.support.converter.SerializerMessageConverter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.Queue;
//import java.util.concurrent.atomic.AtomicBoolean;
//
///**
// * User: zhaolp
// *
// * 和rabbitmq通信的控制器，主要负责：
// * <p>1、和rabbitmq建立连接</p>
// * <p>2、声明exChange和queue以及它们的绑定关系</p>
// * <p>3、启动消息监听容器，并将不同消息的处理者绑定到对应的exchange和queue上</p>
// * <p>4、持有消息发送模版以及所有exchange、queue和绑定关系的本地缓存</p>
// */
//@Service
//public class DefaultMqMsgController implements MqMsgController {
//	private static final Logger logger = LoggerFactory.getLogger(DefaultMqMsgController.class);
//	private CachingConnectionFactory rabbitConnectionFactory;
//	@Autowired
//	private MqConnConfig mqConnConfig;
//
//	private RabbitAdmin rabbitAdmin;
//
//	private CodecFactory defaultCodecFactory = new JsonCodecFactory();
//
//	private SimpleMessageListenerContainer msgListenerContainer; // rabbitMQ msg listener container
//
//	private MessageAdapterHandler msgAdapterHandler = new MessageAdapterHandler();
//
//	private MessageConverter serializerMessageConverter = new SerializerMessageConverter(); // 直接指定
//	//queue cache, key is exchangeName
//	private Map<String, DirectExchange> exchanges = new HashMap<>();
//	//queue cache, key is queueName
//	private Map<String, Queue> queues = new HashMap<>();
//	//bind relation of queue to exchange cache, value is exchangeName | queueName
//	private Set<String> binded = new HashSet<String>();
//
//	private MqMsgTemplate mqMsgTemplate; // 给App使用的Event发送客户端
//
//	private AtomicBoolean isStarted = new AtomicBoolean(false);
//
//	private String CMD = null;
//
//	private static DefaultMqMsgController defaultEventController;
//
//	@Value("#{'${rmq.queuePrefix}'.split(',')}")
//	private List<String> queuePrefix;
//
//	public synchronized static DefaultMqMsgController getInstance(MqConnConfig config){
//		if(defaultEventController==null){
//			defaultEventController = new DefaultMqMsgController(config);
//		}
//		return defaultEventController;
//	}
//
//	private DefaultMqMsgController(MqConnConfig mqConnConfig){//构造器注入
//		if (mqConnConfig == null) {
//			throw new IllegalArgumentException("Config can not be null.");
//		}
//		this.mqConnConfig = mqConnConfig;
//		initRabbitConnectionFactory();
//		// 初始化AmqpAdmin
//		rabbitAdmin = new RabbitAdmin(rabbitConnectionFactory);
//		// 初始化RabbitTemplate
//		RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory);
//		rabbitTemplate.setMessageConverter(serializerMessageConverter);
//		mqMsgTemplate = new DefaultMqMsgTemplate(rabbitTemplate,defaultCodecFactory, this);
//	}
//
//	/**
//	 * 初始化rabbitmq连接
//	 */
//	public synchronized CachingConnectionFactory initRabbitConnectionFactory() {
//		if (rabbitConnectionFactory == null) {
//			rabbitConnectionFactory = new CachingConnectionFactory();
//			//rabbitConnectionFactory.setHost(mqConnConfig.getServerHost());
//			rabbitConnectionFactory.setAddresses(mqConnConfig.getAddresses());
//			rabbitConnectionFactory.setPort(mqConnConfig.getPort());
//			rabbitConnectionFactory.setCacheMode(CacheMode.CHANNEL);
//			rabbitConnectionFactory.setConnectionLimit(mqConnConfig.getConnectionLimit()); //CacheMode.CONNECTION
//			rabbitConnectionFactory.setConnectionCacheSize(mqConnConfig.getConnectionCacheSize()); //CacheMode.CONNECTION
//			rabbitConnectionFactory.setChannelCacheSize(mqConnConfig.getChannelCacheSize()); //CacheMode.CHANNEL
//			rabbitConnectionFactory.setUsername(mqConnConfig.getUsername());
//			rabbitConnectionFactory.setPassword(mqConnConfig.getPassword());
//			if (!StringUtils.isEmpty(mqConnConfig.getVirtualHost())) {
//				rabbitConnectionFactory.setVirtualHost(mqConnConfig.getVirtualHost());
//			}
//		}
//
//		return rabbitConnectionFactory;
//	}
//
//	public CachingConnectionFactory getConnectionFactory(){
//		return rabbitConnectionFactory;
//	}
//	/**
//	 * 注销程序
//	 */
//	public synchronized void destroy() throws Exception {
//		if (!isStarted.get()) {
//			return;
//		}
//		msgListenerContainer.stop();
//		mqMsgTemplate = null;
//		rabbitAdmin = null;
//		rabbitConnectionFactory.destroy();
//	}
//
//	/**
//	 *  绑定新增队列到监听容器（只有调用了创建新队列createQueue后才会进入这个方法）
//	 *createQueue时queueName发送到广播队列QUEUE_NAME_PUBLISH_FANOUT，并直接传进queueName调用本方法；
//	 *  或调度获取QUEUE_NAME_PUBLISH_FANOUT的新增队列queueName
//	 *
//	 * fixed bug by zhongnh
//	 */
//	@Override
//	public void start(String queueName,Integer maxLength) {
//		if (!mqConnConfig.isStartListener()) return;
//
//		if (isStarted.get() && msgListenerContainer !=null && msgListenerContainer.isRunning()) {
//			logger.warn("Mq msg Controller is started1 !!!");
//
//			if(!queueName.startsWith("amq.") && !queues.containsKey(queueName)){
//				logger.debug("found the new queue need to listen: " + queueName);
//				List<String> newQueues = new ArrayList<>();
//				Set<String> mapping = msgAdapterHandler.getAllBinding();
//
//				for (String relation : mapping) {
//					String[] relaArr = relation.split("\\|");
//					String exchangeName1 = relaArr[0];
//					String queueName1 = relaArr[1];
//
//					if(queueName.equals(queueName1)){
//						declareBinding(exchangeName1, queueName1,maxLength);
//					}
//				}
//
//				List<String> newQ = reCalQueuesAndExchanges();
//				newQueues.addAll(newQ);
//				if(!newQueues.contains(queueName)) newQueues.add(queueName);//把所有新绑定的queue记录汇总起来
//
//				String[] a = new String[newQueues.size()];
//
//				addNewQueue2MsgListener(newQueues.toArray(a));  // zhongnh: 只绑定新增的队列到监听器
//				isStarted.set(true);
//			}
//		} else {
//			logger.debug("Mq msg Controller begin first start1 ");
//			reCalQueuesAndExchanges();
//			rebindAndRestartMsgListener();
//			isStarted.set(true);
//		}
//	}
//
//	@Override
//	public void start(String queueName) {
//		start(queueName,null);
//	}
//
//	@Override
//	public void stop() {
//		if(msgListenerContainer!=null) {
//			msgListenerContainer.stop();
//			msgListenerContainer.destroy();
//			binded.clear();
//			queues.clear();
//			exchanges.clear();
//			isStarted.set(false);
//			logger.debug("msgListenerContainer已销毁！！");
//		}
//	}
//
//	/**
//	 * 调度监听消费者是否已经停止或队列是否有新增，如果有则全部重新绑定
//	 *
//	 * fixed bug by zhongnh
//	 *
//	 */
//	@Override
//	public synchronized void start() {
//		if (!mqConnConfig.isStartListener()) return;
//
//		if (isStarted.get() && msgListenerContainer !=null && msgListenerContainer.isRunning()) {
//			logger.warn("Mq msg Controller is started2 !!!");
//
//			// 如果监听器已经启动，但是比对发现有新队列出现，停止监听重新绑定全部队列
//			Set<String> mapping = msgAdapterHandler.getAllBinding(); //mapping来自于添加队列或发送消息时，会把queue与exchange对照关系缓存到mapping
//			if(!binded.containsAll(mapping)){
//				logger.debug("Mq msg Controller getAllBinding2!!!");
//				List<String> newQueues = new ArrayList<>();
//
//				Set<String> diff = new HashSet<String>(mapping.size());
//				diff.addAll(mapping);
//				diff.removeAll(binded);
//
//				if (diff.size() > 0) {
//					logger.debug("把差异部分声明关系(" + diff.size() + "): " + diff);
//					for (String relation : diff) {
//						String[] relaArr = relation.split("\\|");
//						String exchangeName = relaArr[0];
//						String queueName = relaArr[1];
//
//						try {
//							boolean isbinded = declareBinding(exchangeName, queueName, null); //目的是声明并绑定到binded中
//							if(isbinded) newQueues.add(queueName);
//						} catch (Exception ex) {
//							logger.error("声明绑定关系失败：exchangeName=" +exchangeName + ", queueName=" +  queueName, ex);
//						}
//					}
//				}
//
//				List<String> newQueues2 = reCalQueuesAndExchanges(); // 上面更新最新的binded后，如果与MQ全集queue与exchange关系比较还是有新增队列，继续补充声明并绑定到binded中
//				newQueues2.removeAll(newQueues);
//				newQueues.addAll(newQueues2);//唯一合集
//				String[] a = new String[newQueues.size()];
//
//				addNewQueue2MsgListener(newQueues.toArray(a));
//				isStarted.set(true);
//			}
//		} else {
//			//logger.debug("Mq msg Controller begin first start2 ");
//			reCalQueuesAndExchanges();
//			rebindAndRestartMsgListener();
//			isStarted.set(true);
//		}
//	}
//
//	/**
//	 * 等消费者把prefetch的消息消费完毕后，优雅停止消费者后添加的新队列到监听容器并重启监听（警告：依然存在卡死的可能）
//	 *
//	 * fixed bug by zhongnh
//	 */
//	private synchronized void addNewQueue2MsgListener(String... queueNames){
//
//		if (queueNames != null) {
//			if(msgListenerContainer==null) {
//				MessageListener listener = new MessageListenerAdapter(msgAdapterHandler,serializerMessageConverter);
//				msgListenerContainer = new SimpleMessageListenerContainer();
//				msgListenerContainer.setConnectionFactory(rabbitConnectionFactory);
//				msgListenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
//				msgListenerContainer.setMessageListener(listener);
//				msgListenerContainer.setErrorHandler(new MessageErrorHandler());
//				msgListenerContainer.setPrefetchCount(mqConnConfig.getPrefetchSize()); // 设置每个消费者消息的预取值
//				msgListenerContainer.setConcurrentConsumers(mqConnConfig.getConcurrentConsumers());
//				msgListenerContainer.setMaxConcurrentConsumers(mqConnConfig.getMaxConcurrentConsumers());
//				msgListenerContainer.setTxSize(mqConnConfig.getPrefetchSize());//设置有事务时处理的消息数
//				msgListenerContainer.setAutoStartup(true);
//				msgListenerContainer.setQueueNames(queueNames);
//				msgListenerContainer.start();
//				logger.debug("msgListenerContainer启动成功");
//			} else {
//				logger.debug("添加的新队列：" + queueNames + "到监听容器"); // 添加的队列必须已经创建并存在，优雅停止监听后，自动创建新的消费者，重新监听
//				msgListenerContainer.addQueueNames(queueNames);
//
//				if(!msgListenerContainer.isRunning()) {
//					logger.debug("msgListenerContainer没有在运行");
//					msgListenerContainer.start();
//					logger.debug("msgListenerContainer启动成功");
//				}
//			}
//		} else {
//			logger.debug("队列为空暂不监听");
//		}
//	}
//
//	/**
//	 * 初始化消息监听器容器 (zhongnh 警告：只适合第一次启动时使用，中间新增队列调用本方法，可能会出现卡死死锁)
//	 *
//	 * fixed bug by zhongnh
//	 */
//	private synchronized void rebindAndRestartMsgListener(){
//		if(msgListenerContainer!=null) {
//			msgListenerContainer.stop();
//			msgListenerContainer.destroy();
//			logger.debug("msgListenerContainer已销毁！！");
//		}
//
//		MessageListener listener = new MessageListenerAdapter(msgAdapterHandler,serializerMessageConverter);
//		msgListenerContainer = new SimpleMessageListenerContainer();
//		msgListenerContainer.setConnectionFactory(rabbitConnectionFactory);
//		msgListenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
//		msgListenerContainer.setMessageListener(listener);
//		msgListenerContainer.setErrorHandler(new MessageErrorHandler());
//		msgListenerContainer.setPrefetchCount(mqConnConfig.getPrefetchSize()); // 设置每个消费者消息的预取值
//		msgListenerContainer.setConcurrentConsumers(mqConnConfig.getConcurrentConsumers());
//		msgListenerContainer.setMaxConcurrentConsumers(mqConnConfig.getMaxConcurrentConsumers());
//		msgListenerContainer.setTxSize(mqConnConfig.getPrefetchSize());//设置有事务时处理的消息数
//		msgListenerContainer.setQueues(queues.values().toArray(new Queue[queues.size()]));
//		msgListenerContainer.setAutoStartup(true);
//
//		if (!queues.isEmpty()) {
//			msgListenerContainer.start();
//			logger.debug("msgListenerContainer启动成功");
//		} else {
//			logger.debug("队列为空暂不监听");
//		}
//	}
//
//	private List<String> reCalQueuesAndExchanges(){
//		Set<String> mqbinds= MQRestClient.queryAllBindingsSet();//获取MQ服务器所有queue和exchange绑定关系
//		List<String> newQueues = new ArrayList<>();
//
//		if(!binded.containsAll(mqbinds)){
//
//			Set<String> diff = new HashSet<String>(mqbinds.size());
//			diff.addAll(mqbinds);
//			diff.removeAll(binded);
//
//			if(!diff.isEmpty()){//只把差异部分声明
//				for (String mqbind : diff) {
//					if(!StringUtils.isEmpty(mqbind) && mqbind.split("\\|").length==2){
//						String[] relaArr = mqbind.split("\\|");
//						String exchangeName = relaArr[0];//队列
//						String queueName = relaArr[1];//交换器
//						if(!exchangeName.contains("fanout") && !exchangeName.contains("FANOUT")){
//							try {
//								boolean isbinded = declareBinding(exchangeName,queueName);
//								if (isbinded) newQueues.add(queueName); //把绑定成功的记录下来
//							} catch (Exception ex) {
//								logger.error("声明绑定关系失败：exchangeName=" +exchangeName + ", queueName=" +  queueName, ex);
//							}
//						}
//					}
//				}
//			}
//		}
//
//		return newQueues;
//	}
//
//	@Override
//	public MqMsgTemplate getEopEventTemplate() {
//		return mqMsgTemplate;
//	}
//
//	@Override
//	public MqMsgController add(String queueName, String exchangeName, MqMsgProcesser mqMsgProcesser) {
//		return add(queueName, exchangeName, mqMsgProcesser, defaultCodecFactory);
//	}
//
//	public MqMsgController add(String queueName, String exchangeName, MqMsgProcesser mqMsgProcesser, CodecFactory codecFactory) {
//		msgAdapterHandler.add(queueName, exchangeName, mqMsgProcesser, codecFactory); // fix by zhongnh 外层已经调用controller.start进行重新绑定
//		declareBinding(exchangeName, queueName);
//
////		if(isStarted.get()){
////			rebindAndRestartMsgListener();
////		}
//		return this;
//	}
//
//	@Override
//	public MqMsgController add(Map<String, String> bindings,MqMsgProcesser mqMsgProcesser) {
//		return add(bindings, mqMsgProcesser,defaultCodecFactory);
//	}
//
//	public MqMsgController add(Map<String, String> bindings,MqMsgProcesser mqMsgProcesser, CodecFactory codecFactory) {
//		for(Map.Entry<String, String> item: bindings.entrySet())
//			msgAdapterHandler.add(item.getKey(),item.getValue(), mqMsgProcesser,codecFactory);
//		return this;
//	}
//
//	/**
//	 * exchange和queue是否已经绑定
//	 */
//	public boolean beBinded(String exchangeName, String queueName) {
//		return binded.contains(exchangeName+"|"+queueName);
//	}
//
//	/**
//	 * exchange和queue是否已经绑定
//	 */
//	public boolean unBinded(String exchangeName, String queueName) {
//		// 找出队列
//		Queue queue = queues.get(queueName);
//		if(queue == null) {
//			Map<String, Object> arguments= new HashMap<>();
//			arguments.put("ha-mode","all");
//			queue = new Queue(queueName, true, false, false,arguments);//创建镜像队列
//		}
//
//		// 找出交换器
//		DirectExchange directExchange = exchanges.get(exchangeName);
//		if(directExchange == null) {
//			Map<String, Object> arguments= new HashMap<>();
//			arguments.put("ha-mode","all");
//			directExchange = new DirectExchange(exchangeName, true, false, arguments);//创建镜像交换器，zhongnh：注意，此处代码只能声明类型为DirectExchange
//		}
//
//		// MQ解绑
//		Binding binding = BindingBuilder.bind(queue).to(directExchange).with(queueName);
//		rabbitAdmin.removeBinding(binding);
//
//		// 删除关系
//		return binded.remove(exchangeName+"|"+queueName);
//	}
//
//	/**
//	 * 声明exchange和queue已经它们的绑定关系
//	 * @param exchangeName 交换名称
//	 * @param queueName 队列名称
//	 */
//	protected synchronized boolean declareBinding(String exchangeName, String queueName){
//		return declareBinding(exchangeName,queueName,null);
//	}
//
//	/**
//	 * 声明exchange和queue已经它们的绑定关系
//	 * @param exchangeName 交换名称
//	 * @param queueName 队列名称
//	 * @param maxLength 最大长度
//	 */
//	protected synchronized boolean declareBinding(String exchangeName, String queueName,Integer maxLength) {
//		String bindRelation = exchangeName +"|"+ queueName;  //fixed bug by zhongnh
//
//		//logger.info("Mq msg Controller declareBinding "+bindRelation+"");
//		if (binded.contains(bindRelation) || !isMatchQueue(queueName)) {
//			return false;
//		}
//
//		boolean needBinding = false;
//		DirectExchange directExchange = exchanges.get(exchangeName);
//		if(directExchange == null) {
//			Map<String, Object> arguments= new HashMap<>();
//			arguments.put("ha-mode","all");
//			directExchange = new DirectExchange(exchangeName, true, false, arguments);//创建镜像交换器，zhongnh：注意，此处代码只能声明类型为DirectExchange
//			exchanges.put(exchangeName, directExchange);
//			rabbitAdmin.declareExchange(directExchange);//声明exchange
//			needBinding = true;
//		}
//
//		Queue queue = queues.get(queueName);
//		if(queue == null) {
//			Map<String, Object> arguments= new HashMap<>();
//			arguments.put("ha-mode","all");
//			//设置队列长度
//			if(maxLength!=null && maxLength.intValue()>0){
//				arguments.put("x-max-length",maxLength);
//			}
//			queue = new Queue(queueName, true, false, false,arguments);//创建镜像队列
//			queues.put(queueName, queue);
//			rabbitAdmin.declareQueue(queue);	//声明queue
//			needBinding = true;
//		}
//
//		if(needBinding) {
//			Binding binding = BindingBuilder.bind(queue).to(directExchange).with(queueName);//将queue绑定到exchange
//			rabbitAdmin.declareBinding(binding);//声明绑定关系
//			binded.add(bindRelation);
//			return true;
//		}
//
//		return false;
//	}
//
//	private boolean isMatchQueue(String queueName) {
//		if (queueName == null) {
//			return false;
//		}
//		return queuePrefix.stream().anyMatch(queueName::contains);
//	}
//
//	/**
//	 * 删除队列
//	 * @param queueName
//	 * @return
//	 */
//	public boolean deleteQueue(String queueName) {
//		boolean b=rabbitAdmin.deleteQueue(queueName);
//		if(b){
//			queues.remove(queueName);
//		}
//		return b;
//	}
//
//	/**
//	 * 删除队列根据队列名称前缀
//	 * @param queueNamePrefix
//	 * @param exchangeName
//	 * @return
//	 */
//	public int deleteQueueByPrefix(String queueNamePrefix,String exchangeName) {
//		int deleteqty=0;
//		if(!StringUtils.isEmpty(queueNamePrefix)){
//			ArrayList<String> list= MQRestClient.queryAllQueuesName();
//			if(list!=null && !list.isEmpty()){
//				for (String queueName : list) {
//					if(queueName.startsWith(queueNamePrefix)){
//						boolean b=rabbitAdmin.deleteQueue(queueName);
//						if(b){
//							queues.remove(queueName);
//							//解绑关系
//							unBinded(exchangeName,queueName);
//							deleteqty++;
//						}
//					}
//				}
//			}
//		}
//		return deleteqty;
//	}
//	/**
//	 * 清空队列
//	 * @param queueName
//	 * @param noWait 是否等待
//	 */
//	public void purgeQueue(String queueName,boolean noWait) {
//		rabbitAdmin.purgeQueue(queueName,noWait);
//	}
//
//	public boolean deleteExchange(String exchangeName) {
//		return deleteExchange(exchangeName, false);
//	}
//	/**
//	 * 删除交换器
//	 * @param exchangeName
//	 * @return
//	 */
//	public boolean deleteExchange(String exchangeName, boolean isForce) {
//		if(exchangeName.startsWith("amq.")){
//			logger.warn("["+exchangeName+"]系统级别交换器，不能删除！");
//			return false;
//		}
//
//		boolean isCanDelete=true;
//
//		if (!isForce) {
//			ArrayList<String> queues=MQRestClient.queryAllQueuesName();
//
//			//如果还有相关队列就不能删除
//			if(queues!=null && !queues.isEmpty()){
//				for (String queue : queues) {
//					if(queue.startsWith(exchangeName)){//zhongnh：绑定在同一个exchange下的队列名，前缀部分都是exchange的名字。。。否则删除exchange会让其他队列收不到消息
//						isCanDelete=false;
//					}
//				}
//			}
//		}
//
//		//是否删除成功
//		boolean b = false;
//		if(isCanDelete){
//			b = rabbitAdmin.deleteExchange(exchangeName);
//			if(b){
//				exchanges.remove(exchangeName);
//			}
//		}
//		return b;
//	}
//
//	/**
//	 * 获取当前已绑定的队列
//	 * @param exchangeName
//	 * @return
//	 */
//	public Map<String, Queue> getAllQueues() {
//		return queues;
//	}
//
//	/**
//	 * 重置MQ队列
//	 * @return boolean
//	 */
//	public boolean resetMQ() {
//		Set<String> mqbinds = MQRestClient.queryAllBindingsSet();
//		for (String mqbind : mqbinds) {
//			if(!StringUtils.isEmpty(mqbind) && mqbind.split("\\|").length==2){
//				String[] relaArr = mqbind.split("\\|");
//				String exchangeName = relaArr[0];//队列
//				String queueName = relaArr[1];//交换器
//
//				if(!exchangeName.contains("FANOUT") && !queueName.contains("FANOUT") && !exchangeName.startsWith("amq.") && !queueName.startsWith("amq.")){
//					boolean isUnbind = unBinded(exchangeName, queueName);
//					boolean isDelQueue = deleteQueue(queueName);
//					boolean isDelExch = deleteExchange(exchangeName, true); // 有可能在其他绑定关系中已删除
//
//					if (isUnbind && isDelQueue) {
//						logger.debug("exchangeName={}, queueName={} 已被删除", exchangeName, queueName);
//					}
//				} else {
//					logger.debug("系统级别队列或交换机，不能删除：exchangeName={}, queueName={}", exchangeName, queueName);
//				}
//			}
//		}
//
//		return true;
//	}
//
//	public String getCMD() {
//		return CMD;
//	}
//
//	public void setCMD(String CMD) {
//		this.CMD = CMD;
//	}
//
//}
