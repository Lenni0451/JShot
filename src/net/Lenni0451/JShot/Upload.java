package net.Lenni0451.JShot;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Upload {
	
	private final Config config;
	
	public Upload(final Config config) {
		this.config = config;
	}
	
	public void pushFile() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		try {
			if(Arrays.asList(clipboard.getAvailableDataFlavors()).contains(DataFlavor.imageFlavor)) {
				BufferedImage image = (BufferedImage) clipboard.getData(DataFlavor.imageFlavor);
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(image, "png", baos);
				byte[] imageBytes = baos.toByteArray();
				ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
				
				{
					String url = this.config.getValue("url");
					url = (url.toLowerCase().startsWith("http") ? "" : "http://") + url;
					
					HttpURLConnection con = (HttpURLConnection) new URL(url + "?security-token=" + this.config.getValue("security-token")).openConnection();
			        con.setDoOutput(true);
			        con.setRequestMethod("POST");
			        OutputStream os = con.getOutputStream();
			        
			        byte[] buffer = new byte[1024];
			        int length;
			        
			        while((length = bais.read(buffer)) != -1) {
			        	os.write(buffer, 0, length);
			        }
			        os.close();
			        
			        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			        String line = reader.readLine();
			        
			        if(line.toLowerCase().endsWith(".png")) {
			        	url = url.substring(0, url.lastIndexOf("/") + 1);
			        	StringSelection sel = new StringSelection(url + line);
			        	clipboard.setContents(sel, sel);
			        } else {
			        	System.out.println(line);
						JOptionPane.showMessageDialog(null, "The image could not be uploaded!Site message: \n" + line, "JShot", JOptionPane.ERROR_MESSAGE);
			        }
			        
			        con.disconnect();
				}
			} else {
				JOptionPane.showMessageDialog(null, "No image in the clipboard found!", "JShot", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Throwable e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "An error occurred whilst uploading the file!\nPlease try again using a console to see the occurred exception and report it on github.", "JShot", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
