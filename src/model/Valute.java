/*
 *  Created by Gulzar Safar on 10/30/2020
 */

package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Valute {

    private int id;
    private String code;
    private String nominal;
    private String name;
    private BigDecimal value;
    private LocalDate currencyDate;


    public Valute() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDate getCurrencyDate() {
        return currencyDate;
    }

    public void setCurrencyDate(LocalDate currencyDate) {
        this.currencyDate = currencyDate;
    }

    @Override
    public String toString() {
        return "Valute{" +
                "code='" + code + '\'' +
                ", nominal=" + nominal +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", currencyDate=" + currencyDate +
                '}';
    }
}
