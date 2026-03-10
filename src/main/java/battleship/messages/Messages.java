package battleship.messages;

import com.ibm.icu.text.MessageFormat;
import java.util.Locale;

public class Messages {

    private static Locale locale = Locale.getDefault();

    /*
    public Messages() {
        locale = Locale.getDefault();
        System.out.println("[DEBUG] Locale: " + locale);
    }

    public Messages(Locale locale) {
        Messages.locale = locale;
    }*/

    public static void setLocale(String loc) {
        locale = Locale.of(loc);
        System.out.println("[DEBUG] Locale: " + locale.getLanguage());
    }

    public static String goodbyeMessage() {
        String pattern;

        switch (locale.getLanguage()) {
            case "en":
                pattern = "Good Sailing!";
                break;
            case "pt":
                pattern = "Bons ventos!";
                break;
            default:
                pattern = "Bons ventos!";
        }

        return pattern;
    }

    public static String invalidCommand() {
        String pattern;

        switch (locale.getLanguage()) {
            case "en":
                pattern = "Invalid command!";
                break;
            case "pt":
                pattern = "Que comando é esse??? Repete ...";
                break;
            default:
                pattern = "Que comando é esse??? Repete ...";
        }

        return pattern;
    }

    /**
     * This function returns the caption for the game board in the current message language
     * @param markers - markers should be in the following order:
     *                markers[0] = SHIP_MARKER;
     *                markers[1] = SHIP_ADJACENT_MARKER;
     *                markers[2] = EMPTY_MARKER;
     *                markers[3] = SHOT_SHIP_MARKER;
     *                markers[4] = SHOT_WATER_MARKER;
     * @return String[size: 3] containing the 3 string that comprise the caption message
     */
    public static String[] boardCaptions(char shipMarker, char shipAdjacentMarker, char emptyMarker, char shotShipMarker, char shotWaterMarker) {
        String[] pattern;

        switch (locale.getLanguage()) {
            case "en":
               pattern = new String[]{"          CAPTION",
                       "'" + shipMarker + "'->ship, '" + shipAdjacentMarker + "'->ship adjacent, '" + emptyMarker + "'->water",
                       "'" + shotShipMarker + "'->Hit, '" + shotWaterMarker + "'->Missed shot"};
               break;
            case "pt":
                pattern = new String[]{"          LEGENDA",
                        "'" + shipMarker + "'->navio, '" + shipAdjacentMarker + "'->adjacente a navio, '" + emptyMarker + "'->água",
                        "'" + shotShipMarker + "'->Tiro certeiro, '" + shotWaterMarker + "'->Tiro na água"};
                break;
            default:
                pattern = new String[]{"          LEGENDA",
                        "'" + shipMarker + "'->navio, '" + shipAdjacentMarker + "'->adjacente a navio, '" + emptyMarker + "'->água",
                        "'" + shotShipMarker + "'->Tiro certeiro, '" + shotWaterMarker + "'->Tiro na água"};
        }

        return pattern;
    }
}
