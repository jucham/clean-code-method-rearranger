/*
Copyright 2018 Julien Champalbert

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

 */
package test;

import java.math.BigDecimal;
import java.util.StringTokenizer;

/**
 * @author julien
 * This is a class full of comments
 */
public class OneSimpleClassWithComments {

    /**
     * some long
     */
    private static long someLong;
    /**
     * some int
     */
    private int someInt;
    /**
     * some string
     */
    private String someString;

    /**
     * this is method1
     */
    public void method1() {
        StringTokenizer st = new StringTokenizer("");
        BigDecimal bd = BigDecimal.ZERO;
        method2();
        method3();
    }


    // comment to clarify a private method
    private void method2() {
        method4();
    }

    // ----------------------------------------------
    // multi-line comment to clarify a private method
    // ----------------------------------------------
    private void method5() {

    }

    /* comment to clarify a private method */
    private void method6() {

    }

    /**
     * this is method4
     */
    public void method4() {
        System.out.println(someInt);
    }

    /**
     * this is method3
     */
    public void method3() {
        System.out.println(someLong);
    }
}