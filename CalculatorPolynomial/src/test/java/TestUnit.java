import org.example.businessLogic.Operations;
import org.example.dataModels.Polynomial;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class TestUnit {

    @Test
    public void testAddition() {
        Polynomial p1 = new Polynomial();
        Polynomial p2 = new Polynomial();
        Polynomial p3 = new Polynomial(); //answer
        try {
            p1.addElement(5, 4);
            p1.addElement(4, -3);
            p1.addElement(2, 1);
            p1.addElement(1, -8);
            p1.addElement(0, 1);

            p2.addElement(4, 3);
            p2.addElement(3, -1);
            p2.addElement(2, 1);
            p2.addElement(1, 2);
            p2.addElement(0, -1);

            p3.addElement(5, 4);
            p3.addElement(3, -1);
            p3.addElement(2, 2);
            p3.addElement(1, -6);

            assertEquals(p3.getFormat(), Operations.addition(p1, p2).getFormat());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testSubtraction(){
        Polynomial p1 = new Polynomial();
        Polynomial p2 = new Polynomial();
        Polynomial p3 = new Polynomial(); //answer
        try {
            p1.addElement(5, 4);
            p1.addElement(4, -3);
            p1.addElement(2, 1);
            p1.addElement(1, -8);
            p1.addElement(0, 1);

            p2.addElement(4, 3);
            p2.addElement(3, -1);
            p2.addElement(2, 1);
            p2.addElement(1, 2);
            p2.addElement(0, -1);

            p3.addElement(5, 4);
            p3.addElement(4, -6);
            p3.addElement(3, 1);
            p3.addElement(1, -10);
            p3.addElement(0, 2);

            assertEquals(p3.getFormat(), Operations.subtraction(p1, p2).getFormat());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void testMultiplication(){
        Polynomial p3 = new Polynomial();
        Polynomial p4 = new Polynomial();
        Polynomial result = new Polynomial();
        try {
            p3.addElement(2, 3);
            p3.addElement(1, -1);
            p3.addElement(0, 1);

            p4.addElement(1, 1);
            p4.addElement(0, -2);

            result.addElement(3, 3);
            result.addElement(2, -7);
            result.addElement(1, 3);
            result.addElement(0, -2);
            assertEquals(result.getFormat(), Operations.multiplication(p3, p4).getFormat());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void testDerivation(){
        Polynomial p5 = new Polynomial();
        Polynomial result = new Polynomial();
        try{
            p5.addElement(3, 1);
            p5.addElement(2, -2);
            p5.addElement(1, 6);
            p5.addElement(0, -5);

            result.addElement(2, 3);
            result.addElement(1, -4);
            result.addElement(0, 6);

            assertEquals(result.getFormat(), Operations.derivate(p5).getFormat());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testIntegration(){
        Polynomial p6 = new Polynomial();
        Polynomial result = new Polynomial();
        try{
            p6.addElement(3, 1);
            p6.addElement(2, 4);
            p6.addElement(0, 5);

            result.addElement(4, 1/4f);
            result.addElement(3, 4/3f);
            result.addElement(1, 5f);

            assertEquals(result.getFormat(), Operations.integration(p6).getFormat());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void testDivide(){
        Polynomial p1 = new Polynomial();
        Polynomial p2 = new Polynomial();

        Polynomial quotinet = new Polynomial();
        Polynomial remainder = new Polynomial();

        try {
            p1.addElement(3, 1);
            p1.addElement(2, -2);
            p1.addElement(1, 6);
            p1.addElement(0, -5);

            p2.addElement(2, 1);
            p2.addElement(0, -1);
            Polynomial[] answer = Operations.divide(p1, p2);

            quotinet.addElement(1, 1);
            quotinet.addElement(0, -2);

            remainder.addElement(1, 7);
            remainder.addElement(0, -7);

            assertEquals(quotinet.getFormat(), answer[0].getFormat());
            assertEquals(remainder.getFormat(), answer[1].getFormat());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
