package com.base.util2.lang;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanUtils {

	/**
	 * @功能: map转成object
	 * @作者: dingpf
	 * @创建日期: 2016年4月26日 下午12:26:07
	 * @param map
	 * @param clazz
	1）apache的 BeanUtils 拷贝属性时，如果源对象的存在某个属性值为null时，拷贝将会报错，value not specified，这是一个潜在的坑！
	2）apache的 BeanUtils 性能最差，推荐使用 Spring BeanUtils 易用性、兼容性、性能都较好 （虽然CGLIB中BeanCopier性能最好，但是对null等兼容性不是很好）
	3）apache替换为spring的BeanUtils时，注意源对象和目标对象的参数位置是反过来的

	 * @return
	 */
	public static <T> T map2Object(Map<String, Object> map, Class<T> clazz) {
		if (map != null) {
			try {
				T obj = clazz.newInstance();
				BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
				PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
				for (PropertyDescriptor property : propertyDescriptors) {
					Method setter = property.getWriteMethod();
					if (setter != null) {
						setter.invoke(obj, map.get(property.getName()));
					}
				}
				return obj;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @功能: object转成map
	 * @作者: dingpf
	 * @创建日期: 2016年4月26日 下午12:26:30
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> object2Map(Object obj) {
		if (obj != null) {
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
				PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
				for (PropertyDescriptor property : propertyDescriptors) {
					String key = property.getName();
					if (!key.equalsIgnoreCase("class")) {
						Method getter = property.getReadMethod();
						Object value = getter == null ? null : getter.invoke(obj);
						map.put(key, value);
					}
				}
				return map;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(111);
	}
}
