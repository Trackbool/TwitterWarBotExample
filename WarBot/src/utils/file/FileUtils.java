package utils.file;

import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static List<String> readAllLines(String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(fileName);
        Pair<Reader, String> charsetPair = Chardet.guessCharset(inputStream);
        BufferedReader bufferedReader = new BufferedReader(charsetPair.getKey());

        List<String> lines = new ArrayList<>();
        String line = "";
        while(line != null){
            line = bufferedReader.readLine();
            if(line != null){
                lines.add(line);
            }
        }
        inputStream.close();
        bufferedReader.close();

        return lines;
    }

    public static <T> void serialize(T targetObject, File file) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
        outputStream.writeObject(targetObject);
        outputStream.close();
    }

    public static <T> T deserialize(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
        T readObject = (T) inputStream.readObject();
        inputStream.close();

        return readObject;
    }
}
