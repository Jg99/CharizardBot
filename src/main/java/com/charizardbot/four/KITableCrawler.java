package com.charizardbot.four;
import org.jsoup.Jsoup;
import java.io.IOException;
import java.util.Scanner;
public class KITableCrawler {
	private String contents = "";
	private String table = "";
	/**
	 * Crawls Kingsisle's website and returns the data in a list.
	 * @throws IOException
	 */
	public void crawlWebsite() throws IOException
	{
		contents = "";
		table = "";
		String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) HeadlessChrome/74.0.3729.157 Safari/537.36";
        @SuppressWarnings("deprecation")
		org.jsoup.nodes.Document doc = Jsoup.connect("http://www.wizard101.com/pvp/schedule")
        		.userAgent(userAgent)
        		.timeout(20000)
        		.validateTLSCertificates(false)
        		.get();
        org.jsoup.select.Elements rows = doc.select("tr");
        for(org.jsoup.nodes.Element row :rows)
        {
            org.jsoup.select.Elements columns = row.select("td");
            for (org.jsoup.nodes.Element column:columns)
            {
				contents += column.text() + " ";
            }
            contents += "\n";
        }
        Scanner lineScan = new Scanner(contents);
        int count = 0;
        while (lineScan.hasNextLine())
        {
        	String token = lineScan.nextLine();
        	if (token.contains("Wizard101") == false && count < 11)
        	{
        		table += token + "\n";
        		count++;
        	}
        }
   //     System.out.println(table);
        lineScan.close();
        doc.clearAttributes();
	}
	public String toString()
	{
		return contents;
	}
	public String returnContents()
	{
		return table;	
	}
}