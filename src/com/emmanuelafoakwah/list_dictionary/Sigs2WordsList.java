package com.emmanuelafoakwah.list_dictionary;

import com.emmanuelafoakwah.prototype.PredictivePrototype;
import com.emmanuelafoakwah.shared.Dictionary;
import static com.emmanuelafoakwah.shared.Constants.DICTIONARY_PATH;

/**
 * This command line program runs the List Sigs2Words() function
 * @author Emmanuel Afoakwah
 *
 */
public class Sigs2WordsList {

	public static void main(String[] args) {
		
		// Creating an instance of the dictionary
		Dictionary ld = new ListDictionary(DICTIONARY_PATH);
		
		// Loop through the args array returning matching words with the given signature
		for(int i = 0; i<args.length; i++) {
			
			if(PredictivePrototype.isValidSignature(args[i])) {
				System.out.println(args[i] + ": " + ld.signatureToWords(args[i]));
			}

		}
	}

}
