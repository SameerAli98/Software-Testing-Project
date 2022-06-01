public class Main {

    public static void main(String[] args) {
        String[] arg = {"code.txt"};
        Lex.mainExec(arg);
        Parser.mainExec();
        VM.mainExec();
    }
}
