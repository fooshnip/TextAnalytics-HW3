import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class tester {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Document doc = Jsoup.connect("http://en.wikipedia.org/wiki/Kigali").timeout(30000000).get();
	    FileWriter fw = new FileWriter("wikitest.html");
	    fw.write(doc.select("p").text());
	    fw.close();
	}

}
