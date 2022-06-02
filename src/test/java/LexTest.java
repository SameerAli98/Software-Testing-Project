import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class LexTest {

    @Test
    void test1(){
        String code = """
                asdlkjasdlkjasdlkjasdlkjasdlkjasdlkjasdlkjasdlkj""";

        FileIOHelper.writeToFile("code2.txt", code);

        String[] args = {"code2.txt"};
        Lex.mainExec(args);

        String output = FileIOHelper.readFromFile("output.txt");

        String expected = """
                (ID, asdlkjasdlkjasdlkjasdlkjasdlkjasdlkjasdlkjasdlkj)
                """;

        Assert.assertEquals(output, expected);
    }

    @Test
    void test2(){
        String code = """
                \\\\This is a comment to test comment token""";

        FileIOHelper.writeToFile("code2.txt", code);

        String[] args = {"code2.txt"};
        Lex.mainExec(args);

        String output = FileIOHelper.readFromFile("output.txt");

        String expected = """
                (SLC, ^)
                """;

        Assert.assertEquals(output, expected);
    }

    @Test
    void test3(){
        String code = """
                /**/""";

        FileIOHelper.writeToFile("code2.txt", code);

        String[] args = {"code2.txt"};
        Lex.mainExec(args);

        String output = FileIOHelper.readFromFile("output.txt");

        String expected = """
                (MLC, ^)
                """;

        Assert.assertEquals(output, expected);
    }

    @Test
    void test4(){
        String code = """
                /*This is a multiline comment that can contain
                newlines and can span to multiple lines*/""";

        FileIOHelper.writeToFile("code2.txt", code);

        String[] args = {"code2.txt"};
        Lex.mainExec(args);

        String output = FileIOHelper.readFromFile("output.txt");

        String expected = """
                (MLC, ^)
                """;

        Assert.assertEquals(output, expected);
    }

    @Test
    void test5(){
        String code = """
                -456""";

        FileIOHelper.writeToFile("code2.txt", code);

        String[] args = {"code2.txt"};
        Lex.mainExec(args);

        String output = FileIOHelper.readFromFile("output.txt");

        String expected = """
                (SUB, ^)
                (NC, 456)
                """;

        Assert.assertEquals(output, expected);
    }

    @Test
    void test6(){
        String code = """
                "This is a string\"""";

        FileIOHelper.writeToFile("code2.txt", code);

        String[] args = {"code2.txt"};
        Lex.mainExec(args);

        String output = FileIOHelper.readFromFile("output.txt");

        String expected = """
                (STR, "This is a string")
                """;

        Assert.assertEquals(output, expected);
    }

    @Test
    void test7(){
        String code = """
                while""";

        FileIOHelper.writeToFile("code2.txt", code);

        String[] args = {"code2.txt"};
        Lex.mainExec(args);

        String output = FileIOHelper.readFromFile("output.txt");

        String expected = """
                (WHILE, ^)
                """;

        Assert.assertEquals(output, expected);
    }

    @Test
    void test8(){
        String code = """
                if""";

        FileIOHelper.writeToFile("code2.txt", code);

        String[] args = {"code2.txt"};
        Lex.mainExec(args);

        String output = FileIOHelper.readFromFile("output.txt");

        String expected = """
                (IF, ^)
                """;

        Assert.assertEquals(output, expected);
    }

    @Test
    void test9(){
        String code = """
                ++""";

        FileIOHelper.writeToFile("code2.txt", code);

        String[] args = {"code2.txt"};
        Lex.mainExec(args);

        String output = FileIOHelper.readFromFile("output.txt");

        String expected = """
                (INC, ^)
                """;

        Assert.assertEquals(output, expected);
    }

    @Test
    void test10(){
        String code = """
                
                &""";

        FileIOHelper.writeToFile("code2.txt", code);

        String[] args = {"code2.txt"};
        Lex.mainExec(args);

        String output = FileIOHelper.readFromFile("output.txt");

        String expected = """
                Unknown Symbol encountered at line number: 2
                """;

        Assert.assertEquals(output, expected);
    }

    @Test
    void test11(){
        String code = """
                /*Comment without closing tag""";

        FileIOHelper.writeToFile("code2.txt", code);

        String[] args = {"code2.txt"};
        Lex.mainExec(args);

        String output = FileIOHelper.readFromFile("output.txt");

        String expected = """
                Missing comment end tag at line number: 1
                """;

        Assert.assertEquals(output, expected);
    }

    @Test
    void test12(){
        String code = """
                'a""";

        FileIOHelper.writeToFile("code2.txt", code);

        String[] args = {"code2.txt"};
        Lex.mainExec(args);

        String output = FileIOHelper.readFromFile("output.txt");

        String expected = """
                Unclosed character literal at line number: 1
                """;

        Assert.assertEquals(output, expected);
    }

    @Test
    void test13(){
        String code = """
                '\1'""";

        FileIOHelper.writeToFile("code2.txt", code);

        String[] args = {"code2.txt"};
        Lex.mainExec(args);

        String output = FileIOHelper.readFromFile("output.txt");

        String expected = """
                Character literal can only have an ASCII value at line number: 1
                """;

        Assert.assertEquals(output, expected);
    }

    @Test
    void test14(){
        String code = """
                "String without closing quote""";

        FileIOHelper.writeToFile("code2.txt", code);

        String[] args = {"code2.txt"};
        Lex.mainExec(args);

        String output = FileIOHelper.readFromFile("output.txt");

        String expected = """
                Missing string end tag at line number: 1
                """;

        Assert.assertEquals(output, expected);
    }

    @Test
    void test15(){
        String code = """
                /*Comment without closing tag slash*""";

        FileIOHelper.writeToFile("code2.txt", code);

        String[] args = {"code2.txt"};
        Lex.mainExec(args);

        String output = FileIOHelper.readFromFile("output.txt");

        String expected = """
                Missing comment end tag at line number: 1
                """;

        Assert.assertEquals(output, expected);
    }

    @Test
    void test16(){
        String code = """
                ez
                %
                """;

        FileIOHelper.writeToFile("code2.txt", code);

        String[] arg = {"code2.txt"};
        Lex.mainExec(arg);

        String output = FileIOHelper.readFromFile("output.txt");

        String expected = """
                (ID, ez)
                Unknown Symbol encountered at line number: 2
                """;

        Assert.assertEquals(output, expected);
    }

}
