/*
 *  Created by Gulzar Safar on 10/31/2020
 */

package main;

import generator.dom.DomGenerator;
import parser.dom.DomParser;
import java.time.LocalDate;
import java.util.Scanner;

public class DomMain {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String input;

        DomParser.domParser(LocalDate.now());


        System.out.println("Which date's currency do you want to get?");
        System.out.print("Please input in fromat yyyy-MM-dd : ");
        input = scanner.next();

        String fileName = DomGenerator.domGenerator(LocalDate.parse(input));

        System.out.println("You can find data from " + fileName);
    }
}
