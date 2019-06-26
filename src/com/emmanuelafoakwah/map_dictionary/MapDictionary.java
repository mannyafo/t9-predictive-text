package com.emmanuelafoakwah.map_dictionary;

import com.emmanuelafoakwah.shared.Dictionary;
import static com.emmanuelafoakwah.prototype.PredictivePrototype.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * This class details the Map Dictionary which is comprised of a TreeMap
 * The signatureToWords function required by the Dictionary interface, 
 * returns the set of words for a given dictionary pertaining to 
 * the input signature
 * @author Emmanuel Afoakwah
 */
public class MapDictionary implements Dictionary {
	
	// Declaring String path
	private String path;
	
	// ArrayList to store dictionary
	ArrayList<String> words = new ArrayList<String>();
	
	/*
	 *  Map Dictionary to cross reference against input signature
	 *  key: signature
	 *  values: set of words pertaining to that signature
	 */
	Map<String, Set<String>> mapDictionary = new TreeMap<String, Set<String>>();

	/**
	 * This is the constructor for the Map Dictionary class
	 * the constructor performs 3 operations:
	 * 1) assigns the path to the text file to read from when
	 * 		populating the stored dictionary
	 * 2) populates the 'words' Arraylist
	 * 3) uses the words Arraylist to populate the 'mapDictionary' TreeMap
	 * @param path is the String path to the Dictionary text file  
	 */
	public MapDictionary(String path) {
		this.path = path;
		words = getWords();
		mapDictionary = storeMapDictionary();
	}
	
	/**
	 * This function returns Map Dictionary
	 * @return the Map Dictionary
	 */
	public Map<String, Set<String>> getMapDictionary() {
		return mapDictionary;
	}

	
	public TreeMap<String, Set<String> > storeMapDictionary() {
		
		// Declaring the Map Dictionary to return
		Map<String, Set<String>> mapDict = new TreeMap<String, Set<String>>();
		
		for(String word : words) {
			
			// Assigning keyword to check
			String dictWord = word;
			
			// If the keyword is a valid word add an entry in the Map Dictionary
			if( isValidWord(dictWord) ) {
				
				// Assigning signature of keyword
				String signature = wordToSignature(dictWord);
				
				if(mapDict.containsKey(signature)) {
					mapDict.get(signature).add(dictWord);
				}else {
					mapDict.put(signature, new TreeSet<String>());
					mapDict.get(signature).add(dictWord);
				}
			}
		}
		return (TreeMap<String, Set<String>>) mapDict;	
	}
	
	/**
	 * This function returns a set of words pertaining to the given
	 * input signature as defined by the given dictionary
	 * @param signature is the signature to cross reference against 
	 * the Map Dictionary
	 * @return a set of words pertaining to the given signature
	 */
	public Set<String> getWordSet(String signature){
		
		// Declaring the set of strings to return
		Set<String> out = new TreeSet<String>(); 
		
		/*
		 * Loop through the words arraylist, checking whether the 
		 * 1) signature of the word at the given index matches the 
		 * input signature 
		 * 2) if the strings are equal, add the word at the given 
		 * index to the output set
		 */
		for(String word : words) {
			
			// Generating signature for word at given index
			String dictWord = word.toLowerCase();
			String sig = wordToSignature(dictWord);
			
			// Checking for signature equality, and adding if appropriate
			if( signature.equals(sig)  ) {
				out.add(dictWord);
			}
		}
		return out;
	}
	
	
	/**
	 * This function reads the contents of the input file line by line 
	 * adding the string on each line to the output arraylist
	 * @return an arraylist of strings representing the words in the dictionary text file
	 */
	public ArrayList<String> getWords(){
		
		// Declaring path to Dictionary text file
		File file = new File(path);
		
		// Declaring output ArrayLisst
		ArrayList<String> out = new ArrayList<String>();
		
		
		/*
		 * Try catch block attempting to read the dictionary text file 
		 * line by line and add the word at each line to the 
		 * output arraylist
		 * If the file is not present, a message is printed 
		 * along with the stack trace 
		 */
		try {
			Scanner scan = new Scanner(file);
			
			while(scan.hasNextLine()) {
				String dictWord = scan.nextLine().toLowerCase();
				out.add(dictWord);
			}
			
		}catch(FileNotFoundException e) {
			System.out.println("File not found ...");
			e.printStackTrace();
		}
		return out;
	}

	@Override
	public Set<String> signatureToWords(String signature) {
		
		/*
		 * If the map dictionary contains the signature,
		 * return the corresponding set of string words
		 */
		if( mapDictionary.containsKey(signature)) {
			return mapDictionary.get(signature);
		}
		return new TreeSet<String>();
	}
	
}
