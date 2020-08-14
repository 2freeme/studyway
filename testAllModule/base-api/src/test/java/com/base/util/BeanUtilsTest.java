package com.base.util;

import com.alibaba.fastjson.JSON;
import com.base.util.pojo.*;
import com.base.util3.util.DeepCopyUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author： Dingpengfei
 * @Description：apach的beanUtils中如果对象为null的话，就不会抛异常
 * 我们一般的时候就会去使用 spring的beantuils对象，
 *      复制对象：如果对象的属性，不一致的时候，不会去复制，对象的属性不存在，也不会复制
 *      当我们拷贝的对象中是引用类型的话，这样的拷贝就是浅拷贝
 *          *（指的就是在类中拷贝的属性是引用对象的话，拷贝出的新的类中的属性也只是一个引用的地址，
 *              当这个属性改变的时候，就会影响到新的拷贝的对象）
 *           *在实际的应用中，我们还是避免浅拷贝的情况，因为你也不知道别人是怎么修改的。
 *      不支持  map对象--->对象  的拷贝
 * apach的 拷贝是支持 map对象--->对象 拷贝的
 *         用作 map --> 对象的时候，是可以强行转化的， 多的不复制 少的话就不覆盖，使用原来的。
 *         对象 ---> map   转化不了。
 * @Date： 2020-8-14 11:38
 */
public class BeanUtilsTest {
    public static HashMap<String, Object> objectObjectHashMap = new HashMap<>();
    public static Student3 student3forceshia  = new Student3(32, "name32", "32", 32);
    public static Student2 student2HalfNull = new Student2(2, null, 2);




    @Test
    public void tsetMyUtil() throws Exception {
        com.base.myutils.BeanUtils.copyMapToObject(objectObjectHashMap, student3forceshia);
    }
    @Test
    public void tsetMyUtil2() throws Exception {
        DealStudent2 dealStudent2forCopy = new DealStudent2(student2HalfNull, "bb");

        com.base.myutils.BeanUtils.copyProperties(objectObjectHashMap, student3forceshia);
    }

    @Test
    public void tsetMyUtilDeep() throws Exception {

    }



        //初始化对象
    @Before
    public void init (){
        objectObjectHashMap.put("age", "a");  //在强行转化的过程中，发现转化不了的话，就直接设置默认的值 0
        objectObjectHashMap.put("name", "555");
        objectObjectHashMap.put("test",null );
        objectObjectHashMap.put("grade","555" );
        objectObjectHashMap.put("ceshi","555ceshi" );
        System.out.println("Before  student3forceshia :" +student3forceshia.toString());
        System.out.println("Before  objectObjectHashMap :" +JSON.toJSONString(objectObjectHashMap));

    }


    @Test
    /**
     * 测试的是apach的类
     */
    public void testApach() throws InvocationTargetException, IllegalAccessException {

        //尝试map
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("age", "a");  //在强行转化的过程中，发现转化不了的话，就直接设置默认的值 0
        objectObjectHashMap.put("name", "555");
        objectObjectHashMap.put("test",null );
        objectObjectHashMap.put("grade","555" );
        objectObjectHashMap.put("ceshi","555ceshi" );

        Student3 student3forceshi  = new Student3(3, "name3", "3", 3);
        BeanUtils.copyProperties(student3forceshi,objectObjectHashMap); //source 指的是原来的资源对象  target 指的是复制到的目标的对象
        System.out.println(student3forceshi.toString()); //Student3(age=0, name=555, test=null, grade=555)
        System.out.println(objectObjectHashMap.entrySet().iterator().next().getValue());


        //测试 对象转map
        Student3 student3forceshi2  = new Student3(32, "name32", "32", 32);
        HashMap<String, Object> objectHashMap = new HashMap<>();
        objectHashMap.put("name","11" );
        BeanUtils.copyProperties(objectHashMap,student3forceshi2); //source 指的是原来的资源对象  target 指的是复制到的目标的对象
        System.out.println(student3forceshi2.toString());
        System.out.println(JSON.toJSONString(objectHashMap));// {"name":1}



/*
        Student1 student1Null = new Student1();
        Student2 student2Null = new Student2();
        Student1 student1HalfNull = new Student1(1, null, null);
        Student2 student2HalfNull = new Student2(1, null, 1);
        //空的复制
        BeanUtils.copyProperties(student2Null, student1Null); //直接报异常
        System.out.println(student2Null.toString());
        //半空的复制
        BeanUtils.copyProperties(student2HalfNull, student1HalfNull);
        System.out.println(student2HalfNull.toString());

*/
    }

    @Test
    /**
     * 测试的是spring的类
     */
    public void springTest() throws Exception {

        Student1 student1Null = new Student1();
        Student2 student2Null = new Student2();
        Student1 student1HalfNull = new Student1(1, null, "1");
        Student2 student2HalfNull = new Student2(2, null, 2);
        //空的复制
        org.springframework.beans.BeanUtils.copyProperties(student1Null, student2Null);
        System.out.println(student1Null.toString());  //Student1(age=0, name=null, test=null)
        System.out.println(student2Null.toString());  //Student2(age=0, name=null, test=0)
        //半空的复制
        org.springframework.beans.BeanUtils.copyProperties(student1HalfNull, student2HalfNull);
        System.out.println(student1HalfNull.toString()); //Student1(age=1, name=null, test=1)
        System.out.println(student2HalfNull.toString()); //Student2(age=1, name=null, test=2)

        DealStudent dealStudent = new DealStudent(student1HalfNull, "aa");
        DealStudent2 dealStudent2 = new DealStudent2(student2HalfNull, "bb");
        org.springframework.beans.BeanUtils.copyProperties(dealStudent, dealStudent2);
        System.out.println(dealStudent.toString()); //DealStudent(student1=Student1(age=1, name=null, test=1), name=aa)
        System.out.println(dealStudent2.toString()); //DealStudent2(student2=Student2(age=1, name=null, test=2), name=aa)

        //这里测试浅拷贝还是深拷贝  发现是浅拷贝
        DealStudent2 dealStudent2forCopy = new DealStudent2(student2HalfNull, "bb");
        DealStudent2 dealStudent2forCopy2 = new DealStudent2();
       // org.springframework.beans.BeanUtils.copyProperties(dealStudent2forCopy, dealStudent2forCopy2);
        Object o = DeepCopyUtil.depthClone(dealStudent2forCopy);
        System.out.println(JSON.toJSONString(o));
        //测试深度拷贝
        com.base.myutils.BeanUtils.depthClone(dealStudent2forCopy, dealStudent2forCopy2);

        System.out.println("copy before : " + dealStudent2forCopy.toString()); //DealStudent2(student2=Student2(age=1, name=null, test=2), name=bb)
        System.out.println("copy before : " + dealStudent2forCopy2.toString()); //DealStudent2(student2=Student2(age=1, name=null, test=2), name=bb)
        dealStudent2.getStudent2().setName("ceshibbxiugai");
        System.out.println("copy after: "+  dealStudent2forCopy.toString()); //DealStudent2(student2=Student2(age=1, name=ceshibbxiugai, test=2), name=bb)
        System.out.println("copy after: "+ dealStudent2forCopy2.toString()); //DealStudent2(student2=Student2(age=1, name=ceshibbxiugai, test=2), name=bb)

        //这里就可以发现，spring的复制其实就是能复制就复制，类型不同的就不复制了

        //测试多余的字段的复制
        Student3 student3 = new Student3(3, "name3", "3", 3);
        Student2 student2test = new Student2(22, "name22", 22);

     //   org.springframework.beans.BeanUtils.copyProperties(student3, student2test); //source 指的是原来的资源对象  target 指的是复制到的目标的对象
     //   System.out.println(student3.toString()); //Student3(age=3, name=name3, test=3, grade=3)
     //   System.out.println(student2test.toString()); //Student2(age=3, name=name3, test=22)

        org.springframework.beans.BeanUtils.copyProperties(student2test,student3); //source 指的是原来的资源对象  target 指的是复制到的目标的对象
        System.out.println(student3.toString()); //Student3(age=22, name=name22, test=3, grade=3)
        System.out.println(student2test.toString()); //Student2(age=22, name=name22, test=22)

        //尝试map
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("age", "555");
        objectObjectHashMap.put("name", "555");
        objectObjectHashMap.put("test",null );
        objectObjectHashMap.put("grade","555" );
        objectObjectHashMap.put("ceshi","555ceshi" );
        Student3 student3forceshi  = new Student3(3, "name3", "3", 3);
        org.springframework.beans.BeanUtils.copyProperties(objectObjectHashMap,student3forceshi); //source 指的是原来的资源对象  target 指的是复制到的目标的对象
        System.out.println(student3forceshi.toString()); //Student3(age=22, name=name22, test=3, grade=3)
        System.out.println(objectObjectHashMap.entrySet().iterator().next().getValue());

    }

    @Test
    /**
     * 测试的是util2中的 类
     */
    public void testOtherCopy (){
        Student3 student3forceshi  = new Student3(3, "name3", "3", 3);

        Map<String, Object> stringObjectMap = com.base.util2.lang.BeanUtils.object2Map(student3forceshi);
        System.out.println( "stringObjectMap : " + JSON.toJSONString(stringObjectMap));

        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("age", "555");
        objectObjectHashMap.put("name", "555");
        objectObjectHashMap.put("test",null );
        objectObjectHashMap.put("grade","555" );
        objectObjectHashMap.put("ceshi","555ceshi" );

        Student3 student3 = com.base.util2.lang.BeanUtils.map2Object(objectObjectHashMap, Student3.class);
        //这里发现数据类型并不匹配，然后就报错
        // System.out.println(student3.toString());

        HashMap<String, Object> objectObjectHashMap4 = new HashMap<>();
        objectObjectHashMap4.put("age", 555);
        objectObjectHashMap4.put("name", "555");
        Student3 student4 = com.base.util2.lang.BeanUtils.map2Object(objectObjectHashMap4, Student3.class);
        //大致发现的就是，是以转化的object为准。进行转化，如果map中没有的话就会报错
        //System.out.println(student4.toString());


        HashMap<String, Object> objectObjectHashMap5= new HashMap<>();
        objectObjectHashMap5.put("age", 555);
        objectObjectHashMap5.put("name", "555");
        objectObjectHashMap5.put("test", "555");
        objectObjectHashMap.put("test2", "555");
        Student1 student5 = com.base.util2.lang.BeanUtils.map2Object(objectObjectHashMap5, Student1.class);
        //这里发现map即使多了也没关系，自动转化的
        System.out.println("student5 :" +student5.toString());
    }


    @After
    public void print(){
        System.out.println("After student3forceshia :" +student3forceshia.toString());
        System.out.println("After objectObjectHashMap :" +JSON.toJSONString(objectObjectHashMap));

    }

}
