import java.io.FileOutputStream;
import java.io.RandomAccessFile;

public class Lex {

    static char EOF = 3;

    static RandomAccessFile inFile = null;
    static FileOutputStream outFile = null;
    static int fileSize = 0;
    static int currPos = 0;
    static int lineNum = 1;
    static int currState = 1;
    static String lex = "";

    static void printErrorAndExit(String error) {
        System.out.println(error);
        System.exit(1);
    }

    static void openFiles(String[] args) {
        String filename = "code.txt";
        if (args != null) {
            if (args.length > 0)
                filename = args[0];
        }
        try {
            inFile = new RandomAccessFile(filename, "r");
            fileSize = (int) inFile.length();
            inFile.seek(0);
        } catch (Exception ex) {
            printErrorAndExit(ex.getMessage());
        }

        try {
            outFile = new FileOutputStream("output.txt", false);
        } catch (Exception ex) {
            printErrorAndExit(ex.getMessage());
        }
    }

    static char readChar() {
        char c = 0;
        try {
            if (currPos < fileSize)
                c = (char) inFile.readByte();
            else if (currPos == fileSize)
                c = EOF;
            ++currPos;
        } catch (Exception ex) {
            printErrorAndExit(ex.getMessage());
        }
        return c;
    }

    static void moveCursorBack() {
        try {
            if (inFile != null)
                inFile.seek(--currPos);
        } catch (Exception ex) {
            printErrorAndExit(ex.getMessage());
        }
        if (!lex.equals(""))
            lex = lex.substring(0, lex.length() - 1);
    }

    static void closeFiles() {
        try {
            if (inFile != null)
                inFile.close();
            if (outFile != null)
                outFile.close();
        } catch (Exception ex) {
            printErrorAndExit(ex.getMessage());
        }
        inFile = null;
        outFile = null;
        fileSize = 0;
        currPos = 0;
        lineNum = 1;
        currState = 1;
        lex = "";
    }

    static boolean isValidID(char c) {
        return Character.isAlphabetic(c) || Character.isDigit(c) || c == '_';
    }

    static void writeOutput(String s) {
        System.out.println(s);
        try {
            outFile.write((s + '\n').getBytes());
        } catch (Exception ex) {
            printErrorAndExit(ex.getMessage());
        }
    }

    static void printTokenAndMoveBack(String token, String lexeme) {
        moveCursorBack();
        if (lexeme != null)
            lexeme = lexeme.substring(0, lexeme.length() - 1);
        printToken(token, lexeme);
    }

    static void printToken(String token, String lexeme) {
        if (lexeme == null)
            lexeme = "^";
        writeOutput("(" + token + ", " + lexeme + ")");
        currState = 1;
        lex = "";
    }

    static void writeError(String s) {
        writeOutput(s + " at line number: " + lineNum);
        currPos = fileSize;
        currState = 1;
    }

    static void switchToIDState() {
        moveCursorBack();
        currState = 42;
    }

    static void runDFA() {
        char c = readChar();
        while (c != 0) {
            lex += c;

            switch (currState) {
                case 1:
                    if (c == 'i')
                        currState = 2;
                    else if (c == 'c')
                        currState = 5;
                    else if (c == 'e')
                        currState = 10;
                    else if (c == 'w')
                        currState = 16;
                    else if (c == 'p')
                        currState = 24;
                    else if (c == '+')
                        currState = 31;
                    else if (c == '-')
                        currState = 32;
                    else if (c == '*')
                        printToken("MUL", null);
                    else if (c == '/')
                        currState = 33;
                    else if (c == '<')
                        currState = 34;
                    else if (c == '>')
                        currState = 35;
                    else if (c == '=')
                        currState = 36;
                    else if (c == '~')
                        currState = 37;
                    else if (c == '\\')
                        currState = 38;
                    else if (Character.isAlphabetic(c))
                        currState = 42;
                    else if (Character.isDigit(c))
                        currState = 43;
                    else if (c == '\'')
                        currState = 44;
                    else if (c == '"')
                        currState = 46;
                    else if (c == '(')
                        printToken("PARL", null);
                    else if (c == ')')
                        printToken("PARR", null);
                    else if (c == '{')
                        printToken("CURL", null);
                    else if (c == '}')
                        printToken("CURR", null);
                    else if (c == '[')
                        printToken("SQRL", null);
                    else if (c == ']')
                        printToken("SQRR", null);
                    else if (c == ':')
                        printToken("COLON", null);
                    else if (c == ';')
                        printToken("SEMCOL", null);
                    else if (c == ',')
                        printToken("COMMA", null);
                    else if (!Character.isWhitespace(c) && c != EOF)
                        writeError("Unknown Symbol encountered");
                    else
                        lex = "";
                    break;
                case 2:
                    if (c == 'n')
                        currState = 3;
                    else if (c == 'f')
                        currState = 9;
                    else
                        switchToIDState();
                    break;
                case 3:
                    if (c == 't')
                        currState = 4;
                    else if (c == 'p')
                        currState = 21;
                    else
                        switchToIDState();
                    break;
                case 4:
                    if (isValidID(c))
                        currState = 42;
                    else
                        printTokenAndMoveBack("INT", null);
                    break;
                case 5:
                    if (c == 'h')
                        currState = 6;
                    else
                        switchToIDState();
                    break;
                case 6:
                    if (c == 'a')
                        currState = 7;
                    else
                        switchToIDState();
                    break;
                case 7:
                    if (c == 'r')
                        currState = 8;
                    else
                        switchToIDState();
                    break;
                case 8:
                    if (isValidID(c))
                        currState = 42;
                    else
                        printTokenAndMoveBack("CHAR", null);
                    break;
                case 9:
                    if (isValidID(c))
                        currState = 42;
                    else
                        printTokenAndMoveBack("IF", null);
                    break;
                case 10:
                    if (c == 'l')
                        currState = 11;
                    else
                        switchToIDState();
                    break;
                case 11:
                    if (c == 'i')
                        currState = 12;
                    else if (c == 's')
                        currState = 14;
                    else
                        switchToIDState();
                    break;
                case 12:
                    if (c == 'f')
                        currState = 13;
                    else
                        switchToIDState();
                    break;
                case 13:
                    if (isValidID(c))
                        currState = 42;
                    else
                        printTokenAndMoveBack("ELIF", null);
                    break;
                case 14:
                    if (c == 'e')
                        currState = 15;
                    else
                        switchToIDState();
                    break;
                case 15:
                    if (isValidID(c))
                        currState = 42;
                    else
                        printTokenAndMoveBack("ELSE", null);
                    break;
                case 16:
                    if (c == 'h')
                        currState = 17;
                    else
                        switchToIDState();
                    break;
                case 17:
                    if (c == 'i')
                        currState = 18;
                    else
                        switchToIDState();
                    break;
                case 18:
                    if (c == 'l')
                        currState = 19;
                    else
                        switchToIDState();
                    break;
                case 19:
                    if (c == 'e')
                        currState = 20;
                    else
                        switchToIDState();
                    break;
                case 20:
                    if (isValidID(c))
                        currState = 42;
                    else
                        printTokenAndMoveBack("WHILE", null);
                    break;
                case 21:
                    if (c == 'u')
                        currState = 22;
                    else
                        switchToIDState();
                    break;
                case 22:
                    if (c == 't')
                        currState = 23;
                    else
                        switchToIDState();
                    break;
                case 23:
                    if (isValidID(c))
                        currState = 42;
                    else
                        printTokenAndMoveBack("INPUT", null);
                    break;
                case 24:
                    if (c == 'r')
                        currState = 25;
                    else
                        switchToIDState();
                    break;
                case 25:
                    if (c == 'i')
                        currState = 26;
                    else
                        switchToIDState();
                    break;
                case 26:
                    if (c == 'n')
                        currState = 27;
                    else
                        switchToIDState();
                    break;
                case 27:
                    if (c == 't')
                        currState = 28;
                    else
                        switchToIDState();
                    break;
                case 28:
                    if (c == 'l')
                        currState = 29;
                    else if (isValidID(c))
                        currState = 42;
                    else
                        printTokenAndMoveBack("PRINT", null);
                    break;
                case 29:
                    if (c == 'n')
                        currState = 30;
                    else
                        switchToIDState();
                    break;
                case 30:
                    if (isValidID(c))
                        currState = 42;
                    else
                        printTokenAndMoveBack("PRINTLN", null);
                    break;
                case 31:
                    if (c == '+')
                        printToken("INC", null);
                    else
                        printTokenAndMoveBack("ADD", null);
                    break;
                case 32:
                    if (c == '>')
                        printToken("ARROW", null);
                    else
                        printTokenAndMoveBack("SUB", null);
                    break;
                case 33:
                    if (c == '*')
                        currState = 40;
                    else
                        printTokenAndMoveBack("DIV", null);
                    break;
                case 34:
                    if (c == '=')
                        printToken("LTET", null);
                    else
                        printTokenAndMoveBack("LT", null);
                    break;
                case 35:
                    if (c == '=')
                        printToken("GTET", null);
                    else
                        printTokenAndMoveBack("GT", null);
                    break;
                case 36:
                    if (c == '=')
                        printToken("ET", null);
                    else
                        printTokenAndMoveBack("ASSIGN", null);
                    break;
                case 37:
                    if (c == '=')
                        printToken("NET", null);
                    else
                        writeError("Unknown Symbol encountered");
                    break;
                case 38:
                    if (c == '\\')
                        currState = 39;
                    else
                        writeError("Unknown Symbol encountered");
                    break;
                case 39:
                    if (c == '\n' || c == EOF)
                        printToken("SLC", null);
                    break;
                case 40:
                    if (c == '*')
                        currState = 41;
                    else if (c == EOF)
                        writeError("Missing comment end tag");
                    break;
                case 41:
                    if (c == '/')
                        printToken("MLC", null);
                    else if (c != EOF)
                        currState = 40;
                    else
                        writeError("Missing comment end tag");
                    break;

                case 42:
                    if (!isValidID(c))
                        printTokenAndMoveBack("ID", lex);
                    break;

                case 43:
                    if (!Character.isDigit(c))
                        printTokenAndMoveBack("NC", lex);
                    break;
                case 44:
                    if (c > 29 && c < 107)
                        currState = 45;
                    else if (Character.isWhitespace(c))
                        writeError("Unclosed character literal");
                    else
                        writeError("Character literal can only have an ASCII value");
                    break;
                case 45:
                    if (c == '\'')
                        printToken("LC", lex);
                    else
                        writeError("Unclosed character literal");
                    break;
                case 46:
                    if (c == '"')
                        printToken("STR", lex);
                    else if (c == EOF)
                        writeError("Missing string end tag");
                    break;
            }

            if (c == '\n')
                ++lineNum;
            c = readChar();
        }
    }

    public static void mainExec(String[] args) {
        openFiles(args);

        runDFA();

        closeFiles();
    }
}