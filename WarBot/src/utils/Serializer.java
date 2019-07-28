package utils;

import java.io.*;

public class Serializer {

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
