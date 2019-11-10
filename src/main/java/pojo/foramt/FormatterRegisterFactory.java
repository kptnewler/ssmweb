package pojo.foramt;

import org.springframework.format.FormatterRegistrar;
import org.springframework.format.FormatterRegistry;

import java.io.*;

public class FormatterRegisterFactory implements FormatterRegistrar {
    @Override
    public void registerFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter("yyyy-MM-dd"));
    }
    
    public static void main(String[] args) throws IOException {
        File file = new File("F:/Code/Java/ssmweb/target/ssmweb/upload/1");
        FileWriter fileWriter = new FileWriter(file);
        for (int i = 0; i < 10000; i++) {
            fileWriter.write("a");
        }
        fileWriter.close();
    }
}
