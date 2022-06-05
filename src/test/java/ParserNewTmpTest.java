import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ParserNewTmpTest {

    @BeforeTest
    void init(){
        Parser.openFiles();
    }

    @Test
    void test1_VariableTDefaultValueTest() {
        int t = Parser.t;
        Assert.assertEquals(t, 1);
    }

    @Test
    void test2_FirstTempVariableTest() {
        String name = Parser.newTmp();
        Assert.assertEquals(name, "t1");
    }

    @Test
    void test3_SecondTempVariableTest() {
        String name = Parser.newTmp();
        Assert.assertEquals(name, "t2");
    }

    @Test
    void test4_VariableTValueTestAfter3Calls() {
        Parser.newTmp();
        int t = Parser.t;
        Assert.assertEquals(t, 4);
    }

}
