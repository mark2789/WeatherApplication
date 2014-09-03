import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class WebScraper {
	//set to true means this class does no webscraping and instead uses test values to quicker testing
	//set to false for webscraping but slower startup
	boolean isTest = false;
	
	ProgressBar webScrapProgressBar = new ProgressBar();
	
	ArrayList<Integer> hourlyTemperatures = new ArrayList<Integer>();
	ArrayList<Integer> hourlyRainPercentages = new ArrayList<Integer>();
	ArrayList<Integer> hourlyHumidityPercentages = new ArrayList<Integer>();
	ArrayList<Integer> hourlySnowPercentages = new ArrayList<Integer>();
	ArrayList<Integer> hourlyIcePercentages = new ArrayList<Integer>();
	ArrayList<String> parsedCities = new ArrayList<String>();
	LinkedHashMap<String, String> stateCityAndLink = new LinkedHashMap<String, String>();
	
	String stateAndCity = "brunswick-ga";
	String state = "ga";
	String city = "brunswick";
	int progress = 0;
	int temperatureErrorCounter = 1;
	int humidityErrorCounter = 1;
	int rainErrorCounter = 1;
	int snowErrorCounter = 1;
	int iceErrorCounter = 1;

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getProgress() {
		return progress;
	}
	public ProgressBar getProgressBar() {
		return webScrapProgressBar;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public LinkedHashMap<String, String> getStateCityAndLink() {
		return stateCityAndLink;
	}	
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
		if (isTest) {
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
		}
	}
	public void webScrapState() {

		stateCityAndLink.clear();
		parsedCities.clear();
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
	        url = new URL("http://www.accuweather.com/en/browse-locations/nam/us/" + state);
	        urlConnection = url.openConnection();
	        ((HttpURLConnection)urlConnection).setRequestMethod("GET");
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
	        
	        
	        String buffer;
	        while((buffer = inStream.readLine()) != null) {
	        	//Find the hourly temperatures and add them to an ArrayList
	        	if (buffer.contains("href:'") && buffer.contains("weather-forecast") && buffer.contains("http://www.accuweather.com/en/us/")) {
	        		parseCity(buffer);
	        	}
	        }
	        
	        // Close I/O streams
	        inStream.close();
	        outStream.close();
	    }
	    catch(Exception ex) {
	        System.out.println("Exception caught:\n"+ ex.toString());
	    }
			
	}
	public void webScrapCity() {

		hourlyTemperatures.clear();
		hourlyHumidityPercentages.clear();
		hourlyRainPercentages.clear();
		hourlySnowPercentages.clear();
		hourlyIcePercentages.clear();
		progress = 0;
		
		 // OPTIMIZE THIS SO THAT ALL THE EXTRA PAGES GET LOADED LATER
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
	        stateAndCity = city + "-" + state;
	        url = new URL(stateCityAndLink.get(city) + "0");
	        urlConnection = url.openConnection();
	        ((HttpURLConnection)urlConnection).setRequestMethod("GET");
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

	      
        
	        for (int i = 0; i <= 12; i++) {
	        	progress = i;
	        	webScrapProgressBar.updateProgress(progress);
	        	webScrapProgressBar.setValue(i);
	        	webScrapProgressBar.updateUI();
	        	//Initialize variables
	        	String buffer = "";
		        boolean temperatureToggle = false;
		        boolean humidityToggle = false;
		        boolean rainToggle = false;
		        boolean snowToggle = false;
		        boolean iceToggle = false;
		        int counter = 0;
		        
	        	//Get hourly temperatures 8 hours at a time
		        url = new URL(stateCityAndLink.get(city) + i*8);
		        urlConnection = url.openConnection();
		        ((HttpURLConnection)urlConnection).setRequestMethod("GET");
		        urlConnection.setDoOutput(true);
	
		        // Create I/O streams
		        outStream = new DataOutputStream(urlConnection.getOutputStream());
		        inStream = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	
		        // Send request
		        outStream.writeBytes(body);
		        outStream.flush();
		        outStream.close();
		        
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
		        	if (rainToggle || buffer.contains("Rain</th") ) {
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
	        ex.printStackTrace();
	    }
	}
	
	public void parseCity(String input) {
		int lessThanPlaceholder = 0;
		int stateCityStartPlaceholder = 0;
		int stateCityEndPlaceholder = 0;
		String stateCity = "";
		String hyperlink = "";
		
		for (int i = 0; i < input.length(); i++) {
			
			//Find '>' and concat everything before off
			if (input.charAt(i) == ':') {
				lessThanPlaceholder = i - 4;
				stateCityStartPlaceholder = i + 29;
			}
			
			if (i - 10 < input.length() && input.charAt(i) == '/' && input.charAt(i+1) == 'w' && input.charAt(i+2) == 'e'
					 && input.charAt(i+3) == 'a' && input.charAt(i+4) == 't' && input.charAt(i+5) == 'h') {
				hyperlink = input.substring(lessThanPlaceholder, i) + "/hourly-" + input.substring(i + 1, input.length() - 4) + "?hour=";
				
			}
			
		}
		
		for (int i = stateCityStartPlaceholder; i < input.length(); i++) {
			if (input.charAt(i) == '/') {
				stateCityEndPlaceholder = i;
				break;
			}
		}
		
		//get state and city
		stateCity = input.substring(stateCityStartPlaceholder, stateCityEndPlaceholder);

		stateCityAndLink.put(stateCity, hyperlink);
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
		int temp = Integer.parseInt(input.substring(lessThanPlaceholder, ampersandPlaceholder));
		//If temp is 0 and arrayList is empty then increase error counter
		if (temp == 0 && hourlyTemperatures.size() == 0) {
			temperatureErrorCounter++;
		}
		else {
			//Loop to fix repeated 0 errors at the start of parsing
			for (int i = 0; i < temperatureErrorCounter; i++) {
				hourlyTemperatures.add(temp);
			}
			temperatureErrorCounter = 1;
		}
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
		//Return Humidity percentages
		if (input.substring(colonPlaceholder, percentagePlaceholder).compareTo("") != 0) {
			int temp = Integer.parseInt(input.substring(colonPlaceholder, percentagePlaceholder));
			//If temp is 0 and arrayList is empty then increase error counter
			if (temp == 0 && hourlyHumidityPercentages.size() == 0) {
				humidityErrorCounter++;
			}
			else {
				//Loop to fix repeated 0 errors at the start of parsing
				for (int i = 0; i < humidityErrorCounter; i++) {
					hourlyHumidityPercentages.add(temp);
				}
				humidityErrorCounter = 1;
			}
		}
	}
	public void rainParser(String input) {
		int colonPlaceholder = 0;
		int percentagePlaceholder = 0;
		
		//Catches the first line and returns early
		if (input.contains("Rain</th")) {
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
		//Return Rain Percentages
		if (input.substring(colonPlaceholder, percentagePlaceholder).compareTo("") != 0) {
			int temp = Integer.parseInt(input.substring(colonPlaceholder, percentagePlaceholder));
			//If temp is 0 and arrayList is empty then increase error counter
			if (rainErrorCounter == 96) {
				//Loop to fix repeated 0 errors at the start of parsing
				for (int i = 0; i < rainErrorCounter; i++) {
					hourlyRainPercentages.add(temp);
				}
				rainErrorCounter = 1;
			}
			else if (temp == 0 && hourlyRainPercentages.size() == 0) {
				rainErrorCounter++;
			}
			else {
				//Loop to fix repeated 0 errors at the start of parsing
				for (int i = 0; i < rainErrorCounter; i++) {
					hourlyRainPercentages.add(temp);
				}
				rainErrorCounter = 1;
			}
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
		//Return Snow Percentages
		if (input.substring(colonPlaceholder, percentagePlaceholder).compareTo("") != 0) {
			
			int temp = Integer.parseInt(input.substring(colonPlaceholder, percentagePlaceholder));
			//If temp is 0 and arrayList is empty then increase error counter
			 if (snowErrorCounter == 96) {
				//Loop to fix repeated 0 errors at the start of parsing
				for (int i = 0; i < snowErrorCounter; i++) {
					hourlySnowPercentages.add(0);
				}
				snowErrorCounter = 1;
			}
			else if (temp == 0 && hourlySnowPercentages.size() == 0) {
				snowErrorCounter++;
			}
			else {
				//Loop to fix repeated 0 errors at the start of parsing
				for (int i = 0; i < snowErrorCounter; i++) {
					 hourlySnowPercentages.add(temp);
				}
				snowErrorCounter = 1;
			} 
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
		//Return Ice Percentages
		if (input.substring(colonPlaceholder, percentagePlaceholder).compareTo("") != 0) {
			int temp = Integer.parseInt(input.substring(colonPlaceholder, percentagePlaceholder));
			//If temp is 0 and arrayList is empty then increase error counter
			 if (iceErrorCounter == 96) {
				//Loop to fix repeated 0 errors at the start of parsing
				for (int i = 0; i < iceErrorCounter; i++) {
					hourlyIcePercentages.add(0);
				}
				iceErrorCounter = 1;
			}
			else if (temp == 0 && hourlyIcePercentages.size() == 0) {
				iceErrorCounter++;
			}
			else {
				//Loop to fix repeated 0 errors at the start of parsing
				for (int i = 0; i < iceErrorCounter; i++) {
					 hourlyIcePercentages.add(temp);
				}
				iceErrorCounter = 1;
			} 
		}
	}
}
