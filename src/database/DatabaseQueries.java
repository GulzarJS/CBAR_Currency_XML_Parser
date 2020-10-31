/*
 *  Created by Gulzar Safar on 10/30/2020
 */

package database;

import model.Valute;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ListIterator;

import static java.sql.Date.valueOf;

public class DatabaseQueries {

    public static void insertCurrencyDomParser(NodeList currencyNodeList) throws SQLException {


        Database database = new Database();
        String sql;

        try {
            sql = "insert into currency( id, code, nominal, name, value, currency_date)" +
                    " values (CURRENCY_SEQ.nextval, ?, ?, ?, ?, ?)";

            for (int i = 0; i < currencyNodeList.getLength(); i++) {
                Element currencyElement = (Element) currencyNodeList.item(i);

                database.setPs(database.getConnection().prepareStatement(sql));

                database.getPs().setString(1, currencyElement.getAttribute("Code"));
                database.getPs().setInt(2, Integer.parseInt(currencyElement.getElementsByTagName("Nominal").item(0).getTextContent()));
                database.getPs().setString(3, currencyElement.getElementsByTagName("Name").item(0).getTextContent());
                database.getPs().setBigDecimal(4, new BigDecimal(currencyElement.getElementsByTagName("Value").item(0).getTextContent()));
                database.getPs().setDate(5, valueOf(LocalDate.now()));



                database.getPs().executeUpdate();
                database.getConnection().commit();
            }


        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.close();

        }
    }


    // Function for inserting information about seeds => for DOM Parser
    public static void insertCurrencySaxStaxParser(List<Valute> currencyNodeList) throws SQLException {



        Database database = new Database();
        String sql;

        try {
            sql = "insert into currency( id, code, nominal, name, value, currency_date)" +
                    " values (CURRENCY_SEQ.nextval, ?, ?, ?, ?, ?)";


            ListIterator<Valute> currencyIterator = currencyNodeList.listIterator();

            while(currencyIterator.hasNext()){

                Valute temp = currencyIterator.next();

                database.setPs(database.getConnection().prepareStatement(sql));

                database.getPs().setString(1, temp.getCode());
                database.getPs().setInt(2, Integer.parseInt(temp.getNominal()));
                database.getPs().setString(3,temp.getName());
                database.getPs().setBigDecimal(4,  temp.getValue());
                database.getPs().setDate(5, valueOf(temp.getCurrencyDate()));



                database.getPs().executeUpdate();
                database.getConnection().commit();
            }

        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.close();

        }
    }

}
