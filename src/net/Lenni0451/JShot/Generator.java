package net.Lenni0451.JShot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.UUID;

public class Generator {
	
	private final Config config;
	
	public Generator(final Config config) {
		this.config = config;
	}
	
	public void setup() {
		if(this.config.isSetUp()) {
			System.out.println("JShot is already set up!");
			return;
		}
		
		String securityToken = UUID.randomUUID().toString().replace("-", "");
		
		System.out.println("Your security token is: " + securityToken);
		this.config.addValue("security-token", securityToken);
		
		Scanner s = new Scanner(System.in);
		File out;
		while(true) {
			System.out.print("Enter the path where your php file should be saved to: ");
			String line = s.nextLine();
			out = new File(line + (line.toLowerCase().endsWith(".php") ? "" : ".php"));
			if(out.exists()) {
				System.out.println("The given file already exists! Please choose and location where no file is currently stored.");
			} else {
				break;
			}
		}
		if(out.getParentFile() != null) {
			out.getParentFile().mkdirs();
		}
		
		System.out.println("Printing file...");
		LABEL_PRINT_FILE: {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/upload.php")));
				BufferedWriter writer = new BufferedWriter(new FileWriter(out));
				String line;
				while((line = reader.readLine()) != null) {
					writer.write(line.replace("<TOKEN>", securityToken));
					writer.newLine();
				}
				writer.close();
				reader.close();
			} catch (Throwable e) {
				System.out.println("Could not write php file! Please check if you have permissions to write to this location.");
				break LABEL_PRINT_FILE;
			}
			
			System.out.println("The php file has been written. Upload it to your webserver and enter the url where to access it below.");
			System.out.print("URL: ");
			String url = s.nextLine();
			this.config.addValue("url", url);
			
			try {
				this.config.saveConfig();
				System.out.println("Successfully created config file! You are now able to use JShot!");
			} catch (Throwable e) {
				System.out.println("Could not write config file! Please check if you have permissions to write to this location.");
			}
		}
		s.close();
	}
	
}
