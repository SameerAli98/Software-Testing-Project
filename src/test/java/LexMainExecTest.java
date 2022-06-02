import org.testng.Assert;
import org.testng.annotations.Test;

public class LexMainExecTest {

    @Test
    public void EmptyCodeFile() {
        FileIOHelper.writeToFile("code.txt", "");
        Lex.mainExec(null);
        String s = FileIOHelper.readFromFile("output.txt");
        Assert.assertEquals(s, "");
    }

    @Test
    public void TokenForInt() {
        FileIOHelper.writeToFile("code.txt", "int");
        Lex.mainExec(null);
        String s = FileIOHelper.readFromFile("output.txt");
        Assert.assertEquals(s, "(INT, ^)\n");
    }

    @Test
    public void TokenLexemesForVariable() {
        FileIOHelper.writeToFile("code.txt", "var");
        Lex.mainExec(null);
        String s = FileIOHelper.readFromFile("output.txt");
        Assert.assertEquals(s, "(ID, var)\n");
    }
}
