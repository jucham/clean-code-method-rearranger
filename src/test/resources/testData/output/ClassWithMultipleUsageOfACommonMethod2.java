package test.something;

public class ClassWithMultipleUsageOfACommonMethod2 {
    private Object1 object1;
    private boolean isSomething;
    private Object2 object2;
    private StringBuffer sb;
    private Object3 object3;

    private MyClass(Object1 object1) {
        this.object1 = object1;
        object2 = object1.getObject2();
        object3 = object2.getObject3();
        sb = new StringBuffer();
    }

    public static String m1(Object1 object1) throws Exception {
        return m1(object1, false);
    }

    public static String m1(Object1 object1, boolean isSomething) throws Exception {
        return new MyClass(object1).m1(isSomething);
    }

    private String m1(boolean isSomething) throws Exception {
        this.isSomething = isSomething;
        if (m2())
            m3();
        return object1.m1();
    }

    private boolean m2() throws Exception {
        return object1.m2("str");
    }

    private void m3() throws Exception {
        m4();
        m5();
        m6();
        m7();
    }

    private void m4() throws Exception {
        if (isSomething)
            m8();
        m9();
    }

    private void m8() throws Exception {
        m10("str1", "str2");
    }

    private void m9() throws Exception {
        m10("str3", "str4");
    }

    private void m5() throws Exception {
        sb.append(object1.m3());
    }

    private void m6() throws Exception {
        m11();
        if (isSomething)
            m12();
    }

    private void m11() throws Exception {
        m10("str5", "str6");
    }

    private void m12() throws Exception {
        m10("str7", "str8");
    }

    private void m10(String str1, String str2) throws Exception {
        Object2 object2 = m13(str1);
        if (object2 != null) {
            String str3 = m14(object2);
            m15(str3, str2);
        }
    }

    private Object2 m13(String str) throws Exception {
        return Object4.m1(str, object2);
    }

    private String m14(Object2 object2) throws Exception {
        Object5 object5 = object3.m1(object2);
        return Object6.m1(object5);
    }

    private void m15(String str1, String str2) {
        sb.append(str2).append(str1);
    }

    private void m7() throws Exception {
        object1.m4(sb.toString());
    }

}