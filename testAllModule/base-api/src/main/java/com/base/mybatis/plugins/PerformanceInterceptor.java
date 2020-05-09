package com.base.mybatis.plugins;

import com.midea.ccs.core.common.LogPrefix;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * MyBatis 性能拦截器，用于输出每条 SQL 语句及其执行时间
 *
 * @author zhongnh
 * @since 1.0.0
 */
@Intercepts({ // 所有update/insert/delete都是update
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class PerformanceInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(LogPrefix.SQL + PerformanceInterceptor.class.getName());
    
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    private static final String ORACLE_DATE_FORMAT = "yyyy-MM-dd hh:mi:ss";
    
    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    
    private static final String ORACLE_TIMESTAMP_FORMAT = "yyyy-MM-dd hh:mi:ss.ff3";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameterObject = null;
        if (invocation.getArgs().length > 1) {
            parameterObject = invocation.getArgs()[1];
        }

        String statementId = mappedStatement.getId();
        BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
        Configuration configuration = mappedStatement.getConfiguration();
        String sql = getSql(boundSql, parameterObject, configuration);

        long start = System.currentTimeMillis();

        Object result = invocation.proceed();

        long end = System.currentTimeMillis();
        long timing = end - start;
        logger.debug("耗时：" + timing + " ms" + " - id:" + statementId + " - Sql:" + sql);
        return result;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
    }

    private String getSql(BoundSql boundSql, Object parameterObject, Configuration configuration) {
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        if (parameterMappings != null) {
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else {
                        MetaObject metaObject = configuration.newMetaObject(parameterObject);
                        value = metaObject.getValue(propertyName);
                    }
                    sql = replacePlaceholder(sql, value);
                }
            }
        }
        return sql;
    }

    private String replacePlaceholder(String sql, Object propertyValue) {
        String result;
        if (propertyValue != null) {
            if (propertyValue instanceof String) {
                result = "'" + propertyValue + "'";
            } else if (propertyValue instanceof Timestamp) { 
            	result = "to_timestamp('" + new SimpleDateFormat(TIMESTAMP_FORMAT).format(propertyValue) + "','" + ORACLE_TIMESTAMP_FORMAT + "')";
            } else if (propertyValue instanceof Date) {
            	result = "to_date('" + new SimpleDateFormat(DATE_FORMAT).format(propertyValue) + "','" + ORACLE_DATE_FORMAT + "')";
            } else {
                result = propertyValue.toString();
            }
        } else {
            result = "null";
        }
        //替换传入的数据有$符，防止报错
        result = result.replaceAll("\\$", "CCS_DOLLAR");
        sql = sql.replaceFirst("\\?", result);
        return sql.replaceAll("CCS_DOLLAR", "\\$");
    }
}
