package spell;

import java.io.IOException;

import spell.ISpellCorrector.NoSimilarWordFoundException;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the passoff program.
 */
public class Main {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws NoSimilarWordFoundException, IOException {

		String addWord = args[0];
		String searchWord = args[1];
		
		Trie words = new Trie();
		words.add( addWord );
		Trie.INode node = words.find( searchWord );
		if( node == null ) {
			System.out.println( "fail" );
		}else {
			System.out.println( "success" );
		}
		
//		String dictionaryFileName = args[0];
//		String inputWord = args[1];
//		
//		/**
//		 * Create an instance of your corrector here
//		 */
//		ISpellCorrector corrector = new SpellCorrector();
//		
//		corrector.useDictionary( dictionaryFileName );
//		String suggestion = corrector.suggestSimilarWord( inputWord );
//		
//		System.out.println( "Suggestion is: " + suggestion );
	}

}
