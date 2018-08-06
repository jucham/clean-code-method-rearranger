package test;

public class ClassWithRecursiveMethod {

    private void f(int i) {
        if(i == 0)
            return;
        f(i-1);
    }

    private void method2() {
    }

    public void method1() {
        method2();
        f(10);
    }
}