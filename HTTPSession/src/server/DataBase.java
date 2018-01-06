package server;


import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class DataBase {
    public DataBase()
    {
        data = new HashMap<>();
    }
    public DataBase(String path) throws IOException {
        connect(path);
    }
    private HashMap<String, String> data;
    private String path;
    public void connect(String path) throws IOException {
        this.path = path;
        FileReader reader = new FileReader(path);
        data = new HashMap<>();
        Scanner scanner = new Scanner(reader);
        while (scanner.hasNextLine())
        {
            String line = scanner.nextLine();
            String inf[] = line.split(" ");
            if (inf.length == 2)
            data.put(inf[0], inf[1]);
        }
        reader.close();
    }
    public boolean isContained(String key)
    {
        return (data.containsKey(key));
    }
    public boolean isContained(String key, String value)
    {
        return (data.containsKey(key) && data.get(key).equals(value));
    }

    public void insert(String key, String value)
    {
        data.put(key,value);
        String s = "\n" + key + " " + value;
       try
       {
           FileWriter writer = new FileWriter(path, true);
           BufferedWriter bufferWriter = new BufferedWriter(writer);
           bufferWriter.write(s);
           bufferWriter.close();
           writer.close();
       }
       catch (IOException e) {
           e.printStackTrace();
       }

    }
}
