/**
 * Created by villcore on 2017/2/19.
 */
public class StringInternalTest {
    public static void main(String[] args) {
        String str = new StringBuilder().append("test").append(" string").toString();
        System.out.println(str.intern() == str); //true

        String str2 = new StringBuilder().append("ja").append("va").toString();
        System.out.println(str2.intern() == str2); //false
    }
}
