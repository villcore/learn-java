package fun;

/**
 * Created by villcore on 2017/1/24.
 */
public class PrintTheBuddha {
    private static final String STRING_TEMPLATE = "\n" +
            "                   _ooOoo_\n" +
            "                  o8888888o\n" +
            "                  88\" . \"88\n" +
            "                  (| -_- |)\n" +
            "                  O\\  =  /O\n" +
            "               ____/`---'\\____\n "+
            "             .'  \\\\|     |//  `.\n "+
            "            /  \\\\|||  :  |||//  \\\n" +
            "           /  _||||| -:- |||||-  \\\n" +
            "           |   | \\\\\\  -  /// |   |\n" +
            "           | \\_|  ''\\---/''  |   |\n" +
            "           \\  .-\\__  `-`  ___/-. /\n" +
            "         ___`. .'  /--.--\\  `. . __\n" +
            "      .\"\" '<  `.___\\_<|>_/___.'  >'\"\".\n" +
            "     | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |\n" +
            "     \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /\n" +
            "======`-.____`-.___\\_____/___.-`____.-'======\n" +
            "                   `=---='\n"+
            "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n" +
            "         %s       %s\n" +
            "https://github.com/villcore/learn-java/blob/master/src/main/java/fun/PrintTheBuddha.java";

    public static void main(String[] args) {
    	if(args.length != 2) {
    		System.exit(0);
    	}
        System.out.println(String.format(STRING_TEMPLATE, args[0], args[1]));
    }
}
