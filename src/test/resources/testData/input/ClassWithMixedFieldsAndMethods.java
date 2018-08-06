import java.math.BigDecimal;
import java.util.StringTokenizer;

public class ClassWithMixedFieldsAndMethods {

    /**
     * this is method1
     */
    public void method1() {
        StringTokenizer st = new StringTokenizer("");
        BigDecimal bd = BigDecimal.ZERO;
        method2();
        method3();
    }

    // comment to clarify a private method
    private void method2() {
        method4();
    }

    // ----------------------------------------------
    // multi-line comment to clarify a private method
    // ----------------------------------------------
    private void method5() {

    }

    /* comment to clarify a private method */
    private void method6() {

    }

    /**
     * some string
     */
    private String someString;

    /**
     * some int
     */
    private int someInt;

    /**
     * this is method4
     */
    public void method4() {
        System.out.println(someInt);
    }

    /**
     * some long
     */
    private static long someLong;

    /**
     * this is method3
     */
    public void method3() {
        System.out.println(someLong);
    }

}
