/*
 *  Created by Gulzar Safar on 10/31/2020
 */

package generator.stax;


import database.DatabaseQueries;
import model.Valute;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class StaxGenerator {

    public static String staxGenerator(LocalDate date) {

        String xml = "stax-currency-"+ date +".xml";

        try {

            XMLOutputFactory factory = XMLOutputFactory.newFactory();
            List<Valute> currency = DatabaseQueries.selectCurrency(date);
            XMLStreamWriter xmlStreamWriter = factory.createXMLStreamWriter(new FileWriter(xml));

            xmlStreamWriter.writeStartDocument();

            xmlStreamWriter.writeStartElement("Valutes");

            currency.forEach(valute -> {
                try {
                    xmlStreamWriter.writeStartElement("Valute");
                    xmlStreamWriter.writeAttribute("Code", valute.getCode());

                    xmlStreamWriter.writeStartElement("Nominal");
                    xmlStreamWriter.writeCharacters(valute.getNominal());
                    xmlStreamWriter.writeEndElement();

                    xmlStreamWriter.writeStartElement("Name");
                    xmlStreamWriter.writeCharacters(valute.getName());
                    xmlStreamWriter.writeEndElement();

                    xmlStreamWriter.writeStartElement("Value");
                    xmlStreamWriter.writeCharacters(valute.getValue().toString());
                    xmlStreamWriter.writeEndElement();


                    xmlStreamWriter.writeEndElement();
                } catch (XMLStreamException e) {
                    e.printStackTrace();
                }
            });

            xmlStreamWriter.writeEndElement();

            xmlStreamWriter.writeEndDocument();

            xmlStreamWriter.flush();
            xmlStreamWriter.close();



        } catch (XMLStreamException | IOException | SQLException  e) {
            e.printStackTrace();
        }
     return xml;
    }
}
