import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class WeatherApplication extends JApplet {
	JButton testButton = new JButton("Text");
	JSlider xHoursSlider = new JSlider(1, 7);
	JSlider xDaysSlider = new JSlider(1, 4);
	JSlider rainOrSnowOrIceSlider = new JSlider(1, 3);
    JLabel xHoursSliderLabel = new JLabel("Hourly Divisor", JLabel.CENTER);
    JLabel xDaysSliderLabel = new JLabel("Number of Days", JLabel.CENTER);
    JLabel rainOrSnowOrIceSliderLabel = new JLabel("Rain or Snow or Ice", JLabel.CENTER);

    GraphJPanel graphTemperaturesJPanel = new GraphJPanel();  
    GraphJPanel graphHumiditiesJPanel = new GraphJPanel();
    GraphJPanel graphRainJPanel = new GraphJPanel(); 
    GraphJPanel graphSnowJPanel = new GraphJPanel(); 
    GraphJPanel graphIceJPanel = new GraphJPanel();
    
	JPanel centerPanel = new JPanel();
    
    boolean rain = true;
    boolean snow = false;
    boolean ice = false;
    
    WebScraper webScraper = new WebScraper();
	
	public static void main(String[] args) {
		JFrame f = new JFrame("Graph Temperatures");
        JApplet a = new WeatherApplication();
        f.getContentPane().add(a);
        a.init();
        f.setBounds(100, 100, 1000, 800);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ((WeatherApplication)a).makeGui();
        f.setVisible(true);
        
	}
    public void makeGui() {
        
        //Table for JSlider Labels
        Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        labelTable.put(new Integer(1), new JLabel("1"));
       	labelTable.put(new Integer(2), new JLabel("2"));
       	labelTable.put(new Integer(3), new JLabel("3"));
       	labelTable.put(new Integer(4), new JLabel("4"));
       	labelTable.put(new Integer(5), new JLabel("6"));
       	labelTable.put(new Integer(6), new JLabel("12"));
       	labelTable.put(new Integer(7), new JLabel("None"));
       	
       	//Hourly Slider Settings

       	xHoursSlider.setLabelTable(labelTable);
       	xHoursSlider.setPaintLabels(true);
       	xHoursSlider.setValue(5);
        	
       	//Hourly Slider Listener
       	xHoursSlider.addChangeListener(new ChangeListener() {
       	      public void stateChanged(ChangeEvent e) {
       	    	  //Catch the slider value of 5 and change it to 6 (for hour labels)
       	    	  if (xHoursSlider.getValue() == 5) {
       	    		graphTemperaturesJPanel.setVerticalLinesEveryXHours(6);
       	    		graphHumiditiesJPanel.setVerticalLinesEveryXHours(6);
       	    		graphRainJPanel.setVerticalLinesEveryXHours(6);
       	    		graphSnowJPanel.setVerticalLinesEveryXHours(6);
       	    		graphIceJPanel.setVerticalLinesEveryXHours(6);
       	    	  }
       	    	  //Catch the slider value of 6 and change it to 12 (for hour labels)
       	    	  else if (xHoursSlider.getValue() == 6) {
       	    		graphTemperaturesJPanel.setVerticalLinesEveryXHours(12);
       	    		graphHumiditiesJPanel.setVerticalLinesEveryXHours(12);
       	    		graphRainJPanel.setVerticalLinesEveryXHours(12);
       	    		graphSnowJPanel.setVerticalLinesEveryXHours(12);
       	    		graphIceJPanel.setVerticalLinesEveryXHours(12);
       	    	  }
       	    	  //Catch the slider value of 7 and change it to 12 (for hour labels)
       	    	  else if (xHoursSlider.getValue() == 7) {
       	    		graphTemperaturesJPanel.setVerticalLinesEveryXHours(0);
       	    		graphHumiditiesJPanel.setVerticalLinesEveryXHours(0);
       	    		graphRainJPanel.setVerticalLinesEveryXHours(0);
       	    		graphSnowJPanel.setVerticalLinesEveryXHours(0);
       	    		graphIceJPanel.setVerticalLinesEveryXHours(0);
       	    	  }
       	    	  //1-4 slider values are fine
       	    	  else {
       	    		graphTemperaturesJPanel.setVerticalLinesEveryXHours(xHoursSlider.getValue());
       	    		graphHumiditiesJPanel.setVerticalLinesEveryXHours(xHoursSlider.getValue());
       	    		graphRainJPanel.setVerticalLinesEveryXHours(xHoursSlider.getValue());
       	    		graphSnowJPanel.setVerticalLinesEveryXHours(xHoursSlider.getValue());
       	    		graphIceJPanel.setVerticalLinesEveryXHours(xHoursSlider.getValue());
       	    	  }
       	    	  //Refresh the Graph when slider is changed
       	    	  repaint();
       	      }
       	});
       	
    	//Daily Slider Settings
       	xDaysSlider.setLabelTable(labelTable);
       	xDaysSlider.setPaintLabels(true);

       	xDaysSlider.setValue(4);
        	
       	//Daily Slider Listener
       	xDaysSlider.addChangeListener(new ChangeListener() {
       	      public void stateChanged(ChangeEvent e) {
       	    	graphTemperaturesJPanel.setNumberOfDaysDisplayed(xDaysSlider.getValue());
       	    	graphHumiditiesJPanel.setNumberOfDaysDisplayed(xDaysSlider.getValue());
       	    	graphRainJPanel.setNumberOfDaysDisplayed(xDaysSlider.getValue());
       	    	graphSnowJPanel.setNumberOfDaysDisplayed(xDaysSlider.getValue());
       	    	graphIceJPanel.setNumberOfDaysDisplayed(xDaysSlider.getValue());
       	    	  //Refresh the Graph when slider is changed
       	    	  repaint();
       	      }
       	});
       	
      	Hashtable<Integer, JLabel> rainOrSnowOrIceLabels = new Hashtable<Integer, JLabel>();
        rainOrSnowOrIceLabels.put(new Integer(1), new JLabel("Rain"));
       	rainOrSnowOrIceLabels.put(new Integer(2), new JLabel("Snow"));
       	rainOrSnowOrIceLabels.put(new Integer(3), new JLabel("Ice"));
       	
       	//Hourly Slider Settings
       	rainOrSnowOrIceSlider.setLabelTable(rainOrSnowOrIceLabels);
       	rainOrSnowOrIceSlider.setPaintLabels(true);
       	rainOrSnowOrIceSlider.setValue(1);
       	
       	//Rain or Snow or Ice Graph Slider
       	rainOrSnowOrIceSlider.addChangeListener(new ChangeListener() {
       	      public void stateChanged(ChangeEvent e) {
       	    	  //
       	    	  if (rainOrSnowOrIceSlider.getValue() == 1) {
       	    		  //System.out.println("Rain");
       	    		  rain = true;
       	    		  snow = false;
       	    		  ice = false;

       	    	  }
       	    	  //
       	    	  else if (rainOrSnowOrIceSlider.getValue() == 2) {
       	    		  //System.out.println("Snow");
       	    		  rain = false;
     	    		  snow = true;
     	    		  ice = false;
       	    	  }
       	    	  //
       	    	  else if (rainOrSnowOrIceSlider.getValue() == 3) {
       	    		  //System.out.println("Ice");
       	    		  rain = false;
     	    		  snow = false;
     	    		  ice = true;
       	    	  }
   	    		  updateGraphs();
   	    		  centerPanel.updateUI();
       	      }
       	});
       	
       	//Bottom Panels with Interactive elements
       	JPanel bottomPanel = new JPanel();
       	JPanel bottom2Panel = new JPanel();
       	bottomPanel.setLayout(new BorderLayout());
       	bottom2Panel.setLayout(new BorderLayout());      	
       	
       	JPanel slidersPanel = new JPanel();
       	slidersPanel.setLayout(new GridLayout(2,3));

       	slidersPanel.add(xHoursSliderLabel);
       	slidersPanel.add(xDaysSliderLabel);
       	slidersPanel.add(rainOrSnowOrIceSliderLabel);
       	slidersPanel.add(xHoursSlider);
       	slidersPanel.add(xDaysSlider);
       	slidersPanel.add(rainOrSnowOrIceSlider);
        
        add(getGraphs(), BorderLayout.CENTER);
       	add(slidersPanel, BorderLayout.SOUTH);

    }
    public JPanel getGraphs() {

       	centerPanel.setLayout(new GridLayout(3,1));
    	
    	Thread graphTemperaturesThread = new Thread(graphTemperaturesJPanel);
        graphTemperaturesThread.start();
        graphTemperaturesJPanel.setXSpacingPercentage(0.05);
        graphTemperaturesJPanel.setYSpacingPercentage(0.1);
        graphTemperaturesJPanel.setGraphPoints(webScraper.getTemperatures());
        graphTemperaturesJPanel.setTitle("Hourly Temperature");
        
        
        Thread graphHumiditiesThread = new Thread(graphHumiditiesJPanel);
        graphHumiditiesThread.start();
        graphHumiditiesJPanel.setXSpacingPercentage(0.05);
        graphHumiditiesJPanel.setYSpacingPercentage(0.1);
        graphHumiditiesJPanel.setGraphPoints(webScraper.getHumidityPercentages());
        graphHumiditiesJPanel.setMax(100);
        graphHumiditiesJPanel.setMin(0);
        graphHumiditiesJPanel.setYAxisIncrements(20);
        graphHumiditiesJPanel.setTitle("Hourly Humidity");
        
    	
    	Thread graphRainThread = new Thread(graphRainJPanel);
    	graphRainThread.start();
    	graphRainJPanel.setXSpacingPercentage(0.05);
    	graphRainJPanel.setYSpacingPercentage(0.1);
    	graphRainJPanel.setGraphPoints(webScraper.getRainPercentages());
    	graphRainJPanel.setMax(100);
    	graphRainJPanel.setMin(0);
    	graphRainJPanel.setYAxisIncrements(20);
    	graphRainJPanel.setTitle("Hourly Chance of Rain");
    	
    	Thread graphSnowThread = new Thread(graphSnowJPanel);
    	graphSnowThread.start();
    	graphSnowJPanel.setXSpacingPercentage(0.05);
    	graphSnowJPanel.setYSpacingPercentage(0.1);
    	graphSnowJPanel.setGraphPoints(webScraper.getSnowPercentages());
    	graphSnowJPanel.setMax(100);
    	graphSnowJPanel.setMin(0);
    	graphSnowJPanel.setYAxisIncrements(20);
    	graphSnowJPanel.setTitle("Hourly Chance of Snow");
    	
    	Thread graphIceThread = new Thread(graphIceJPanel);
    	graphIceThread.start();
    	graphIceJPanel.setXSpacingPercentage(0.05);
    	graphIceJPanel.setYSpacingPercentage(0.1);
    	graphIceJPanel.setGraphPoints(webScraper.getIcePercentages());
    	graphIceJPanel.setMax(100);
    	graphIceJPanel.setMin(0);
    	graphIceJPanel.setYAxisIncrements(20);
    	graphIceJPanel.setTitle("Hourly Chance of Ice");
    	
    	centerPanel.add(graphTemperaturesJPanel);
        centerPanel.add(graphHumiditiesJPanel);
        centerPanel.add(graphRainJPanel);
        
    	return centerPanel;
    }
    public void updateGraphs() {
    	//Remove the previous panel and replace with new panel
    	try {
            centerPanel.remove(2);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
        	ex.printStackTrace();
        }
    	//Add rain panel
    	if (rain) {
            centerPanel.add(graphRainJPanel);
        }
    	//Add snow panel
    	else if (snow) {
            centerPanel.add(graphSnowJPanel);
        }
        //Add ice panel
    	else if (ice) {
            centerPanel.add(graphIceJPanel);
        }
    }
}
