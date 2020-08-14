package com.base.mybatis.plugins;


import com.base.network.ApplicationException;
import com.base.util.VersionControlHelper;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * <p>Optimistic Lock Plugin</p>
 * @author zhongnh
 */
@Intercepts({
	//@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class}),
	@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }),
	@Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class})
})
public class OptimisticLockInterceptor implements Interceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(OptimisticLockInterceptor.class.getName());
		
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object intercept(Invocation invocation) throws Exception {
		VersionControlHelper.Version version = VersionControlHelper.getVersionControl();
		String interceptMethod = invocation.getMethod().getName();
		
		if("prepare".equals(interceptMethod)) {
						
			if(version == null || version.getColumn() == null) {
				return invocation.proceed();
			}
			
//			ParameterHandler handler = (ParameterHandler) this.processTarget(invocation.getTarget());
//			MetaObject mo = SystemMetaObject.forObject(handler);
//			
//			BoundSql boundSql = (BoundSql) mo.getValue("boundSql");
//			Object parameterObject = boundSql.getParameterObject();
//			
//			Configuration configuration = ((MappedStatement) mo.getValue("mappedStatement")).getConfiguration();
//			MetaObject pm = configuration.newMetaObject(parameterObject);
//	        Object value = pm.getValue(version.getField());
//	        
//	        if (value == null && !version.isForceCheck()) {
//	        	return invocation.proceed();
//	        }
			
			// 在where条件中自动添加 and version = ?			
			StatementHandler routingHandler = (StatementHandler) this.processTarget(invocation.getTarget());
			MetaObject routingMeta = SystemMetaObject.forObject(routingHandler);
			MetaObject hm = routingMeta.metaObjectForProperty("delegate");
						
			String originalSql = (String) hm.getValue("boundSql.sql");
			StringBuilder builder = new StringBuilder(originalSql);
			builder.append(" AND ").append(version.getColumn()).append(" = ?");
			hm.setValue("boundSql.sql", builder.toString());
			
		} else if("setParameters".equals(interceptMethod)) {
				
			if(version == null || version.getField() == null) {
				return invocation.proceed();
			}

			String versionField = version.getField();
			
			ParameterHandler handler = (ParameterHandler) this.processTarget(invocation.getTarget());
			MetaObject hm = SystemMetaObject.forObject(handler);
			BoundSql boundSql = (BoundSql) hm.getValue("boundSql");
			Object parameterObject = boundSql.getParameterObject();
			if(parameterObject instanceof MapperMethod.ParamMap<?>) {
				MapperMethod.ParamMap<?> paramMap = (MapperMethod.ParamMap<?>) parameterObject;
				if(!paramMap.containsKey(versionField)) {
					throw new TypeException("参数 '" + versionField + "' 不能缺失, 或可以使用 @Param注解传入参数");
				}
			}
			
			Configuration configuration = ((MappedStatement) hm.getValue("mappedStatement")).getConfiguration();
			MetaObject pm = configuration.newMetaObject(parameterObject);
	        Object value = pm.getValue(versionField);
	        
	        if (value == null) {
	        	throw new ApplicationException("版本控制：参数" + versionField + "的值不能为空");
	        } else {
	        	
	        	JdbcType jdbcType = configuration.getJdbcTypeForNull();
		        if(value.getClass() == Long.class || value.getClass() == long.class || value.getClass() == Integer.class || value.getClass() == int.class || value.getClass() == BigDecimal.class) {	 			
		        	jdbcType = JdbcType.BIGINT;
		 		} else if (value.getClass() == Timestamp.class || value.getClass() == Date.class) {
		 			jdbcType = JdbcType.TIMESTAMP;
		 		}
		        
		        // where ... and version = ? 对where条件最后一个参数设置#{versionField}对应的值
		        ParameterMapping versionMapping = new ParameterMapping.Builder(configuration, versionField, value.getClass()).jdbcType(jdbcType).build();
		        if (!hasParameterMapping(boundSql.getParameterMappings(), versionMapping)) { // double-lock双锁机制
		        	synchronized (this.getClass()) { // 防止多线程并发问题
			        	if (!hasParameterMapping(boundSql.getParameterMappings(), versionMapping)) {
				        	boundSql.getParameterMappings().add(versionMapping);
				        }
			        }
		        }	        
		        
		        boundSql.setAdditionalParameter(versionField, value);
		        
	 			
		 		// 如果是数字型版本号，修改变量#{versionField}自动加一, update set version = #{versionField}
		 		if(value.getClass() == Long.class || value.getClass() == long.class) {	 			
			 		pm.setValue(versionField, (long) value + 1);
		 		} else if(value.getClass() == Integer.class || value.getClass() == int.class) {
		 			pm.setValue(versionField, (int) value + 1);
		 		} else if (value.getClass() == Date.class || value.getClass() == Timestamp.class) {
		 			// 如果需要传入时间戳型版本号，则默认初始化为当前时间，使#{versionField}为当前时间
		 			// 如果不需要#{versionField}则忽略以下，直接使用systimestamp
		 			pm.setValue(versionField, new Timestamp(System.currentTimeMillis())); 
		 		}
	        	
	        }
	 		
		}
		
		return invocation.proceed();
	}
	
	// 校验是否已经参数匹配
	private boolean hasParameterMapping(List<ParameterMapping> list, ParameterMapping pm) {
		if (list != null) {
			for(ParameterMapping p: list) {
				if (p.getProperty().equals(pm.getProperty())) {
					return true;
				}
			}
		}
		return false;
	}
	
	// 获取ParameterHandler或StatementHandler
	private Object processTarget(Object target) {
		if(Proxy.isProxyClass(target.getClass())) {
			MetaObject mo = SystemMetaObject.forObject(target);
			return processTarget(mo.getValue("h.target"));
		}
		
		// must keep the result object is StatementHandler or ParameterHandler in Optimistic Loker plugin
		if(!(target instanceof StatementHandler) && !(target instanceof ParameterHandler)) {
			logger.error("插件初始化失败.");
			throw new ApplicationException("插件初始化失败.");
		}
		return target;
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler || target instanceof ParameterHandler)
            return Plugin.wrap(target, this);
		return target;
	}
	
	@Override
    public void setProperties(Properties properties) {
    }
}