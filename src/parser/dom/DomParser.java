/*
 *  Created by Gulzar Safar on 10/30/2020
 */

package parser.dom;


import database.DatabaseQueries;
import model.Valute;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import util.Utility;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DomParser {

    public static void main(String[] args) {

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(Utility.createLink());


            NodeList root = document.getElementsByTagName("ValType");
            List<Valute> currencyList = new ArrayList<>();

            for (int j = 0; j < root.getLength(); j++) {

                Node node = root.item(j);
                Element rootNode = (Element) node;

                if (rootNode.getAttribute("Type").equals("Xarici valyutalar")) {


                    NodeList currencyNodeList2 = rootNode.getElementsByTagName("Valute");

                    DatabaseQueries.insertCurrencyDomParser(currencyNodeList2);

                } else {
                    continue;
                }

            }

        } catch (ParserConfigurationException | SAXException | IOException | SQLException e) {
            e.printStackTrace();
        }

    }

}

