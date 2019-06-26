package com.emmanuelafoakwah.tree_dictionary;

import com.emmanuelafoakwah.shared.Dictionary;
import static com.emmanuelafoakwah.prototype.PredictivePrototype.isValidSignature;
import static com.emmanuelafoakwah.shared.Constants.DICTIONARY_PATH;

/**
 * This command line program runs the Tree Sigs2Words() function
 * @author Emmanuel Afoakwah
 *
 */
public class Sigs2WordsTree {

	public static void main(String[] args) {

		// Creating an instance of the dictionary
		Dictionary td = new TreeDictionary(DICTIONARY_PATH);
		
		// Loop through the args array returning matching words with the given signature
		for(int i = 0; i<args.length; i++) {
			
			if(isValidSignature(args[i])) {
				System.out.println(args[i] + ": " + td.signatureToWords(args[i]));
			}
		}
	}
}
