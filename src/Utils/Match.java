package Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @DESCRIPTION:
 * @USER: f0ng
 * @DATE: 2023/2/15 下午10:01
 */
public class Match {
    public static void main(String[] args) {
        System.out.println(MatchReg("{\"req\":\"I9z1fsH5QQ2NUbJi/7a8lw==\"}","req\":\"(.*?)\""));

    }
    public static String MatchReg(String data, String reg){
        String total = "";
        String pattern = reg ;
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(data);
        if (m.find()) {
            total = m.group(1);
        }

        return total;

    }
}
