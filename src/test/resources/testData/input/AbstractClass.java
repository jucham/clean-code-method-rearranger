public abstract class AbstractClass {

    private int someInt;

    public abstract int method3();

    public void method1() {
        method2();
        this.someInt = method3();
    }

    public abstract void method4();

    public void method2() {
        method4();
    }

}
