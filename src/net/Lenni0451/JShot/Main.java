package net.Lenni0451.JShot;

import javax.swing.JOptionPane;

public class Main {
	
	public static void main(String[] args) {
		Config config;
		try {
			config = new Config();
			config.loadConfig();
		} catch (Throwable e) {
			JOptionPane.showMessageDialog(null, "Could not load config file!", "JShot", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(args.length == 0) {
			if(!config.isSetUp()) {
				JOptionPane.showMessageDialog(null, "Your config is not set up. Please run 'JShot.jar setup'");
				return;
			}
			Upload upload = new Upload(config);
			upload.pushFile();
		} else if(args.length == 1 && args[0].equalsIgnoreCase("setup")) {
			Generator generator = new Generator(config);
			generator.setup();
		} else if(args.length == 1 && args[0].equalsIgnoreCase("clear")) {
			try {
				config.clear();
				config.saveConfig();
				System.out.println("Successfully resetted config!");
			} catch (Throwable e) {
				System.out.println("Could not clear config file! Please check if you have permissions to write to this location.");
			}
		} else {
			System.out.println("Invalid arguments!");
			System.out.println("Use no argument to upload.");
			System.out.println("Use 'JShot.jar setup' to generate a security token and setup the php file.");
			System.out.println("Use 'JShot.jar clear' to reset the config file.");
		}
	}
	
}
