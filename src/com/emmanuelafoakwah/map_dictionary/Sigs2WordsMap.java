package com.emmanuelafoakwah.map_dictionary;

import com.emmanuelafoakwah.shared.Dictionary;
import static com.emmanuelafoakwah.prototype.PredictivePrototype.isValidSignature;
import static com.emmanuelafoakwah.shared.Constants.DICTIONARY_PATH;

/**
 * This command line program runs the Map Sigs2Words() function
 * @author Emmanuel Afoakwah
 */
public class Sigs2WordsMap {

	public static void main(String[] args) {

		// Creating an instance of the dictionary
		Dictionary md = new MapDictionary(DICTIONARY_PATH);
		
		// Loop through the args array returning matching words with the given signature
		for(int i = 0; i<args.length; i++) {
			
			if(isValidSignature(args[i])) {
				System.out.println(args[i] + ": " + md.signatureToWords(args[i]));
			}
		}
	}
}
