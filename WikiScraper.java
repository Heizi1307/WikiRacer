
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * TODO: You will have to implement memoization somewhere in this class.
 */
public class WikiScraper {

	/*
	 * TODO: Comment this function
	 * 
	 * Find corresponding links
	 */
	public static Set<String> findWikiLinks(String link) {
		if (cache.containsKey(link)) {
			return cache.get(link);
		} else {
			String html = fetchHTML(link);
			Set<String> links = scrapeHTML(html);
			cache.put(link, links);
			return links;
		}
	}

	/*
	 * TODO: Comment this function. What does it do at a high level. I don't expect
	 * you to read/understand the StringBuffer and while loop. But from the spec and
	 * your understanding of this assignment, what is the purpose of this function.
	 * 
	 * Get the URL and download all the html from this URL.c
	 */
	private static String fetchHTML(String link) {
		StringBuffer buffer = null;
		try {
			URL url = new URL(getURL(link));
			InputStream is = url.openStream();
			int ptr = 0;
			buffer = new StringBuffer();
			while ((ptr = is.read()) != -1) {
				buffer.append((char) ptr);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return buffer.toString();
	}

	/*
	 * TODO: Comment this function. What does it do.
	 * 
	 * Get the complete URL of the link.
	 */
	private static String getURL(String link) {
		return "https://en.wikipedia.org/wiki/" + link;
	}

	/*
	 * Takes in a string of the HTML source for a webPage. Returns a Set<String>
	 * containing all of the valid wiki link titles found in the HTML source.
	 * 
	 * In order for a link to be a valid wikiLink for our case, it must match the
	 * pattern: <a href="/wiki/Marine_mammal"> and NOT contain the character '#' nor
	 * ':'. From the above match, you would then extract the link name which in this
	 * case is: Marine_mammal Refer to the testcases for more examples.
	 * 
	 * The fact that the input to this parameter is HTML is largely irrelevant to
	 * this function. It is just a string processing function. You take in a string,
	 * and need to search for matches to a specific pattern in that string. We went
	 * through a brief description of HTML in class if you are curious.
	 * 
	 * Your first job is to pass all of the tests. This means you either have a
	 * functionally correct algorithm, or are close to one. However, performance and
	 * efficiency will be very important for this PA. After finding a functionally
	 * correct algorithm, spend time designing other approaches to see if you can
	 * determine a more efficient approach. It will pay off when writing the PA!
	 * i.e. do not do anything inefficient, for instance, you should only go through
	 * the string once.
	 */
	private static Set<String> scrapeHTML(String html) {
		Set<String> set1 = new HashSet<String>();
		String[] arrOfStr = html.split("<a ");
		for (String link : arrOfStr)
			if (link.contains("href=\"/wiki/")) {
				link = link.replace("href=\"/wiki/", "");
				String[] arrOfStr1 = link.split("\"");
				if ((!arrOfStr1[0].contains(":")) && (!arrOfStr1[0].contains("#") && (!arrOfStr1[0].contains("=")))) {
					set1.add(arrOfStr1[0]);
				}
			}
		return set1;
	}

	// Purpose: memoization
	public static Map<String, Set<String>> cache = new HashMap<>();

}
