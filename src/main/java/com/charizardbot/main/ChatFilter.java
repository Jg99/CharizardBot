package com.charizardbot.main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
public class ChatFilter {
	private String content = "";
	private String filter = "";
	private String filterFile = "chatfilter.txt";
	private boolean isFiltered = false;
	/**
	 * ChatizardBot's chat filter word checker
	 * @param content
	 * @param filter
	 */
	public ChatFilter (String content, String filter)
	{
		this.content = content;
		this.filter = filter;
	}
	public Boolean isFilteredWord() {

		return isFiltered;
	}
	public void checkFilter()
	{
		/* -- Chat filter check */
		isFiltered = false;

		Scanner filterScan = new Scanner(filter);
		while (filterScan.hasNextLine())
		{
			String token = filterScan.next();
			if (content.contains(token))
				isFiltered = true;
		}
		filterScan.close();
	}
	public void addWord(String word)
	{
	      BufferedWriter bw = null;
	      try {
	         // APPEND MODE SET HERE
	         bw = new BufferedWriter(new FileWriter(filterFile, true));
	         bw.write(word);
	         bw.newLine();
	         bw.flush();
	      } catch (IOException ioe) {
		 ioe.printStackTrace();
	      } finally {                       // always close the file
		 if (bw != null) try {
		    bw.close();
		 } catch (IOException ioe2) {
			 System.out.println("Error, could not write to file chatfilter.txt");
		 }
	      } // end try/catch/finally
	   }
	public void removeWord(String word) throws IOException
	{
	    File inputFile = new File(filterFile);
	    File tempFile = new File("tempFilter.txt");
	    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
	    String lineToRemove = word;
	    String currentLine;
	    while((currentLine = reader.readLine()) != null) {
	        // trim newline when comparing with lineToRemove
	        String trimmedLine = currentLine.trim();
	        if(trimmedLine.equals(lineToRemove)) continue;
	    	writer.write(currentLine + System.getProperty("line.separator"));
	    }
	    writer.close(); 
	    reader.close();
	}
}