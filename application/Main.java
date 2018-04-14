package application;
	
import algorithms.A_LRU;
import algorithms.FIFO;
import algorithms.LRU;
import algorithms.OPT;
import algorithms.RAND;
import hardware.CPU;
import hardware.RAM;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import virtual_memory.VirtualMemory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	
	private TextArea out;
	private Button start;
	private TextField ramSize;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			
			VBox menu = new VBox(15);
			menu.setAlignment(Pos.CENTER);
			menu.setPadding(new Insets(20, 20, 20, 20));
			
			start = new Button("Start");
			start.setOnAction(e -> {
				
				out.appendText("RAM: " + ramSize.getText());
				RAM ram = new RAM(Integer.parseInt(ramSize.getText()));
				VirtualMemory vm = new VirtualMemory(5000);
				CPU cpu = new CPU(new OPT(ram, vm), ram, vm);
				cpu.run();
				out.appendText("\n\nOPT:                         " + cpu.getMmu().getPageFaults());
				
				ram = new RAM(Integer.parseInt(ramSize.getText()));
				vm = new VirtualMemory(5000);
				cpu = new CPU(new LRU(ram, vm), ram, vm);
				cpu.run();
				out.appendText("\nLRU:                         " + cpu.getMmu().getPageFaults());
				
				ram = new RAM(Integer.parseInt(ramSize.getText()));
				vm = new VirtualMemory(5000);
				cpu = new CPU(new A_LRU(ram, vm), ram, vm);
				cpu.run();
				out.appendText("\nApproximated LRU: " + cpu.getMmu().getPageFaults());
				
				ram = new RAM(Integer.parseInt(ramSize.getText()));
				vm = new VirtualMemory(5000);
				cpu = new CPU(new FIFO(ram, vm), ram, vm);
				cpu.run();
				out.appendText("\nFIFO:                        " + cpu.getMmu().getPageFaults());
				
				ram = new RAM(Integer.parseInt(ramSize.getText()));
				vm = new VirtualMemory(5000);
				cpu = new CPU(new RAND(ram, vm), ram, vm);
				cpu.run();
				out.appendText("\nRAND:                      " + cpu.getMmu().getPageFaults() + "\n\n\n\n\n");
			});
			
			Separator separator = new Separator();
			separator.setPadding(new Insets(20, 0, 20, 0));
			Label ramLabel = new Label("RAM size:");
			ramSize = new TextField("10");
			
			
			Button generate = new Button("New data");
			generate.setOnAction(e -> {
				DataGenerator dg = new DataGenerator();
				dg.generate();
			});
			
			menu.getChildren().addAll(start, generate, separator, ramLabel, ramSize);
			root.setLeft(menu);
			
			
			GridPane center = new GridPane();
			center.setAlignment(Pos.CENTER);
			
			out = new TextArea();
			out.setEditable(false);
			out.setPrefWidth(300);
			out.setPrefHeight(300);
			center.add(out, 0, 0);
			root.setCenter(center);
			
			Scene scene = new Scene(root,590,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Page Replacement Simulator");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
