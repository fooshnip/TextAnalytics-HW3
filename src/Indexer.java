
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: joonhyunglim
 * Date: 11/10/13
 * Time: 12:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class Indexer {

    private IndexWriter writer;
    private WikiData store;
    public Indexer(WikiData store) throws IOException {
        this.store = store;
        String indexDir = "C:/Users/Riles/Text3/index";
        Directory dir = FSDirectory.open(new File(indexDir));
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_45, new StandardAnalyzer(Version.LUCENE_45));
        writer = new IndexWriter(dir, config);
        }

    public int index() {
    	FieldType fieldType = new FieldType();
		fieldType.setIndexed(true);
		fieldType.setTokenized(true);
		fieldType.setStored(true);
		fieldType.setStoreTermVectors(true);
		fieldType.setStoreTermVectorPositions(true);
		fieldType.freeze();
        try {
            int size = store.getSize();
            for (int i=0; i< size; i++) {
                Document doc = new Document();
                doc.add(new Field("Continent", store.getContinent(i), fieldType));
                doc.add(new Field("CountryName", store.getCountryName(i), fieldType));
                doc.add(new Field("CapitalName", store.getCapitalName(i), fieldType));
                doc.add(new Field("CountryText", store.getCountrytxt(i), fieldType));
                doc.add(new Field("CapitalText", store.getCapitalTxt(i), fieldType));
                writer.addDocument(doc);
                writer.commit();
            }
        } catch(IOException io) {
            io.printStackTrace();
        }
        return writer.numDocs();
    }
    public void close() throws IOException {
        writer.close();
    }

}