package com.example.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import jdk.nashorn.internal.parser.JSONParser;

public class TestConsumer {
    public static void main(String[] args){
        List<Person> lisiList = new ArrayList<Person>();
        Consumer<Person> consumer  = x -> {
            if (x.name.equals("lisi")){
                lisiList.add(x);
            }
        };


//        consumer  = x -> {
//            if (x.name.equals("lisi")){
//                lisiList.add(x);
//            }
//        };

        consumer = consumer.andThen(
                x -> lisiList.removeIf(y -> y.age < 23)
        );

        Stream.of(
                new Person(21,"zhangsan"),
                new Person(22,"lisi"),
                new Person(23,"wangwu"),
                new Person(24,"wangwu"),
                new Person(23,"lisi"),
                new Person(26,"lisi"),
                new Person(26,"zhangsan")
        ).forEach(consumer);

//        lisiList.forEach(System.out::println);
        lisiList.forEach(x ->{System.out.println(x);});

        for (Person list :lisiList){
//            System.out.println(list);
        }

    }
}
