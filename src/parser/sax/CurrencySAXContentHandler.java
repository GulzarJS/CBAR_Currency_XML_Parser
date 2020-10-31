/*
 *  Created by Gulzar Safar on 10/31/2020
 */

package parser.sax;


import model.Valute;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CurrencySAXContentHandler extends DefaultHandler {

    private List<Valute> currencyList;
    private Valute temp;
    private boolean isValType;
    private boolean isValute;
    private boolean isCode;
    private boolean isNominal;
    private boolean isName;
    private boolean isValue;
    private boolean isForeignValute = false;
    private int limit;
    private int counter = 0;

    public CurrencySAXContentHandler() {
        this.currencyList = new ArrayList<>();
        this.limit = 0;
    }

    public CurrencySAXContentHandler(int limit) {
        this();
        this.limit = limit;
    }

    public List<Valute> getCurrencyList() {
        return currencyList;
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Reading XML Document has started");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//        System.out.println("Has just started to read " + qName + " element");

        if(qName.equals("ValType")){
            isValType = true;
            for (int i = 0; i < attributes.getLength(); i++) {
                if(attributes.getValue("Type").equals("Xarici valyutalar")){
                    isForeignValute = true;
                }

            }
        } if(isForeignValute){
            if (qName.equals("Valute")) {
                if(isForeignValute) {
                    temp = new Valute();
                    temp.setCurrencyDate(LocalDate.now());
                    temp.setCode(attributes.getValue("Code"));
                }
                isValute = true;
            } else if (qName.equals("Nominal")) {
                isNominal = true;
            } else if (qName.equals("Name")) {
                isName = true;
            } else if (qName.equals("Value")) {
                isValue = true;
            }
        }
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String data = new String(ch, start, length);
//        System.out.println("Data which is read = " + data);

        if (isValType) {
            if(isForeignValute){
                if (isNominal) {
                    temp.setNominal(data);
                } else if (isName) {
                    temp.setName(data);
                } else if (isValue) {
                    temp.setValue(new BigDecimal(data));
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
//        System.out.println("Has just finished to read " + qName + " element");

        if(qName.equals("ValType")){
            isValType = false;
            isForeignValute = false;

        } if(isForeignValute) {
            if (qName.equals("Valute")) {


                if (counter < limit) {
                    currencyList.add(temp);
                }

                counter++;

                temp = null;
                isValute = false;
            } else if (qName.equals("Nominal")) {
                isNominal = false;
            } else if (qName.equals("Name")) {
                isName = false;
            } else if (qName.equals("Value")) {
                isValue = false;
            }
        }
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("Reading XML Document has finished");
    }


}
