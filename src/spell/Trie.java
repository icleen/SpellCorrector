package spell;

public class Trie implements ITrie {
	
	private final int LETTER_MIN = 97;
	
	wordNode root;
	
	public Trie() {
		root = new wordNode();
	}

	@Override
	public void add(String word) {
		
		recursiveAdd( word, root );
		
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
		node.nodes[index] = new wordNode( c );
		if( word.length() > 0 ) {
			recursiveAdd( word.substring( 1 ), node.nodes[index] );
		}
	}

	@Override
	public INode find(String word) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getWordCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNodeCount() {
		// TODO Auto-generated method stub
		return 0;
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
