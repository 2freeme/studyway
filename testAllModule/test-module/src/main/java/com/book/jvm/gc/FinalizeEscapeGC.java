package com.book.jvm.gc;

/**
 * @Author： Dingpengfei
 * @Description：测试逃脱gc的代码 不建议使用
 * @Date： 2020-7-6 17:31
 */
public class FinalizeEscapeGC {

    public static FinalizeEscapeGC SAVE_HOOK = null;

    public void isAlive(){
        System.out.println("yes,i am still alive");
    }
    protected  void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed");
        FinalizeEscapeGC.SAVE_HOOK =this;

    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGC();
        //对象第一次成功拯救自己
        SAVE_HOOK =null;
        System.gc();
        //因为 finalize方法的优先级很低，所以先暂停0.5s 等待
        Thread.sleep(500);
        if (SAVE_HOOK!=null){
            SAVE_HOOK.isAlive();
        }else{
            System.out.println("no, i am dead ");
        }

        //下一段代码和上面的代码一样，但是这个自救失败
        SAVE_HOOK =null;
        System.gc();
        //因为 finalize方法的优先级很低，所以先暂停0.5s 等待
        Thread.sleep(500);
        if (SAVE_HOOK!=null){
            SAVE_HOOK.isAlive();
        }else{
            System.out.println("no, i am dead ");
        }
    }
}
