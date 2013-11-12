import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import org.apache.lucene.codecs.TermVectorsReader;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.util.BytesRef;


public class TermDoc {

	/**
	 * @param args
	 * @throws IOException 
	 */
	private final static Set<String> terms = new HashSet<>();

	public static Map<String, Integer> getTermFrequencies(IndexReader reader, int docId)
			throws IOException {
		Terms vector = reader.getTermVector(docId, "CountryText");
		TermsEnum termsEnum = null;
		termsEnum = vector.iterator(termsEnum);
		Map<String, Integer> frequencies = new HashMap<>();
		BytesRef text = null;
		while ((text = termsEnum.next()) != null) {
			String term = text.utf8ToString();
			int freq = (int) termsEnum.totalTermFreq();
			frequencies.put(term, freq);
			terms.add(term);
		}
		return frequencies;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String indexDir = "C:/Users/Riles/Text3/index"; // where index is located.
		Directory dir = MMapDirectory.open(new File(indexDir));
		IndexReader reader = DirectoryReader.open(dir);
		System.out.println(reader.getSumTotalTermFreq("CountryText"));
		System.out.println(reader.maxDoc());
		HashMap<String,ArrayList<Integer>> docTermFreq = new HashMap<String,ArrayList<Integer>>();
		for(int i=0;i<reader.maxDoc();i++){
			if(reader.getTermVector(i,"CountryText")==null)
				continue;

			Map<String,Integer> map = getTermFrequencies(reader,i);
			Iterator<Entry<String, Integer>> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, Integer> entry = iter.next();
				ArrayList<Integer> terms = docTermFreq.get(entry.getKey());
				if(terms == null) {
					ArrayList<Integer> newDocsRow = new ArrayList<Integer>(Collections.nCopies(251, 0));
					newDocsRow.set(i,entry.getValue());
					docTermFreq.put(entry.getKey(), newDocsRow);
				}
				else {
					terms.set(i,entry.getValue());
					docTermFreq.put(entry.getKey(), terms);
				}
			}
		}

		FileWriter fw = new FileWriter("termdoc.txt");
		Iterator<Entry<String, ArrayList<Integer>>> iter2 = docTermFreq.entrySet().iterator();
		while (iter2.hasNext()) {
			            Entry<String, ArrayList<Integer>> entry = iter2.next();
			            fw.write(entry.getKey());
			            for(int count:entry.getValue()){
			            	fw.write("\t"+count);
			            }
			            fw.write("\n");
		}
		fw.close();
	}
}
