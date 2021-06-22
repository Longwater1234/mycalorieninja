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
    static String APIKEY = "";

    public static void main(String[] args) throws Exception {

        //-- First, Load the CalorieNinja APIKEY from local.properties file
        Properties prop = new Properties();
        FileInputStream fStream = new FileInputStream("local.properties");
        prop.load(fStream);
        APIKEY = prop.getProperty("APIKEY");
        fStream.close();

        System.out.print("Enter any food name: ");

        // -- Take user input
        Scanner input = new Scanner(System.in);
        userInput = input.nextLine();
        input.close();

        // -- Use the user-input to build a valid request URL
        MakeURI query = new MakeURI();
        URI _uri = query.makeLink(userInput);

        // -- Build the request call to the API
        HttpRequest request = HttpRequest.newBuilder()
                .uri(_uri).header("Content-Type", "application/json")
                .header("X-Api-Key", APIKEY)
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();

        // -- Send the request Async; Handle the response.
        var response = HttpClient.newHttpClient()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());

        int statusCode = response.get().statusCode();
        System.out.println("Status code: " + statusCode);

        if (statusCode == 200) {
            String json = response.get().body();

            JSONObject resultObject = new JSONObject(json);
            if (resultObject.getJSONArray("items").isEmpty()) {
                System.out.println("Sorry, no info for given food found!");
                System.exit(0);
            }

            Map<String, Object> nutrients = new HashMap<>();

            nutrients = resultObject.getJSONArray("items").getJSONObject(0).toMap();

            // --Build a readable report
            StringBuilder report = new StringBuilder();
            report.append("\n Nutritional information for ").append(userInput);
            report.append("\n------------------------------------\n");
            for (Map.Entry<String, Object> entry : nutrients.entrySet()) {
                report.append(String.format("%s:\t%s\n", entry.getKey(), entry.getValue()));
            }

            System.out.println(report);

            // --Print the report to a text file
            PrintFile file1 = new PrintFile();
            file1.setFileName("example1234.txt");
            file1.setfileContent(report.toString());
            file1.makeFile();

        } else {
            // request to API failed.
            System.out.printf("Error: %d %s", statusCode, response.get().body());
        }
    }
}

