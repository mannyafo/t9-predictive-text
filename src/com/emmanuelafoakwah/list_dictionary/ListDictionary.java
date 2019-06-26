package com.emmanuelafoakwah.list_dictionary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import com.emmanuelafoakwah.shared.Dictionary;
import static com.emmanuelafoakwah.prototype.PredictivePrototype.*;

/**
 * This class details the dictionary arraylist of WordSig entries
 * enabling the return of matching words for a given signature 
 * via the signatureToWords() class
 * @author Emmanuel Afoakwah
 */
public class ListDictionary implements Dictionary {
	
	// Declaring path variable
	private String path;
	
	// Declaring the arraylist storing the dictionary entries
	ArrayList<WordSig> dictionary = new ArrayList<WordSig>();

	/**
	 * This is the constructor for the ListDictionary class
	 * @param path to the dictionary file
	 */
	public ListDictionary(String path) {
		this.path = path;
		dictionary = storeDictionary();
	}
	
	/**
	 * This function returns the dictionary arraylist
	 * @return the dictionary arraylist
	 */
	public ArrayList<WordSig> getDictionary() {
		return dictionary;
	}

	
	/**
	 * This function reads the dictionary file and populates the 
	 * dictionary arraylist with the corresponding WordSig entries
	 * @return an arraylist dictionary converted from the dictionary file
	 */
	public ArrayList<WordSig> storeDictionary(){
		
		// Creating the file to read from
		File file = new File(path);
		
		// Generating the arraylist to store the entries
		ArrayList<WordSig> dict = new ArrayList<WordSig>();
		
		/*
		 * Try-catch statement attempting to read file 
		 * If the file is present the scanner scans each word
		 * adding a WordSig entry using the wordToSignature function 
		 * to generate the appropriate signature
		 * If the file is not present an error message is printed 
		 * along with the stack trace  
		 */
		try {
			Scanner scan = new Scanner(file);
			
			while(scan.hasNextLine()) {
				
				String dictWord = scan.nextLine().toLowerCase();
				
				if( isValidWord(dictWord) ) {
					WordSig add = new WordSig(dictWord, wordToSignature(dictWord));
					dict.add(add);
				}
			}
			
		}catch(FileNotFoundException e) {
			System.out.println("File not found ...");
			e.printStackTrace();
		}
		
		// Return the sorted arraylist
		Collections.sort(dict);
		return dict;
	}
	
	/*
	 * (non-Javadoc)
	 * @see predictive.Dictionary#signatureToWords(java.lang.String)
	 * This function implements the required signatureToWords method
	 * Taking in the signature and generating a set of corresponding 
	 * words from the dictionary 
	 */
	@Override
	public Set<String> signatureToWords(String signature) {
		
		//System.out.println(getDictionary());
		return binarySearch(signature, dictionary);
	}
	
	/**
	 * This function performs a binary search of a given arraylist 
	 * of WordSig entries for a given signature, returning a set of 
	 * Words which match the given signature
	 * @param signature
	 * @param d
	 * @return a set of words matching the input signature
	 */
	public Set<String> binarySearch(String signature, ArrayList<WordSig> d){
		
		// Declaring the output Set
		Set<String> out = new TreeSet<String>();
		
		// Storing dictionary to edit on iterations
		ArrayList<WordSig> copyd = d;
		
		// Scan above and below every found word until there are no more matches
		while(Collections.binarySearch(d, new WordSig(null, signature))>=0){
			
			// Store the index of the entry in the ArrayList
			int index = Collections.binarySearch(d, new WordSig(null, signature));
			
			// Add the word from that index to the output set
			out.add(d.get(index).getWords());
			
			// Remove the stored word from the dictionary 
			copyd.remove(index);
			
		}
		return out;
	}
}


