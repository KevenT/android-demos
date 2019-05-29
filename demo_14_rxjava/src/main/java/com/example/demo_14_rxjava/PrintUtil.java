package com.example.demo_14_rxjava;

import java.io.PrintStream;
import java.util.function.Consumer;

public class PrintUtil {

    /**
     * 对要遍历的元素添加add操作
     */
    public void addString(String x) {
        System.out.println(x + "   add");
    }

    public void printTest(String x){
        /**
         * 我初次见到的写法
         */
        Consumer<String> fun = System.out::println;
        fun.accept(x);

        System.out.println("========================================");

        /**
         * 现在想想其实很简单，查看println方法的源码得知println是PrintStream类中的一个非静态方法
         * 因此按照方法引用的逻辑，它肯定可以使用
         * “函数式接口 变量名 = 类实例::方法名” 的方式对该方法进行引用
         *
         * 而System.out的作用肯定就是来获取PrintStream类的一个类实例,
         *
         * 验证代码如下：
         */
        PrintStream out = System.out;
        Consumer<String> fun2 = out::println;
        fun2.accept(x);
    }
}
