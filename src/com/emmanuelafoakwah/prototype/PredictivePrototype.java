package com.emmanuelafoakwah.prototype;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import static com.emmanuelafoakwah.shared.Constants.DICTIONARY_PATH;

/**
 * This class contains the predictive text prototype comprising four functions:
 * 1) wordToSignature - converts a word to the corresponding T9 signature
 * 2) signatureToWords - returns from the dictionary text file all the words that match the signature
 * 3) isValidWord - validates word inputs
 * 4) isValidSignature - validates signature input
 * @author Emmanuel Afoakwah
 *
 */
public class PredictivePrototype {
	
	/**
	 * This function converts a word to a signature
	 * @param word is the string to convert
	 * @return a string representing the signature corresponding to the word
	 */
	public static String wordToSignature(String word) {
		
		// Creating the char array
		String wordIn = word.toLowerCase();
		char[] wordChars = wordIn.toCharArray();
		
		// The string buffer to cumulatively store the signature
		StringBuffer sBuffer = new StringBuffer("");
		
		// Loops through the char array and cumulatively adds the appropriate digit
		for(char c : wordChars) {
			
			String sigInt = "";
			
			if( Character.isAlphabetic(c)) {
				
				switch(c) {
					case 'a': case 'b': case 'c': sigInt = "2"; break;
					case 'd': case 'e': case 'f': sigInt = "3"; break;
					case 'g': case 'h': case 'i': sigInt = "4"; break;
					case 'j': case 'k': case 'l': sigInt = "5"; break;
					case 'm': case 'n': case 'o': sigInt = "6"; break;
					case 'p': case 'q': case 'r': case 's': sigInt = "7"; break;
					case 't': case 'u': case 'v': sigInt = "8"; break;
					case 'w': case 'x': case 'y': case 'z': sigInt = "9"; break;
					default: sigInt = " ";
				}
				sBuffer.append(sigInt);
			}else {
				// Add a space for non alphabetical
				sBuffer.append(" ");
			}
		}
		// return the complete signature as a string
		return sBuffer.toString();
	}
	
	/**
	 * This function produces a set of words corresponding to the given signature
	 * @param signature is the signature to cross-reference against the dictionary
	 * @return a set of words whose signature matches the input signature
	 */
	public static Set<String> signatureToWords(String signature){

		File dictionary = new File(DICTIONARY_PATH); // Final Dictionary
		
		// The set of string words to return
		Set<String> out = new TreeSet<String>();
		
		// Try-catch attempting to read the file, throws an error if it isn't present
		try { 
			Scanner scan = new Scanner(dictionary);
			
			// Scan each word in the dictionary and check if it's signature matches
			while(scan.hasNextLine()) {
				
				String dictWord = scan.nextLine().toLowerCase();
				
				// If the signature matches, add the word to the set
				if( signature.equals(wordToSignature(dictWord)) 
						&& (out.contains(dictWord)==false)
						&& isValidWord(dictWord)) {
					out.add(dictWord);
				}
			}
			scan.close();
		}catch(FileNotFoundException e) {
			// If the file is not there print a message and the stack trace
			System.out.println("File not found ...");
			e.printStackTrace();
		}
		return out;
	}
	
	/**
	 * This functions checks if a given string is a valid word
	 * @param word is the word to check
	 * @return a boolean indicating whether the word is valid 
	 */
	public static boolean isValidWord(String word) {
		
		// Creating char array
		String wordIn = word.toLowerCase();
		char[] wordChars = wordIn.toCharArray();
		
		// If any of the characters are alphabetic return true
		for( char c : wordChars ) {
			if( Character.isAlphabetic(c) == false ) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This function checks to see whether the given signature is valid
	 * -The signature is valid if it contains no non-alphabetic characters
	 * these would be represented by spaces eg.) '123 456'.
	 * -Therefore the function ensures that there are no spaces in the input strings
	 * to the args array.
	 * @param signature is the String signature to check
	 * @return a boolean indicating whether the given signature is valid
	 */
	public static boolean isValidSignature(String signature) {
		char[] sig = signature.toCharArray();
		for(char c : sig) {
			if(( c == ' ')) {
				return false;
			}
		}
		return true;
	}

}
