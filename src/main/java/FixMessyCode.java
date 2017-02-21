import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by villcore on 2017/2/17.
 */
public class FixMessyCode {
    public static void main(String[] args) throws IOException {
        try(InputStream is = new FileInputStream("c://text.dat")) {
            byte[] bytes = new byte[is.available()];
            is.read(bytes);

            System.out.println(bytes.length);
        }
    }
}