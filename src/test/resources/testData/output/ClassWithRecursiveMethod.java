package test;

public class ClassWithRecursiveMethod {

    public void method1() {
        method2();
        f(10);
    }

    private void method2() {
    }

    private void f(int i) {
        if(i == 0)
            return;
        f(i-1);
    }

}