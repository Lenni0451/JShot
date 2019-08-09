package net.Lenni0451.JShot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Config {
	
	private static final String separator = "=";
	
	private final File configFile;
	private final Map<String, String> configValues;
	
	public Config() throws IOException {
		this.configFile = new File(System.getProperty("user.home"), "jshot.cfg");
		this.configValues = new HashMap<>();
		
		this.configFile.getParentFile().mkdirs();
		this.configFile.createNewFile();
	}
	
	public File getConfigFile() {
		return this.configFile;
	}
	
	public void loadConfig() throws FileNotFoundException {
		Scanner s = new Scanner(this.configFile);
		while(s.hasNextLine()) {
			String line = s.nextLine();
			if(line.split(separator).length != 2) {
				System.out.println("Invalid line in config:");
				System.out.println(line);
				continue;
			}

			String key = line.split(separator)[0].trim();
			String value = line.split(separator)[1].trim();
			this.configValues.put(key, value);
		}
		s.close();
	}
	
	public void saveConfig() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(this.configFile));
		for(Map.Entry<String, String> entry : this.configValues.entrySet()) {
			writer.write(entry.getKey() + separator + entry.getValue());
			writer.newLine();
		}
		writer.close();
	}
	
	public boolean isSetUp() {
		return this.configValues.containsKey("url") && this.configValues.containsKey("security-token");
	}
	
	public String addValue(final String key, final String value) {
		this.configValues.put(key, value);
		return value;
	}
	
	public String getValue(final String key) {
		return this.configValues.get(key);
	}
	
	public void clear() {
		this.configValues.clear();
	}
	
}
