/*
 *  Created by Gulzar Safar on 10/30/2020
 */


// https://www.cbar.az/currencies/26.09.2020.xml

package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utility {

    public static String createLink(LocalDate date){

        String base = "https://www.cbar.az/currencies/";;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String nowStr = date.format(formatter);


        String link = base + nowStr + ".xml";

        return link;
    }

}
