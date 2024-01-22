package com.example.gfup2.util;

//import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


@Deprecated
public class GetRandomNum {
    public static String getRandomNum(){
//        Random random = new Random();
//        int randomNum = random.nextInt(900000) + 100000;
//        return String.valueOf(randomNum);
        return String.valueOf(ThreadLocalRandom.current().nextInt(100000,900000));
    }
}

/*
*
* getRandomNum을 호출할때마다 Random 객체를 생성하는 것은 오버헤드가 큼.
* ThreadLocalRandom의 스태틱 메소드를 사용하여, 스레드에서 독립적인 랜덤값을 생성하면서 오버헤드를 줄일 수 있음
*
* */