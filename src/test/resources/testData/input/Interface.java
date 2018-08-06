public interface Interface {

    int method1();

    String method2();

    default void method3() {
        method1();
        method4();
    }

    default void method4() {
        System.out.println("Hello World");
    }

}
