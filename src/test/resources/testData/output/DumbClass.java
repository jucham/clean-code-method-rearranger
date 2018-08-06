package whatever;

public class MyClass {

    public static long someLong;
    protected int someInt;
    private String someString;

    void method1() {
        method2();
        method3();
    }

    void method2() {
        int num = 42;
        method4("test", 123, num, new Object(), new long[]{1, 2, 3});
    }

    void method4(String str, int i, int j, Object obj, long[] longArray) {

    }

    void method3() {

    }

}