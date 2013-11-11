import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.lucene.index.Fields;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;


public class TermDoc {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String indexDir = "C:/Users/Riles/Text3/index"; // where index is located.
        Directory dir = FSDirectory.open(new File(indexDir));
        IndexReader reader = IndexReader.open(dir);
        Fields fields = MultiFields.getFields(reader);
        Terms terms = fields.terms("CountryText");
        TermsEnum iterator = terms.iterator(null);
        BytesRef byteRef = null;
        FileWriter fw = new FileWriter("C:/Users/Riles/Text3/alldocfreq.txt");
        while((byteRef = iterator.next()) != null) {
        	int docFreq = iterator.docFreq();
            String term = new String(byteRef.bytes, byteRef.offset, byteRef.length);
            fw.write("'"+term+"'," + " " + docFreq + ",\n");
        }
        fw.close();
	}
}
