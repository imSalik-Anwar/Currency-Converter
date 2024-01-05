import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import javax.management.RuntimeErrorException;

public class Operations {
    public static void convert(String[] args) {
        // validate arguments
        if(!ValidateArguments.isConvertArgumentValid(args)){
            throw new InvalidArgumentsException("Command not found. Enter valid command.");
        }
        // convert currency codes to uppercase to maintain consistancy
        convertCurrencyCodesToUppercase(args);
        // prepare URL
        String url = prepareURL(args);

        try {
            // get exhange rate from third-party API
            String responseBody = getResponse(url);
            // print exchange value
            int n = responseBody.length();
            boolean flag = false;
            for(int i = 150; i <= n-3 ; i++){
                if(responseBody.substring(i, i+3).equals(args[2])){
                    System.out.println("------------------------");
                    System.out.println("source   :   "+args[1]);
                    System.out.println(args[2]+"      :   "+responseBody.substring(i+5, i+12));
                    System.out.println("------------------------");
                    System.out.println("*"+args[2]+" "+responseBody.substring(i+5, i+13)+" for 1 "+args[1]);
                    System.out.println("------------------------");
                    System.out.println();
                    flag = true;
                    break;
                }
            }
            if(!flag){ // in case upper loop didn't execute
                System.out.println("You might have entered wrong currency code. Try Again.");
                System.out.println();
            }

        } catch (Exception e) {
            System.out.println("Some unknown error occured. Please try again.");
            System.out.println();
        }

    }

    public static void addFavorite(HashMap<String, String> favoriteCurrencies, String[] args){
        // validate arguments
        if(!ValidateArguments.isAddArgumentValid(args)){
            throw new InvalidArgumentsException("Command not found. Enter valid command.");
        }
        // convert currency codes to uppercase to maintain consistancy
        convertCurrencyCodesToUppercase(args);
        if(args[2].equals("USD")){
            System.out.println("Base currency can not be added as favorite.");
            System.out.println();
            return;
        }
        // prepare URL
        String url = prepareURLKeepingSourceUSD(args);
                    
        try {
            // get exchange rate from third-party API
            String responseBody = getResponse(url);
            // extract favorite currency and exchange rate to add to favorite currencies' list
            int n = responseBody.length();
            boolean flag = false;
            String favoriteCurrency = args[2];
            for(int i = 150; i <= n-3 ; i++){
                if(responseBody.substring(i, i+3).equals(favoriteCurrency)){
                    favoriteCurrencies.put(favoriteCurrency, responseBody.substring(i+5, i+13));
                    flag = true;
                    break;
                }
            }
            if(!flag){ // in case upper loop didn't execute
                System.out.println("You might have entered wrong currency code. Try Again.");
                System.out.println();
            }

        } catch (Exception e) {
            System.out.println("Some unknown error occured. Please try again.");
            System.out.println();
            // e.printStackTrace();
        }
    }

    public static void removeFavorite(HashMap<String, String> favoriteCurrencies, String[] args){
        // validate arguments
        if(!ValidateArguments.isRemoveArgumentValid(args)){
            throw new InvalidArgumentsException("Command not found. Enter valid command.");
        }
        // validate if favorite currencies' list isn't already empty
        if(favoriteCurrencies.isEmpty()){
            throw new EmptyFavoriteListException("Your favorite currencies' list is already empty.");
        }
        // convert currency codes to uppercase to maintain consistancy
        convertCurrencyCodesToUppercase(args);
        // validate if requested currency is on the list
        if(!favoriteCurrencies.containsKey(args[2])){
            throw new EmptyFavoriteListException("Your favorite currencies' list does not contain the requested currency.");
        }
        // if everything is fine, remove requested currency
        favoriteCurrencies.remove(args[2]);
    }

    public static void listFavorite(HashMap<String, String> favoriteCurrencies, String[] args){
        // validate arguments
        if(!ValidateArguments.isListArgumentValid(args)){
            throw new InvalidArgumentsException("Command not found. Enter valid command.");
        }
        // validate if favorite currencies list is not empty
        if(favoriteCurrencies.isEmpty()){
            throw new EmptyFavoriteListException("Favorite currencies' list is empty.");
        }
        // print all favorite currencies
        System.out.println("------------------------------------------");
        System.out.println("Currencies      Exchange rates");
        System.out.println("------------------------------------------");
        for(String currency : favoriteCurrencies.keySet()){
            System.out.println(currency+"         :   "+favoriteCurrencies.get(currency));
        }
        System.out.println("------------------------------------------");
        System.out.println("Note: Exhange rates are given against USD.");
        System.out.println("------------------------------------------");
        System.out.println();
    }

    public static void listCommands(String[] args) {
        // validate arguments
        if(!ValidateArguments.isListCommandArgumentValid(args)){
            throw new InvalidArgumentsException("Command not found. Enter valid command.");
        }
        // print all arguments
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Commands                Functions");
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("init cc app         :   runs the currency converter [cc] application.");
        System.out.println("convert USD INR     :   converts source currency [USD] into target currency [INR].");
        System.out.println("add fav USD         :   adds the given [USD] currency to the favorite currencies' list.");
        System.out.println("remove fav INR      :   deletes requested currency [INR] from favorite currencies' list.");
        System.out.println("list fav cur        :   lists all favorite currencies.");
        System.out.println("list cc commands    :   lists all the currency converter [cc] application commands.");
        System.out.println("stop cc app         :   stops the corrency converter [cc] application.");
        System.out.println();
    }

    public static void convertCurrencyCodesToUppercase(String[] args){
        for(int i = 1; i < 3; i++){
            args[i] = args[i].toUpperCase();
        }       
    }

    public static String prepareURL(String[] args){
        String baseURL = "http://apilayer.net/api/live";
        String accessKey = "4941b552a490fe4ab105f8359f8fd874";
        String target = args[2];
        String source = args[1];
        String url = baseURL + "?access_key="+accessKey + "&currencies="+target + "&source="+source + "&format=1";

        return url;
    }

    public static String prepareURLKeepingSourceUSD(String[] args){
        String baseURL = "http://apilayer.net/api/live";
        String accessKey = "4941b552a490fe4ab105f8359f8fd874";
        String target = args[2];
        String url = baseURL + "?access_key="+accessKey + "&currencies="+target + "&source=USD" + "&format=1";

        return url;
    }

    public static String getResponse(String url) throws IOException, InterruptedException{
            // Create an instance of HttpClient
            HttpClient httpClient = HttpClient.newHttpClient();

            // Create an HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")  // Set headers if needed
                .build();
            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
    }
}
