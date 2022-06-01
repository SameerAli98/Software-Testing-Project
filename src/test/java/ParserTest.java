import org.testng.Assert;
import org.testng.annotations.Test;

public class ParserTest {

    @Test
    void test1() {
        String output = """
                (INT, ^)
                (COLON, ^)
                (ID, num)
                (ASSIGN, ^)
                (NC, 0)
                (SEMCOL, ^)
                """;

        FileIOHelper.writeToFile("output.txt", output);

        Parser.mainExec();

        String tac = FileIOHelper.readFromFile("tac.txt");
        String sTable = FileIOHelper.readFromFile("symboltable.txt");

        String expectedSTable = """
                num INT 0
                """;

        String expectedTac = """
                num = 0
                """;

        Assert.assertEquals(tac, expectedTac);
        Assert.assertEquals(sTable, expectedSTable);
    }

    @Test
    void test2() {
        String output = """
                (INT, ^)
                (COLON, ^)
                (ID, num)
                (ASSIGN, ^)
                (NC, 0)
                (SEMCOL, ^)
                (CHAR, ^)
                (COLON, ^)
                (ID, character)
                (ASSIGN, ^)
                (LC, 'a')
                (SEMCOL, ^)
                """;

        FileIOHelper.writeToFile("output.txt", output);

        Parser.mainExec();

        String tac = FileIOHelper.readFromFile("tac.txt");
        String sTable = FileIOHelper.readFromFile("symboltable.txt");

        String expectedSTable = """
                num INT 0
                character CHAR 4
                """;

        String expectedTac = """
                num = 0
                character = 'a'
                """;

        Assert.assertEquals(tac, expectedTac);
        Assert.assertEquals(sTable, expectedSTable);
    }

    @Test
    void test3() {
        String output = """
                (SLC, ^)
                """;

        FileIOHelper.writeToFile("output.txt", output);

        Parser.mainExec();

        String tac = FileIOHelper.readFromFile("tac.txt");
        String sTable = FileIOHelper.readFromFile("symboltable.txt");

        String expectedSTable = """
                """;

        String expectedTac = """
                """;

        Assert.assertEquals(tac, expectedTac);
        Assert.assertEquals(sTable, expectedSTable);
    }

    @Test
    void test4() {
        String output = """
                (MLC, ^)
                """;

        FileIOHelper.writeToFile("output.txt", output);

        Parser.mainExec();

        String tac = FileIOHelper.readFromFile("tac.txt");
        String sTable = FileIOHelper.readFromFile("symboltable.txt");

        String expectedSTable = """
                """;

        String expectedTac = """
                """;

        Assert.assertEquals(tac, expectedTac);
        Assert.assertEquals(sTable, expectedSTable);
    }

    @Test
    void test5() {
        String output = """
                (INT, ^)
                (COLON, ^)
                (ID, count)
                (ASSIGN, ^)
                (NC, 0)
                (SEMCOL, ^)
                (ID, count)
                (INC, ^)
                (SEMCOL, ^)
                """;

        FileIOHelper.writeToFile("output.txt", output);

        Parser.mainExec();

        String tac = FileIOHelper.readFromFile("tac.txt");
        String sTable = FileIOHelper.readFromFile("symboltable.txt");

        String expectedSTable = """
                count INT 0
                """;

        String expectedTac = """
                count = 0
                count = count + 1
                """;

        Assert.assertEquals(tac, expectedTac);
        Assert.assertEquals(sTable, expectedSTable);
    }

    @Test
    void test6() {
        String output = """
                (INT, ^)
                (ID, num)
                (ASSIGN, ^)
                (NC, 0)
                (SEMCOL, ^)
                """;

        FileIOHelper.writeToFile("output.txt", output);

        Parser.mainExec();

        String tac = FileIOHelper.readFromFile("tac.txt");
        String sTable = FileIOHelper.readFromFile("symboltable.txt");

        String expectedSTable = """
                """;

        String expectedTac = """
                """;

        Assert.assertEquals(tac, expectedTac);
        Assert.assertEquals(sTable, expectedSTable);
    }

    @Test
    void test7() {
        String output = """
                (INT, ^)
                (COLON, ^)
                (ID, num1)
                (ASSIGN, ^)
                (NC, 1)
                (SEMCOL, ^)
                (INT, ^)
                (COLON, ^)
                (ID, num2)
                (ASSIGN, ^)
                (NC, 2)
                (SEMCOL, ^)
                (INT, ^)
                (COLON, ^)
                (ID, sum)
                (ASSIGN, ^)
                (NC, 0)
                (SEMCOL, ^)
                (ID, sum)
                (ASSIGN, ^)
                (ID, num1)
                (ADD, ^)
                (ID, num2)
                (SEMCOL, ^)
                """;

        FileIOHelper.writeToFile("output.txt", output);

        Parser.mainExec();

        String tac = FileIOHelper.readFromFile("tac.txt");
        String sTable = FileIOHelper.readFromFile("symboltable.txt");

        String expectedSTable = """
                num1 INT 0
                num2 INT 4
                sum INT 8
                t1 INT 12
                """;

        String expectedTac = """
                num1 = 1
                num2 = 2
                sum = 0
                t1 = num1 + num2
                sum = t1
                """;
        Assert.assertEquals(tac, expectedTac);
        Assert.assertEquals(sTable, expectedSTable);
    }

    @Test
    void test8() {
        String output = """
                (CHAR, ^)
                (COLON, ^)
                (ID, a)
                (ASSIGN, ^)
                (LC, 'a')
                (SEMCOL, ^)
                (CHAR, ^)
                (COLON, ^)
                (ID, b)
                (ASSIGN, ^)
                (LC, 'b')
                (SEMCOL, ^)
                (CHAR, ^)
                (COLON, ^)
                (ID, c)
                (ASSIGN, ^)
                (LC, 'c')
                (SEMCOL, ^)
                (ID, c)
                (ASSIGN, ^)
                (ID, a)
                (ADD, ^)
                (ID, b)
                (SEMCOL, ^)
                """;

        FileIOHelper.writeToFile("output.txt", output);

        Parser.mainExec();

        String tac = FileIOHelper.readFromFile("tac.txt");
        String sTable = FileIOHelper.readFromFile("symboltable.txt");

        String expectedSTable = """
                a CHAR 0
                b CHAR 1
                c CHAR 2
                t1 INT 3
                """;

        String expectedTac = """
                a = 'a'
                b = 'b'
                c = 'c'
                t1 = a + b
                c = t1
                """;

        Assert.assertEquals(tac, expectedTac);
        Assert.assertEquals(sTable, expectedSTable);
    }

    @Test
    void test9() {
        String output = """
                (INT, ^)
                (COLON, ^)
                (ID, a)
                (ASSIGN, ^)
                (NC, 5)
                (SEMCOL, ^)
                (INT, ^)
                (COLON, ^)
                (ID, b)
                (ASSIGN, ^)
                (NC, 10)
                (SEMCOL, ^)
                (INT, ^)
                (COLON, ^)
                (ID, swap)
                (ASSIGN, ^)
                (NC, 0)
                (SEMCOL, ^)
                (ID, swap)
                (ASSIGN, ^)
                (ID, a)
                (SEMCOL, ^)
                (ID, a)
                (ASSIGN, ^)
                (ID, b)
                (SEMCOL, ^)
                (ID, b)
                (ASSIGN, ^)
                (ID, swap)
                (SEMCOL, ^)
                """;

        FileIOHelper.writeToFile("output.txt", output);

        Parser.mainExec();

        String tac = FileIOHelper.readFromFile("tac.txt");
        String sTable = FileIOHelper.readFromFile("symboltable.txt");

        String expectedSTable = """
                a INT 0
                b INT 4
                swap INT 8
                """;

        String expectedTac = """
                a = 5
                b = 10
                swap = 0
                swap = a
                a = b
                b = swap
                """;

        Assert.assertEquals(tac, expectedTac);
        Assert.assertEquals(sTable, expectedSTable);
    }

    @Test
    void test10() {
        String output = """
                (INT, ^)
                (COLON, ^)
                (ID, a)
                (ASSIGN, ^)
                (NC, 5)
                (SEMCOL, ^)
                (INT, ^)
                (COLON, ^)
                (ID, b)
                (ASSIGN, ^)
                (NC, 10)
                (SEMCOL, ^)
                (INT, ^)
                (COLON, ^)
                (ID, mul)
                (ASSIGN, ^)
                (ID, a)
                (MUL, ^)
                (ID, b)
                (SEMCOL, ^)
                (INT, ^)
                (COLON, ^)
                (ID, div)
                (ASSIGN, ^)
                (ID, mul)
                (DIV, ^)
                (NC, 5)
                (SEMCOL, ^)
                """;

        FileIOHelper.writeToFile("output.txt", output);

        Parser.mainExec();

        String tac = FileIOHelper.readFromFile("tac.txt");
        String sTable = FileIOHelper.readFromFile("symboltable.txt");

        String expectedSTable = """
                a INT 0
                b INT 4
                mul INT 8
                t1 INT 12
                div INT 16
                t2 INT 20
                """;

        String expectedTac = """
                a = 5
                b = 10
                t1 = a * b
                mul = t1
                t2 = mul / 5
                div = t2
                """;

        Assert.assertEquals(tac, expectedTac);
        Assert.assertEquals(sTable, expectedSTable);
    }

    @Test
    void test11() {
        String output = """
                (PRINTLN, ^)
                (PARL, ^)
                (PARR, ^)
                (SEMCOL, ^)
                """;

        FileIOHelper.writeToFile("output.txt", output);

        Parser.mainExec();

        String tac = FileIOHelper.readFromFile("tac.txt");
        String sTable = FileIOHelper.readFromFile("symboltable.txt");

        String expectedSTable = """
                """;

        String expectedTac = """
                out \\n
                """;

        Assert.assertEquals(tac, expectedTac);
        Assert.assertEquals(sTable, expectedSTable);
    }

    @Test
    void test12() {
        String output = """
                (PRINTLN, ^)
                (PARL, ^)
                (PARR, ^)
                (SEMCOL, ^)
                """;

        FileIOHelper.writeToFile("output.txt", output);

        Parser.mainExec();

        String tac = FileIOHelper.readFromFile("tac.txt");
        String sTable = FileIOHelper.readFromFile("symboltable.txt");

        String expectedSTable = """
                """;

        String expectedTac = """
                out \\n
                """;

        Assert.assertEquals(tac, expectedTac);
        Assert.assertEquals(sTable, expectedSTable);
    }

    @Test
    void test13() {
        String output = """
                (PRINTLN, ^)
                (PARL, ^)
                (STR, "hey world")
                (PARR, ^)
                (SEMCOL, ^)
                """;

        FileIOHelper.writeToFile("output.txt", output);

        Parser.mainExec();

        String tac = FileIOHelper.readFromFile("tac.txt");
        String sTable = FileIOHelper.readFromFile("symboltable.txt");

        String expectedSTable = """
                """;

        String expectedTac = """
                out "hey world"
                out \\n
                """;

        Assert.assertEquals(tac, expectedTac);
        Assert.assertEquals(sTable, expectedSTable);
    }


}
