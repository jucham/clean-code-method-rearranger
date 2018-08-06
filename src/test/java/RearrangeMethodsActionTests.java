import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;

public class RearrangeMethodsActionTests extends LightPlatformCodeInsightFixtureTestCase {

    @Override
    protected String getTestDataPath() {
        return "src/test/resources/testData";
    }

    private void testRearrangeMethods(String className) {
        myFixture.configureByFile("input/" + className + ".java");
        myFixture.performEditorAction("RearrangeMethods");
        myFixture.checkResultByFile("output/" + className + ".java");
    }

    //-------------------------------------------------------------------------

    public void testDumbClass() {
        testRearrangeMethods("DumbClass");
    }

    public void testOneSimpleClass() {
        testRearrangeMethods("OneSimpleClass");
    }

    public void testOneSimpleClassWithComments() {
        testRearrangeMethods("OneSimpleClassWithComments");
    }

    public void testClassWithSpecialCallTypes() {
        testRearrangeMethods("ClassWithSpecialCallTypes");
    }

    public void testMethodWithGeneric() {
        testRearrangeMethods("ClassWithGeneric");
    }

    public void testNoCalleeMethods() {
        testRearrangeMethods("ClassWithNoCallee");
    }

    public void testOneSimpleClassAlreadyRearranged() {
        testRearrangeMethods("OneSimpleClassAlreadyRearranged");
    }

    public void testClassWithNoMethod() {
        testRearrangeMethods("ClassWithNoMethod");
    }

    public void testClassWithNoCodeAtAll() {
        testRearrangeMethods("ClassWithNoCodeAtAll");
    }

    public void testClassWithRecursiveMethod() {
        testRearrangeMethods("ClassWithRecursiveMethod");
    }

    public void testSeveralClassesInFile() {
        testRearrangeMethods("SeveralClassesInFile");
    }

    public void testClassWithInnerClasses() {
        testRearrangeMethods("ClassWithInnerClasses");
    }

    public void testAbstractClass() {
        testRearrangeMethods("AbstractClass");
    }

    public void testInterface() {
        testRearrangeMethods("Interface");
    }

    public void testClassWithMixedFieldsAndMethods() {
        testRearrangeMethods("ClassWithMixedFieldsAndMethods");
    }

}
