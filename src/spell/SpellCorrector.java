package spell;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import spell.Trie.wordNode;

public class SpellCorrector implements ISpellCorrector {

	private final int ALPHABET = 26;
	private final int LETTER_MIN = 97;
	
	private Trie dictionary;
	
	private String suggestion;
	private int highestFrequency;
	
	public SpellCorrector() {
		dictionary = new Trie();
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
			return inputWord;
		}
		int degree = 1;
		String answer = findDistance( inputWord.toLowerCase(), degree );
		if( answer != null ) {
			return answer;
		}
		degree = 2;
		answer = findDistance( inputWord.toLowerCase(), degree );
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
	
	private void deletion( String word,  int degree ) {
		String temp = "";
		Trie.wordNode tempNode = null;
		for( int i = 0; i < word.length() + 1 - degree; i++ ) {
			temp = word.substring( 0, i ) + word.substring( i + degree );
//			System.out.println( temp );
			tempNode = (wordNode) dictionary.find( temp );
			if( tempNode != null && tempNode.getValue() > highestFrequency ) {
				highestFrequency = tempNode.getValue();
				suggestion = temp;
//				System.out.println( "deletion, frequency, degree: " + suggestion + ", " + highestFrequency + ", " + degree );
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
			}
		}
	}
	
	private void alteration( String word, int degree) {
		StringBuilder save = null;
		Trie.wordNode tempNode = null;
		char c = 0, d = 0;
		if( degree == 1 ) {
			for( int i = 0; i < word.length(); i++ ) {
				save = new StringBuilder( word );
				for( int j = 0; j < ALPHABET; j++ ) {
					c = (char) (LETTER_MIN + j);
					save.setCharAt( i, c );
//					System.out.println( save.toString() );
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
//						System.out.println( "alteration, frequency, degree: " + suggestion + ", " + highestFrequency + ", " + degree );
					}
				}
			}
		}else if ( degree == 2 ) {
			for( int i = 0; i < word.length() - 1; i++ ) {
				save = new StringBuilder( word );
				for( int j = 0; j < ALPHABET; j++ ) {
					for( int k = 0; k < ALPHABET; k++ ) {
						c = (char) (LETTER_MIN + j);
						d = (char) (LETTER_MIN + k);
						save.setCharAt( i, c );
						save.setCharAt( i + 1, d );
//						System.out.println( save.toString() );
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
//							System.out.println( "alteration, frequency, degree: " + suggestion + ", " + highestFrequency + ", " + degree );
						}
					}
				}
			}
		}
		
	}
	
	private void insertion( String word, int degree ) {
		StringBuilder save = null;
		Trie.wordNode tempNode = null;
		char c = 0, d = 0;
		if( degree == 1 ) {
			for( int i = 0; i <= word.length(); i++ ) {
				for( int j = 0; j < ALPHABET; j++ ) {
					save = new StringBuilder( word );
					c = (char) (LETTER_MIN + j);
					save.insert( i, c );
//					System.out.println( save.toString() );
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
//						System.out.println( "insertion, frequency, degree: " + suggestion + ", " + highestFrequency + ", " + degree );
					}
				}
			}
		}else if( degree == 2 ) {
			for( int i = 0; i <= word.length(); i++ ) {
				for( int j = 0; j < ALPHABET; j++ ) {
					for( int k = 0; k < ALPHABET; k++ ) {
						save = new StringBuilder( word );
						c = (char) (LETTER_MIN + j);
						d = (char) (LETTER_MIN + k);
						save.insert( i, c );
						save.insert( i, d );
//						System.out.println( save.toString() );
						tempNode = (wordNode) dictionary.find( save.toString() );
						if( tempNode != null && tempNode.getValue() > highestFrequency ) {
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
//							System.out.println( "insertion, frequency, degree: " + suggestion + ", " + highestFrequency + ", " + degree );
						}
					}
				}
			}
		}
	}
	
	@Override
	public String toString() {
		return dictionary.toString();
	}

}
