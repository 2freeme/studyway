package com.book.jvm.outmemery;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author： Dingpengfei
 * @Description：用于查看java方法区的OOM
 * VM Args：-XX:PermSize=10M -XX:MaxPermSize=10M
 * 借助CGLib使得方法区出现内存溢出异常
 * 结果：即使加上了参数也没办法模拟出内存溢出的场景
 * 理由：在java8 之后永久代便完全退出了历史舞台，元空间作为其替代者登场。在默认设置下，前面
        列举的那些正常的动态创建新类型的测试用例已经很难再迫使虚拟机产生方法区的溢出异常了
 * @Date： 2020-7-2 16:37
 */
public class JavaMethodAreaOOM {
    public static void main(String[] args) {
        while (true){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invokeSuper(objects,args );
                }
            });
        }
    }
    static class OOMObject{}
}
