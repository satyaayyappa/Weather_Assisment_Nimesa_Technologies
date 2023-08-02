import java.io.*;
import java.util.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherApp {

    private static final String API_URL = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int option;
        do {
            System.out.println("Please select an option:");
            System.out.println("1. Get weather");
            System.out.println("2. Get Wind Speed");
            System.out.println("3. Get Pressure");
            System.out.println("0. Exit");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    getWeather(scanner);
                    break;
                case 2:
                    getWindSpeed(scanner);
                    break;
                case 3:
                    getPressure(scanner);
                    break;
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (option != 0);

        scanner.close();
    }

    private static void getWeather(Scanner scanner) {
        System.out.print("Enter the date (yyyy-MM-dd HH:mm:ss): ");
        String inputDate = scanner.next();

        JSONObject weatherData;
        JSONArray list;
		try {
			 weatherData = fetchDataFromAPI();
			list = weatherData.getJSONArray("list");
			for (int i = 0; i < list.length(); i++) {
	            JSONObject weatherInfo = list.getJSONObject(i);
	            String dt_txt = weatherInfo.getString("dt_txt");
	            if (dt_txt.contains(inputDate)) {
	                JSONObject main = weatherInfo.getJSONObject("main");
	                double temp = main.getDouble("temp");
	                System.out.println("Temperature on " + dt_txt + ": " + temp + " °C");
	                return;
	            }
	        }
	        System.out.println("No data available for the input date.");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    private static void getWindSpeed(Scanner scanner) {
        System.out.print("Enter the date (yyyy-MM-dd HH:mm:ss): ");
        String inputDate = scanner.next();

        JSONObject weatherData;
        JSONArray list;
		try {
			 weatherData = fetchDataFromAPI();
			list = weatherData.getJSONArray("list");
			for (int i = 0; i < list.length(); i++) {
	            JSONObject weatherInfo = list.getJSONObject(i);
	            String dt_txt = weatherInfo.getString("dt_txt");
	            if (dt_txt.contains(inputDate)) {
	                JSONObject wind = weatherInfo.getJSONObject("wind");
	                double speed = wind.getDouble("speed");
	                System.out.println("Wind Speed on " + dt_txt + ": " + speed + " m/s");
	                return;
	            }
	        }
	        System.out.println("No data available for the input date.");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    private static void getPressure(Scanner scanner) {
        System.out.print("Enter the date (yyyy-MM-dd HH:mm:ss): ");
        String inputDate = scanner.next();

        JSONObject weatherData;
        JSONArray list;
		try {
			weatherData = fetchDataFromAPI();
			list = weatherData.getJSONArray("list");
			for (int i = 0; i < list.length(); i++) {
	            JSONObject weatherInfo = list.getJSONObject(i);
	            String dt_txt = weatherInfo.getString("dt_txt");
	            if (dt_txt.contains(inputDate)) {
	                JSONObject main = weatherInfo.getJSONObject("main");
	                double pressure = main.getDouble("pressure");
	                System.out.println("Pressure on " + dt_txt + ": " + pressure + " hPa");
	                return;
	            }
	        }
	        System.out.println("No data available for the input date.");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    private static JSONObject fetchDataFromAPI() throws JSONException {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed: HTTP error code: " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            conn.disconnect();
            return new JSONObject(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
