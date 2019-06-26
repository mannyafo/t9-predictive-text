package com.emmanuelafoakwah.list_dictionary;

/**
 * This class represents entries in the dictionaries arraylist
 * Entries are comprised of paired words and signatures
 * @author Emmanuel Afoakwah
 *
 */
public class WordSig implements Comparable<WordSig>{
	
	// Declaring variables
	private String words;
	private String signature;
	
	/**
	 * This is the construction for the WordSig class
	 * The class represents entries in the dictionary
	 * @param words is the word to assign to the entry
	 * @param signature is the signature to assign to the entry
	 */
	public WordSig(String words, String signature) {
		this.words = words;
		this.signature = signature;
	}
	
	/**
	 * This functions retrieves the word in this dictionary entry
	 * @return a string representing the word for the entry
	 */
	public String getWords() {
		return words;
	}
	
	/**
	 * This functions retrieves the signature in this dictionary entry
	 * @return a string representing the word for the entry
	 */
	public String getSignature() {
		return signature;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * Function defines how one entry can be compared to another
	 * Returning an integer indicating comparison outcome
	 */
	public int compareTo(WordSig ws) {
		
		int out = signature.compareTo(ws.signature);
		
		if( out>0 ) { 
			return 1;
		}else if( out == 0 ) {
			return 0;
		}else {
			return -1;
		}
	}

	@Override
	// Overrides toString() for easy printing
	public String toString() {
		return "WordSig [words=" + words + ", signature=" + signature + "]";
	}

}
