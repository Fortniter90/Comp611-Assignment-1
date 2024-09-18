
import java.io.*;
import java.net.*;
import com.google.gson.*;

import static java.lang.System.exit;


public class RealTimeExchangeRate {
    public static HttpURLConnection RealTimeAPIConnect(String url_str) throws IOException {
        URL url = new URL(url_str); // Creates A URL Object
        return (HttpURLConnection) url.openConnection(); // Opens A Http Connection To The Url And Returns The Connection
    }

    public static void realTimeCases() throws IOException {
        String[] labels = CurrencyLabels.getLabels(); // Gets The Labels From CurrencyLabels With The getLabels Method
        // Initialise An NxN Adjacency Matrix
        // Using 'labels.length' The Number Of Vertices In The Graph
        double[][] realGraph = new double[labels.length][labels.length];
        System.out.println("Table Of Exchange Rates:");
        // Prints Out The Labels Of Each Currency
        for(int i = 0; i < realGraph.length; i++) {
            System.out.print(labels[i] + ", ");
        }
        System.out.println();
        // Creates The Graph With Real Exchange Rate API
        RealTimeExchangeRateGraphCreator(realGraph);
        System.out.println();

        double[][] graph = CurrencyExchange.createGraph(realGraph); // Coverts Graphs Values To Negative Logarithm
        CurrencyExchange.Options(graph); // Executes Exchange Options
    }

    public static void currencyChecker(String currency) {
        // URL For Exchange Rate API
        String url_str = "https://v6.exchangerate-api.com/v6/8e340cf015bea7077a8811e6/latest/USD";

        try {
            // Establishes A Connection Using API URL
            HttpURLConnection request = RealTimeAPIConnect(url_str);
            request.connect(); // Connects To API

            // Checks If The Response Code Is 200 (HTTP_OK) Meaning The Request Was Successful
            if (request.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Parse The Response To JSON Format
                try {
                    JsonParser jp = new JsonParser();
                    JsonElement root = JsonParser.parseReader(new InputStreamReader(request.getInputStream()));
                    JsonObject jsonobj = root.getAsJsonObject();

                    // Gets The Conversion Rates From The JSON Response
                    JsonObject conversionRates = jsonobj.getAsJsonObject("conversion_rates");

                    // Gets The Conversion Rate For Specified Currency
                    double rates = conversionRates.get(currency).getAsDouble();
                } catch (NullPointerException E) {
                    // If The Currency Is Not Found It Prints An Error Message
                    System.out.println("Error: Currency Is Either Not Real Or Not Available In The API");
                    exit(0);
                }
            }else {
                // Print An Error Message If The Request Was Not Successful
                System.out.println("Error in response: " + request.getResponseCode());
                exit(0);
            }
        } catch (IOException E) {
            // Handles IO Exceptions
            System.out.println("Error: " + E.getMessage());
            exit(0);
        }
    }

    public static void RealTimeExchangeRateGraphCreator(double[][] graph) throws IOException {
        // Loop Over The Graph Rows (Currencies)
        for (int i = 0; i < graph.length; i++) {
            String[] labels = CurrencyLabels.getLabels(); // Gets The Labels We Just Set From CurrencyLabels With The getLabels Method

            // URL For Exchange Rate API
            String url_str = "https://v6.exchangerate-api.com/v6/8e340cf015bea7077a8811e6/latest/" + labels[i];

            // Establishes A Connection Using API URL
            HttpURLConnection request = RealTimeAPIConnect(url_str);
            request.connect();

            // Checks If The Response Code Is 200 (HTTP_OK) Meaning The Request Was Successful
            if (request.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Parse The Response To JSON Format
                JsonParser jp = new JsonParser();
                JsonElement root = JsonParser.parseReader(new InputStreamReader(request.getInputStream()));
                JsonObject jsonobj = root.getAsJsonObject();

                // Gets The Conversion Rates From The JSON Response
                JsonObject conversionRates = jsonobj.getAsJsonObject("conversion_rates");

                // Loop Over The Graph Columns (Other Currencies) To Fill Conversion Rates
                for (int j = 0; j < graph.length; j++) {
                    // Retrieves The Conversion Rate For The Currency At Index j From The ConversionRates JSON Object
                    double rate = conversionRates.get(labels[j]).getAsDouble();
                    graph[j][i] = rate; // Update The Graph With The Conversion Rate For The Current Currency Pair
                    System.out.print(rate + ","); // Print The Conversion Rate For Visual
                }
                System.out.println();
            } else {
                // Print An Error Message If The Request Was Not Successful
                System.out.println("Error in response: " + request.getResponseCode());
            }
        }
    }
}
