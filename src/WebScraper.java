import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


public class WebScraper {
	ArrayList<Integer> hourlyTemperatures = new ArrayList<Integer>();
	ArrayList<Integer> hourlyRainPercentages = new ArrayList<Integer>();
	ArrayList<Integer> hourlyHumidityPercentages = new ArrayList<Integer>();
	ArrayList<Integer> hourlySnowPercentages = new ArrayList<Integer>();
	ArrayList<Integer> hourlyIcePercentages = new ArrayList<Integer>();

	public ArrayList<Integer> getTemperatures() {
		return hourlyTemperatures;
	}
	public ArrayList<Integer> getHumidityPercentages() {
		return hourlyHumidityPercentages;
	}
	public ArrayList<Integer> getRainPercentages() {
		return hourlyRainPercentages;
	}
	public ArrayList<Integer> getSnowPercentages() {
		return hourlySnowPercentages;
	}
	public ArrayList<Integer> getIcePercentages() {
		return hourlyIcePercentages;
	}
	public WebScraper() {
		
		
		Integer[] testTemperatures = {
				85, 83, 82, 81, 80, 80, 79, 78, 80, 83, 86, 89, 92, 93, 95,	97,
				94, 93, 92, 89, 87, 86, 85, 84, 82, 81, 81, 80, 79, 78, 77, 76,
				78, 80, 81, 84, 86, 87, 87, 89, 88, 87, 86, 84, 82, 81, 80, 80,
				79, 78, 77, 77, 76, 76, 74, 75, 77, 79, 81, 84, 86, 86, 88, 87,
				85, 84, 82, 81, 79, 79, 79, 79, 79, 79, 78, 78, 77, 76, 76, 76,
				78, 80, 82, 83, 85, 85, 87, 85, 84, 83, 82, 81, 80, 79, 79, 79};
		Integer[] testHumidities = {
				90, 80, 90, 80, 100, 95, 85, 16, 7, 7, 6, 5, 5, 8, 43, 47, 51, 22,
				17, 22, 34, 34, 37, 43, 47, 51, 11, 7, 7, 7, 7, 7, 7, 7, 6, 5, 5,
				8, 43, 47, 51, 22, 17, 14, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 5, 7,
				7, 6, 5, 5, 8, 43, 47, 51, 23, 18, 16, 11, 11, 11, 11, 11, 8, 3, 3,
				3, 3, 3, 3, 5, 5, 6, 7, 7, 9, 13, 13, 17, 43, 47, 51, 12, 8, 8, 8};
		
		Integer[] testRains = {
				47, 51, 25, 20, 20, 20, 20, 16, 7, 7, 6, 5, 5, 8, 43, 47, 51, 22,
				17, 22, 34, 34, 37, 43, 47, 51, 11, 7, 7, 7, 7, 7, 7, 7, 6, 5, 5,
				8, 43, 47, 51, 22, 17, 14, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 5, 7,
				7, 6, 5, 5, 8, 43, 47, 51, 23, 18, 16, 11, 11, 11, 11, 11, 8, 3, 3,
				3, 3, 3, 3, 5, 5, 6, 7, 7, 9, 13, 13, 17, 43, 47, 51, 12, 8, 8, 8};
		
		Integer[] testSnow = {
				90, 80, 90, 80, 100, 95, 85, 16, 7, 7, 6, 5, 5, 8, 43, 47, 51, 22,
				17, 22, 34, 34, 37, 43, 47, 51, 11, 7, 7, 7, 7, 7, 7, 7, 6, 5, 5,
				8, 43, 47, 51, 22, 17, 14, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 5, 7,
				7, 6, 5, 5, 8, 43, 47, 51, 23, 18, 16, 11, 11, 11, 11, 11, 8, 3, 3,
				3, 3, 3, 3, 5, 5, 6, 7, 7, 9, 13, 13, 17, 43, 47, 51, 12, 8, 8, 8};
		
		Integer[] testIce = {
				85, 83, 82, 81, 80, 80, 79, 78, 80, 83, 86, 89, 92, 93, 95,	97,
				94, 93, 92, 89, 87, 86, 85, 84, 82, 81, 81, 80, 79, 78, 77, 76,
				78, 80, 81, 84, 86, 87, 87, 89, 88, 87, 86, 84, 82, 81, 80, 80,
				79, 78, 77, 77, 76, 76, 74, 75, 77, 79, 81, 84, 86, 86, 88, 87,
				85, 84, 82, 81, 79, 79, 79, 79, 79, 79, 78, 78, 77, 76, 76, 76,
				78, 80, 82, 83, 85, 85, 87, 85, 84, 83, 82, 81, 80, 79, 79, 79};
				

		hourlyTemperatures = new ArrayList<Integer>(Arrays.asList(testTemperatures));
		hourlyHumidityPercentages = new ArrayList<Integer>(Arrays.asList(testHumidities));
		hourlyRainPercentages = new ArrayList<Integer>(Arrays.asList(testRains));
		hourlySnowPercentages = new ArrayList<Integer>(Arrays.asList(testSnow));
		hourlyIcePercentages = new ArrayList<Integer>(Arrays.asList(testIce));
		
		/*
		 try {
		        URL url;
		        URLConnection urlConnection;
		        DataOutputStream outStream;
		        BufferedReader inStream;

		        //Build request body
		        String body =
		        "fName=" + URLEncoder.encode("Atli", "UTF-8") +
		        "&lName=" + URLEncoder.encode("Þór", "UTF-8");

		        //Create connection
		        url = new URL("http://www.accuweather.com/en/us/brunswick-ga/31520/hourly-weather-forecast/332506?hour=0");
		        urlConnection = url.openConnection();
		        ((HttpURLConnection)urlConnection).setRequestMethod("POST");
		        urlConnection.setDoInput(true);
		        urlConnection.setDoOutput(true);
		        urlConnection.setUseCaches(false);

		        //Create I/O streams
		        outStream = new DataOutputStream(urlConnection.getOutputStream());
		        inStream = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

		        //Send request
		        outStream.writeBytes(body);
		        outStream.flush();
		        outStream.close();

		        //i = 11 because the website only goes up to 88 hours past current (88 / 8 = 11)
		        
		        
		        
		        // OPTIMIZE THIS SO THAT ALL THE EXTRA PAGES GET LOADED LATER
		        
		        for (int i = 0; i <= 12; i++) {
		        	
		        	//Initialize variables
		        	String buffer;
			        boolean temperatureToggle = false;
			        boolean humidityToggle = false;
			        boolean rainToggle = false;
			        boolean snowToggle = false;
			        boolean iceToggle = false;
			        int counter = 0;
			        
		        	//Get hourly temperatures 8 hours at a time
			        url = new URL("http://www.accuweather.com/en/us/brunswick-ga/31520/hourly-weather-forecast/332506?hour=" + i*8);
			        urlConnection = url.openConnection();
			        ((HttpURLConnection)urlConnection).setRequestMethod("POST");
			        urlConnection.setDoOutput(true);

			        // Create I/O streams
			        outStream = new DataOutputStream(urlConnection.getOutputStream());
			        inStream = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

			        // Send request
			        outStream.flush();
			        outStream.close();

			        temperatureToggle = false;
			        humidityToggle = false;
			        rainToggle = false;
			        counter = 0;
			        
			        //Parse the web pages into usable information
			        while((buffer = inStream.readLine()) != null) {
			            
			        	//Find the hourly temperatures and add them to an ArrayList
			        	if (temperatureToggle || buffer.contains("Temp ")) {
			        		temperatureToggle = true;
			        		temperatureParser(buffer);
			        		counter++;
			        		
			        		//After 8 lines (8 hours of temperatures) set the toggle to false
			        		if (counter > 8) {
			        			temperatureToggle = false;
			        			counter = 0;
			        			continue;
			        		}
			        	}
			        	
			        	//Find the humidity percentages and add them to an ArrayList
			        	if (humidityToggle || buffer.contains("Humidity<") ) {
			            	humidityParser(buffer);
			            	humidityToggle = true;
			            	counter++;
			            	
			            	//After 8 lines (8 hours of humidities) set the toggle to false to exit loop
			            	if (counter > 8) {
			            		humidityToggle = false;
			            		counter = 0;
			            		continue;
			            	}
			            }
			        	
			        	//Find the chance of rain percentages and add them to an ArrayList
			        	if (rainToggle || buffer.contains("Rain<") ) {
			            	rainParser(buffer);
			            	rainToggle = true;
			            	counter++;
			            	
			            	//After 8 lines (8 hours of rain) set the toggle to false to exit loop
			            	if (counter > 8) {
			            		rainToggle = false;
			            		counter = 0;
			            		continue;
			            	}
			            }
			        	//Find the chance of snow percentages and add them to an ArrayList
			        	if (snowToggle || buffer.contains("Snow<") ) {
			            	snowParser(buffer);
			            	snowToggle = true;
			            	counter++;
			            	
			            	//After 8 lines (8 hours of snow) set the toggle to false to exit loop
			            	if (counter > 8) {
			            		snowToggle = false;
			            		counter = 0;
			            		continue;
			            	}
			            }
			        	//Find the chance of rain percentages and add them to an ArrayList
			        	if (iceToggle || buffer.contains("Ice<") ) {
			            	iceParser(buffer);
			            	iceToggle = true;
			            	counter++;
			            	
			            	//After 8 lines (8 hours of rain) set the toggle to false to exit loop
			            	if (counter > 8) {
			            		iceToggle = false;
			            		counter = 0;
			            		continue;
			            	}
			            }    
			        }
		        }      
		        // Close I/O streams
		        inStream.close();
		        outStream.close();
		    }
		    catch(Exception ex) {
		        System.out.println("Exception caught:\n"+ ex.toString());
		    }
		    */
	}
	
	public void temperatureParser(String input) {
		int lessThanPlaceholder = 0;
		int ampersandPlaceholder = 0;
		
		//Catches the first line and returns early
		if (input.contains("Temp")) {
			return;
		}
		for (int i = 0; i < input.length(); i++) {
			
			//Find '>' and concat everything before off
			if (input.charAt(i) == '>') {
				lessThanPlaceholder = i + 1;
			}
			//Find '&' and concat everything after off
			if (input.charAt(i) == '&') {
				ampersandPlaceholder = i;
				break;
			}
		}
		//Return temperatures
		//System.out.println(input.substring(lessThanPlaceholder, ampersandPlaceholder));
		hourlyTemperatures.add(Integer.parseInt(input.substring(lessThanPlaceholder, ampersandPlaceholder)));
	}
	public void humidityParser(String input) {
		int colonPlaceholder = 0;
		int percentagePlaceholder = 0;
		
		//Catches the first line and returns early
		if (input.contains("Humidity<")) {
			return;
		}
		for (int i = 0; i < input.length(); i++) {
			
			//Find '>' and concat everything before off
			if (input.charAt(i) == '>') {
				colonPlaceholder = i + 1;
			}
			//Find '%' and concat everything after off
			if (input.charAt(i) == '%') {
				percentagePlaceholder = i;
				break;
			}
		}
		//System.out.println(input.substring(colonPlaceholder, percentagePlaceholder));
		//Return temperatures
		if (input.substring(colonPlaceholder, percentagePlaceholder).compareTo("") != 0) {
			hourlyHumidityPercentages.add(Integer.parseInt(input.substring(colonPlaceholder, percentagePlaceholder)));
		}
	}
	public void rainParser(String input) {
		int colonPlaceholder = 0;
		int percentagePlaceholder = 0;
		
		//Catches the first line and returns early
		if (input.contains("Rain<")) {
			return;
		}
		for (int i = 0; i < input.length(); i++) {
			
			//Find ':' and concat everything before off
			if (input.charAt(i) == ':') {
				colonPlaceholder = i + 2;
			}
			//Find '%' and concat everything after off
			if (input.charAt(i) == '%') {
				percentagePlaceholder = i;
				break;
			}
		}
		//System.out.println(input.substring(colonPlaceholder, percentagePlaceholder));
		//Return temperatures
		if (input.substring(colonPlaceholder, percentagePlaceholder).compareTo("") != 0) {
			hourlyRainPercentages.add(Integer.parseInt(input.substring(colonPlaceholder, percentagePlaceholder)));
		}
	}
	public void snowParser(String input) {
		int colonPlaceholder = 0;
		int percentagePlaceholder = 0;
		
		//Catches the first line and returns early
		if (input.contains("Snow<")) {
			return;
		}
		for (int i = 0; i < input.length(); i++) {
			
			//Find ':' and concat everything before off
			if (input.charAt(i) == ':') {
				colonPlaceholder = i + 2;
			}
			//Find '%' and concat everything after off
			if (input.charAt(i) == '%') {
				percentagePlaceholder = i;
				break;
			}
		}
		//System.out.println(input.substring(colonPlaceholder, percentagePlaceholder));
		//Return temperatures
		if (input.substring(colonPlaceholder, percentagePlaceholder).compareTo("") != 0) {
			hourlySnowPercentages.add(Integer.parseInt(input.substring(colonPlaceholder, percentagePlaceholder)));
		}
	}
	public void iceParser(String input) {
		int colonPlaceholder = 0;
		int percentagePlaceholder = 0;
		
		//Catches the first line and returns early
		if (input.contains("Ice<")) {
			return;
		}
		for (int i = 0; i < input.length(); i++) {
			
			//Find ':' and concat everything before off
			if (input.charAt(i) == ':') {
				colonPlaceholder = i + 2;
			}
			//Find '%' and concat everything after off
			if (input.charAt(i) == '%') {
				percentagePlaceholder = i;
				break;
			}
		}
		//System.out.println(input.substring(colonPlaceholder, percentagePlaceholder));
		//Return temperatures
		if (input.substring(colonPlaceholder, percentagePlaceholder).compareTo("") != 0) {
			hourlyIcePercentages.add(Integer.parseInt(input.substring(colonPlaceholder, percentagePlaceholder)));
		}
	}
}
