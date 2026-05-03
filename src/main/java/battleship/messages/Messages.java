package battleship.messages;

import com.ibm.icu.text.MessageFormat;
import java.util.Locale;

/**
 * Class that defines international messages based on selected language
 */
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

    public static Locale LOCALE() {
        return locale;
    }

    public static void setLocale(String loc) {
        locale = Locale.of(loc);
        System.out.println("[DEBUG] Locale: " + locale.getLanguage());
    }

    /**
     * Function that returns the message to be sent when the game is terminated in the current language
     * @return String goodbye message
     */
    public static String goodbyeMessage() {
        String pattern;

        switch (locale.getLanguage()) {
            case "en":
                return "Good Sailing!";
            case "pt":
                return "Bons ventos!";
            default:
                return "Bons ventos!";
        }

        //return pattern;
    }

    /**
     * Function that returns the message for when an invalid input is given in the menu in the current language
     * @return String invalid command message
     */
    public static String invalidCommand() {
        String pattern;

        switch (locale.getLanguage()) {
            case "en":
                return "Invalid command!";
            case "pt":
                return "Que comando é esse??? Repete ...";
            default:
                return "Que comando é esse??? Repete ...";
        }

        //return pattern;
    }

    /**
     * This function returns the caption for the game board in the current message language
     * @param shipMarker - symbol for ships
     * @param shipAdjacentMarker - symbol for borders
     * @param emptyMarker - symbol for empty slots
     * @param shotShipMarker - symbol for hit (shot that hit a ship)
     * @param shotWaterMarker - symbol for missed shot (shot that hit water)
     * @return String[size:3] containing the content of the board caption
     */
    public static String[] boardCaptions(char shipMarker, char shipAdjacentMarker, char emptyMarker, char shotShipMarker, char shotWaterMarker) {
        String[] pattern;

        switch (locale.getLanguage()) {
            case "en":
               return new String[]{"          CAPTION",
                       "'" + shipMarker + "'->ship, '" + shipAdjacentMarker + "'->ship adjacent, '" + emptyMarker + "'->water",
                       "'" + shotShipMarker + "'->Hit, '" + shotWaterMarker + "'->Missed shot"};
            case "pt":
                return new String[]{"          LEGENDA",
                        "'" + shipMarker + "'->navio, '" + shipAdjacentMarker + "'->adjacente a navio, '" + emptyMarker + "'->água",
                        "'" + shotShipMarker + "'->Tiro certeiro, '" + shotWaterMarker + "'->Tiro na água"};
            default:
                return new String[]{"          LEGENDA",
                        "'" + shipMarker + "'->navio, '" + shipAdjacentMarker + "'->adjacente a navio, '" + emptyMarker + "'->água",
                        "'" + shotShipMarker + "'->Tiro certeiro, '" + shotWaterMarker + "'->Tiro na água"};
        }

        //return pattern;
    }

    public static String status(int floatingShips, int sunkenShips) {
        String pattern;

        switch (locale.getLanguage()) {
            case "en":
                pattern = "Fleet State: {0} floating, {1} sunken!";
                break;
            case "pt":
                pattern = "Estado da Frota: {0} a flutuar, {1} afundados!";
                break;
            default:
                pattern = "Estado da Frota: {0} a flutuar, {1} afundados!";
        }

        MessageFormat mf = new MessageFormat(pattern, locale);

        return mf.format(new Object[]{floatingShips, sunkenShips});
    }


}
