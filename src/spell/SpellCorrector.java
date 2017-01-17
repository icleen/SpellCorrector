package spell;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

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
		// TODO Auto-generated method stub
		return null;
	}

}
