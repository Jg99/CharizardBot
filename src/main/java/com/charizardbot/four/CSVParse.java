package com.charizardbot.four;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
//Contributed by JFSniper#7288 aka Jay.
public class CSVParse {
    public static Map<String, String> getCSVResults(File spreadsheet, String regex){
        Map<String, String> map = new HashMap<String, String>();
        Pattern pattern = Pattern.compile(regex.concat(".*"), Pattern.CASE_INSENSITIVE);
        try {
            CSVParser parser = CSVFormat.EXCEL.withHeader("Name").withIgnoreEmptyLines().withTrim().parse(new FileReader(spreadsheet));
            List<CSVRecord> list = parser.getRecords();
            CSVRecord headerRow = list.get(4);
            int i = 0;
            for (Iterator<String> it = headerRow.iterator(); it.hasNext(); i++) {
                String s = it.next();
                if(s.equals("Value")){
                    break;
                }
            }
            list = list.subList(5, list.size());
            for (CSVRecord csvRecord : list) {
                // Change the index of the value column if needed
                Matcher matcher = pattern.matcher(csvRecord.get(1));
                if(matcher.matches()){
                    // Change the index of the value column if needed
                    map.put(csvRecord.get(1), csvRecord.get(i));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
