public interface Interface {

    String method2();

    default void method3() {
        method1();
        method4();
    }

    int method1();

    default void method4() {
        System.out.println("Hello World");
    }

}
