package spell;

import java.util.ArrayList;

public class Trie implements ITrie {
	
	private final int LETTER_MIN = 97;
	
	private int nodeCount;
	
	wordNode root;
	
	ArrayList<String> words;
	
	public Trie() {
		root = new wordNode();
		nodeCount = 0;
		words = new ArrayList<String>();
	}

	@Override
	public void add(String word) {
		recursiveAdd( word.toLowerCase(), root );
		words.add( word );
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
		return words.size();
	}

	@Override
	public int getNodeCount() {
		return nodeCount;
	}
	
	@Override
	public String toString() {
		return words.toString();
//		StringBuilder output = new StringBuilder();
//		for( int i = 0; i < words.size(); i++ ) {
//			output.append( words.get(i) );
//		}
//		return output.toString();
	}
	
	public class wordNode implements ITrie.INode {

		public int frequency;
		private char letter;
		
		public wordNode[] nodes;
		
		public wordNode() {
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
