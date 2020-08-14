package com.base.myutils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author： Dingpengfei
 * @Description：
 *  * 我们一般的时候就会去使用 spring的beantuils对象，
 *      复制对象：如果对象的属性，不一致的时候，不会去复制，对象的属性不存在，也不会复制
 *      当我们拷贝的对象中是引用类型的话，这样的拷贝就是浅拷贝
 *          *（指的就是在类中拷贝的属性是引用对象的话，拷贝出的新的类中的属性也只是一个引用的地址，
 *              当这个属性改变的时候，就会影响到新的拷贝的对象）
 *           *在实际的应用中，我们还是避免浅拷贝的情况，因为你也不知道别人是怎么修改的。
 *      不支持  map对象--->对象  的拷贝
 * apach的 拷贝是支持 map对象--->对象 拷贝的
 *         用作 map --> 对象的时候，是可以强行转化的， 多的不复制 少的话就不覆盖，使用原来的。
 *         对象 ---> map   转化不了。
 * @Date： 2020-8-14 16:19
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {
    /**
     * @param map
     * @param clazz 1）apache的 BeanUtils 拷贝属性时，如果源对象的存在某个属性值为null时，拷贝将会报错，value not specified，这是一个潜在的坑！
     *              2）apache的 BeanUtils 性能最差，推荐使用 Spring BeanUtils 易用性、兼容性、性能都较好 （虽然CGLIB中BeanCopier性能最好，但是对null等兼容性不是很好）
     *              3）apache替换为spring的BeanUtils时，注意源对象和目标对象的参数位置是反过来的
     * @return
     * @功能: map转成object
     * @作者: dingpf
     * @创建日期: 2016年4月26日 下午12:26:07
     */
    public static <T> T map2Object(Map<String, Object> map, Class<T> clazz) throws Exception {
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
    }

    /**
     * @param obj
     * @return
     * @功能: object转成map
     * @作者: dingpf
     * @创建日期: 2016年4月26日 下午12:26:30
     */
    public static Map<String, Object> object2Map(Object obj) throws Exception {

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
    }


    /**
     * 这里使用 apach的  beanutils 注意调换位置
     * 因为spring的方法中没有提供 MapToObject 的方法，所以使用apach的工具类。
     *
     * @param source
     * @param target
     */
    public static void copyMapToObject(Map source, Object target) throws Exception {
        org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);
    }
}
