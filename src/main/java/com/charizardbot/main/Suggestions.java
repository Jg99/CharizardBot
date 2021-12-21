package com.charizardbot.main;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Random;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
public class Suggestions {
private InputStream input = null;
private OutputStream output = null;
private String fileName;
private String statusFileName;
private File dbFile;
private File statusdbFile;
private String sugID;
private  Properties suggestionDB = new Properties(); //Suggestion DB is using Properties.
private  Properties statusDB = new Properties();
/**
 * Suggestions function for CharizardBot suggestions system.
 * Uses text files for each server as the database.
 * @param serverID
 */
	public Suggestions (String serverID)
	{
	this.dbFile = new File(serverID + "_suggestiondb.txt");
	this.statusdbFile = new File (serverID + "_status_suggestiondb.txt");
	this.fileName = serverID + "_suggestiondb.txt";
	this.statusFileName = serverID + "_status_suggestiondb.txt";
	if (!dbFile.exists())
	{
		System.out.println("Suggestion Database for server id: " + serverID + " does not exist. Creating it now.");
		try {
			new FileOutputStream(serverID + "_suggestiondb.txt", false).close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	if (!statusdbFile.exists())
	{
		System.out.println("Suggestion (status) Database for server id: " + serverID + " does not exist. Creating it now.");
		try {
			new FileOutputStream(serverID + "_status_suggestiondb.txt", false).close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	}
	public void loadDatabase(String serverID) {
		try {
			this.fileName = serverID + "_suggestiondb.txt";
			input = new FileInputStream(fileName);

			// load a properties file
			suggestionDB.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//suggestions (approve/deny/none) DB
		try {
			this.statusFileName = serverID + "_status_suggestiondb.txt";
			input = new FileInputStream(statusFileName);

		// load a properties file
			statusDB.load(input);
		} catch (IOException ex) {
		ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}	
		}
	}
	public String getSuggestion(String suggestionID, String serverID) {
		statusDB.clear();
		suggestionDB.clear();
		this.dbFile = new File(serverID + "_suggestiondb.txt");
		this.statusdbFile = new File (serverID + "_status_suggestiondb.txt");
		this.fileName = serverID + "_suggestiondb.txt";
		this.statusFileName = serverID + "_status_suggestiondb.txt";
		loadDatabase(serverID);
		String suggestions = "";
		suggestions += suggestionDB.getProperty(suggestionID);
		return suggestions;
	}
	public void addSuggestion(String suggestionText, String serverID) {
		statusDB.clear();
		suggestionDB.clear();
		this.dbFile = new File(serverID + "_suggestiondb.txt");
		this.statusdbFile = new File (serverID + "_status_suggestiondb.txt");
		this.fileName = serverID + "_suggestiondb.txt";
		this.statusFileName = serverID + "_status_suggestiondb.txt";
		loadDatabase(serverID);
		try {
			Random rand = new Random();
			rand.setSeed(System.currentTimeMillis());
			this.sugID = Integer.toString(rand.nextInt(199999));
			while (suggestionDB.containsKey(sugID)){
			this.sugID = Integer.toString(rand.nextInt(199999));
			}
			output = new FileOutputStream(fileName);
			suggestionDB.setProperty(sugID, suggestionText);
			suggestionDB.store(output, null);
			suggestionDB.clear();
			//ADD TO STATUS DB (approve/deny/none)
			output = new FileOutputStream(statusFileName);
			statusDB.setProperty(sugID, "None");
			statusDB.store(output, null);
			statusDB.clear();
			output.close();
		} catch (Exception e) {e.printStackTrace();}
	}
	public void removeSuggestion(String suggestionID, String serverID) {
		statusDB.clear();
		suggestionDB.clear();
		this.dbFile = new File(serverID + "_suggestiondb.txt");
		this.statusdbFile = new File (serverID + "_status_suggestiondb.txt");
		this.fileName = serverID + "_suggestiondb.txt";
		this.statusFileName = serverID + "_status_suggestiondb.txt";
		loadDatabase(serverID);
		try {
			output = new FileOutputStream(fileName);
			suggestionDB.remove(suggestionID);
			suggestionDB.store(output, null);
			suggestionDB.clear();
			//REMOVE FROM STATUS DB (approve/deny/none)
			output = new FileOutputStream(statusFileName);
			statusDB.remove(suggestionID);
			statusDB.store(output, null);
			statusDB.clear();
			output.close();
			} catch (Exception e) {e.printStackTrace();}
	}
	@SuppressWarnings("unchecked")
	public String listSuggestions(String serverID) {
		suggestionDB.clear();
		suggestionDB.clear();
		this.dbFile = new File(serverID + "_suggestiondb.txt");
		this.statusdbFile = new File (serverID + "_status_suggestiondb.txt");
		this.fileName = serverID + "_suggestiondb.txt";
		this.statusFileName = serverID + "_status_suggestiondb.txt";
		loadDatabase(serverID);
		String suggestions = "";
	    Enumeration<String> suggestionsList = (Enumeration<String>) suggestionDB.propertyNames();
	    while (suggestionsList.hasMoreElements()) {
	      String key = suggestionsList.nextElement();
	      String value = suggestionDB.getProperty(key);
	      suggestions += "Suggestion ID: " + key + ", Suggestion: " + value + "\n";
	    }
		return suggestions; //placeholder
	}
	public String getSuggestionID(String serverID) {
		return sugID;
	}
	public File getFile(String serverID) {
		return new File(serverID + "_suggestiondb.txt");
	}
	public void approveSuggestion(String suggestionID, String serverID) {
		statusDB.clear();
		suggestionDB.clear();
		this.dbFile = new File(serverID + "_suggestiondb.txt");
		this.statusdbFile = new File (serverID + "_status_suggestiondb.txt");
		this.fileName = serverID + "_suggestiondb.txt";
		this.statusFileName = serverID + "_status_suggestiondb.txt";
		loadDatabase(serverID);
		try {
			output = new FileOutputStream(statusFileName);
			statusDB.setProperty(suggestionID, "Approved");
			statusDB.store(output, null);
			statusDB.clear();
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void denySuggestion(String suggestionID, String serverID){
		statusDB.clear();
		suggestionDB.clear();
		this.dbFile = new File(serverID + "_suggestiondb.txt");
		this.statusdbFile = new File (serverID + "_status_suggestiondb.txt");
		this.fileName = serverID + "_suggestiondb.txt";
		this.statusFileName = serverID + "_status_suggestiondb.txt";
		loadDatabase(serverID);
		try {
			output = new FileOutputStream(statusFileName);
			statusDB.setProperty(suggestionID, "Denied");
			statusDB.store(output, null);
			statusDB.clear();
			output.close();
		} catch (Exception e) {}
	}
	public String getSuggestionStatus(String suggestionID, String serverID) {
		statusDB.clear();
		suggestionDB.clear();
		this.dbFile = new File(serverID + "_suggestiondb.txt");
		this.statusdbFile = new File (serverID + "_status_suggestiondb.txt");
		this.fileName = serverID + "_suggestiondb.txt";
		this.statusFileName = serverID + "_status_suggestiondb.txt";
		loadDatabase(serverID);
		return statusDB.getProperty(suggestionID);
	}
}