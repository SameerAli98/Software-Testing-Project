import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;

public class FileIOHelper {

    public static void writeToFile(String filename, String content) {
        try {
            FileOutputStream outFile = new FileOutputStream(filename, false);
            outFile.write((content).getBytes());
            outFile.close();
        } catch (Exception ignored) {
        }
    }

    public static String readFromFile(String filename){
        try {
            BufferedReader inFile = new BufferedReader(new FileReader(filename));
            StringBuilder content = new StringBuilder();
            while (inFile.ready())
                content.append(inFile.readLine()).append("\n");
            inFile.close();
            return content.toString();
        } catch (Exception ignored) {
        }
        return null;
    }
}
