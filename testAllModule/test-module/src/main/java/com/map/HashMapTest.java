package com.map;

import java.util.HashMap;

/**
 * @Author： Dingpengfei
 * @Description：首先设置一个合理的初始化容量可以提高HashMap的性能
 *        在当我们对HashMap初始化没设置初始化容量时，系统会默认创建一个容量为16的大小的集合。
 *        若我们的所需的集合很小则会造成内存浪费，而当HashMap的容量值超过了临界值（threshold)时HashMap将会重新扩容的下一个2的指数幂（16->32）。
 *        HashMap扩容将会重新创建hash表降低性能。
 *
 * 如何设置一个合理的初始化容量
 *        当我们使用HashMap(int initialCapacity)来初始化容量的时候，jdk会默认帮我们计算一个相对合理的值当做初始容量。
 *        当HashMap的容量值超过了临界值（threshold)时就会扩容，threshold = HashMap的容量值*0.75，比如初始化容量为8的HashMap当大小达到8*0.75=6时将会扩容到16
 *        。当我们设置HashMap的初始化容量是遵循expectedSize /0.75+1，
 *        比如expectedSize是6时 6/0.75+1=9，此时jdk处理后会被设置成16，大大降低了HashMap被扩容的几率。
 *        即使是指定的大小，超过了也是会被扩容的。
 *        当我们通过HashMap(int initialCapacity)设置初始容量的时候，HashMap并不一定会直接采用我们传入的数值，而是经过计算，得到一个新值，目的是提高hash的效率。(1->2、3->4、7->8、9->16)
 *       HashMap会选择大于初始化值的第一个2的幂作为容量。不然会限制了散列的范围。
 *       HashMap 之所以速度快，因为它使用的是散列表，根据 key 的 hashcode 值生成数组下标（通过内存地址直接查找，没有任何判断），
 *       时间复杂度完美情况下可以达到 n1（和数组相同，但是比数组用着爽多了，但是需要多出很多内存，相当于以空间换时间）。
 * lisit的扩容
 *       ArrayList的策略是 newCapacity = oldCapacity + (oldCapacity >> 1) ，每次扩容1.5倍。并且ArrayList的默认容量是10，
 * 扩容的机制
 *       HashMap进行扩容时候还有一系列操作（例如，newTab[e.hash & (newCap - 1)] = e……等）；相比较而言，ArrayList的扩容更为“单纯”（elementData[size++] = e）。
 *       HashMap是重新建立的，而list的扩容是直接加上去的。
 *
 *
 *       测试的话是看不到性能的差异
 * @Date： 2020-9-18 9:18
 */
public class HashMapTest {
    public static void main(String[] args) {


        long l1 = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            HashMap<Object, Object> objectObjectHashMap = new HashMap<>(32);
            objectObjectHashMap.put("1","1");
            objectObjectHashMap.put("2","1");
            objectObjectHashMap.put("3","1");
            objectObjectHashMap.put("4","1");
            objectObjectHashMap.put("5","1");
            objectObjectHashMap.put("6","1");
            objectObjectHashMap.put("7","1");
            objectObjectHashMap.put("8","1");
            objectObjectHashMap.put("9","1");
            objectObjectHashMap.put("10","1");
            objectObjectHashMap.put("11","1");
            objectObjectHashMap.put("12","1");
            objectObjectHashMap.put("13","1");

        }
        System.out.println(System.currentTimeMillis()-l1);// 543

        long l = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("1","1");
            objectObjectHashMap.put("2","1");
            objectObjectHashMap.put("3","1");
            objectObjectHashMap.put("4","1");
            objectObjectHashMap.put("5","1");
            objectObjectHashMap.put("6","1");
            objectObjectHashMap.put("7","1");
            objectObjectHashMap.put("8","1");
            objectObjectHashMap.put("9","1");
            objectObjectHashMap.put("10","1");
            objectObjectHashMap.put("11","1");
            objectObjectHashMap.put("12","1");
            objectObjectHashMap.put("13","1");

        }
        System.out.println(System.currentTimeMillis()-l); //885


    }

}
