import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class URLConnectionReader {
    public static void main(String[] args) throws Exception {
    	Document doc = Jsoup.connect("http://en.wikipedia.org/wiki/List_of_countries_and_capitals_with_currency_and_language").get();
//    	FileWriter fw = new FileWriter("wikiparsed.html");
    	FileWriter fw2 = new FileWriter("wikitest.html");
    	Elements links = doc.select(".wikitable").select("b>a");
    	//.out.println(""+links);
    	Elements trs = doc.select("table.wikitable tr");
    	
    	for(Element a:links){
    		String link = a.attr("href");
    		String name = a.text();
    		Elements search = doc.select(name);
    		System.out.println(search);
    		//System.out.println(name+": http://en.wikipedia.org"+link);
    	}
		//Document d2 = Jsoup.parse(in);
    	//</a></b></td><td> patterns for cities.
    
		
		
//		Elements titles = d2.select("a");
//		for (Element link:titles){
//			Integer index = d2.elementSiblingIndex();
//			System.out.println(d2+" , "+index);
//		}
		
/*		IndexWriter writer = new IndexWriter(FSDirectory.open("/index"), 
				new StandardAnalyzer(Version.LUCENE_CURRENT), 
				true, 
				IndexWriter.MaxFieldLength.LIMITED);*/
		
//		ArrayList<String> linklist = new ArrayList<String>();
//		for (Element link : titles){
//			//System.out.println(link.get);
//			String links = link.attr("href");
//			linklist.add("http://en.wikipedia.org"+links);
//		}
//		String links = titles.attr("href");
//		Document cities = Jsoup.parse(in);
//		fw2.write(""+linklist);
//		fw2.close();
//		fw.write(""+doc.body().html());
//		fw.close();
//		int i = 0;
/*		for (String link:linklist){
			Document docthis = Jsoup.connect(link).get();
			FileWriter fw1 = new FileWriter("Country"+i+".txt");
			String cleaned = docthis.body().text();
			System.out.println(i);
			fw1.write(""+cleaned);
			fw1.close();
			i++;
		}*/
    }
}
