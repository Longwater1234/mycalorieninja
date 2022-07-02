package org.davistiba;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author davis tibbz, 2021
 */
public class App {
    static String userInput;

    public static void main(String[] args) throws Exception {

        /*  First, Load the CalorieNinja APIKEY from local.properties file  */
        Properties prop = new Properties();
        FileInputStream inputStream = new FileInputStream("local.properties");
        prop.load(inputStream);
        final String APIKEY = prop.getProperty("API-KEY", null);
        inputStream.close();

        System.out.print("Enter any food name: ");

        // Take user input
        Scanner input = new Scanner(System.in);
        userInput = input.nextLine();
        input.close();

        // Use the input to build a valid request URL
        final URI _uri = MakeURI.makeLink(userInput);

        // Build the request call to the API
        HttpRequest request = HttpRequest.newBuilder()
                .uri(_uri).header("X-Api-Key", APIKEY)
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();

        // -- Send the request, Handle the response.
        var response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        final int statusCode = response.statusCode();
        System.out.println("Status code: " + statusCode);

        if (statusCode == 200) {
            JSONObject resultObject = new JSONObject(response.body());
            if (resultObject.getJSONArray("items").isEmpty()) {
                System.out.println("Sorry, no info for given food found!");
                System.exit(0);
            }

            Map<String, Object> nutrients = resultObject.getJSONArray("items").getJSONObject(0).toMap();

            // --Build a readable report
            StringBuilder report = new StringBuilder();
            report.append("\n Nutritional information for ").append(userInput);
            report.append("\n------------------------------------\n");
            for (Map.Entry<String, Object> entry : nutrients.entrySet()) {
                report.append(String.format("%s:\t%s\n", entry.getKey(), entry.getValue()));
            }

            System.out.println(report);

            // --Print the report to a text file
            FilePrinter printer = new FilePrinter();
            printer.setFileName(String.format("output_%d.txt", System.currentTimeMillis()));
            printer.setFileContent(report.toString());
            printer.makeFile();

        } else {
            // request to API failed.
            System.out.printf("Error: %d %s", statusCode, response.body());
        }
    }
}
