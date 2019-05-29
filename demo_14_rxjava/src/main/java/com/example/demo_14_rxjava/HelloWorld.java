package com.example.demo_14_rxjava;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
//import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import sun.rmi.runtime.Log;

public class HelloWorld {
    public static void main(String[] args) {

//        测试1
//// 函数式接口 变量名 = 类实例::方法名
//        Flowable.just("Hello world").subscribe(System.out::println);
//        Observable.just("Hello world1").subscribe(new PrintUtil()::addString);
//        Observable.just("Hello world1").subscribe(new PrintUtil()::printTest);

//      测试2
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                System.out.println("flatMap : apply : " + integer);
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list);//.delay(delayTime, TimeUnit.MILLISECONDS);
            }
        })//.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
//                        Log.e(TAG, "flatMap : accept : " + s + "\n");
//                        mRxOperatorsText.append("flatMap : accept : " + s + "\n");
                        System.out.println("flatMap : accept : " + s);

                    }
                });




    }


}



