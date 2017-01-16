package spell;

public class Trie implements ITrie {
	
	private final int LETTER_MIN = 97;
	
	private int count;
	private int nodeCount;
	
	wordNode root;
	
	public Trie() {
		root = new wordNode();
		count = 0;
		nodeCount = 0;
	}

	@Override
	public void add(String word) {
		
		recursiveAdd( word.toLowerCase(), root );
		count++;
//		char c;
//		int index;
//		for( int i = 0; i < word.length(); i++ ) {
//			c = word.charAt( i );
//			index = c - LETTER_MIN;
//			root.nodes[index] = new wordNode( c );
//		}
	}
	
	private void recursiveAdd( String word, wordNode node ) {
		char c = word.charAt( 0 );
		int index = c - LETTER_MIN;
		if( node.nodes[index] == null ) {
			node.nodes[index] = new wordNode( c );
			nodeCount++;
		}
		if( word.length() > 0 ) {
			recursiveAdd( word.substring( 1 ), node.nodes[index] );
		}
	}

	@Override
	public INode find(String word) {
		return recursiveFind( word.toLowerCase(), root );
	}
	
	private wordNode recursiveFind( String word, wordNode node ) {
		
		char c = word.charAt( 0 );
		int index = c - LETTER_MIN;
		if( node.nodes[index] == null ) {
			return null;
		}
		if( word.length() > 0 ) {
			return recursiveFind( word.substring( 1 ), node.nodes[index] );
		}
		
		return node.nodes[index];
	}

	@Override
	public int getWordCount() {
		return count;
	}

	@Override
	public int getNodeCount() {
		return nodeCount;
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
