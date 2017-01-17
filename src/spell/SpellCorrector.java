package spell;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import spell.Trie.wordNode;

public class SpellCorrector implements ISpellCorrector {

	private Trie dictionary;
	
	public SpellCorrector() {
		dictionary = new Trie();
	}
	
	@Override
	public void useDictionary(String dictionaryFileName) throws IOException {
		Scanner scanner = new Scanner(
										new BufferedReader(
												new FileReader( dictionaryFileName )));
		
		while( scanner.hasNext() ) {
			dictionary.add( scanner.next() );
		}
		scanner.close();
	}

	@Override
	public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException {
		if( dictionary.find( inputWord ) != null ) {
			return "That word is in the dictionary!";
		}
		String answer = findDistance( inputWord );
		if( answer != null ) {
			return answer;
		}
//		else {
//			throw null;
//		}
		return "Word not found";
	}
	
	private String findDistance( String word ) {
		int delHigh = 0, tranHigh = 0, altHigh = 0, insertHigh = 0;
		String deleter = deletion( word, delHigh );
//		Trie.wordNode transposer = transposition();
//		Trie.wordNode alterator = alteration();
//		Trie.wordNode inserter = insertion();
//		if( deleter.getValue() > transposer.getValue() && deleter.getValue() > alterater.getValue() && deleter.getValue() > inserter.getValue() ) {
//			return deleter.toString();
//		}
		return deleter;
	}
	
	private String deletion( String word, int highestValue ) {
		String temp = "", lastString = "";
		Trie.wordNode tempNode = null;
		for( int i = 0; i < word.length(); i++ ) {
			temp = word.substring( 0, i ) + word.substring( i + 1 );
			tempNode = (wordNode) dictionary.find( temp );
			if( tempNode != null && tempNode.getValue() > highestValue ) {
				highestValue = tempNode.getValue();
				lastString = temp;
			}
		}
//		System.out.println( lastString );
		return lastString;
	}
	
	private String transposition() {
		
		
		return null;
	}
	
	private String alteration() {
		
		
		return null;
	}
	
	private String insertion() {
		
		
		return null;
	}

}
