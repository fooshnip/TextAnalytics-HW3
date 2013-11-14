
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Created with IntelliJ IDEA.
 * User: joonhyunglim
 * Date: 11/10/13
 * Time: 1:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class Problem2Driver {

    public static void main(String[] args) throws IOException, ParseException {
        /*
         * A good blog post about Lucene 4 Searching:
         * http://www.javacodegeeks.com/2013/06/searching-made-easy-with-apache-lucene-4-3.html
         */
        String indexDir = "C:/Users/Riles/Text3/index"; // where index is located.
        Directory dir = FSDirectory.open(new File(indexDir));
        IndexSearcher is = new IndexSearcher(DirectoryReader.open(dir));
        EnglishAnalyzer analyzer = new EnglishAnalyzer(Version.LUCENE_45);
        // defining the query parser to search items by title field.
        QueryParser ContinentParser = new QueryParser(Version.LUCENE_45, "Continent", analyzer);
        QueryParser CountryNameParser = new QueryParser(Version.LUCENE_45, "CountryName", analyzer);
        QueryParser CapitalNameParser = new QueryParser(Version.LUCENE_45, "CapitalName", analyzer);
        // defining the query parser to search items by content field.
        QueryParser CountryTextParser = new QueryParser(Version.LUCENE_45, "CountryText", analyzer);
        QueryParser CapitalTextParser = new QueryParser(Version.LUCENE_45, "CapitalText", analyzer);

        //Start Search
        BooleanCapitalSearch(is,CapitalTextParser);
        FuzzyCapitalSearch(is,CapitalTextParser);
        PhraseCapitalSearch(is,CapitalTextParser);
        BooleanWarSearch(is,CapitalTextParser);

    }

    public static void BooleanCapitalSearch(IndexSearcher is, QueryParser CapitalTextParser) throws IOException, ParseException {
        File f = new File("C:/Users/Riles/Text3/Search_Result/BooleanQueryResult.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));

        BooleanQuery bool = new BooleanQuery();
        bool.add(new TermQuery(new Term("CapitalText", "greek")), BooleanClause.Occur.MUST);
        bool.add(new TermQuery(new Term("CapitalText", "roman")), BooleanClause.Occur.MUST);
        bool.add(new TermQuery(new Term("CapitalText", "persian")), BooleanClause.Occur.MUST_NOT);
        System.out.println("Boolean =>\t"+bool.toString());
        Query query = CapitalTextParser.parse(bool.toString());

        int numResults = 100;
        ScoreDoc[] hits =   is.search(query,numResults).scoreDocs;
        for (int i = 0; i < hits.length; i++) {
            Document doc = is.doc(hits[i].doc);
            writer.write(doc.get("CountryName")+":\t"+doc.get("CapitalName"));
            writer.newLine();
        }
        writer.close();
    }

    public static void FuzzyCapitalSearch(IndexSearcher is, QueryParser CapitalTextParser) throws IOException, ParseException {
        /* Brief introduction of Fuzzy Query and how it works.
         * http://developer4life.blogspot.com/2013/02/solr-and-lucene-fuzzy-search-closer-look.html
         */
        File f = new File("C:/Users/Riles/Text3/Search_Result/FuzzyQueryResult.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));

        FuzzyQuery fuzz = new FuzzyQuery(new Term("CapitalText","Shakespeare"));
        System.out.println("Fuzz =>\t"+fuzz.toString());
        Query query = CapitalTextParser.parse(fuzz.toString());

        int numResults = 100;
        ScoreDoc[] hits =   is.search(query,numResults).scoreDocs;
        for (int i = 0; i < hits.length; i++) {
            Document doc = is.doc(hits[i].doc);
            writer.write(doc.get("CountryName")+":\t"+doc.get("CapitalName"));
            writer.newLine();
        }
        writer.close();
    }

    public static void PhraseCapitalSearch(IndexSearcher is, QueryParser CapitalTextParser) throws IOException, ParseException {
        /* Brief introduction of Fuzzy Query and how it works.
         * http://developer4life.blogspot.com/2013/02/solr-and-lucene-fuzzy-search-closer-look.html
         */
        File f = new File("C:/Users/Riles/Text3/Search_Result/PhraseQueryResult.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));

        String SearchPhrase = "located below sea level";
        PhraseQuery phrase = new PhraseQuery();
        phrase.setSlop(5);
        for (String s: SearchPhrase.split("\\s")) {
            phrase.add(new Term("CapitalText",s));
        }
        System.out.println("Phrase =>\t"+phrase.toString());
        Query query = CapitalTextParser.parse(phrase.toString());

        int numResults = 100;
        ScoreDoc[] hits =   is.search(query,numResults).scoreDocs;
        for (int i = 0; i < hits.length; i++) {
            Document doc = is.doc(hits[i].doc);
            writer.write(doc.get("CountryName")+":\t"+doc.get("CapitalName"));
            writer.newLine();
        }
        writer.close();
    }

    public static void BooleanWarSearch(IndexSearcher is, QueryParser CountryTextParser) throws IOException, ParseException {
        File f = new File("C:/Users/Riles/Text3/Search_Result/FIFAPhraseResult.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));

        String SearchPhrase = "fifa world cup";
        PhraseQuery phrase = new PhraseQuery();
        phrase.setSlop(5);
        for (String s: SearchPhrase.split("\\s")) {
            phrase.add(new Term("CapitalText",s));
        }
        System.out.println("Phrase =>\t"+phrase.toString());
        Query query = CountryTextParser.parse(phrase.toString());

        int numResults = 202;
        ScoreDoc[] hits =   is.search(query,numResults).scoreDocs;
        for (int i = 0; i < hits.length; i++) {
            Document doc = is.doc(hits[i].doc);
            writer.write(doc.get("CountryName")+":\t"+doc.get("CapitalName"));
            writer.newLine();
        }
        writer.close();
    }


}
