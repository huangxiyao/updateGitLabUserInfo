package updateGitLabUserInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
public static void main(String[] args) {
	String user = "uid=xiyao@hpe.com,ou=people,o=hp";
	Pattern p = Pattern.compile("uid=(.*),ou=people,o=hp");
    Matcher m = p.matcher(user);
    if (m.find()) {
        System.out.println(m.group(1));
    }else{
    	System.out.print("=============");
    }
}
}
