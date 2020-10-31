/*
 *  Created by Gulzar Safar on 10/31/2020
 */

package parser.stax;


import database.DatabaseQueries;
import model.Valute;
import util.Utility;

import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StaxParser {

    public static void main(String[] args) {
        try {
            URL xml = new URL(Utility.createLink());
            InputStream in = xml.openStream();
            XMLInputFactory factory = XMLInputFactory.newFactory();
            XMLStreamReader reader = factory.createXMLStreamReader(in);

            List<Valute> currencyList = new ArrayList<>();
            Valute temp = null;
             boolean isValType = false;
             boolean isValute = false;
             boolean isCode= false;
             boolean isNominal = false;
             boolean isName = false;
             boolean isValue = false;
             boolean isForeignValute = false;
            int counter = 0;
            int limit = 50;

            int event = reader.getEventType();

            while (true) {

                if (event == XMLStreamConstants.START_DOCUMENT) {
                    System.out.println("Reading XML Document has started");
                } else if (event == XMLStreamConstants.END_DOCUMENT) {
                    System.out.println("Reading XML Document has finished");
                } else if (event == XMLStreamConstants.START_ELEMENT) {
                    String name = reader.getName().toString();
                    if(name.equals("ValType")){
                        String value = reader.getAttributeValue(0);
                        if(value.equals("Xarici valyutalar")){
                            isForeignValute = true;
                        }
                    }else{
                        if(isForeignValute){
                            switch (name) {
                                case "Valute":
                                    temp = new Valute();
                                    temp.setCurrencyDate(LocalDate.now());
                                    isValute = true;
                                    String code = reader.getAttributeValue(0);
                                    temp.setCode(code);
                                    break;
                                case "Nominal":
                                    isNominal = true;
                                    break;
                                case "Name":
                                    isName = true;
                                    break;
                                case "Value":
                                    isValue = true;
                                    break;

                            }
                        }
                    }

                } else if (event == XMLStreamConstants.CHARACTERS) {

                    if(isForeignValute){
                        if(isNominal){
                            temp.setNominal(reader.getText());
                        }else if(isName){
                            temp.setName(reader.getText());
                        }else if(isValue){
                            temp.setValue(new BigDecimal(reader.getText()));
                        }
                    }

                } else if (event == XMLStreamConstants.END_ELEMENT) {
                    String name = reader.getName().toString();
                    if(name.equals("ValType")){
                        isValType = false;
                        isForeignValute = false;
                    }else if(isForeignValute){
                        if (name.equals("Valute")) {
                            isValute = false;

                            if (counter < limit) {
                                currencyList.add(temp);
                                counter++;
                            } else {
                                break;
                            }

                            temp = null;
                        } else if (name.equals("Nominal")) {
                            isNominal = false;
                        } else if (name.equals("Name")) {
                            isName = false;
                        } else if (name.equals("Value")) {
                            isValue = false;
                        }
                    }

                }

                if(!reader.hasNext()){
                    break;
                }
                event = reader.next();

            }



            DatabaseQueries.insertCurrencySaxStaxParser(currencyList);



        } catch (XMLStreamException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
