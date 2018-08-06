package test;

public class ClassWithGeneric<T,U,V> {

    private void method2(V data2) {
        System.out.println(data2);
    }

    public void doSomething(T data) {

        U data1 = null;
        method1(data1);
        V data2 = null;
        method2(data2);
    }

    private void method1(U data) {
        System.out.println(data);
    }
}