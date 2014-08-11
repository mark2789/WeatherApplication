import java.util.ArrayList;

import javax.swing.JApplet;
import javax.swing.JFrame;

public class WeatherApplication {
	
	public static void main(String[] args) {
		//ArrayList<Integer> temperatures = new ArrayList<Integer>();
		
		Temperatures test = new Temperatures();
		//temperatures = test.dailyTemperatures;
		//System.out.println(temperatures);
		
		JFrame f = new JFrame("Graph Temperatures");
        JApplet a = new GraphTemperatures();
        f.getContentPane().add(a);
        a.init();
        f.setBounds(100, 100, 800, 300);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
	}
}
