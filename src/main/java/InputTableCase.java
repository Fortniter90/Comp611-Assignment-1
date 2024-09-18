import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.exit;

public class InputTableCase {
    public static void inputTableCase() {
        System.out.println("1. Custom Currencies");
        System.out.println("2. Real-Word Currencies");
        Scanner scanner = new Scanner(System.in);
        try {
            int choice = scanner.nextInt(); // Takes In The Users Choice
            scanner.nextLine(); // Consumes NewLine

            switch (choice) {
                case 1:
                    // Executes Input Table Case (Custom Currencies So IsReal Is Set To False)
                    inputTableCase(false);
                case 2:
                    // Executes Input Table Case ( Using Real Currencies So IsReal Is Set To True)
                    inputTableCase(true);
                default:
                    // Handles Invalid Choices
                    System.out.println("Invalid choice. Please try again.");
                    exit(0);
            }
        } catch (InputMismatchException | IOException E) {
            // Handles Non-Integer Values
            System.out.println("Invalid input, select an option from the menu. ");
        }
    }

    public static void inputTableCase(boolean isReal) throws IOException {
        int numberOfCurrencies = 0; // Initialises The Number Of Currencies As 0
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("How many currencies would you like to look at (max 10)");
            numberOfCurrencies = scanner.nextInt();// Takes In The Amount Of Currencies The User Wants To Look At
            scanner.nextLine(); // Consumes NewLine

            if (numberOfCurrencies > 10) {
                //Handles Invalid Integer Input
                System.out.println("Invalid input please enter a integer that is 10 or below");
            }
        } catch (InputMismatchException E) {
            // Handles Non-Integer Input
            System.out.println("Invalid input please enter a integer");
            exit(0);
        }
        // Initialise An NxN Adjacency Matrix
        // Using 'numberOfCurrencies' The Number Of Vertices In The Graph
        double[][] inputGraph = new double[numberOfCurrencies][numberOfCurrencies];
        String[] labels = new String[numberOfCurrencies]; // Initialises A Labels Array The Length Being The Number
        try {
            System.out.println("Please Label Your " + numberOfCurrencies + " Currencies");
            // Prompts User To Label All Of Their Currencies
            for (int i = 0; i < numberOfCurrencies; i++) {
                System.out.println("Please Enter Currency " + (i + 1) + ": ");
                labels[i] = scanner.nextLine(); // Takes In Users Currency
                if(isReal){
                    // If User Chose Real Currencies It Will Check If The Inputted Currency Is Real
                    RealTimeExchangeRate.currencyChecker(labels[i]);
                }
                // Sets The New Labels
                CurrencyLabels.setLabels(labels);
            }
        } catch (InputMismatchException E) {
            // Handles Invalid Labels
            System.out.println("Error That Label Is Not Accepted Please Try Again");
        }

        if(isReal){
            // Executes RealTimeCase If User Picked Real-Word Currencies
            RealTimeExchangeRate.realTimeCases();
        }else {
            try {
                String[] Labels = CurrencyLabels.getLabels(); // Gets The Labels We Just Set From CurrencyLabels With The getLabels Method
                // Prompts User To Input Exchange Rates Of Custom Currencies
                for (int i = 0; i < numberOfCurrencies; i++) {
                    for (int j = 0; j < numberOfCurrencies; j++) {
                        if (i != j) {
                            System.out.println("Please Enter Rate From  " + Labels[i] + " to " + Labels[j] + " (It Is A Double)");
                            inputGraph[i][j] = scanner.nextDouble(); // Takes In Exchange Rate Of Currency 1 To Currency 2
                        } else {
                            inputGraph[i][j] = 1; // Sets The Currencies Self Vertex To 1
                        }
                    }
                }
            } catch (InputMismatchException E) {
                // Handles Invalid Rates
                System.out.println("Error That Rate Is Not Accepted Please Try Again");
                exit(0);
            }

            double[][] graph = CurrencyExchange.createGraph(inputGraph); // Coverts Graphs Values To Negative Logarithm
            CurrencyExchange.Options(graph); // Executes Exchange Options
        }
    }
}
