/*
 *  Created by Gulzar Safar on 10/31/2020
 */

package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Valutes")
@XmlAccessorType(XmlAccessType.FIELD)

public class ValuteList {



        @XmlElement(name = "Valutes")
        private List<Valute> currencyList;

    public List<Valute> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<Valute> currencyList) {
        this.currencyList = currencyList;
    }
}



