package battleship.ai.utils;

import java.util.regex.*;

public class MoveExtractor {

    public static String extract(String response) {

        Pattern pattern = Pattern.compile("rajada\\s+[A-Z]\\\\d+\\\\s+[A-Z]\\\\d+\\\\s+[A-Z]\\\\d+");
        Matcher matcher = pattern.matcher(response);

        if (matcher.find())
            return matcher.group();

        return "rajada A1 A2 A3";
    }
}
