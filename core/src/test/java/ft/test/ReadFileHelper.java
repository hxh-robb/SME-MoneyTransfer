package ft.test;

import java.io.BufferedReader;
import java.io.FileReader;

public interface ReadFileHelper {

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
