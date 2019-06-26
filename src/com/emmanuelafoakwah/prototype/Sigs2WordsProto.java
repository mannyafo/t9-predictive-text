package com.emmanuelafoakwah.prototype;

/**
 * This command line program runs the prototype Sigs2Words() function
 * @author Emmanuel Afoakwah
 */
public class Sigs2WordsProto {

	public static void main(String[] args) {
		
		// Loop through the args array returning matching words with the given signature
		for(int i = 0; i<args.length; i++) {
			
			if( PredictivePrototype.isValidSignature(args[i])) {
				System.out.println(args[i] + ": " + PredictivePrototype.signatureToWords(args[i]));
			}
		}
	}
	
}
