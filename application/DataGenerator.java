package application;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DataGenerator {
	public void generate() {
		
		try(PrintWriter writer = new PrintWriter(new FileWriter("data/data.txt"))){
			
			int currentCenter;
			int nextPage;
			
			writer.println("process1");
			
			for (int i = 0; i < 100; i++) {
				currentCenter = (int) (Math.random() * 50);
				
				for (int j = 0; j < 15; j++) {
					nextPage = (int) (Math.random() * 2 + currentCenter);
					writer.println(nextPage);
					writer.println(5);
				}
				
				for (int j = 0; j < 5; j++) {
					nextPage = (int) (Math.random() * 50);
					writer.println(nextPage);
				}
				
				
				for (int k = 0; k < 15; k++) {
					nextPage = (int) (Math.random() * 5 + currentCenter);
					writer.println(nextPage);
					writer.println(5);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
