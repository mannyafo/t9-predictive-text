package com.emmanuelafoakwah.tree_dictionary;

import com.emmanuelafoakwah.shared.Dictionary;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Scanner;
import java.util.Set;
import static com.emmanuelafoakwah.prototype.PredictivePrototype.isValidWord;
import static com.emmanuelafoakwah.prototype.PredictivePrototype.wordToSignature;
import com.emmanuelafoakwah.tree_dictionary.Octree.*;

/**
 * This class details the Tree Dictionary which is comprised of Octrees
 * The signatureToWords function required by the Dictionary interface, 
 * returns the set of words for a given dictionary pertaining to 
 * the input signature
 * 
 * @author Emmanuel Afoakwah
 */
public class TreeDictionary implements Dictionary {
	
	// Declaring String path
	private String path;
		
	// ArrayList to store dictionary
	ArrayList<String> words = new ArrayList<String>();
	
	// Octree to store Dictionary (start with empty)
	Octree octreeDictionary = new Octree();

	/**
	 * This is the constructor for the tree dictionary
	 * @param path is the string path to the dictionary text file
	 */
	public TreeDictionary(String path) {
		this.path = path;
		words = getWords();
		octreeDictionary = storeTreeDictionary();
	}
	
	/* (non-Javadoc)
	 * @see predictive.Dictionary#signatureToWords(java.lang.String)
	 * This function returns the set of words pertaining to a given signature 
	 * as defined by the Octree stored within this Tree Dictionary
	 * The words are trimmed to match the length of the input string
	 */
	@Override
	public Set<String> signatureToWords(String signature) {
		
		// Store matching whole words 
		Set<String> wordMatches = searchNodes(signature, octreeDictionary); 
		
		// Declare Set to hold trimmed words
		Set<String> out = new TreeSet<String>();
		
		// Store the length of the input signature
		int sigLength = signature.length();
		
		/*
		 * Loop through the set of matching whole words
		 * add each word to the output set after trimming to 
		 * the appropriate size
		 */
		for(String word : wordMatches) {
			out.add(word.substring(0, sigLength));
		}
		
		// Return the trimmed output
		return out;
	}
	
	/**
	 * This function recursively searches an Octree to locate the node
	 * with matching signature for the given signature and Octree
	 * and returns the set of words at the matching node
	 * @param signature is the signature to search for
	 * @param tree is the Octree to search
	 * @return the set of words stored at the matching nodes
	 */
	public Set<String> searchNodes(String signature, Octree tree){
		
		// Store the signature at the current node
		String nodeSignature = tree.getSignature();
		
		/*
		 * If the tree has no matching children return an empty TreeSet
		 * else check for full or partial matches
		 * Full match -> return words at this node 
		 * Partial match -> recursively call this function on the matching child Octree
		 */
		if( tree.getChild(signature)==null ) {
			return new TreeSet<String>();
		}else {
			// Calculate match result via signatureMatch helper function
			int matchResult = signatureMatch( tree.getChild(signature).getSignature() , signature);
			
			// Check for full or partial match
			if( matchResult  == 2 ) {
				return tree.getChild(signature).getWords();
			}else {
				return searchNodes(signature, tree.getChild(signature));
			}
		}
	}
	
	/**
	 * This function stores the tree dictionary as defined 
	 * by the dictionary arraylist
	 * @return an Octree storing the dictionary words
	 */
	public Octree storeTreeDictionary() {
		
		Octree out = new Octree();
		
		for(String word : words) {
			insertWord(word, out);
		}
		return out;
	}
	
	/**
	 * This function inserts the input word into the given tree
	 * @param word to be inserted
	 * @param tree to insert the word into
	 * @return an Octree with the word inserted
	 */
	public Octree insertWord(String word, Octree tree) {
		
		String nodeSignature = tree.getSignature();
		String wordSignature = wordToSignature(word);

		int sigTargetLength = signatureTargetLength(tree);
		String partialSig = wordSignature.substring(0, sigTargetLength);
		//System.out.println(partialSig);
		
		if( tree.getChild(wordSignature)==null ) {
			//System.out.println("No child match");
			Octree addTree = new Octree(partialSig, new TreeSet<String>() );
			tree.addChild(addTree);
			return insertWord(word, tree);
		}else {
			
			// Check for full or partial match
			int matchResult = signatureMatch( tree.getChild(wordSignature).getSignature() , wordSignature);
			//System.out.println("Match Result: " + matchResult);
			
			if(matchResult == 2) {
				tree.getChild(wordSignature).addWord(word);
				return tree;
			}else {
				tree.getChild(wordSignature).addWord(word);
				return insertWord(word, tree.getChild(wordSignature));
			}
		}
	}
	
	/**
	 * This function calculates the expected length of the 
	 * signatures of the children of a given node
	 * @param tree is the tree to calculate the signature target length for
	 * @return an integer representing the expected signature length for the children of the node 
	 */
	public int signatureTargetLength(Octree tree) {
		
		if(tree.getSignature() == null) {
			return 1;
		}else{
			return tree.getSignature().length()+1;
		}
	}

	/**
	 * This functions checks two input strings to establish
	 * whether they are full or partial matches
	 *
	 * Output:
	 * 2 = Full Match (exact match)
	 * 1 = Partial Match (reference is a substring of check)
	 * 0 = Partial Match (check is a substring of check)
	 * -1 = No Match
	 *
	 * @param reference is the reference string
	 * @param check is the string to check
	 * @return an integer indicating full(2), partial(1,0) or no match (-1)
	 */
	public int signatureMatch(String reference, String check) {

		// If either input string is null return -1 (no match)
		if( reference == null || check == null ) {
			return -1;
		}

		// Generate Char Arrays
		char[] ref = reference.toCharArray();
		char[] ch = check.toCharArray();


		/*
		 * Loop through the check string
		 * 1) If the strings do not match at an index return -1 (no match)
		 * 2) Check for partial matches
		 * - If the loop reaches the end of either char array without returning -1
		 * - There must be a partial match (one string is a substring of the other)
		 * - Therefore return either 0 or 1 as appropriate (see javadoc comment)
		 */
		for(int i = 0; i<ch.length; i++) {

			Character c1 = ch[i];
			Character c2 = ref[i];

			// Check for non-matches
			if( c1.equals(c2) == false) {
				return -1;
			}

			// Logic to handle partial matches
			if( (i == ch.length-1) && (i < ref.length - 1) ) {
				return 0;
			}else if( (i < ch.length-1) && (i == ref.length - 1) ) {
				return 1;
			}
		}

		/*
		 * If there are no non matching characters and
		 * every character in both strings has been checked
		 * there must be a full match (return 2)
		 */
		return 2;
	}

		
	/**
	 * This function returns the Octree dictionary 
	 * @return the Octree dictionary
	 */
	public Octree getDictionary() {
		return octreeDictionary;
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
				
				if(isValidWord(dictWord)) {
					out.add(dictWord);
				}
			}
			
		}catch(FileNotFoundException e) {
			System.out.println("File not found ...");
			e.printStackTrace();
		}
		return out;
	}
	
}
