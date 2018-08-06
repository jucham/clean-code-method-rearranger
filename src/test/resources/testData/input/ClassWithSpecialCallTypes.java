package test;

import java.util.function.Consumer;

public class ClassWithSpecialCallTypes {

    private void method7(Consumer<Integer> consumer) {
        consumer.accept(42);
    }

    private int method6() {
        return 42;
    }

    /**
     * method call as method call parameter
     */
    private int method2() {
        return method5(method6());
    }

    /**
     *
     * method call as lambda expression of method call parameter
     */
    private void method4(Integer x) {
        method7(y -> method8(y));
    }

    private String method10() {
        return "3";
    }

    private void method8(Integer x) {
        System.out.println(42);
    }

    /**
     * method call as rvalue
     */
    public void method1() {
        int i = method2();
    }


    private int method5(int i) {
        return 42;
    }

    /**
     * method call as lambda expression of rvalue
     */
    public void method3() {
        Consumer<Integer> consumer = (x) -> method4(x);
        consumer.accept(42);
    }

    /**
     * method call as value in array initialization
     */
    public void method9() {
        String[] array = {"1", "2", method10()};
    }

}