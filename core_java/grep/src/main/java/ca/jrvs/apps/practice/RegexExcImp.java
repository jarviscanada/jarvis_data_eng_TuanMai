package ca.jrvs.apps.practice;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexExcImp implements RegexExc {

    /**
     * return true if filename extension is jpg or jpeg (case insensitive)
     * @param filename
     * @return
     */
    public boolean matchJpeg(String filename) {
        Pattern pattern = Pattern.compile("(.*\\.)(jpg|jpeg)$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(filename);

        return matcher.matches();
    }

    /**
     * return true if ip is valid
     * to simplify the problem, IP address range is from 0.0.0.0 to 999.999.999.999
     * @param ip
     * @return
     */
    public boolean matchIp (String ip){
        Pattern pattern = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
        Matcher matcher = pattern.matcher(ip);

        return matcher.matches();
    }

    /**
     * return true if line is empty (e.g. empty, white space, tabs, etc.. )
     * @param line
     * @return
     */
    public boolean isEmptyLine(String line){
        Pattern pattern = Pattern.compile(".*\\s.*");
        Matcher matcher = pattern.matcher(line);

        return matcher.matches();
    }
}
