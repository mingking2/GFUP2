package com.example.gfup2.util;

import java.util.Random;

public class MakeRandomNum {
    public static String makeRandomNum(){
        Random random = new Random();
        int randomNum = random.nextInt(900000) + 100000;

        return String.valueOf(randomNum);
    }

}