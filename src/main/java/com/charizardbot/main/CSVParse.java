package com.charizardbot.main;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
//Contributed by JFSniper#7288 aka Jay.
public class CSVParse {
    public static List<CSVRecord> getCSVResults(File spreadsheet) {//String regex){
        List<CSVRecord> list;
        try {
            CSVParser parser = CSVFormat.EXCEL.builder().setHeader("Name").setIgnoreEmptyLines(true).setTrim(true).get().parse(new FileReader(spreadsheet));
             list = parser.getRecords();
             return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
