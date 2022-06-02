import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class ParserAddToSTTest {

    @BeforeTest
    void init(){
        Parser.openFiles();
    }

    @Test
    void test1_DefaultAdrValueTest(){
        int adr = Parser.adr;
        Assert.assertEquals(adr, 0);
    }

    @Test
    void test2_SymbolTableTestForINT(){
        Parser.addToST("dummy", "INT");
        int sTableSize = Parser.sTable.size();
        int stNameSize = Parser.sTable.get(0).size();
        int stTypeSize = Parser.sTable.get(0).size();
        int stAddressSize = Parser.sTable.get(0).size();
        Assert.assertEquals(sTableSize, 3);
        Assert.assertEquals(stNameSize, 1);
        Assert.assertEquals(stTypeSize, 1);
        Assert.assertEquals(stAddressSize, 1);
    }

    @Test
    void test3_AdrValueTestAfterINT(){
        int adr = Parser.adr;
        Assert.assertEquals(adr, 4);
    }

    @Test
    void test4_SymbolTableTestForCHAR(){
        Parser.addToST("dummy2", "CHAR");
        int sTableSize = Parser.sTable.size();
        int stNameSize = Parser.sTable.get(0).size();
        int stTypeSize = Parser.sTable.get(0).size();
        int stAddressSize = Parser.sTable.get(0).size();
        Assert.assertEquals(sTableSize, 3);
        Assert.assertEquals(stNameSize, 2);
        Assert.assertEquals(stTypeSize, 2);
        Assert.assertEquals(stAddressSize, 2);
    }

    @Test
    void test5_AdrValueTestAfterINTAndCHAR(){
        int adr = Parser.adr;
        Assert.assertEquals(adr, 5);
    }

    @Test
    void test6_SymbolTableValuesTestAfterINTAndCHAR(){
        List<String> names = Parser.sTable.get(0);
        List<String> types = Parser.sTable.get(1);
        List<String> addresses = Parser.sTable.get(2);
        Assert.assertEquals(names, Arrays.asList("dummy", "dummy2"));
        Assert.assertEquals(types, Arrays.asList("INT", "CHAR"));
        Assert.assertEquals(addresses, Arrays.asList("0", "4"));
    }

}
