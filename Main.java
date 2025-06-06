package edu.iastate.cs228.hw4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;


/**
 * 
 * @author Lachlan Kreunen
 *
 */



/**
 * The Main class contains the main method to execute the decoding process.
 */
public class Main {

    /**
     * The entry point of the program, responsible for decoding a message.
     *
     * @param args Command-line arguments (not used in this program).
     * @throws IOException If an I/O error occurs during file reading.
     */
    public static void main(String[] args) throws IOException {
    	System.out.println("Please enter filename to decode:");
    	Scanner scnr = new Scanner(System.in);
    	String nameOfFile = scnr.nextLine(); 
    	scnr.close();

    	String contentOfFile = new String(Files.readAllBytes(Paths.get(nameOfFile))).trim();


    	int currChar = contentOfFile.lastIndexOf('\n');
    	String scheme = contentOfFile.substring(0, currChar); 
    	String binCode = contentOfFile.substring(currChar).trim(); 


    	boolean[] seenCharacters = new boolean[128];
    	StringBuilder codeBuilder = new StringBuilder();

    	for (char c : scheme.toCharArray()) {
    	    if (c != '^' && !seenCharacters[c]) {
    	        seenCharacters[c] = true; 
    	        codeBuilder.append(c); 
    	    }
    	}
    	String code = codeBuilder.toString();
    	MsgTree tree = new MsgTree(scheme);
    	MsgTree.printCodes(tree, code);
    	tree.decode(tree, binCode);
    	
    }

}

