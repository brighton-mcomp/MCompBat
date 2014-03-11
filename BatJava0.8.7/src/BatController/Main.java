package BatController;

import java.io.File;

/**
 * Main method which initiates the program.
 * @author Sean Murphy
 * @author John Miles-Groves
 * 
 */

public class Main {

	public static void main(String[] args) {

		new GUI();

		// remove when compiling for Linux
		File dll = new File("rxtxSerial.dll");
		System.load(dll.getAbsolutePath());
		// add for Linux
		// File so = new File("librxtxSerial.so");
		// System.load(so.getAbsolutePath());
	}
}