import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.util.*;

public class VM {
    static List<String[]> tac = new ArrayList<>();
    static List<List<String>> symbolTable = new ArrayList<>();
    static byte[] heap = null;

    static void readFiles() {
        try {
            BufferedReader br1 = new BufferedReader(new FileReader("tac.txt")),
                    br2 = new BufferedReader(new FileReader("symboltable.txt"));

            while (br1.ready()) {
                String s = br1.readLine();
                if (s.startsWith("out \"")) {
                    String[] t = new String[2];
                    t[0] = "out";
                    t[1] = s.substring(4);
                    tac.add(t);
                } else
                    tac.add(s.split(" "));
            }

            String[] symbols = null;
            for (int i = 0; i < 3; i++)
                symbolTable.add(new LinkedList<>());
            while (br2.ready()) {
                symbols = br2.readLine().split(" ");
                symbolTable.get(0).add(symbols[0]);
                symbolTable.get(1).add(symbols[1]);
                symbolTable.get(2).add(symbols[2]);
            }

            if (symbols != null) {
                int n = 4;
                if (Objects.equals(symbols[1], "CHAR"))
                    n = 1;
                heap = new byte[Integer.parseInt(symbols[2]) + n];
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static String[] getSymbol(String name) {
        int i = symbolTable.get(0).indexOf(name);
        String[] symbol = new String[3];
        symbol[0] = symbolTable.get(0).get(i);
        symbol[1] = symbolTable.get(1).get(i);
        symbol[2] = symbolTable.get(2).get(i);
        return symbol;
    }

    static int getInt(int off) {
        byte[] bytes = new byte[4];
        bytes[0] = heap[off];
        bytes[1] = heap[off + 1];
        bytes[2] = heap[off + 2];
        bytes[3] = heap[off + 3];
        return ByteBuffer.wrap(bytes).getInt();
    }

    static int getValue(String name) {
        int min = 1;
        if(name.startsWith("-")){
            min = -1;
            name = name.substring(1);
        }
        int n;
        if (name.startsWith("'"))
            n = name.charAt(1);
        else if (Character.isDigit(name.charAt(0)))
            n = Integer.parseInt(name);
        else {
            String[] symbol = getSymbol(name);
            int off = Integer.parseInt(symbol[2]);
            if (symbol[1].equals("CHAR"))
                n = heap[off];
            else
                n = getInt(off);
        }
        return n * min;
    }

    static void storeValue(String name, int val) {
        String[] symbol = getSymbol(name);
        int off = Integer.parseInt(symbol[2]);

        if (symbol[1].equals("CHAR"))
            storeChar((char) val, off);
        else
            storeInt(val, off);
    }

    static void storeInt(int n, int off) {
        byte[] bytes = ByteBuffer.allocate(4).putInt(n).array();
        heap[off] = bytes[0];
        heap[off + 1] = bytes[1];
        heap[off + 2] = bytes[2];
        heap[off + 3] = bytes[3];
    }

    static void storeChar(char c, int off) {
        heap[off] = (byte) c;
    }

    static void execute() {
        for (int i = 0; i < tac.size(); i++) {
            String[] line = tac.get(i);
            switch (line[0]) {
                case "goto" -> i = Integer.parseInt(line[1]) - 2;
                case "in" -> {
                    String[] symbol = getSymbol(line[1]);
                    int off = Integer.parseInt(symbol[2]);
                    try {
                        Scanner scanner = new Scanner(System.in);
                        if (scanner.hasNextInt()) {
                            int n = scanner.nextInt();
                            storeInt(n, off);
                        } else
                            storeChar(scanner.next().charAt(0), off);

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                }
                case "out" -> {
                    if (line[1].startsWith("'"))
                        System.out.print(line[1].charAt(1));
                    else if (line[1].startsWith("\""))
                        System.out.print(line[1].substring(1, line[1].length() - 1));
                    else if (line[1].equals("\\n"))
                        System.out.println();
                    else {
                        String[] symbol = getSymbol(line[1]);
                        if (symbol[1].equals("INT")) {
                            int n = getValue(symbol[0]);
                            System.out.print(n);
                        } else {
                            char c = (char) getValue(symbol[0]);
                            System.out.print(c);
                        }
                    }
                }
                case "if" -> {
                    int x = getValue(line[1]), y = getValue(line[3]);
                    if ("<".equals(line[2])) {
                        if (x < y) i = Integer.parseInt(line[5]) - 2;
                    } else if ("<=".equals(line[2])) {
                        if (x <= y) i = Integer.parseInt(line[5]) - 2;
                    } else if (">".equals(line[2])) {
                        if (x > y) i = Integer.parseInt(line[5]) - 2;
                    } else if (">=".equals(line[2])) {
                        if (x >= y) i = Integer.parseInt(line[5]) - 2;
                    } else if ("==".equals(line[2])) {
                        if (x == y) i = Integer.parseInt(line[5]) - 2;
                    } else {
                        if (x != y) i = Integer.parseInt(line[5]) - 2;
                    }
                }
                default -> {
                    int x = getValue(line[2]), z;
                    if (line.length > 3) {
                        int y = getValue(line[4]);
                        switch (line[3]) {
                            case "+" -> z = x + y;
                            case "-" -> z = x - y;
                            case "*" -> z = x * y;
                            default -> z = x / y;
                        }
                    } else z = x;
                    storeValue(line[0], z);
                }
            }
        }
    }

    public static void mainExec() {
        readFiles();
        execute();
    }
}
