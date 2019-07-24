public class ClassWithMultipleUsageOfACommonMethod1 {

    private void A1(){
        B();
    }

    private void A2(){
        B();
    }

    private void B(){
        C1();
        C2();
    }

    private void C1(){}

    private void C2(){}

}