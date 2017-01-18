package spell;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import spell.Trie.wordNode;

public class SpellCorrector implements ISpellCorrector {

	private Trie dictionary;
	
	private String output;
	private int highestFrequency;
	
	public SpellCorrector() {
		dictionary = new Trie();
		output = "";
		highestFrequency = 0;
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
		int degree = 1;
		String answer = findDistance( inputWord.toLowerCase(), degree );
		if( answer != null ) {
			return answer;
		}
//		else {
//			throw null;
//		}
		return "Word not found";
	}
	
	private String findDistance( String word, int degree ) {
		deletion( word, degree );
		transposition( word, degree );
		alteration();
		insertion();
		
		return output;
	}
	
	private void deletion( String word,  int degree ) {
		String temp = "";
		Trie.wordNode tempNode = null;
		for( int i = 0; i < word.length() + 1 - degree; i++ ) {
			temp = word.substring( 0, i ) + word.substring( i + degree );
//			System.out.println( temp );
			tempNode = (wordNode) dictionary.find( temp );
			if( tempNode != null && tempNode.getValue() > highestFrequency ) {
				highestFrequency = tempNode.getValue();
				output = temp;
			}
		}
	}
	
	private void transposition( String word,  int degree ) {
		StringBuilder tempString = null;
		char[] save = word.toCharArray();
		char[] temp = null;
		char oldChar = 0, newChar = 0;
		Trie.wordNode tempNode = null;
		for( int i = 0; i < save.length - degree; i++ ) {
			tempString = new StringBuilder();
			temp = save.clone();
			oldChar = save[i];
			newChar = save[i + degree];
			temp[i] = newChar;
			temp[i + degree] = oldChar;
			tempString.append( temp );
			
			tempNode = (wordNode) dictionary.find( tempString.toString() );
			if( tempNode != null && tempNode.getValue() > highestFrequency ) {
				highestFrequency = tempNode.getValue();
				output = tempString.toString();
			}
		}
	}
	
	private String alteration() {
		
		
		return null;
	}
	
	private String insertion() {
		
		
		return null;
	}

}
