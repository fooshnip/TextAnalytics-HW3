
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: joonhyunglim
 * Date: 11/10/13
 * Time: 2:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class WikiData {
    /*
     * Relational Schema style custom data structure to store WIKIPEDIA data.
     */
    private List<Integer> id = new LinkedList<Integer>();
    private Hashtable<Integer,String> continent = new Hashtable<Integer, String>();
    private Hashtable<Integer,String> countryname = new Hashtable<Integer, String>();
    private Hashtable<Integer,String> countrytxt = new Hashtable<Integer, String>();
    private Hashtable<Integer,String> capitalname = new Hashtable<Integer, String>();
    private Hashtable<Integer,String> capitaltxt = new Hashtable<Integer, String>();


    public WikiData() {
    }
    public void AddContinent(int id,String cont) {
        if(!this.id.contains(id)) this.id.add(id);
        continent.put(id,cont);
    }
    public String getContinent(int id) {
        if (!this.id.contains(id)) {
            System.err.println("Error : the corresponding data is not available. Try another.");
        }
        return continent.get(id);
    }
    public void AddCountry(int id, String countryname, String countrytext) {
        if(!this.id.contains(id)) this.id.add(id);
        this.countryname.put(id,countryname);
        countrytxt.put(id,countrytext);
    }
    public String getCountryName(int id) {
        if (!this.id.contains(id)) {
            System.err.println("Error : the corresponding data is not available. Try another.");
        }
        return countryname.get(id);
    }
    public String getCountrytxt(int id) {
        if (!this.id.contains(id)) {
            System.err.println("Error : the corresponding data is not available. Try another.");
        }
        return countrytxt.get(id);
    }
    public void AddCapital(int id, String capname, String capitaltexts) {
        if(!this.id.contains(id)) this.id.add(id);
        this.capitalname.put(id,capname);
        capitaltxt.put(id,capitaltexts);
    }

    public String getCapitalName(int id) {
        if (!this.id.contains(id)) {
            System.err.println("Error : the corresponding data is not available. Try another.");
        }
        String capnames = capitalname.get(id);
        return capnames;
    }

    public String getCapitalTxt(int id) {
        if (!this.id.contains(id)) {
            System.err.println("Error : the corresponding data is not available. Try another.");
        }
        String captexts = capitaltxt.get(id);
        return captexts;
    }

    public int getSize() {
       return id.size();
    }

    public void remove(int i) {
        id.remove(i);
        continent.remove(i);
        countryname.remove(i);
        countrytxt.remove(i);
        capitalname.remove(i);
        capitaltxt.remove(i);
        System.out.printf("%sth data point is successfully removed.\n",i);
    }

    public void stats() {
        HashSet<String> CountrySet = new HashSet<String>();
        for (String name: countryname.values()) {
            CountrySet.add(name);
        }
        System.out.printf("--------------------\nTotal Countries: %d\nTotal Capitals : %d\n",
                CountrySet.size(),id.size());
    }

}
