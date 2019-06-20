import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.testFramework.UsefulTestCase;
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

    private void testCaretPositionAfterRearrangeMethods(String className, int lineBefore, int columnBefore, int lineAfter, int columnAfter) {
        lineBefore--;
        columnBefore--;
        lineAfter--;
        columnAfter--;
        myFixture.configureByFile("input/" + className + ".java");
        LogicalPosition caretPositionBefore = new LogicalPosition(lineBefore, columnBefore);
        int caretOffsetBefore = myFixture.getEditor().logicalPositionToOffset(caretPositionBefore);
        myFixture.getEditor().getCaretModel().moveToOffset(caretOffsetBefore);
        myFixture.performEditorAction("RearrangeMethods");
        LogicalPosition expectedCaretPositionAfter = new LogicalPosition(lineAfter, columnAfter);
        LogicalPosition actualCaretPositionAfter = myFixture.getEditor().getCaretModel().getLogicalPosition();

        String errorMessage =
                "(line,column) of caret is wrong, expected is : (" + (expectedCaretPositionAfter.line + 1) + "," + (expectedCaretPositionAfter.column + 1) + ")" +
                " but actual is : (" + (actualCaretPositionAfter.line + 1) + "," + (actualCaretPositionAfter.column + 1) + ")";

        UsefulTestCase.assertEquals(errorMessage, expectedCaretPositionAfter, actualCaretPositionAfter);
//        int expectedCaretOffsetAfter = myFixture.getEditor().logicalPositionToOffset(expectedCaretPositionAfter);
//        int actualCaretOffsetAfter = myFixture.getEditor().getCaretModel().getOffset();
//        UsefulTestCase.assertEquals(expectedCaretOffsetAfter, actualCaretOffsetAfter);
        //myFixture.checkResultByFile("output/" + className + ".java");
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

    public void testClassWithPlentyMethods() {
        testRearrangeMethods("ClassWithPlentyMethods");
    }

    public void testCaretPosition_BetweenMethods1() {
        testCaretPositionAfterRearrangeMethods("OneSimpleClass", 12, 14, 27, 5);
    }

    public void testCaretPosition_BetweenMethods2() {
        testCaretPositionAfterRearrangeMethods("OneSimpleClassWithComments", 51, 1, 51, 5);
    }

    public void testCaretPosition_BetweenMethods3() {
        testCaretPositionAfterRearrangeMethods("OneSimpleClassWithComments", 42, 18, 41, 5);

    }

    public void testCaretPosition_OnMethodSignature() {
        testCaretPositionAfterRearrangeMethods("OneSimpleClassWithComments", 72, 14, 56, 5);
    }

    public void testCaretPosition_OnMethodSignature_InParameters() {
        testCaretPositionAfterRearrangeMethods("OneSimpleClass", 16, 37, 12, 5);
    }

    public void testCaretPosition_InsideMethod() {
        testCaretPositionAfterRearrangeMethods("ClassWithPlentyMethods", 35, 20, 32, 20);
    }

    public void testCaretPosition_OnField() {
        testCaretPositionAfterRearrangeMethods("OneSimpleClassWithComments", 35, 10, 32, 5);
    }

    public void testCaretPosition_BeforeField() {
        testCaretPositionAfterRearrangeMethods("OneSimpleClassWithComments", 27, 1, 28, 5);
    }

    public void testCaretPosition_OnImport() {
        testCaretPositionAfterRearrangeMethods("OneSimpleClassWithComments", 20, 13, 20, 1);
    }

    public void testCaretPosition_OnPackage() {
        testCaretPositionAfterRearrangeMethods("OneSimpleClassWithComments", 17, 6, 17, 1);
    }

    public void testCaretPosition_BetweenFieldAndMethod() {
        testCaretPositionAfterRearrangeMethods("OneSimpleClassWithComments", 40, 1, 41, 5);
    }

    public void testCaretPosition_OnClassStatement() {
        testCaretPositionAfterRearrangeMethods("OneSimpleClassWithComments", 26, 32, 22, 1);
    }

    public void testCaretPosition_AfterClassStatement() {
        testCaretPositionAfterRearrangeMethods("OneSimpleClassWithComments", 82, 2, 82, 2);
    }

    public void testCaretPosition_ClassWithNoMethod() {
        testCaretPositionAfterRearrangeMethods("ClassWithNoMethod", 4, 1, 3, 1);
    }

}
