package spell;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
//
//		Test to see if the Trie was programmed correctly
//		
//		String addWord = args[0];
//		String searchWord = args[1];
//		
//		Trie words = new Trie();
//		words.add( addWord );
//		Trie.INode node = words.find( searchWord );
//		if( node == null ) {
//			System.out.println( "fail" );
//		}else {
//			System.out.println( "success" );
//		}
//		
		
		String dictionaryFileName = args[0];
		String dictionaryFileName1 = "spell/wordsTest.txt";
		String dictionaryFileName2 = "spell/wordsTest2.txt";
		String inputWord = args[1];
		
//		String temp = "";
//		for( int i = 0; i < inputWord.length(); i++ ) {
//			temp = inputWord.substring( 0, i ) + inputWord.substring( i + 1 );
//			System.out.println( temp );
//		}
		/**
		 * Create an instance of your corrector here
		 */
		ISpellCorrector corrector = new SpellCorrector();
		ISpellCorrector check = new SpellCorrector();
		ISpellCorrector check1 = new SpellCorrector();
		ISpellCorrector check2 = new SpellCorrector();
		corrector.useDictionary( dictionaryFileName );
		check.useDictionary( dictionaryFileName );
		check1.useDictionary( dictionaryFileName1 );
		check2.useDictionary( dictionaryFileName2 );
		PrintWriter writer = new PrintWriter( 
				new BufferedWriter( 
						new FileWriter( "spell/output.txt" ) ) );
		writer.print( corrector.toString() );
		writer.close();
		String suggestion = corrector.suggestSimilarWord( inputWord );
		if( inputWord.equals( suggestion ) ) {
			System.out.println( inputWord.toLowerCase() );
		}else {
			System.out.println( "Suggestion is: " + suggestion );
		}
		System.out.print( "Check: " );
		System.out.println( corrector.equals( check ) );
		System.out.print( "Check1: " );
		System.out.println( corrector.equals( check1 ) );
		System.out.print( "Check2: " );
		System.out.println( corrector.equals( check2 ) );
		System.out.print( "Check other: " );
		System.out.println( corrector.equals( dictionaryFileName ) );
		System.out.print( "Check null: " );
		System.out.println( corrector.equals( null ) );
	}

}
