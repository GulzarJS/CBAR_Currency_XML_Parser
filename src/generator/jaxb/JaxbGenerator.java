/*
 *  Created by Gulzar Safar on 10/31/2020
 */

package generator.jaxb;

import database.DatabaseQueries;
import model.Valute;
import model.ValuteList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class JaxbGenerator {

    public static String jaxbGenerator(LocalDate date) {

        String xml ="sax-jaxb-currency-" + date +".xml";

        try {
            List<Valute> valuteList = DatabaseQueries.selectCurrency(date);
            ValuteList currencyList = new ValuteList();
            currencyList.setCurrencyList(valuteList);


            JAXBContext context = JAXBContext.newInstance(ValuteList.class);
            Marshaller marshaller = context.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(currencyList, writer);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);


            // save document to xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");

            transformer.transform(
                    new StreamSource(new StringReader(writer.toString())),
                    new StreamResult(new FileWriter(xml)));

        } catch (JAXBException | IOException | TransformerException | SQLException e) {
            e.printStackTrace();
        }
        return xml;

    }
}
