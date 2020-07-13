package com.test.module.transien.transienttes.entity;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-7-2 8:36
 */
public class Test2 {

    public static void main(String[] args) {
        Test2 test2 = new Test2();
        int a = 0;

        test2.testA(a);
        System.out.println(a);

        Integer b = new Integer(0);
        test2.testB(b);
        System.out.println(b);

        IntergerA intergerA = new IntergerA(new Integer(888));

        test2.testB(intergerA.getA());
        System.out.println(intergerA.getA());

        test2.testC(intergerA);
        System.out.println(intergerA.getA());
    }

    public void testA(int c) {
        c = 22;
    }

    public void testB(Integer c) {
        c = new Integer(99);
    }

    public void testC(IntergerA intergerA) {
        intergerA.setA(new Integer(777));
    }
}
