import org.testng.Assert;
import org.testng.annotations.Test;

public class ParserGetTokenTest {

    @Test
    void test1_DefaultTokenValueTest(){
        String token = Parser.token;
        Assert.assertNull(token);
    }

    @Test
    void test2_DefaultLexemeValueTest(){
        String lexeme = Parser.lexeme;
        Assert.assertNull(lexeme);
    }

    @Test
    void test3_ParseTestForINT(){
        Parser.line = "(INT, ^)";
        Parser.getToken();
        String token = Parser.token;
        String lexeme = Parser.lexeme;
        Assert.assertEquals(token, "INT");
        Assert.assertEquals(lexeme, "^");
    }

    @Test
    void test4_ParseTestForVariable(){
        Parser.line = "(ID, dummy)";
        Parser.getToken();
        String token = Parser.token;
        String lexeme = Parser.lexeme;
        Assert.assertEquals(token, "ID");
        Assert.assertEquals(lexeme, "dummy");
    }

    @Test
    void test4_ParseTestForEmptyLine(){
        Parser.line = null;
        Parser.getToken();
        String token = Parser.token;
        String lexeme = Parser.lexeme;
        Assert.assertNull(token);
        Assert.assertNull(lexeme);
    }
}
