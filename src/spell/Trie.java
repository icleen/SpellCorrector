package spell;

import java.util.ArrayList;

public class Trie implements ITrie {
	
	private final int LETTER_MIN = 97;
	private final int ALPHABET = 26;
	
	private int nodeCount;
	private int wordCount;
	
	wordNode root;
	
	StringBuilder output;
	
	ArrayList<String> words;
	
	public Trie() {
		root = new wordNode();
		nodeCount = 0;
		wordCount = 0;
	}

	@Override
	public void add(String word) {
		recursiveAdd( word.toLowerCase(), root );
		wordCount++;
	}
	
	private void recursiveAdd( String word, wordNode node ) {
//		System.out.println( word );
		char c = word.charAt( 0 );
		int index = c - LETTER_MIN;
		if( node.nodes[index] == null ) {
			node.nodes[index] = new wordNode( c );
			nodeCount++;
		}
		if( word.length() > 1 ) {
			recursiveAdd( word.substring( 1 ), node.nodes[index] );
		}else {
			node.nodes[index].addValue();
		}
	}

	@Override
	public INode find(String word) {
		return recursiveFind( word.toLowerCase(), root );
	}
	
	private wordNode recursiveFind( String word, wordNode node ) {
//		System.out.println( word );
		char c = word.charAt( 0 );
		int index = c - LETTER_MIN;
		if( node.nodes[index] == null ) {
			return null;
		}
		if( word.length() > 1 ) {
			return recursiveFind( word.substring( 1 ), node.nodes[index] );
		}
		if( node.nodes[index].frequency > 0 ) {
			return node.nodes[index];
		}
		return null;
	}

	@Override
	public int getWordCount() {
		return wordCount;
	}

	@Override
	public int getNodeCount() {
		return nodeCount;
	}
	
	//***********************************************************
	// my toString function
	//***********************************************************
	
	@Override
	public String toString() {
		output = new StringBuilder();
		StringBuilder word = new StringBuilder();
		for( int i = 0; i < ALPHABET; i++ ) {
			recursiveToString( root.nodes[i], word );
		}
		return output.toString();
	}
	
	private void recursiveToString( wordNode node, StringBuilder word ) {
		if ( node == null ) {
			return;
		}
		word.append( node.getLetter() );
		if ( node.getValue() > 0 ) {
			output.append( word + "\n" );
		}
		for( int i = 0; i < ALPHABET; i++ ) {
			recursiveToString( node.nodes[i], word );
		}
		word.deleteCharAt( word.length() - 1 );		// deletes the last char added, which would be the one added in this call of the recursive function
	}
	
	@Override
	public int hashCode() {
		return 0;
	}
	
	@Override
	public boolean equals(Object o) {
		String current = this.toString();
		String test = o.toString();
		if( current.equals( test ) ) {
			return true;
		}
		return false;
	}
	
//*****************************************************
//	my implementation of the node class
//*****************************************************
	public class wordNode implements ITrie.INode {

		public int frequency;
		private char letter;
		
		public wordNode[] nodes;
		
		public wordNode() {
			letter = 0;
			nodes = new wordNode[26];
			frequency = 0;
		}
		
		public wordNode( char letter ) {
			this.letter = letter;
			nodes = new wordNode[26];
			frequency = 0;
		}
		
		public char getLetter() {
			return letter;
		}

		public void setLetter(char letter) {
			this.letter = letter;
		}

		@Override
		public int getValue() {
			return frequency;
		}
		
		public void addValue() {
			frequency++;
		}
		
	}

}
