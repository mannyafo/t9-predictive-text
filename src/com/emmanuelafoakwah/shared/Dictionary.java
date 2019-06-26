package com.emmanuelafoakwah.shared;

import java.util.Set;

/**
 * This interface defines the necessary function for dictionary classes
 * @author Emmanuel Afoakwah
 *
 */
public interface Dictionary {
	
	// Classes implementing dictionary must have the signatureToWords function
	/** 
	 * Classes implementing dictionary must have the signatureToWords function
	 * This functions returns a set of string words pertaining to the given input signature
	 * as defined by the given dictionary
	 * @param signature
	 * @return a set of string words pertaining to the given input signatures defined by the given dictionary
	 */
	public Set<String> signatureToWords(String signature);
	
}
