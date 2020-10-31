/*
 *  Created by Gulzar Safar on 10/31/2020
 */

package generator.dom;

import database.DatabaseQueries;
import model.Valute;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DomGenerator {

    public static String domGenerator(LocalDate date) {

        String fileName = "dom-currency-" + date + ".xml";

        try {
            List<Valute> currency = DatabaseQueries.selectCurrency(date);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.newDocument();

            Element currencyElement = document.createElement("Valutes");

            currency.forEach(valute -> {

                Element valuteElement = document.createElement("Valute");
                valuteElement.setAttribute("Code", valute.getCode());


                Element nominalElement = document.createElement("Nominal");
                System.out.println(valute.getNominal());
                nominalElement.setTextContent(valute.getNominal());
                valuteElement.appendChild(nominalElement);

                Element nameElement = document.createElement("Name");
                nameElement.setTextContent(valute.getName());
                valuteElement.appendChild(nameElement);

                Element valueElement = document.createElement("Value");
                valueElement.setTextContent(valute.getValue().toString());
                valuteElement.appendChild(valueElement);

                currencyElement.appendChild(valuteElement);
            });

            document.appendChild(currencyElement);


            // save document to xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");

            transformer.transform(
                    new DOMSource(document),
                    new StreamResult(new FileWriter(fileName)));



        } catch (ParserConfigurationException | TransformerException | SQLException | IOException e) {
            e.printStackTrace();
        }

        return fileName;

    }
}
