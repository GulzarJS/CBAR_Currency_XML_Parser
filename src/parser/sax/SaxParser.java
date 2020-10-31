/*
 *  Created by Gulzar Safar on 10/31/2020
 */

package parser.sax;



import database.DatabaseQueries;
import model.Valute;
import org.xml.sax.SAXException;
import util.Utility;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.ListIterator;

public class SaxParser {

    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            javax.xml.parsers.SAXParser parser = factory.newSAXParser();

            String xml = Utility.createLink();
            CurrencySAXContentHandler handler = new CurrencySAXContentHandler(50);
            parser.parse(xml, handler);

            List<Valute> currencyList = handler.getCurrencyList();



            DatabaseQueries.insertCurrencySaxStaxParser(currencyList);


        } catch (ParserConfigurationException | SAXException | IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}

