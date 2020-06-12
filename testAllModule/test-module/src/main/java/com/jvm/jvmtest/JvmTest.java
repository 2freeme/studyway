package com.jvm.jvmtest;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @Author： Dingpengfei
 * @Description： 用作试探jvm的引用类型
 * @Date： 2020-6-12 16:22
 */
public class JvmTest {
    public static void main(String[] args) {

    }

    /**
     * 强引用 ，当这个线程不结束的时候就不会回收这个类
     */
    public static void StrongReference(){
        Object object = new Object();
        Object[] objArr = new Object[1000];

    }

    /**
     * 　软引用是用来描述一些有用但并不是必需的对象，在Java中用java.lang.ref.SoftReference类来表示。
     * 对于软引用关联着的对象，只有在内存不足的时候JVM才会回收该对象。因此，这一点可以很好地用来解决OOM的问题，
     * 并且这个特性很适合用来实现缓存：比如网页缓存、图片缓存等。
     *软引用可以和一个引用队列（ReferenceQueue）联合使用，如果软引用所引用的对象被JVM回收，这个软引用就会被加入到与之关联的引用队列中
     */
    public static void SoftReference(){
        SoftReference<String> sr = new SoftReference<String>(new String("hello"));
        System.out.println(sr.get());
    }


    /**
     * 弱引用也是用来描述非必需对象的，当JVM进行垃圾回收时，无论内存是否充足，都会回收被弱引用关联的对象。
     * 在java中，用java.lang.ref.WeakReference类来表示。
     */
    public static void WeakReference(){
        WeakReference<String> sr = new WeakReference<String>(new String("hello"));

        System.out.println(sr.get());
        System.gc();                //通知JVM的gc进行垃圾回收
        System.out.println(sr.get());
    }

    /**
     * 　虚引用和前面的软引用、弱引用不同，它并不影响对象的生命周期。在java中用java.lang.ref.PhantomReference类表示。
     * 如果一个对象与虚引用关联，则跟没有引用与之关联一样，在任何时候都可能被垃圾回收器回收。
     *
     *　要注意的是，虚引用必须和引用队列关联使用，当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，就会把这个虚引用加入到与之 关联的引用队列中。
     *  程序可以通过判断引用队列中是否已经加入了虚引用，来了解被引用的对象是否将要被垃圾回收。如果程序发现某个虚引用已经被加入到引用队列，
     *  那么就可以在所引用的对象的内存被回收之前采取必要的行动。
     */
    public static void PhantomReference(){
        ReferenceQueue<String> queue = new ReferenceQueue<String>();
        PhantomReference<String> pr = new PhantomReference<String>(new String("hello"), queue);
        System.out.println(pr.get());
    }


    }
