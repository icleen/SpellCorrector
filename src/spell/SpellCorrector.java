package spell;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import spell.Trie.wordNode;

public class SpellCorrector implements ISpellCorrector {

	private final int ALPHABET = 26;
	private final int LETTER_MIN = 97;
	
	private Trie dictionary;
	
	private String suggestion;
	private int highestFrequency;
	
	private Set<String> wrongWords;
	
	public SpellCorrector() {
		dictionary = new Trie();
		highestFrequency = 0;
		wrongWords = new TreeSet<String>();
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
		if(inputWord.equals("")) {
			throw new NoSimilarWordFoundException();
		}
		if( dictionary.find( inputWord ) != null ) {
			return inputWord;
		}
		int degree = 1;
		String answer = findDistance( inputWord.toLowerCase(), degree );
		if( answer != null ) {
			return answer;
		}
		degree = 2;
		assert( wrongWords.size() != 0 );
		Iterator<String> it = wrongWords.iterator();
		String check = "";
		while( it.hasNext() ) {
			check = it.next();
//			System.out.println( check );
			answer = findDistance( check, degree );
		}
		if( answer != null ) {
			return answer;
		}
		throw new NoSimilarWordFoundException();
	}
	
	private String findDistance( String word, int degree ) {
		deletion( word, degree );
		transposition( word, degree );
		alteration( word, degree );
		insertion( word, degree );
		
		return suggestion;
	}
	
	private void deletion( String word, int degree ) {
		if( word.length() == 1 ) {
			return;
			
		}
		String temp = "";
		Trie.wordNode tempNode = null;
		for( int i = 0; i < word.length(); i++ ) {
			temp = word.substring( 0, i ) + word.substring( i + 1 );
//			System.out.println( temp );
			tempNode = (wordNode) dictionary.find( temp );
			if( tempNode != null && tempNode.getValue() > highestFrequency ) {
				highestFrequency = tempNode.getValue();
				suggestion = temp;
//				System.out.println( "deletion, frequency, degree: " + suggestion + ", " + highestFrequency + ", " + degree );
			}else if( tempNode == null && degree == 1 ) {
				wrongWords.add( temp );
			}
		}
	}
	
	private void transposition( String word, int degree ) {
		StringBuilder tempString = null;
		char[] save = word.toCharArray();
		char[] temp = null;
		char oldChar = 0, newChar = 0;
		Trie.wordNode tempNode = null;
		for( int i = 0; i < save.length - 1; i++ ) {
			tempString = new StringBuilder();
			temp = save.clone();
			oldChar = save[i];
			newChar = save[i + 1];
			temp[i] = newChar;
			temp[i + 1] = oldChar;
			tempString.append( temp );
			
			tempNode = (wordNode) dictionary.find( tempString.toString() );
			if( tempNode != null && tempNode.getValue() >= highestFrequency ) {
				if( tempNode.getValue() == highestFrequency ) {
					int test = suggestion.compareTo( tempString.toString() );
					if( test > 0 ) {
						highestFrequency = tempNode.getValue();
						suggestion = tempString.toString();
					}
				}else {
					highestFrequency = tempNode.getValue();
					suggestion = tempString.toString();
				}
//				System.out.println( "transposition, frequency, degree: " + suggestion + ", " + highestFrequency + ", " + degree );
			}else if( tempNode == null && degree == 1 ) {
				wrongWords.add( tempString.toString() );
			}
		}
	}
	
	private void alteration( String word, int degree ) {
		StringBuilder save = null;
		Trie.wordNode tempNode = null;
		char c = 0;
		for( int i = 0; i < word.length(); i++ ) {
			save = new StringBuilder( word );
			for( int j = 0; j < ALPHABET; j++ ) {
				c = (char) (LETTER_MIN + j);
				save.setCharAt( i, c );
//				System.out.println( save.toString() );
				tempNode = (wordNode) dictionary.find( save.toString() );
				if( tempNode != null && tempNode.getValue() >= highestFrequency ) {
					if( tempNode.getValue() == highestFrequency ) {
						int test = suggestion.compareTo( save.toString() );
						if( test > 0 ) {
							highestFrequency = tempNode.getValue();
							suggestion = save.toString();
						}
					}else {
						highestFrequency = tempNode.getValue();
						suggestion = save.toString();
					}
//					System.out.println( "alteration, frequency, degree: " + suggestion + ", " + highestFrequency + ", " + degree );
				}else if( tempNode == null && degree == 1 ) {
					wrongWords.add( save.toString() );
				}
			}
		}
		
	}
	
	private void insertion( String word, int degree ) {
		StringBuilder save = null;
		Trie.wordNode tempNode = null;
		char c = 0;
		for( int i = 0; i <= word.length(); i++ ) {
			for( int j = 0; j < ALPHABET; j++ ) {
				save = new StringBuilder( word );
				c = (char) (LETTER_MIN + j);
				save.insert( i, c );
//				System.out.println( save.toString() );
				tempNode = (wordNode) dictionary.find( save.toString() );
				if( tempNode != null && tempNode.getValue() >= highestFrequency ) {
					if( tempNode.getValue() == highestFrequency ) {
						int test = suggestion.compareTo( save.toString() );
						if( test > 0 ) {
							highestFrequency = tempNode.getValue();
							suggestion = save.toString();
						}
					}else {
						highestFrequency = tempNode.getValue();
						suggestion = save.toString();
					}
//					System.out.println( "insertion, frequency, degree: " + suggestion + ", " + highestFrequency + ", " + degree );
				}else if( tempNode == null && degree == 1 ) {
					wrongWords.add( save.toString() );
				}
			}
		}
		
	}
	
	@Override
	public String toString() {
		return dictionary.toString();
	}
	
	@Override
	public boolean equals( Object o ) {
		if( o == null || !SpellCorrector.class.isAssignableFrom( o.getClass() ) ) {
//			System.out.println( "They aren't the same SpellCorrector" );
			return false;
		}
		SpellCorrector check = (SpellCorrector) o;
		return dictionary.equals( check.getDictionary() );
	}
	
	public Trie getDictionary() {
		return dictionary;
	}

}
