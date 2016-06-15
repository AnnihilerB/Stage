import org.junit.Assert;
import org.junit.Test;

public class TestClass {

    @Test
    public void addTest(){
        Barcode tst = new Barcode();
        int result = tst.add(5,5);
        Assert.assertEquals(10,result);



   }
}




