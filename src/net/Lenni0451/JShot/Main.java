package net.Lenni0451.JShot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main implements ClipboardOwner {

    public static void main(String[] args) {
        Config config;
        try {
            config = new Config();
            config.loadConfig();
        } catch (Throwable e) {
            JOptionPane.showMessageDialog(null, "Could not load config file!", "JShot", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (args.length == 0) {
            if (!config.isSetUp()) {
                JOptionPane.showMessageDialog(null, "Your config is not set up. Please run 'JShot.jar setup'");
                return;
            }
            Upload upload = new Upload(config);
            upload.pushFile();
        } else if (args.length == 1 && args[0].equalsIgnoreCase("setup")) {
            Generator generator = new Generator(config);
            generator.setup();
        } else if (args.length == 1 && args[0].equalsIgnoreCase("clear")) {
            try {
                config.clear();
                config.saveConfig();
                System.out.println("Successfully resetted config!");
            } catch (Throwable e) {
                System.out.println("Could not clear config file! Please check if you have permissions to write to this location.");
            }
        } else if (args.length == 1 && new File(args[0]).isFile()) {
            try {
                File in = new File(args[0]);
                BufferedImage image = ImageIO.read(in);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                TransferableImage img = new TransferableImage(image);
                clipboard.setContents(img, new Main());
            } catch (Throwable e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Could not read the file to clipboard!", "JShot", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Main.main(new String[]{});
        } else {
            System.out.println("Invalid arguments!");
            System.out.println("Use no argument to upload.");
            System.out.println("Use 'JShot.jar setup' to generate a security token and setup the php file.");
            System.out.println("Use 'JShot.jar clear' to reset the config file.");
        }
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
    }

}
