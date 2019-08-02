
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;


public class Connections {


    public static void createGame(){
        URL url = null;
        try {
            url = new URL("https://www.dragonsofmugloar.com/api/v2/game/start");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String response = getResponse(url,"POST");
        if (response!=null){
            createGameObject(response);
        }

    }

    public static void createGameObject(String jsonString){
        JSONObject jsonObjectMessageResponse = new JSONObject(jsonString);
        Main.GAME_ID = jsonObjectMessageResponse.getString("gameId");
        Main.HIGHSCORE = jsonObjectMessageResponse.getInt("highScore");
    }

    public static List<Message> getMessages(){
        URL url = null;
        try {
            url = new URL("https://dragonsofmugloar.com/api/v2/" + Main.GAME_ID + "/messages");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String response = getResponse(url, "GET");
        if (response!=null){
            return createMessageObjects(response);
        }
        return null;

    }
    public static String getResponse(URL url,String method){
        String response = null;
        try {
            response = sendGet(url,method);
            if (response!=(null)){
                return response;

            }else {
                System.out.println("Failed to create connection");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static List<Message> createMessageObjects(String jsonString){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Arrays.asList(mapper.readValue(jsonString, Message[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void solveMessage(){
        URL url = null;
        try {
            url = new URL("https://dragonsofmugloar.com/api/v2/"+Main.GAME_ID+"/solve/"+ Main.MESSAGE_ID);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String response = getResponse(url,"POST");
        if (response!=null){
            solveMessageResponse(response);
        }
    }
    public static void purchaseItem(String objectToBuy){
        URL url = null;
        try {
            url = new URL("https://dragonsofmugloar.com/api/v2/"+Main.GAME_ID+"/shop/buy/"+objectToBuy);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String response = getResponse(url,"POST");
        if (response!=null){
            JSONObject jsonObjectMessageResponse = new JSONObject(response);
            if (jsonObjectMessageResponse.getBoolean("shoppingSuccess")){
                if (objectToBuy.equals("hpot")){
                    System.out.println("You have bought Healing Potion \n");
                } else {
                    System.out.println("You have bought an item \n");
                    Main.LEVEL = jsonObjectMessageResponse.getInt("level");
                }

                Main.GOLD = jsonObjectMessageResponse.getInt("gold");
                Main.LIVES = jsonObjectMessageResponse.getInt("lives");
            }else {
                System.out.println("You have failed to buy an item\n");
            }
        }
    }

    public static void solveMessageResponse(String jsonString){
        JSONObject jsonObjectMessageResponse = new JSONObject(jsonString);
        System.out.println(jsonObjectMessageResponse.getString("message"));
        Main.GOLD = jsonObjectMessageResponse.getInt("gold");
        Main.LIVES = jsonObjectMessageResponse.getInt("lives");
        Main.SCORE = jsonObjectMessageResponse.getInt("score");
        Main.TURN = jsonObjectMessageResponse.getInt("turn");


    }

    public static String sendGet(URL url, String method) throws Exception {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);

        int responseCode = con.getResponseCode();

        if (responseCode == 200) {

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        }
    return null;
    }

}
