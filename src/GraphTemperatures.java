import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GraphTemperatures extends JApplet {
	ArrayList<Integer> temperatures = new ArrayList<Integer>();
	JButton testButton = new JButton("Text");
	JSlider xHoursSlider = new JSlider(1, 7);
	JSlider xDaysSlider = new JSlider(1, 4);
    JLabel xHoursSliderLabel = new JLabel("Hourly Divisor", JLabel.CENTER);
    JLabel xDaysSliderLabel = new JLabel("Number of Days", JLabel.CENTER);
	//JLabel selectionSort = new JLabel("Selection Sort");
	int[] ss = new int[50];
	ArrayList<Integer> arrayList = new ArrayList<Integer>(50);
	int min;
	int max;
	Temperatures tempClass = new Temperatures();
	double heightScaler;
	double widthScaler;
	double xSpacingPercentage = 0.05;
	double ySpacingPercentage = 0.10;
    int verticalLinesEveryXHours = 6;
    int numberOfDaysDisplayed = 4; //Number between 1-4
	MyJPanel mjp = new MyJPanel();
	
	public GraphTemperatures() {
		getTemperatures(tempClass.dailyTemperatures);
	}
	public void getTemperatures(ArrayList<Integer> temperatures) {
		this.temperatures = temperatures;
	}
	
	public class Graph implements Runnable {
        public void run() {
        	//Create a custom graph JPanel and add it to the Window
            MyJPanel mjp = new MyJPanel();
            add(mjp, BorderLayout.CENTER);

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
           	    		  verticalLinesEveryXHours = 6;
           	    	  }
           	    	  //Catch the slider value of 6 and change it to 12 (for hour labels)
           	    	  else if (xHoursSlider.getValue() == 6) {
           	    		  verticalLinesEveryXHours = 12;
           	    	  }
           	    	  //Catch the slider value of 7 and change it to 12 (for hour labels)
           	    	  else if (xHoursSlider.getValue() == 7) {
           	    		  verticalLinesEveryXHours = 0;
           	    	  }
           	    	  //1-4 slider values are fine
           	    	  else {
           	    		  verticalLinesEveryXHours = xHoursSlider.getValue();
           	    	  }
           	    	  //Refresh the Graph when slider is changed
           	    	  repaint();
           	      }
           	    });
           	
        	//Daily Slider Settings
           	xDaysSlider.setLabelTable(labelTable);
           	xDaysSlider.setPaintLabels(true);
            	
           	//Daily Slider Listener
           	xDaysSlider.addChangeListener(new ChangeListener() {
           	      public void stateChanged(ChangeEvent e) {
           	    	  numberOfDaysDisplayed = xDaysSlider.getValue();
           	    	  
           	    	  //Refresh the Graph when slider is changed
           	    	  repaint();
           	      }
           	    });

           	//Bottom Panels with Interactive elements
           	JPanel bottomPanel = new JPanel();
           	JPanel bottom2Panel = new JPanel();
           	bottomPanel.setLayout(new BorderLayout());
           	bottom2Panel.setLayout(new BorderLayout());
           	
           	bottomPanel.add(xHoursSliderLabel, BorderLayout.NORTH);
            bottomPanel.add(xHoursSlider, BorderLayout.CENTER);
            bottomPanel.add(bottom2Panel, BorderLayout.SOUTH);
           	bottom2Panel.add(xDaysSliderLabel, BorderLayout.NORTH);
           	bottom2Panel.add(xDaysSlider, BorderLayout.CENTER);
           	add(bottomPanel, BorderLayout.SOUTH);
        }
    }
	public void init() {
        Graph task1 = new Graph();
        Thread thread1 = new Thread(task1);
        
        thread1.start();
	}
	public class MyJPanel extends JPanel {
		
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            
            
            int xCoordinate = 0;
            int yCoordinate = 0;
            int y2Coordinate = 0;
            ArrayList<Integer> tempSubList = new ArrayList<Integer>();
            tempSubList.addAll(temperatures.subList(0, numberOfDaysDisplayed * 24 - 1));
            getMinMax(tempSubList);
            heightScaler(this.getHeight());
            widthScaler(this.getWidth(), tempSubList);
            
            for (int i = 0; i < tempSubList.size() - 1; i++) {
            	
            	g.setColor(Color.black);
            	g2.setStroke(new BasicStroke(3));
            	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            	try {
	            	//Draw the Temperature Line Graph
	            	g2.draw(new Line2D.Float((int)(i*widthScaler + getWidth()*xSpacingPercentage),
	                    	(int)(getHeight() * ySpacingPercentage + (max - tempSubList.get(i)) * heightScaler),
	                    	(int)((i+1)*widthScaler + getWidth()*xSpacingPercentage),
	                 		(int)(getHeight() * ySpacingPercentage + (max - tempSubList.get(i+1)) * heightScaler)));
	            		
            	}
                catch (IndexOutOfBoundsException ex) {
                	System.out.println("Out of Bounds");
                	break;
                }
            	
                //Add a vertical line every X hours
                g2.setStroke(new BasicStroke(2));
                xCoordinate = (int)(i*widthScaler + getWidth() * xSpacingPercentage);
                yCoordinate = (int)(getHeight() * ySpacingPercentage);
                y2Coordinate = (int)(getHeight() - getHeight() * ySpacingPercentage);
                
                
                
                //No Vertical Lines
                if (verticalLinesEveryXHours == 0) {
                	
                	//Only Draw a vertical line every 12 hours
                	if (i % 12 == 0) {
                		//Draw am/pm below the number for every vertical line to match the hours
                    	String amOrPm = amPmCalculator(i);
                    	
                    	if (amOrPm.equals("a")) {
                        	Color myColor = new Color(0, 0, 255, 64);
                            g.setColor(myColor);
                            g2.fillRect(xCoordinate,
                            		yCoordinate,
                            		(int)((i+12)*widthScaler + getWidth()*xSpacingPercentage)-xCoordinate,
                            		y2Coordinate-yCoordinate);
                    	}
                    	else {
                    		Color myColor = new Color(255, 0, 0, 64);
                            g.setColor(myColor);
                            g2.fillRect(xCoordinate,
                            		yCoordinate,
                            		(int)((i+12)*widthScaler + getWidth()*xSpacingPercentage)-xCoordinate,
                            		y2Coordinate-yCoordinate);
                    	}   
                	}
                }
                else if (i % verticalLinesEveryXHours == 0) {
                	
                	//Draw Vertical Lines
                	g2.draw(new Line2D.Float(xCoordinate, yCoordinate, xCoordinate, y2Coordinate));

                	//Draw a number for every vertical line to match the hours
                	//MAGIC NUMBER 5.4 WHY DOES IT WORK
                	if (i%24%12 == 0) {
                		g2.drawString(12 + "", xCoordinate - 3,
                        		(int)(getHeight() - getHeight() * ySpacingPercentage + 15));
                	}
                	else {
                		g2.drawString(i%24%12 + "", xCoordinate - 3,
                        		(int)(getHeight() - getHeight() * ySpacingPercentage + 15));
                	}
                	
                	
                	//Draw am/pm below the number for every vertical line to match the hours
                	String amOrPm = amPmCalculator(i);
                	if (amOrPm.equals("a")) {
                    	Color myColor = new Color(0, 0, 255, 64);
                        g.setColor(myColor);
                        g2.fillRect(xCoordinate,
                        		yCoordinate,
                        		(int)((i+verticalLinesEveryXHours)*widthScaler + getWidth()*xSpacingPercentage)-xCoordinate,
                        		y2Coordinate-yCoordinate);
                	}
                	else {
                		Color myColor = new Color(255, 0, 0, 64);
                        g.setColor(myColor);
                        g2.fillRect(xCoordinate,
                        		yCoordinate,
                        		(int)((i+verticalLinesEveryXHours)*widthScaler + getWidth()*xSpacingPercentage)-xCoordinate,
                        		y2Coordinate-yCoordinate);
                	}     	            	
                }
            }
            g.setColor(Color.black);
            
            //Draw Y-axis line for graph
        	g2.draw(new Line2D.Float((int)(getWidth() * xSpacingPercentage), (int)(getHeight() * ySpacingPercentage), (int)(getWidth() * xSpacingPercentage), (int)(getHeight() - getHeight() * ySpacingPercentage)));
            
            //Draw X-axis line for graph
            g2.draw(new Line2D.Float((int)(getWidth() * xSpacingPercentage),
            		(int)(getHeight() - getHeight() * ySpacingPercentage),
            		(int)(getWidth() - getWidth() * xSpacingPercentage),
            		(int)(getHeight() - getHeight() * ySpacingPercentage)));
            
            //Add Temperatures on Left Side
            int tempXPosition = (int)(getWidth()*xSpacingPercentage - 23);
            
            //Max Temp
            g.drawString(max + "",tempXPosition,(int)(getHeight() * ySpacingPercentage + 5));
            
            //Min Temp
            g.drawString(min + "", tempXPosition, (int)(getHeight() - getHeight() * ySpacingPercentage + 5));
            
            //Other Temps
            //Figure out pixels/temp ratio
            int graphHeightInPixels = y2Coordinate - yCoordinate;
            int tempDifference = max - min;
            double pixelPerTempRatio = 1.0 * graphHeightInPixels / tempDifference;
            
            
            //Midpoint
            int drawTemp = 0;
            for (int i = 0; i < (max - min) / 5 + 1; i++) {
            	drawTemp = min + i * 5;
            	//System.out.println("E: " + drawTemp);
            	
            	//Convert drawTemp into a number divisible by 5
            	if (drawTemp % 5 > 3) {
            		drawTemp = 5 - drawTemp % 5 + drawTemp;
            		//System.out.println("D: " + drawTemp);
            	}
            	else {
            		drawTemp = 5 - drawTemp % 5 + drawTemp;
            		//System.out.println("Test:" + drawTemp);
            	}
            	
            	//Draw the temperatures that are divisible by 5 AND more than 3 degrees from max/min temperatures
            	if (max - drawTemp >= 3 && drawTemp - min >= 3) {
            		int yHeight =  (int)((getHeight() * ySpacingPercentage) + (max - drawTemp) * pixelPerTempRatio);
            		
            		//Draw Temperatures for the Y Axis
            		//For spacing purposes based on number of digits
            		//10 to 99
            		if (drawTemp < 100 && drawTemp >= 10) {
            			g2.drawString("  " + drawTemp, tempXPosition, yHeight);
            		}
            		//-99 to -10
            		else if (drawTemp > -100 && drawTemp <= -10) {
            			g2.drawString(" " + drawTemp, tempXPosition, yHeight);
            		}
            		//0 to 9
            		else if (drawTemp >= 0 && drawTemp < 10) {
            			g2.drawString("    " + drawTemp, tempXPosition + 1, yHeight);
            		}
            		//-9 to -1
            		else if (drawTemp > -10 && drawTemp < 0){
            			g2.drawString("   " + drawTemp, tempXPosition, yHeight);
            		}
            		//100 to 999
            		else if (drawTemp >= 100 && drawTemp < 1000) {
            			g2.drawString("" + drawTemp, tempXPosition - 1, yHeight);
            		}
            		//-999 to 100
            		else if (drawTemp <= -100 && drawTemp > -1000) {
            			g2.drawString("" + drawTemp, tempXPosition - 4, yHeight);
            		}
            		//1000+ or -1000 below
            		else {
            			g2.drawString("" + drawTemp, tempXPosition - 50, yHeight);
            		}
            		
            		//Draw Horizontal Lines corresponding to the temperatures
            		g2.draw(new Line2D.Float((int)(getWidth()*xSpacingPercentage), yHeight, (int)(getWidth() - getWidth() * xSpacingPercentage),yHeight));
            	}
            }
        }
    }
	
	//Calculates if the hour is am or pm
	//Not efficient to use strings but was repurposed
	// **improve
	public String amPmCalculator(int hour) {
		int dailyHour = hour % 24;
		if (dailyHour < 12 ) {
			return "a";
		}
		else {
			return "p";
		}		
	}
	
	//Method for scaling the width of the graph based on the panel size
	public void widthScaler(int myJPanelWidth, List<Integer> tempList) {
		double startWidth = xSpacingPercentage * myJPanelWidth;
		double endWidth = myJPanelWidth - xSpacingPercentage * myJPanelWidth;
		
		widthScaler = (endWidth - startWidth) / (tempList.size() + 1);
		//System.out.println("Width Scaler: " + widthScaler);
	}
	
	//Method for scaling the height of the graph based on the panel size
	public void heightScaler(int myJPanelHeight) {
		//Scale height with temperature with 5% white space on top/bottom
		double maxTempHeight = ySpacingPercentage * myJPanelHeight;
		double minTempHeight = myJPanelHeight - ySpacingPercentage * myJPanelHeight;

		//Get how many pixels per 1 temperature
		heightScaler = (minTempHeight - maxTempHeight)/(max - min);
		//System.out.println("Height Scaler: " + heightScaler);
	}
	
	public void getMinMax(List<Integer> temps) {
		min = temps.get(0);
		max = temps.get(0);
		if (temps.size() <= 1) {
			return;
		}
		
		for (int i = 1; i < temps.size(); i++) {
			if (temps.get(i) < min) {
				min = temps.get(i);
			}
			if (temps.get(i) > max) {
				max = temps.get(i);
			}
		}
	}
}
