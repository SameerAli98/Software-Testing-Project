import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ParserEmitTest {

    @BeforeTest
    void init(){
        Parser.openFiles();
    }

    @Test
    void test1_DefaultLineNumberNTest(){
        int n = Parser.n;
        Assert.assertEquals(n, 1);
    }

    @Test
    void test2_DefaultCodeValueTest(){
        String code = Parser.code;
        Assert.assertEquals(code, "");
    }

    @Test
    void test3_FileWriteTest(){
        Parser.code = "Test";
        Parser.emit();
        String tac = FileIOHelper.readFromFile("tac.txt");
        Assert.assertEquals(tac, "Test\n");
    }

    @Test
    void test4_LineNumberNTestAfterWritingOnce(){
        int n = Parser.n;
        Assert.assertEquals(n, 2);
    }

    @Test
    void test5_DefaultCodeValueTestAfterWriting(){
        String code = Parser.code;
        Assert.assertEquals(code, "");
    }

    @Test
    void test6_LineNumberNTestAfterWritingTwice(){
        Parser.emit();
        int n = Parser.n;
        Assert.assertEquals(n, 3);
    }

}
