
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
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
        try {
            int size = store.getSize();
            for (int i=0; i< size; i++) {
                Document doc = new Document();
                doc.add(new StringField("Continent", store.getContinent(i), Field.Store.YES));
                doc.add(new StringField("CountryName", store.getCountryName(i), Field.Store.YES));
                doc.add(new StringField("CapitalName", store.getCapitalName(i), Field.Store.YES));
                doc.add(new TextField("CountryText", store.getCountrytxt(i), Field.Store.YES));
                doc.add(new TextField("CapitalText", store.getCapitalTxt(i), Field.Store.YES));
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