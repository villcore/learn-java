/**
 * Created by villcore on 2017/2/17.
 */
public class GetSystemProperity {
    public static void main(String[] args) {
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")){
            System.out.println(os + " can't gunzip");
        }
    }
}
