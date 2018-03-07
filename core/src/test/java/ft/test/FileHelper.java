package ft.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public interface FileHelper {

    default File get(String path) {
        try {
            return new File(this.getClass().getClassLoader().getResource(path).getFile());
        } catch (Throwable throwable) {
            return null;
        }
    }

    default String read(String file) {
        try {
            FileReader reader = new FileReader(this.getClass().getClassLoader().getResource(file).getFile());
            BufferedReader buffer = new BufferedReader(reader);
            String lines = null, line;
            while( null != (line = buffer.readLine())) {
                if( null == lines)
                    lines = line;
                else
                    lines += "\n" + line;
            }
            return lines;
        } catch (Throwable throwable) {
            return null;
        }
    }
}
