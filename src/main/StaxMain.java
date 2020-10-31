/*
 *  Created by Gulzar Safar on 10/31/2020
 */

package main;


import generator.stax.StaxGenerator;
import parser.stax.StaxParser;
import java.time.LocalDate;
import java.util.Scanner;

public class StaxMain {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String input;

        StaxParser.staxParser(LocalDate.now());


        System.out.println("Which date's currency do you want to get?");
        System.out.print("Please input in fromat yyyy-MM-dd : ");
        input = scanner.next();

        String fileName = StaxGenerator.staxGenerator(LocalDate.parse(input));

        System.out.println("You can find data from " + fileName);
    }
}
