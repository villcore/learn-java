import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;

/**
 * Created by WangTao on 2016/12/15.
 *
 * use java internal CRC32 checksum
 */
public class CRC32Demo {
    public static void main(String[] args) {
        String content = "this is a test string...";

        Checksum crc32 = new CRC32();

        try(CheckedInputStream cis = new CheckedInputStream(
                new ByteArrayInputStream(content.getBytes()),
                crc32)) {

            while(cis.read() > 0) {}
            long checkSum = crc32.getValue();
            System.out.printf("content checksum = [%s]\n", checkSum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
