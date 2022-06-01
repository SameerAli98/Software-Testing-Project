import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Stream;

public class Parser {

    static BufferedReader br = null;
    static FileOutputStream STFile = null;
    static FileOutputStream TacFile = null;
    static String line = null;
    static String token = null;
    static String lexeme = null;
    static String prevToken = null;
    static String prevLexeme = null;
    static String lastVar = null;
    static String datatype = null;
    static String currDatatype = null;
    static String code = "";
    static String id = "";
    static int adr = 0;
    static int n = 1;
    static int t = 1;
    static int f = 0;
    static int ln = 0;

    static List<List<String>> sTable = new ArrayList<>();

    static void printErrorAndExit(String error) {
        System.out.println(error);
        System.exit(1);
    }

    static String newTmp() {
        String name = "t" + t++;
        addToST(name, "INT");
        return name;
    }

    static void emit() {
        writeToFile(TacFile, code + "\n");
        ++n;
        code = "";
    }

    static void backPatch(int lineNo, int value) {
        BufferedReader b;
        try {
            b = new BufferedReader(new FileReader("tac.txt"));
            Stream<String> lines = b.lines();
            List<String> linesList = lines.toList();
            TacFile.close();
            TacFile = new FileOutputStream("tac.txt", false);
            for (int i = 0; i < linesList.size(); i++) {
                String line = linesList.get(i);
                if (i == lineNo - 1)
                    line += value;
                TacFile.write(line.getBytes(StandardCharsets.UTF_8));
                TacFile.write('\n');
            }
        } catch (Exception ex) {
            printErrorAndExit(ex.getMessage());
        }
    }

    static void writeToFile(FileOutputStream file, String s) {
        try {
            file.write((s).getBytes());
        } catch (Exception ex) {
            printErrorAndExit(ex.getMessage());
        }
    }

    static void writeST() {
        for (int i = 0; i < sTable.get(0).size(); i++) {
            writeToFile(STFile, sTable.get(0).get(i) + " " +
                    sTable.get(1).get(i) + " " + sTable.get(2).get(i) + "\n");
        }
    }

    static void addToST(String name, String type) {
        sTable.get(0).add(name);
        sTable.get(1).add(type);
        sTable.get(2).add(Integer.toString(adr));
        int n = 4;
        if (type.equals("CHAR")) n = 1;
        adr += n;
    }

    static void openFiles() {
        try {
            br = new BufferedReader(new FileReader("output.txt"));
            STFile = new FileOutputStream("symboltable.txt", false);
            TacFile = new FileOutputStream("tac.txt", false);
        } catch (Exception ex) {
            printErrorAndExit(ex.getMessage());
        }

        for (int i = 0; i < 3; i++)
            sTable.add(new LinkedList<>());
    }

    static void getLine() {
        String s = null;
        try {
            if (br.ready())
                s = br.readLine();
        } catch (Exception ex) {
            printErrorAndExit(ex.getMessage());
        }
        line = s;
    }

    static void getToken() {
        String t = null;
        String l = null;
        if (line != null) {
            int i = 2;
            StringBuilder tBuilder = new StringBuilder(String.valueOf(line.charAt(1)));
            while (line.charAt(i) != ',')
                tBuilder.append(line.charAt(i++));
            t = tBuilder.toString();
            i += 2;
            StringBuilder lBuilder = new StringBuilder(String.valueOf(line.charAt(i++)));
            while (line.charAt(i) != ')')
                lBuilder.append(line.charAt(i++));
            l = lBuilder.toString();
        }
        token = t;
        lexeme = l;
    }

    static void nextToken() {
        getLine();
        getToken();
    }

    static boolean notMatched(String s) {
        if (token == null || !token.equals(s))
            return true;
        prevToken = token;
        prevLexeme = lexeme;
        nextToken();
        return false;
    }

    static boolean DECLARE() {
        if (DATATYPE()) {
            if (notMatched("COLON"))
                printErrorAndExit("Expected :");
            NAME();
            return true;
        }
        return false;
    }

    static boolean DATATYPE() {
        if (notMatched("INT") && notMatched("CHAR"))
            return false;
        datatype = prevToken;
        return true;
    }

    //Helper Function
    static void NAME() {
        if (notMatched("ID"))
            printErrorAndExit("Expected an identifier");
        if (sTable.get(0).contains(prevLexeme))
            printErrorAndExit("Variable already defined");
        if (prevLexeme.equals("int") || prevLexeme.equals("char"))
            printErrorAndExit("Reserved word cannot be used as variable name");
        currDatatype = datatype;
        lastVar = prevLexeme;

        addToST(lastVar, currDatatype);

        INITIALIZE();
        TERMINATE();
    }

    static void TERMINATE() {
        if (!notMatched("COMMA"))
            NAME();
        else if (notMatched("SEMCOL"))
            printErrorAndExit("Expected ;");
    }

    static void INITIALIZE() {
        String temp = prevLexeme;
        if (!notMatched("ASSIGN")) {
            ARITHMETIC();
            code = temp + " = " + id;
            emit();
        }
    }

    static boolean ARITHMETIC() {
        boolean flag = VALUE();
        if (!flag)
            id = "0";
        MUL();
        ADD();
        return flag;
    }

    static void ADD() {
        if (!notMatched("ADD")) {
            String id1 = id;
            ARITHMETIC();
            String temp = newTmp();
            code = temp + " = " + id1 + " + " + id;
            emit();
            id = temp;
        } else if (!notMatched("SUB")) {
            String id1 = id;
            ARITHMETIC();
            String temp = newTmp();
            code = temp + " = " + id1 + " - " + id;
            emit();
            id = temp;
        }
    }

    static void MUL() {
        if (!notMatched("MUL")) {
            String id1 = id;
            VALUE();
            MUL();
            String temp = newTmp();
            code = temp + " = " + id1 + " * " + id;
            emit();
            id = temp;
        } else if (!notMatched("DIV")) {
            String id1 = id;
            VALUE();
            MUL();
            String temp = newTmp();
            code = temp + " = " + id1 + " / " + id;
            emit();
            id = temp;
        }
    }

    static boolean VALUE() {
        String minus = "";
        if (!notMatched("SUB"))
            minus = "-";

        if (!notMatched("NC")) {
            id = minus + prevLexeme;
        } else if (!notMatched("LC")) {
            id = minus + prevLexeme;
        } else if (!notMatched("ID")) {
            id = minus + prevLexeme;
        } else if (!notMatched("PARL")) {
            if (!ARITHMETIC())
                printErrorAndExit("Error");

            id = minus + id;

            if (notMatched("PARR"))
                printErrorAndExit("Expected )");
        } else {
            return false;
        }
        return true;


    }

    static boolean RELATION() {
        boolean flag = false;
        if (!notMatched("LT")) {
            code += " <";
            flag = true;
        } else if (!notMatched("GT")) {
            code += " >";
            flag = true;
        } else if (!notMatched("LTE")) {
            code += " <=";
            flag = true;
        } else if (!notMatched("GTE")) {
            code += " >=";
            flag = true;
        } else if (!notMatched("ET")) {
            code += " ==";
            flag = true;
        } else if (!notMatched("NTE")) {
            code += " ~=";
            flag = true;
        }
        return flag;
    }

    static boolean ASSIGNMENT() {
        if (notMatched("ID"))
            return false;
        String temp = prevLexeme;

        if (!notMatched("INC")) {
            code = temp + " = " + temp + " + 1";
            emit();
            if (notMatched("SEMCOL"))
                printErrorAndExit("Expected ;");
            return true;
        }

        if (notMatched("ASSIGN"))
            printErrorAndExit("Expected =");

        if (!notMatched("ID")) {
            String temp2 = prevLexeme;
            if (!notMatched("ASSIGN")) {
                boolean flag = ASSIGNMENT();
                code = temp2 + " = " + id;
                emit();
                code = temp + " = " + id;
                emit();
                return flag;
            }
            id = temp2;
            MUL();
            ADD();
        } else ARITHMETIC();

        code = temp + " = " + id;
        emit();
        if (notMatched("SEMCOL"))
            printErrorAndExit("Expected ;");
        return true;
    }

    static boolean LOOP() {
        if (notMatched("WHILE"))
            return false;
        int s = n;
        CONDITION();
        code = "goto " + s;
        emit();
        backPatch(f, n);
        return true;
    }

    static boolean DECISION() {
        if (notMatched("IF"))
            return false;
        CONDITION();
        OTHERWISE();
        return true;
    }

    static void OTHERWISE() {
        if (!notMatched("ELIF")) {
            int temp = n;
            code = "goto ";
            emit();
            backPatch(f, n);
            CONDITION();
            OTHERWISE();
            backPatch(temp, n);
        } else if (!notMatched("ELSE")) {
            int temp = n;
            code = "goto ";
            emit();
            backPatch(f, n);
            if (notMatched("COLON"))
                printErrorAndExit("Expected :");
            BLOCK();
            backPatch(temp, n);
        } else backPatch(f, n);
    }

    static void CONDITION() {
        code = "if ";
        if (ARITHMETIC()) {
            code += id;
            if (!COMPARISON())
                code += "> 0";
            if (notMatched("COLON"))
                printErrorAndExit("Expected :");
            code += " goto " + (n + 2);
            emit();
            f = n;
            code = "goto ";
            emit();
            BLOCK();

        }
    }

    static boolean COMPARISON() {
        if (RELATION()) {
            if (!ARITHMETIC())
                printErrorAndExit("Expected a value");
            code += " " + id;
            return true;
        }
        return false;
    }

    static void BLOCK() {
        if (!notMatched("CURL")) {
            if (!MULTISTATEMENTS())
                printErrorAndExit("Expected a valid statement");

            if (notMatched("CURR"))
                printErrorAndExit("Expected }");
        } else {
            if (!STATEMENT())
                printErrorAndExit("Expected a statement");
        }
    }

    static boolean MULTISTATEMENTS() {
        boolean flag = STATEMENT();
        if (flag) {
            MULTISTATEMENTS();
        }
        return flag;
    }

    static boolean OUTPUT() {
        if (!PRINT())
            return false;
        if (notMatched("PARL"))
            printErrorAndExit("Expected (");
        code = "out ";
        if (PARAMETER()) {
            code += id;
            emit();
        }
        if (notMatched("PARR"))
            printErrorAndExit("Expected )");
        if (notMatched("SEMCOL"))
            printErrorAndExit("Expected ;");
        if (ln > 0) {
            code = "out \\n";
            emit();
        }
        return true;
    }

    static boolean PRINT() {
        boolean flag = false;
        ln = 0;
        if (!notMatched("PRINT")) {
            flag = true;
        } else if (!notMatched("PRINTLN")) {
            ln = 1;
            flag = true;
        }
        return flag;
    }

    static boolean PARAMETER() {
        boolean flag = true;
        String temp = code;
        if (!notMatched("STR")) {
            id = prevLexeme;
        } else if (ARITHMETIC()) {
            code = temp;
        } else {
            flag = false;
        }

        return flag;
    }

    static boolean INPUT() {
        if (notMatched("INPUT"))
            return false;

        if (notMatched("ARROW"))
            printErrorAndExit("Expected ->");
        code = "in ";
        if (notMatched("ID"))
            printErrorAndExit("Expected a variable");

        code += prevLexeme;
        emit();

        if (notMatched("SEMCOL"))
            printErrorAndExit("Expected ;");
        return true;
    }

    static boolean STATEMENT() {
        boolean flag;
        if (token == null) {
            flag = false;
        } else if (token.equals("INT") || token.equals("CHAR")) {
            flag = DECLARE();
        } else if (token.equals("ID")) {
            flag = ASSIGNMENT();
        } else if (token.equals("INPUT")) {
            flag = INPUT();
        } else if (token.equals("PRINT") || token.equals("PRINTLN")) {
            flag = OUTPUT();
        } else if (token.equals("IF")) {
            flag = DECISION();
        } else if (token.equals("WHILE")) {
            flag = LOOP();
        } else if (!notMatched("SLC")) {
            flag = true;
        } else if (!notMatched("MLC")) {
            flag = true;
        } else {
            flag = !notMatched("SEMCOL");
        }
        return flag;
    }

    public static void mainExec() {
        openFiles();
        nextToken();
        while (token != null)
            if (!STATEMENT())
                printErrorAndExit("Unexpected token");
        writeST();
    }
}
