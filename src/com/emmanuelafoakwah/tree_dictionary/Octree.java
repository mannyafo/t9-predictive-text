package com.emmanuelafoakwah.tree_dictionary;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Set;

/**
 * This class contains my Octree implementation:
 * 
 * Each node stores:
 * - A boolean indicating whether it is empty
 * - The string signature
 * - A set of words pertaining to the signature
 * - An arraylist of up to 8 child nodes
 * 
 * The class has the following functionality (key functions):
 * 		1) Get the child for a given signature (getChild)
 * 		2) Adding words to the set of words (addWord)
 * 		3) Adding a child to the arraylist of children (addChild)
 * 		4) Utility function to compare signatures for full or 
 * 			partial matches (signatureMatch)
 * 		5) Return the signature of the root node(getSignature)
 * 		6) Return the set of words for the root node (getWords)
 * 		7) Return the arraylist of children for the root node (getChildren)
 * 
 * @author Emmanuel Afoakwah
 */
public class Octree {
	
	// Declaring tree properties
	private boolean empty;
	private String signature;
	private Set<String> words;
	private ArrayList<Octree> children;
	
	/**
	 * This is the constructor for an empty tree
	 */
	public Octree() {
		this.empty = true;
		this.signature = null;
		this.words = new TreeSet<String>();
		this.children = new ArrayList<Octree>();
	}
	
	/**
	 * This is the constructor for a master root node
	 * (Master Root - no signature, no words, w/ children)
	 * @param children is the arraylist of Octree children
	 */
	public Octree(ArrayList<Octree> children) {
		this.empty = false;
		this.signature = null;
		this.words = new TreeSet<String>();
		this.children = children;
	}
	
	/**
	 * This is the constructor for a leaf node
	 * @param signature is the signature of the node
	 * @param words is the Set of words stored at the node
	 */
	public Octree(String signature, Set<String> words) {
		this.empty = false;
		this.signature = signature;
		this.words = words;
		this.children = new ArrayList<Octree>();
	}
	
	/**
	 * This is the constructor for a Full mid-tree Node
	 * @param signature is the signature of the node
	 * @param words is the Set of words for the node
	 * @param children is the arraylist of Octree children
	 */
	public Octree(String signature, Set<String> words, ArrayList<Octree> children) {
		this.empty = false;
		this.signature = signature;
		this.words = words;
		this.children = children;
	}

	/**
	 * This function returns the child whose signature matches
	 * the input string, if there is no such child return null
	 * @param signature is the signature to cross reference against the children
	 * @return the child Octree matching the given input signature
	 */
	public Octree getChild(String signature) {
		
		// If there are no children return null
		if(children.isEmpty()) {
			return null;
		}
		
		// Loop through the children and return the matching child
		for(int i = 0; i<children.size(); i++) {
			
			// Calculate the match result via the signatureMatch helper function
			int matchResult = signatureMatch(children.get(i).getSignature(), signature);
			
			/*
			 * If there is a match at this index:
			 * -return the child at this index
			 */
			if( matchResult > 0 ) {
				return children.get(i);
			}
		}
		/*
		 * If the for loop does not hit a match
		 * there are no matching children
		 * therefore return null
		 */
		return null;
	}
	
	/**
	 * This functions checks two input strings to establish
	 * whether they are full or partial matches
	 *
	 * Output:
	 * 2 = Full Match (exact match)
	 * 1 = Partial Match (reference is a substring of check)
	 * 0 = Partial Match (check is a substring of reference)
	 * -1 = No Match
	 *
	 * @param reference is the reference string
	 * @param check is the string to check
	 * @return an integer indicating full(2), partial(1,0) or no match (-1)
	 */
	public static int signatureMatch(String reference, String check) {

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
	 * This function adds a word to the set of words stored at this node
	 * @param word is the word to add to the set of words 
	 */
	public void addWord(String word) {
		words.add(word);
	}
	
	/**
	 * This function adds a child to the arraylist of children at this node
	 * @param add is the Octree to add to the arraylist of children
	 */
	public void addChild(Octree add) {
		children.add(add);
	}
	
	/**
	 * This function checks whether the tree is empty
	 * @return a boolean indicating whether the tree is empty
	 */
	public boolean isEmpty() {
		return empty;
	}
	
	/**
	 * This function returns the signature of this node
	 * @return a String storing the signature of this node
	 */
	public String getSignature() {
		return signature;
	}
	
	/**
	 * This function returns the set of words stored at this node
	 * @return a set of words storing the words at this node
	 */
	public Set<String> getWords() {
		return words;
	}

	/**
	 * This function returns the arraylist of children for this node
	 * @return an arraylist of children for this node
	 */
	public ArrayList<Octree> getChildren() {
		return children;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * Overriden toString() method for easier printing/ testing
	 */
	@Override
	public String toString() {
		return "\n" + "Octree [empty=" + empty + ", signature=" + signature + ", words=" + words + ", children=" + children
				+ "]";
	}

}
