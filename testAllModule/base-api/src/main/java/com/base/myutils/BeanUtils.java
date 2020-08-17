package com.base.myutils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author： Dingpengfei
 * @Description： * 我们一般的时候就会去使用 spring的beantuils对象，
 * 复制对象：如果对象的属性，不一致的时候，不会去复制，对象的属性不存在，也不会复制
 * 当我们拷贝的对象中是引用类型的话，这样的拷贝就是浅拷贝
 * *（指的就是在类中拷贝的属性是引用对象的话，拷贝出的新的类中的属性也只是一个引用的地址，
 * 当这个属性改变的时候，就会影响到新的拷贝的对象）
 * *在实际的应用中，我们还是避免浅拷贝的情况，因为你也不知道别人是怎么修改的。
 * 不支持  map对象--->对象  的拷贝
 * apach的 拷贝是支持 map对象--->对象 拷贝的
 * 用作 map --> 对象的时候，是可以强行转化的， 多的不复制 少的话就不覆盖，使用原来的。
 * 对象 ---> map   转化不了。
 * @Date： 2020-8-14 16:19
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    /**
     * 这个方法因为效率不高，而且对于 类型不同的时候会报错。所以使用apach的比较好
     * @param map
     * @param clazz 1）apache的 BeanUtils 拷贝属性时，如果源对象的存在某个属性值为null时，拷贝将会报错，value not specified，这是一个潜在的坑！
     *              2）apache的 BeanUtils 性能最差，推荐使用 Spring BeanUtils 易用性、兼容性、性能都较好 （虽然CGLIB中BeanCopier性能最好，但是对null等兼容性不是很好）
     *              3）apache替换为spring的BeanUtils时，注意源对象和目标对象的参数位置是反过来的
     * @return
     * @功能: map转成object
     * @作者: dingpf
     * @创建日期: 2016年4月26日 下午12:26:07
     */
/*    public static <T> T map2Object(Map<String, Object> map, Class<T> clazz) throws Exception {
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
    }*/


    /**
     * 返回的就是拷贝之后的对象
     *
     * @param source 需要拷贝的对象
     * @param clazz
     * @param <T>    泛型
     * @return
     * @throws Exception
     */
    public static <T> T depthClone(Object source, Class<T> clazz) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(out);
        oo.writeObject(source);
        ByteArrayInputStream in = new ByteArrayInputStream(
                out.toByteArray());
        ObjectInputStream oi = new ObjectInputStream(in);
        return (T) oi.readObject();
    }


    /**
     * 多个对象的深拷贝，srcObj对应的需实现java.io.Serializable接口
     *
     * @param list obj
     * @return new list obj
     */
    public static <T> List<T> listDepthClone(List<T> list) throws Exception {
        List<T> newList = new ArrayList<>();
        for (T item : list) {
            if (item == null) {
                continue;
            }
            T val = depthClone(item, (Class<T>) item.getClass());
            if (val != null) {
                newList.add((T) val);
            }
        }
        return newList;
    }


    /**
     * @param obj
     * @return
     * @功能: object转成map
     * @作者: dingpf
     * @创建日期: 2016年4月26日 下午12:26:30
     */
    public static Map<String, Object> object2Map(Object obj) throws Exception {

        Map<String, Object> map = new HashMap<>();
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
     * 注意：apach提供的方法中只有   MapToObject 的方法，并未有  ObjectToMap的方法
     * 在查看源码的过程中，发现其实我们的复制的过程，
     *      1、首先去判断 source 是什么类型 javabean or map  or DynaBean（自己的类型）
     *      2、不同的类型有不同取值的方式， map的话就将值挨个的key  value取出来。 javabean的话 就挨个取出字段名+值
     *      3、拿着 值去找 target的类中的属性的 set 或者 is +字段名的方法。无则报错 ，然后被catch（这里未兼容map）
     *      4、因为有转化失败报错catch的机制 ，所以属性是可以强行转化的， 多的不复制 少的话就不覆盖，使用原来的
     *      5、null值的话就容易有问题  因为map转的时候，会去判断是否能写入（3）  报错catch 。 但是null就有问题
     * @param source
     * @param target
     */
    public static void copyMapToObject(Map source, Object target) throws Exception {
        org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);
    }



}
