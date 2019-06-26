package com.emmanuelafoakwah.prototype;

/**
 * This command line program runs the prototype wordToSignature() function
 * @author Emmanuel Afoakwah
 */
public class Words2SigProto {

	public static void main(String[] args) {

		// Loop through the args array converting and printing the words to signatures
		for(int i = 0; i<args.length; i++) {
			System.out.println(args[i] + ": " + PredictivePrototype.wordToSignature(args[i]));
		}
		
	}

}
