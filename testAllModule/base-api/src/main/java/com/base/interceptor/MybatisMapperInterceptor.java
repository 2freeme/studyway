//package com.base.interceptor;
//
//import com.midea.ccs.core.annotation.DataSourceStrategy;
//import com.midea.ccs.core.annotation.IgnoreFields;
//import com.midea.ccs.core.annotation.VersionControl;
//import com.midea.ccs.core.context.DataSourceContextHolder;
//import com.midea.ccs.core.datasource.ConnectionProxy;
//import com.midea.ccs.core.enums.ConnectionType;
//import com.midea.ccs.core.utils.VersionControlHelper;
//import org.aopalliance.intercept.MethodInterceptor;
//import org.aopalliance.intercept.MethodInvocation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.BeanFactoryAware;
//import org.springframework.dao.OptimisticLockingFailureException;
//import org.springframework.jdbc.datasource.DataSourceUtils;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.support.TransactionSynchronizationManager;
//
//import javax.sql.DataSource;
//import java.lang.annotation.Annotation;
//import java.sql.Connection;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// *
// *
// * 首先配置了DynamicRoutingDataSource方可有效：
// *
// * 1. 当某方法只使用@DataSourceStrategy时，效果等同于不参与当前事务，切换数据源策略，开启长连接执行当前方法，方法结束后切回旧数据源，连接需要手动关闭；
// *
// * 2. 当某方法同时有@DataSourceStrategy和@Transactional, 效果等同于切换数据源策略后才开启事务（主要是为了产生支持长连接的事务），该方法范围同时具有长连接和长事务，事务完成后连接自动关闭；
// *
// * 警告：不要在@DataSourceStrategy的方法范围内使用select for update等锁操作，否则数据库的连接很快就耗尽（长连接释放久）
// *       因为Spring只会在事务关闭时才会去关闭关联的connection，然而在Mapper切换库后的连接并不关联在该事务上，spring是不知道的这个connection存在的，所以要主动关闭连接。
// *
// */
//public class MybatisMapperInterceptor implements MethodInterceptor, BeanFactoryAware {
//
//	private final static Logger logger = LoggerFactory.getLogger(MybatisMapperInterceptor.class.getName());
//
//	private final static ThreadLocal<List<String>> ignoreFields = new ThreadLocal<List<String>>();
//
//	private final static ThreadLocal<Boolean> ignoreAll = new ThreadLocal<Boolean>();
//
//	private final static ThreadLocal<Boolean> forceLowerCase = new ThreadLocal<Boolean>();
//
//	private BeanFactory beanFactory;
//
//
//	/**
//	 * 拦截接口
//	 */
//	@Override
//    public Object invoke(MethodInvocation mi) throws Throwable {
//		ConnectionType oldVal = null; // 当前的数据源策略模式
//		String dataSourceId = null; // 当前的数据源ID
//		ConnectionType newVal = null; // 新数据源策略模式
//		try {
//			if(hasAnnotation(mi, DataSourceStrategy.class)) {
//				DataSourceContextHolder.addCounter();
//
//				DataSourceStrategy anotation = mi.getMethod().getAnnotation(DataSourceStrategy.class);
//				dataSourceId = anotation.dataSourceId();
//				oldVal = DataSourceContextHolder.getConnectionType(dataSourceId);
//
////				newVal = anotation.type();
//				DataSourceContextHolder.setConnectionType(dataSourceId, newVal); // 只会对 DynamicRoutingDataSource 有效果
//				if (!newVal.equals(oldVal)) {
//					logger.debug("设置数据源策略模式：dataSourceId=" + dataSourceId + ", connectionType=" + newVal);
//
//					boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
//					Transactional txAn = mi.getMethod().getAnnotation(Transactional.class);
//					Propagation p = null;
//					if(txAn != null) {
//						p = txAn.propagation();
//					}
//
//					// 切换数据源后（Spring不知道背后的真正数据源已被更改），当前事务是active的，但是事务定义不是require new
//					if (isActive && !Propagation.REQUIRES_NEW.equals(p)) {
//						Class[] clazz = mi.getThis().getClass().getInterfaces();
//
//						String className = "unknown";
//						if(clazz != null && clazz.length == 1) className = clazz[0].getSimpleName();
//
//						String errMsg = "***警告：当前数据源的事务，在切换数据源后已不能传递使用，必须使用Require_New定义新的事务，否则当前方法无事务! ("
//							+ className + "." + mi.getMethod().getName() + ")";
//						System.err.println(errMsg);
//						logger.error(errMsg);
//					}
//				}
//			}
//
//			if (hasAnnotation(mi, IgnoreFields.class)) {
//			    IgnoreFields annotation = mi.getMethod().getAnnotation(IgnoreFields.class);
//			    boolean isIgnoreAll = annotation.ignoreAll();
//			    boolean isForceLowerCase = annotation.forceLowerCase();
//			    forceLowerCase.set(isForceLowerCase);
//
//			    if (isIgnoreAll) { // 忽略所有
//			        ignoreAll.set(isIgnoreAll);
//			        logger.debug("驼峰转换将忽略所有字段：" + isIgnoreAll);
//
//			    } else { // 不是忽略所有
//			        if (annotation.value().length > 0) {
//	                    // 转换为小写
//	                    List<String> tempList = Arrays.asList(annotation.value());
//	                    List<String> newList = new ArrayList<String>();
//	                    for (String val : tempList) {
//	                        newList.add(val.toLowerCase());
//	                    }
//
//	                    ignoreFields.set(newList);
//	                    logger.debug("ignoreFields: " + annotation.value());
//	                }
//			    }
//			}
//
//			if (hasAnnotation(mi, VersionControl.class)) {
//			    if (VersionControlHelper.getVersionControl() == null) { // VersionControlHelper的优先级比@VersionControl要高
//			    	VersionControl annotation = mi.getMethod().getAnnotation(VersionControl.class);
//				    String field = annotation.field();
//				    String column = annotation.column();
//			    	VersionControlHelper.setVersionControl(field, column);
//			    } // 如果VersionControlHelper已经设置，则忽略@VersionControl的设置
//			}
//
//		} catch (Exception ex) {
//			logger.error("Encounter error when intercept mapper method.", ex);
//		}
//
//		try {
//			Object result = mi.proceed();
//
//			if ((hasAnnotation(mi, VersionControl.class) || VersionControlHelper.getVersionControl() != null) && result != null) {
//				boolean zero = false;
//				if (result.getClass() == Integer.class && ((Integer)result).intValue() == 0) {
//					zero = true;
//				} else if (result.getClass() == int.class && (int)result == 0) {
//					zero = true;
//				} else if (result.getClass() == Long.class && ((Long)result).intValue() == 0) {
//					zero = true;
//				} else if (result.getClass() == long.class && (long)result == 0) {
//					zero = true;
//				}
//
//				if (zero) throw new OptimisticLockingFailureException("系统并发更新失败，请刷新重试");
//			}
//
//			return result;
//		} finally {
//
//			if (VersionControlHelper.getVersionControl() != null) {
//				VersionControlHelper.removeVersionControl();
//			}
//
//		    if (hasAnnotation(mi, IgnoreFields.class)) {
//		        if (ignoreFields.get() != null) {
//		            ignoreFields.get().clear(); // 清除引用
//		            ignoreFields.remove(); // 清除threadlocal防止线程池重用
//		        }
//
//		        if (ignoreAll.get() != null) {
//		            ignoreAll.remove(); // 清除threadlocal防止线程池重用
//		        }
//
//		        if (forceLowerCase.get() != null) {
//		            forceLowerCase.remove(); // 清除threadlocal防止线程池重用
//		        }
//		    }
//
//			if(hasAnnotation(mi, DataSourceStrategy.class)) {
//				DataSourceContextHolder.minusCounter();
//
//				if (dataSourceId != null) {
//					if (!newVal.equals(oldVal)) this.closeUnmanagedConnection(dataSourceId); // 如果连接类型不一致时，恢复前先关闭
//					DataSourceContextHolder.setConnectionType(dataSourceId, oldVal); // 恢复当前的数据源策略模式
//					if (!newVal.equals(oldVal)) logger.debug("切回数据源策略模式：dataSourceId=" + dataSourceId + ", connectionType=" + oldVal);
//				}
//
//				// 返回到最外层，则清理threadlocal，因为一开始默认threadlocal没值
//				if (DataSourceContextHolder.getCounter() == 0) {
//					DataSourceContextHolder.removeConnectionType();
//				}
//			}
//		}
//	}
//
//	private boolean hasAnnotation(MethodInvocation mi, Class<? extends Annotation> annotation) {
//		return mi.getMethod().isAnnotationPresent(annotation);
//	}
//
//	/**
//	 *  如果存在这个一个非Spring管理的连接，则主从切换后需要手动关闭连接
//	 *  从事务中获取的连接为Proxy连接，AutoCommit可以判断事务被Spring管理
//	 */
//	private void closeUnmanagedConnection(String dataSourceId) { // 不能抛出异常，否则无法切回去旧的数据源
//		try {
//			//ConnectionType connType = DataSourceContextHolder.getConnectionType(dataSourceId);
//
//			DataSource dataSource = beanFactory.getBean(dataSourceId, DataSource.class);
//			Connection connection = DataSourceUtils.getConnection(dataSource); // 恢复旧数据源之前先关闭当前连接防止连接泄漏(如果当前有事务返回的连接不为空)
//
//			//不能用DataSourceUtils.isConnectionTransactional()判断是否是spring管理的连接，切换长短连接后还是DynamicRoutingDataSource，对应同一个connectionProxy
//			//if (connection != null && !connection.isClosed() && connection.getAutoCommit()) { // 虽然有事务但auto commit为true表示非Spring事务管理
//			if (connection != null) { // 虽然有事务但auto commit为true表示非Spring事务管理
//				if(connection instanceof ConnectionProxy) {
//					((ConnectionProxy) connection).releaseUnmanagedConnection(); // Spring事务管理的都会在事务完成时自动关闭，非Spring管理的需要手动关闭
//				}
//			}
//		} catch (Exception ex) {
//			logger.error("Encounter error when close the cached connection.", ex);
//		}
//	}
//
//	@Override
//	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
//		this.beanFactory = beanFactory;
//	}
//
//	public static List<String> getIgnoreFields() {
//	    return ignoreFields.get();
//	}
//
//	public static Boolean getIgnoreAll() {
//	    return ignoreAll.get();
//	}
//
//    public static Boolean getForceLowerCase() {
//        return forceLowerCase.get();
//    }
//
//}
