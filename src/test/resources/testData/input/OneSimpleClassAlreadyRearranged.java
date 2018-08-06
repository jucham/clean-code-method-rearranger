package com.jucham;

import java.math.BigDecimal;
import java.util.StringTokenizer;

public class OneSimpleClass {

    private static long someLong;
    private int someInt;
    private String someString;

    public void method1() {
        StringTokenizer st = new StringTokenizer("");
        BigDecimal bd = BigDecimal.ZERO;
        method2();
        method3();
    }

    private void method2() {
        method4();
    }

    public void method4() {
        System.out.println(someInt);
    }

    public void method3() {
        System.out.println(someLong);
    }
}