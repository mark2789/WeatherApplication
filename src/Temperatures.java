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


public class Temperatures {
	ArrayList<Integer> dailyTemperatures = new ArrayList<Integer>();

	public Temperatures() {
		
		Integer[] test = {
				85, 83, 82, 81, 80, 80, 79, 78, 80, 83, 86, 89,
				92, 93, 95,
				
				97, 94, 93, 92, 89, 87, 86, 85, 84, 82, 81, 81, 80,
				79, 78, 77, 76, 78, 80, 81, 84, 86, 87, 87, 89, 88, 87, 86, 84,
				82, 81, 80, 80, 79, 78, 77, 77, 76, 76, 74, 75, 77, 79, 81, 84,
				86, 86, 88, 87, 85, 84, 82, 81, 79, 79, 79, 79, 79, 79, 78, 78,
				77, 76, 76, 76, 78, 80, 82, 83, 85, 85, 87, 85, 84, 83, 82, 81,
				80, 79, 79, 79
				 
				};
		dailyTemperatures = new ArrayList<Integer>(Arrays.asList(test));
		
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
			        boolean tempToggle = false;
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

			        tempToggle = false;
			        counter = 0;
			        while((buffer = inStream.readLine()) != null) {
			            
			            if (tempToggle || buffer.contains("Temp ") ) {
			            	temperatureAdder(buffer);
			            	tempToggle = true;
			            	counter++;
			            	
			            	//After 8 lines (8 hours of temperatures) set the toggle to false to exit loop
			            	if (counter > 8) {
			            		tempToggle = false;
			            		break;
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
	
	public void temperatureAdder(String input) {
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
		dailyTemperatures.add(Integer.parseInt(input.substring(lessThanPlaceholder, ampersandPlaceholder)));
	}
}
