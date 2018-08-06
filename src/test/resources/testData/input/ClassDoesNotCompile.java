public class ClassDoesNotCompile {

    private static long someLong;
    private int someInt;
    private String someString;

    public void method3() {
        System.out.println(someLong);
    }

    public void method1() {
        StringTokenizer st = new StringTokenizer("");
        BigDecimal bd = BigDecimal.ZERO;
        method2();
        method3();
    }

    private void method2() {
        method42();
    }

    public void method4() {
        System.out.println(someInt);
    }
}
