package test;

import java.math.BigDecimal;
import java.util.StringTokenizer;

public class ClassWithNestedClasses {

    private static long someLong;
    private int someInt;
    private String someString;

    private void method2() {
        method4();
    }

    private class NestedClass3 {

    }

    private class NestedClass1 {

        private int someInt;
        private String someString;

        private void method1() {

        }

        private class NestedNestedClass1 {

            private int i;

            public int get() {
                return this.i;
            }

            public void method1() {
                int j = get();
            }
        }

        public void nestedMethod() {
            method1();
            method2();
        }

        private void method2() {

        }
    }

    public void method1() {
        StringTokenizer st = new StringTokenizer("");
        BigDecimal bd = BigDecimal.ZERO;
        method2();
        method3();
        NestedClass1 nc1 =  new NestedClass1();
        nc1.nestedMethod();
    }

    public void method3() {
        System.out.println(someLong);
    }

    private class NestedClass2 {

        private int someInt;
        private String someString;

    }

    public void method4() {
        System.out.println(someInt);
    }

}