import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.exit;

public class DemoCases {
    // NZD, GBP, JPY, AUD, CAD, CHF, HKD
    public static void demoCases() {
        double[][] demoGraph1 = {
                // Arbitrage Expected
                // NZD, GBP, JPY, AUD, CAD
                {1.0000, 1.2500, 1.5000, 135.0000, 1.3500},
                {0.8000, 1.0000, 1.2000, 108.0000, 1.0800},
                {0.6667, 0.8333, 1.0000, 90.0000, 0.9000},
                {0.0074, 0.0093, 0.0111, 1.0000, 0.0100},
                {0.7407, 0.9259, 1.1111, 100.0000, 1.0000}
        };

        double[][] demoGraph2 = {
                // Arbitrage Not Expected
                // NZD, GBP, JPY, AUD, CAD
                {1, 1.1, 1.2, 110, 1.3},
                {0.0, 1, 1.09, 100, 1.18},
                {0.0, 0.0, 1, 91, 1.08},
                {0.0, 0.0, 0.0, 1, 0.0118},
                {0.0, 0.0, 0.0, 0.0, 1}
        };

        double[][] demoGraph3 = {
                // Arbitrage Not Expected
                // NZD, GBP, JPY, AUD, CAD, CHF, HKD
                {0.000000000000, 0.917431192661, 0.782608695652, 145.652173913043, 1.347826086957, 1.521739130435, 0.869565217391},
                {0.000000000000, 0.000000000000, 0.853333333333, 158.771929824561, 1.469230769231, 1.658974358974, 0.947692307692},
                {0.000000000000, 0.000000000000, 0.000000000000, 186.111111111111, 1.722222222222, 1.944444444444, 1.111111111111},
                {0.000000000000, 0.000000000000, 0.000000000000, 0.000000000000, 0.009253731343, 0.010447761194, 0.005970149254},
                {0.000000000000, 0.000000000000, 0.000000000000, 0.000000000000, 0.000000000000, 1.129032258065, 0.645161290323},
                {0.000000000000, 0.000000000000, 0.000000000000, 0.000000000000, 0.000000000000, 0.000000000000, 0.571428571429},
                {0.000000000000, 0.000000000000, 0.000000000000, 0.000000000000, 0.000000000000, 0.000000000000, 0.000000000000}
        };

        double[][] demoGraph4 = {
                // Arbitrage Expected
                // NZD, GBP, JPY, AUD, CAD, CHF, HKD
                {1.000000000000, 0.917431192661, 0.782608695652, 145.652173913043, 1.347826086957, 1.521739130435, 0.869565217391},
                {1.090000000000, 1.000000000000, 0.853333333333, 158.771929824561, 1.469230769231, 1.658974358974, 0.947692307692},
                {1.277777777778, 1.171875000000, 1.000000000000, 186.111111111111, 1.722222222222, 1.944444444444, 1.111111111111},
                {0.006865284974, 0.006298185941, 0.005373134328, 1.000000000000, 0.009253731343, 0.010447761194, 0.005970149254},
                {0.741935483871, 0.680555555556, 0.580645161290, 108.064516129032, 1.000000000000, 1.129032258065, 0.645161290323},
                {0.657142857143, 0.602857142857, 0.514285714286, 95.714285714286, 0.885714285714, 1.000000000000, 0.571428571429},
                {1.150000000000, 1.055000000000, 0.900000000000, 167.500000000000, 1.550000000000, 1.750000000000, 1.000000000000}
        };

        // CLI For Demo Cases
        System.out.println("\nDemoCases");
        System.out.println("1. DemoCase1 (Arbitrage Expected)");
        System.out.println("2. DemoCase2 (No Arbitrage Expected)");
        System.out.println("3. DemoCase3 (No Arbitrage Expected)");
        System.out.println("4. DemoCase4 (Arbitrage Expected)");
        boolean arbitrageFound = false; // Tracks If Arbitrage Has Been Found
        Scanner scanner = new Scanner(System.in);
        try {
            int choice = scanner.nextInt(); // Takes In The Users Choice
            scanner.nextLine(); // Consumes NewLine

            switch (choice) {
                case 1:
                    // Handles First DemoCase
                    double[][] graph1 = CurrencyExchange.createGraph(demoGraph1); // Coverts Graphs Values To Negative Logarithm
                    for (int i = 0; i < graph1.length; i++) {
                        // Checks If A Certain Currency Has An Arbitrage Opportunity
                        if (DetectArbitrage.detectArbitrage(graph1, i)) {
                            arbitrageFound = true;
                            break;
                        }
                    }
                    // If Arbitrage Is Not Found It Will Execute FindBestExchangeRate
                    FindBestExchange.arbitrageFound(arbitrageFound, true, graph1);
                    break;
                case 2:
                    // Handles Second DemoCase
                    double[][] graph2 = CurrencyExchange.createGraph(demoGraph2); // Coverts Graphs Values To Negative Logarithm
                    for (int i = 0; i < graph2.length; i++) {
                        if (DetectArbitrage.detectArbitrage(graph2, i)) {
                            arbitrageFound = true;
                            break;
                        }
                    }
                    // If Arbitrage Is Not Found It Will Execute FindBestExchangeRate
                    FindBestExchange.arbitrageFound(arbitrageFound, true, graph2);
                    break;
                case 3:
                    // Handles Third DemoCase
                    double[][] graph3 = CurrencyExchange.createGraph(demoGraph3); // Coverts Graphs Values To Negative Logarithm
                    for (int i = 0; i < graph3.length; i++) {
                        if (DetectArbitrage.detectArbitrage(graph3, i)) {
                            arbitrageFound = true;
                            break;
                        }
                    }
                    // If Arbitrage Is Not Found It Will Execute FindBestExchangeRate
                    FindBestExchange.arbitrageFound(arbitrageFound, true, graph3);
                    break;
                case 4:
                    // Handle Fourth DemoCase
                    double[][] graph4 = CurrencyExchange.createGraph(demoGraph4); // Coverts Graphs Values To Negative Logarithm
                    for (int i = 0; i < graph4.length; i++) {
                        if (DetectArbitrage.detectArbitrage(graph4, i)) {
                            arbitrageFound = true;
                            break;
                        }
                    }
                    // If Arbitrage Is Not Found It Will Execute FindBestExchangeRate
                    FindBestExchange.arbitrageFound(arbitrageFound, true, graph4);
                    break;
                default:
                    // Handles Invalid Choice
                    System.out.println("Invalid choice. Please try again.");
                    exit(0);
            }
            // Handles Non-Integer Inputs
        } catch (InputMismatchException E) {
            System.out.println("Invalid input, select an option from the menu. ");
            exit(0);
        }
    }
}
