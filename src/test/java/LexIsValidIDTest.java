import org.testng.Assert;
import org.testng.annotations.Test;

public class LexIsValidIDTest {

    @Test
    public void testValidIdentifierForNullCharacter(){
        boolean flag = Lex.isValidID('\0');
        Assert.assertFalse(flag);
    }

    @Test
    public void testValidIdentifierForCapitalA(){
        boolean flag = Lex.isValidID('A');
        Assert.assertTrue(flag);
    }

    @Test
    public void testValidIdentifierForCapitalZ(){
        boolean flag = Lex.isValidID('Z');
        Assert.assertTrue(flag);
    }

    @Test
    public void testValidIdentifierForSmallA(){
        boolean flag = Lex.isValidID('a');
        Assert.assertTrue(flag);
    }

    @Test
    public void testValidIdentifierForSmallZ(){
        boolean flag = Lex.isValidID('z');
        Assert.assertTrue(flag);
    }

    @Test
    public void testValidIdentifierForNumber0(){
        boolean flag = Lex.isValidID('0');
        Assert.assertTrue(flag);
    }

    @Test
    public void testValidIdentifierForNumber9(){
        boolean flag = Lex.isValidID('9');
        Assert.assertTrue(flag);
    }

    @Test
    public void testValidIdentifierForUnderscore(){
        boolean flag = Lex.isValidID('_');
        Assert.assertTrue(flag);
    }

    @Test
    public void testValidIdentifierForHashSign(){
        boolean flag = Lex.isValidID('#');
        Assert.assertFalse(flag);
    }

}
