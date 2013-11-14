import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.safety.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;


/**
 * Created with IntelliJ IDEA.
 * User: joonhyunglim
 * Date: 11/9/13
 * Time: 9:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class FetchWiki {

	String[] Continent = {"Africa","Asia","Europe","North America","South America","Oceania","Antarctica"};
	WikiData store;
	public FetchWiki() {
		try {
			store = new WikiData();
			Document doc = Jsoup.connect("http://en.wikipedia.org/wiki/List_of_countries_and_capitals_with_currency_and_language").timeout(30000000).get();
			Elements Tables = doc.select("table[class=wikitable sortable]"); // table with wikitable sortable
			int row = 0;
			int ContiIter=0;
			for (Element table: Tables) {
				Iterator<Element> trow = table.select("tr").iterator();
				trow.next(); //first row is just a header.
				while (trow.hasNext()) {
					Iterator<Element> itd = trow.next().select("td").iterator();
					itd.next(); // pass first column.
					Element country = itd.next();
					String countrylink = country.select("b>a").attr("abs:href");
					String countryname = country.select("b>a").attr("title");
					if(countrylink != ""){
						Document countrydoc = Jsoup.connect(countrylink).timeout(30000000).get();
						//countrydoc = new Cleaner(Whitelist.simpleText()).clean(countrydoc);
						String countrybody= countrydoc.select("p").text();
						Element capital = itd.next();
						Iterator<Element> CapitalElements = capital.select("a").iterator();
						while(CapitalElements.hasNext()) {
							Element e = CapitalElements.next();
							String capitallink = e.attr("abs:href");
							String capitalname = e.attr("title");
							Document capitaldoc = Jsoup.connect(capitallink).timeout(30000000).get();
							
							String capitalbody=capitaldoc.select("p").text();
							//capitalbody = new Cleaner(Whitelist.simpleText()).clean(capitalbody);
							// now pass the data
							System.out.println(Continent[ContiIter]+"\t"+countryname+"\n"+countrybody+"\n"+capitalname+"\n"+capitalbody+"\n\n");
							store.AddContinent(row,Continent[ContiIter]);
							store.AddCountry(row,countryname,countrybody);
							store.AddCapital(row,capitalname,capitalbody);
						}
						row++;

					}
				}
				ContiIter++;
			}

			/*
            URL Wiki = new URL("http://en.wikipedia.org/wiki/List_of_countries_and_capitals_with_currency_and_language");
            URLConnection yc = Wiki.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                body+=inputLine;
            in.close();
			 */

			// check number of countries and capitals we are able to fetch.
			store.stats();
		} catch(MalformedURLException m) {
			m.printStackTrace();
		} catch(IOException io) {
			io.printStackTrace();
		}
	}

	public void index() {
		try {
			Indexer LuceneIndexer = new Indexer(store);
			int Docs = LuceneIndexer.index();
			LuceneIndexer.close();
			System.out.println(Docs+" documents are indexed.");
		} catch (IOException io) {
			io.printStackTrace();
		}

	}


}
