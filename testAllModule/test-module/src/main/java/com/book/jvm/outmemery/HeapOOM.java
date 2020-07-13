package com.book.jvm.outmemery;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author： Dingpengfei
 * @Description：堆溢出的情况
 * VM Args：-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * --  最小值20m 最大值 20m     后面的参数的意思就是虚拟机出现内存溢出异常的时候，dump当前的内存堆转储快照以便事后分析
 * java.lang.OutOfMemoryError: Java heap space
        Dumping heap to java_pid10348.hprof ...
        Heap dump file created [28221671 bytes in 0.182 secs]

 *设置了上面的参数，如果不设置的话，会一致的去增加内存，
 * @Date： 2020-7-2 14:41
 */
public class HeapOOM {
    static class OOMObject {
    }
    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();
        while (true) {
            list.add(new OOMObject());
        }
    }
}
