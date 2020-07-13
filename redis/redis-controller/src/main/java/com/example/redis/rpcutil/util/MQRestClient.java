//package com.example.redis.rpcutil.util;
//
//import com.alibaba.fastjson.JSON;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import sun.misc.BASE64Encoder;
//
//import javax.annotation.PostConstruct;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.ConnectException;
//import java.util.*;
//
///**
// * Created by zhaolp on 2017/7/12 0012.
// * Rabbit MQ Rest API工具类
// */
//@Component
//public class MQRestClient {
//    private static Logger logger = LoggerFactory.getLogger(MQRestClient.class);
//    private static String USERNAME;
//    private static String PASSWORD;
//    //private static String WEBURL;
//    private static String ADDRESSES;
//    private static List<String> HOSTS;
//    private static String PORT;
//    private static String VHOST;
//
//    @Value("${rmq.username}")
//    public void setUsername(String username) {
//        USERNAME = username;
//    }
//
//    @Value("${rmq.password}")
//    public void setPassword(String password) {
//        PASSWORD = password;
//    }
//
//    @Value("${rmq.addresses}")
//    public void setAddresses(String addresses) {
//    	ADDRESSES = addresses;
//    }
//
//    @Value("${rmq.port}")
//    public void setPort(String port) {
//        PORT = port;
//    }
//
////    @Value("${rmq.webUrl}")
////    public void setWebUrl(String webUrl) {
////        this.WEBURL = webUrl;
////    }
//
//    @Value("${rmq.virtualHost}")
//    public void setVhost(String vhost) {
//        VHOST = vhost;
//    }
//
//    //定义MQ的健康状态值
//    public static final Integer OK =0;
//    public static final Integer WARNING =1;
//    public static final Integer CRITICAL =2;
//    public static final Integer UNKNOWN =3;
//
//    /**
//     * 获得绑定信息
//     * @return exchangeName|queueName
//     */
//    public static Set<String> queryAllBindingsSet(){
//        ArrayList<HashMap> list=queryAllBindingsInfo();
//        Set<String> binds=new HashSet<String>();
//        if(list!=null && !list.isEmpty()){
//            for (HashMap hashMap : list) {
//                if(hashMap!=null){
//                    String exchangeName = (String) hashMap.get("source");//交换器
//                    String queueName = (String) hashMap.get("destination");//队列
//                    if(!StringUtils.isEmpty(exchangeName) && !StringUtils.isEmpty(queueName)){
//                        String bindRelation =exchangeName+"|"+queueName;
//                        binds.add(bindRelation);
//                    }
//                }
//            }
//        }
//        return binds;
//    }
//    /**
//     * 获取所有绑定信息
//     * @return source交换器 destination 队列
//     */
//    public static ArrayList<HashMap> queryAllBindingsInfo(){
//        ArrayList<HashMap> bindings= new ArrayList<>();
//        HttpResponse response = null;
//        int statusCode = 0;
//        String repsonseStr=null,source,destination,vhost,destination_type;
//        String path = "/api/bindings";
//
//        try {
//            response = sendGetRequest(path);
//            if(response == null) {
//                logger.error("获取绑定信息失败, response " + response);
//                throw new RuntimeException("获取绑定信息失败, response " + response);
//            }
//            //读取响应内容
//            try(BufferedReader breader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
//            StringBuilder responseString = new StringBuilder();
//            String line = null;
//            while ((line = breader.readLine()) !=null) {
//                responseString.append(line);
//            }
//            breader.close();
//            repsonseStr = responseString.toString();
//            } catch (Exception e) {
//                logger.error("读取响应内容, response " + response, e);
//                throw new RuntimeException("读取响应内容, response " + response);
//            }
//            List<HashMap> jsonArray= JSON.parseArray(repsonseStr,HashMap.class);
//            if(jsonArray!=null && !jsonArray.isEmpty()){
//                for (HashMap map : jsonArray) {
//                    if(map!=null){
//                        source = (String) map.get("source");//交换器
//                        destination = (String) map.get("destination");//队列
//                        vhost= (String) map.get("vhost");//队列
//                        destination_type= (String) map.get("destination_type");//队列
//                        if(!StringUtils.isEmpty(source) && vhost.equals(vhost) && "queue".equals(destination_type)){
//                            bindings.add(map);
//                            //logger.info("INFO: exhange ["+source+"] -> queue ["+destination+"] are binding ,this info is query from MQ Server.");
//                        }
//                    }
//                }
//            } else {
//            	logger.info("statusCode="+statusCode+" repsonseStr =" + repsonseStr);
//            }
//
//            statusCode = response.getStatusLine().getStatusCode();
//
//            if (statusCode != 200) {
//            	logger.error("statusCode="+statusCode+" repsonseStr =" + repsonseStr);
//            }
//
//        } catch (Exception e) {
//        	logger.error("Could not connect to "+path, e);
//        }
//
//        return bindings;
//    }
//
//    @PostConstruct
//	public void afterPropertiesSet() throws Exception {
//    	if (ADDRESSES != null && ADDRESSES.length() > 0 && HOSTS==null) { //初始化数组
//    		HOSTS = new ArrayList<String>(Arrays.asList(ADDRESSES.split(",")));
//			Collections.shuffle(HOSTS);
//		}
//	}
//
//    /**
//     *	防止单点故障
//     * fixed by zhongnh
//     * @param path
//     * @return
//     */
//    public static HttpResponse sendGetRequest(String path) {
//    	HttpResponse response = null;
//    	int failFlag = 0;
//    	String WEBURL = null;
//
//        for (int i = 0 ;i < HOSTS.size(); i++) {
//        	try {
//        		DefaultHttpClient Client = new DefaultHttpClient();
//
//        		WEBURL = getWebUrl(path, i);
//        		//logger.debug("请求URL " + WEBURL);
//
//                HttpGet httpGet = new HttpGet(WEBURL);
//                String up = USERNAME+":"+PASSWORD;
//                //设置凭证
//                String credentials = new BASE64Encoder().encode(up.getBytes("UTF-8"));
//                httpGet.setHeader("Authorization","Basic "+credentials);
//                response = Client.execute(httpGet);
//
//            	break;
//            } catch(ConnectException e){
//            	failFlag ++;
//            	logger.error("http通讯失败, URL " + WEBURL + " , 重试第" + failFlag + " 次", e);
//            	if (i==HOSTS.size()-1) throw new RuntimeException(e.getMessage(), e);
//            } catch (Exception ex) {
//            	logger.error("http通讯失败, URL " + WEBURL,ex);
//            	throw new RuntimeException(ex.getMessage(), ex);
//            }
//        }
//
//        if(failFlag > 0) {
//        	adjustHosts(failFlag);
//        }
//
//        return response;
//    }
//
//    public static String getWebUrl(String path, int i) {
//    	String host = HOSTS.get(i).trim();
//    	return "http://" + host + ":"  + PORT + path;
//    }
//
//    private static synchronized void adjustHosts(int failFlag){
//		List<String> adjustList = new ArrayList<String>();
//
//		String successHost = HOSTS.get(failFlag);
//		adjustList.add(successHost);
//		HOSTS.remove(failFlag);
//		adjustList.addAll(HOSTS);
//		HOSTS = adjustList;
//	}
//
//    /**
//     * 根据exchangeName查询交换器信息
//     * @param exchangeName
//     * @return
//     */
//    public static HashMap queryExchangebyName(String exchangeName){
//        HttpResponse response = null;
//        HashMap exchangeInfo=new HashMap();
//        int statusCode = 0;
//        String repsonseStr=null;
//        Boolean auto_delete,durable;
//        String vhost_encode=VHOST.replace("/","%2F");
//        String path = "/api/exchanges/"+vhost_encode+"/"+exchangeName;
//        try {
//
//        	response = sendGetRequest(path);
//            if(response == null) {
//                logger.error("查询交换器信息, response " + response);
//                throw new RuntimeException("查询交换器信息, response " + response);
//            }
//            //读取响应内容
//            try(BufferedReader breader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
//            StringBuilder responseString = new StringBuilder();
//            String line = null;
//            while ((line = breader.readLine()) !=null) {
//                responseString.append(line);
//            }
//            breader.close();
//            repsonseStr = responseString.toString();
//            } catch (Exception e) {
//                logger.error("读取响应内容, response " + response, e);
//                throw new RuntimeException("读取响应内容, response " + response);
//            }
//            HashMap repsonseInfo= JSON.parseObject(repsonseStr,HashMap.class);
//            if(repsonseInfo!=null){
//                exchangeInfo = repsonseInfo;
//                auto_delete = (Boolean) exchangeInfo.get("auto_delete");
//                durable = (Boolean) exchangeInfo.get("durable");
//                exchangeInfo.put("status", OK);
//                //检测auto_delete或者durable属性不对应则警告
//                if(auto_delete){
//                    logger.warn("WARN: Exchange "+exchangeName+"-auto_delete flag is not false");
//                    exchangeInfo.put("status", WARNING);
//                }
//                if(!durable){
//                    logger.warn("WARN: Exchange "+exchangeName+"-durable flag is not true");
//                    exchangeInfo.put("status", WARNING);
//                }
//            }
//            statusCode = response.getStatusLine().getStatusCode();
//
//            if (statusCode != 200) {
//            	logger.error("statusCode="+statusCode+" repsonseStr =" + repsonseStr);
//            }
//        } catch (Exception e) {
//            logger.error("Could not connect to "+path, e);
//            exchangeInfo.put("status", CRITICAL);
//        }
//        //队列不存在的情况
//        if(statusCode==404){
//            logger.error("Critical:Exchange "+exchangeName+" does not exist");
//            exchangeInfo.put("status", CRITICAL);
//        }
//        if(statusCode>299){
//            logger.error("Unexpected API error :"+repsonseStr);
//            exchangeInfo.put("status", UNKNOWN);
//        }
//
//        if(OK.equals(exchangeInfo.get("status"))){
//            logger.info("OK! Connect to "+path+" successful ");
//        }
//        return exchangeInfo;
//    }
//
//    /**
//     * 获取所有交换器名称
//     * @return 交换器对象数组
//     */
//    public static ArrayList<HashMap> queryAllExchangesInfo(){
//        ArrayList<HashMap> exchanges= new ArrayList<>();
//        HttpResponse response = null;
//        int statusCode = 0;
//        String repsonseStr=null;
//        Boolean auto_delete,durable;
//        String path = "/api/exchanges";
//        try {
//            response = sendGetRequest(path);
//            if(response == null) {
//                logger.error("获取所有交换器名称, response " + response);
//                throw new RuntimeException("获取所有交换器名称, response " + response);
//            }
//            //读取响应内容
//            try(BufferedReader breader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
//            StringBuilder responseString = new StringBuilder();
//            String line = null;
//            while ((line = breader.readLine()) !=null) {
//                responseString.append(line);
//            }
//            breader.close();
//            repsonseStr = responseString.toString();
//            }  catch (Exception e) {
//                logger.error("读取响应内容, response " + response, e);
//                throw new RuntimeException("读取响应内容, response " + response);
//            }
//            List<HashMap> jsonArray= JSON.parseArray(repsonseStr,HashMap.class);
//            if(jsonArray!=null && !jsonArray.isEmpty()){
//                for (HashMap map : jsonArray) {
//                    if(map!=null){
//                        auto_delete = (Boolean) map.get("auto_delete");
//                        durable = (Boolean) map.get("durable");
//                        String exchangeName=(String) map.get("name");
//                        exchanges.add(map);
//                        //检测auto_delete或者durable属性不对应则警告
//                        if(auto_delete){
//                            logger.warn("WARN: Exchanges "+exchangeName+"-auto_delete flag is not false");
//                        }
//                        if(!durable){
//                            logger.warn("WARN: Exchanges "+exchangeName+"-durable flag is not true");
//                        }
//                    }
//                }
//            }
//
//            statusCode = response.getStatusLine().getStatusCode();
//            if (statusCode != 200) {
//            	logger.error("statusCode="+statusCode+" repsonseStr =" + repsonseStr);
//            }
//
//        } catch (Exception e) {
//            logger.error("Could not connect to "+path, e);
//        }
//        //队列不存在的情况
//        if(statusCode==404){
//            logger.error("Critical:Exchange query has failed!");
//        }
//        if(statusCode>299){
//            logger.error("Unexpected API error :"+repsonseStr);
//        }
//        return exchanges;
//    }
//
//    /**
//     * 获取所有交换器名称
//     * @return 交换器名称数组
//     */
//    public static ArrayList<String> queryAllExchangeName(){
//        ArrayList<String> exchanges=new ArrayList<String>();
//        List<HashMap> exchangeArray=queryAllExchangesInfo();
//        if(exchangeArray!=null && !exchangeArray.isEmpty()){
//            for (HashMap hashMap : exchangeArray) {
//                if(hashMap!=null){
//                    String exchangeName= (String) hashMap.get("name");
//                    exchanges.add(exchangeName);
//                }
//            }
//        }
//        return exchanges;
//    }
//
//    /**
//     * 根据queueName查询队列信息
//     * @param queueName
//     * @return
//     */
//    public static HashMap queryQueuebyName(String queueName){
//        HttpResponse response = null;
//        HashMap queueInfo=new HashMap();
//        int statusCode = 0;
//        String repsonseStr=null;
//        Boolean auto_delete,durable;
//        String vhost_encode=VHOST.replace("/","%2F");
//        String path = "/api/queues/"+vhost_encode+"/"+queueName;
//        try {
//        	response = sendGetRequest(path);
//            if(response == null) {
//                logger.error("查询队列信息, response " + response);
//                throw new RuntimeException("查询队列信息, response " + response);
//            }
//            //读取响应内容
//            try(BufferedReader breader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
//            StringBuilder responseString = new StringBuilder();
//            String line = null;
//            while ((line = breader.readLine()) !=null) {
//                responseString.append(line);
//            }
//            breader.close();
//            repsonseStr = responseString.toString();
//            }  catch (Exception e) {
//                logger.error("读取响应内容, response " + response, e);
//                throw new RuntimeException("读取响应内容, response " + response);
//            }
//            HashMap repsonseInfo= JSON.parseObject(repsonseStr,HashMap.class);
//            if(repsonseInfo!=null){
//                queueInfo = repsonseInfo;
//                auto_delete = (Boolean) queueInfo.get("auto_delete");
//                durable = (Boolean) queueInfo.get("durable");
//                queueInfo.put("status", OK);
//                //检测auto_delete或者durable属性不对应则警告
//                if(auto_delete){
//                    logger.warn("WARN: Queue "+queueName+"-auto_delete flag is not false");
//                    queueInfo.put("status", WARNING);
//                }
//                if(!durable){
//                    logger.warn("WARN: Queue "+queueName+"-durable flag is not true");
//                    queueInfo.put("status", WARNING);
//                }
//            }
//            statusCode = response.getStatusLine().getStatusCode();
//            if (statusCode != 200) {
//            	logger.error("statusCode="+statusCode+" repsonseStr =" + repsonseStr);
//            }
//
//        } catch (Exception e) {
//            logger.error("Could not connect to "+path, e);
//            queueInfo.put("status", CRITICAL);
//        }
//        //队列不存在的情况
//        if(statusCode==404){
//            logger.error("Critical:Queue "+queueName+" does not exist");
//            queueInfo.put("status", CRITICAL);
//            queueInfo.put("name",queueName);
//        }
//        if(statusCode>299){
//            logger.error("Unexpected API error :"+repsonseStr);
//            queueInfo.put("status", UNKNOWN);
//            queueInfo.put("name",queueName);
//        }
//        return queueInfo;
//    }
//
//    /**
//     * 获取所有队列名称
//     * @return 队列名称数组
//     */
//    public static ArrayList<HashMap> queryAllQueuesInfo(){
//        ArrayList<HashMap> queues=new ArrayList<HashMap>();
//        HttpResponse response = null;
//        int statusCode = 0;
//        String repsonseStr=null;
//        Boolean auto_delete,durable;
//        String path = "/api/queues";
//        try {
//            response = sendGetRequest(path);
//            if(response == null) {
//                logger.error("获取所有队列名称, response " + response);
//                throw new RuntimeException("获取所有队列名称, response " + response);
//            }
//            //读取响应内容
//            try(BufferedReader breader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
//            StringBuilder responseString = new StringBuilder();
//            String line = null;
//            while ((line = breader.readLine()) !=null) {
//                responseString.append(line);
//            }
//            breader.close();
//            repsonseStr = responseString.toString();
//            } catch (Exception e) {
//                logger.error("读取响应内容, response " + response, e);
//                throw new RuntimeException("读取响应内容, response " + response);
//            }
//            List<HashMap> jsonArray= JSON.parseArray(repsonseStr,HashMap.class);
//            if(jsonArray!=null && !jsonArray.isEmpty()){
//                for (HashMap map : jsonArray) {
//                    if(map!=null){
//                        auto_delete = (Boolean) map.get("auto_delete");
//                        durable = (Boolean) map.get("durable");
//                        String queueName=(String) map.get("name");
//                        queues.add(map);
//                        //检测auto_delete或者durable属性不对应则警告
//                        if(auto_delete){
//                            logger.warn("WARN: Queue "+queueName+"-auto_delete flag is not false");
//                        }
//                        if(!durable){
//                            logger.warn("WARN: Queue "+queueName+"-durable flag is not true");
//                        }
//                    }
//                }
//            }
//
//            statusCode = response.getStatusLine().getStatusCode();
//            if (statusCode != 200) {
//            	logger.error("statusCode="+statusCode+" repsonseStr =" + repsonseStr);
//            }
//        } catch (Exception e) {
//            logger.error("Could not connect to "+path);
//            logger.error(e.getMessage(), e);
//        }
//        //队列不存在的情况
//        if(statusCode==404){
//            logger.error("Critical:Queue query has failed!");
//        }
//        if(statusCode>299){
//            logger.error("Unexpected API error :"+repsonseStr);
//        }
//        return queues;
//    }
//
//    /**
//     * 获取所有队列名称
//     * @return 队列名称数组
//     */
//    public static ArrayList<String> queryAllQueuesName(){
//        ArrayList<String> queues=new ArrayList<String>();
//        List<HashMap> queueArray=queryAllQueuesInfo();
//        if(queueArray!=null && !queueArray.isEmpty()){
//            for (HashMap hashMap : queueArray) {
//                if(hashMap!=null){
//                    String queueName= (String) hashMap.get("name");
//                    queues.add(queueName);
//                }
//            }
//        }
//        return queues;
//    }
//
//    /**
//     * 检查MQ存活状态
//     * @return
//     */
//    public static int checkMQAlive(){
//        HttpResponse response = null;
//        int mqstatus= OK;
//        int statusCode = 0;
//        String path = "/api/aliveness-test/%2F";
//        String repsonseStr = null;
//        try {
//            response = sendGetRequest(path);
//            if(response == null) {
//                logger.error("检查MQ存活状态, response " + response);
//                throw new RuntimeException("检查MQ存活状态, response " + response);
//            }
//            //读取响应内容
//            try(BufferedReader breader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
//            StringBuilder responseString = new StringBuilder();
//            String line = null;
//            while ((line = breader.readLine()) !=null) {
//                responseString.append(line);
//            }
//            breader.close();
//                repsonseStr =responseString.toString();
//            } catch (Exception e) {
//                logger.error("读取响应内容, response " + response, e);
//                throw new RuntimeException("读取响应内容, response " + response);
//            }
//            statusCode = response.getStatusLine().getStatusCode();
//
//            if (statusCode != 200) {
//            	logger.error("statusCode="+statusCode+" repsonseStr =" + repsonseStr);
//            }
//        } catch (Exception e) {
//            mqstatus= CRITICAL;
//            logger.error(e.getMessage(), e);
//        }
//        //响应码大于299要么代表错误，要么就是发送给客户端额外的指令
//        if(statusCode>299){
//            logger.error("Critical:Broker not alive:"+statusCode);
//            mqstatus= CRITICAL;
//        }
//        if(mqstatus== OK){
//            logger.info("OK! Connect to "+path+" successful ");
//        }
//        return mqstatus;
//    }
//}
