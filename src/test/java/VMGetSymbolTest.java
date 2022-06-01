import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

public class VMGetSymbolTest {

    @Test
    void test1_sTableDefaultValueTest(){
        int sTableSize = VM.symbolTable.size();
        Assert.assertEquals(sTableSize, 0);
    }

    @Test
    void test2_ParsingFromSymbolTableTest(){
        VM.symbolTable = Arrays.asList(
                Arrays.asList("var1", "var2", "var3"),
                Arrays.asList("INT", "CHAR", "INT"),
                Arrays.asList("0", "4", "5")
        );

        String[] symbol = VM.getSymbol("var1");
        String[] expected = {"var1","INT","0"};
        Assert.assertEquals(symbol, expected);
    }
}
