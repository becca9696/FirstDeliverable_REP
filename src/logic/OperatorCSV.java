package logic;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;

public class OperatorCSV {
	
	private static Logger logger;
	private static final String CSV_SEPARATOR = ";";
	private static final String CSV_INDEX = "/";
	
	public void writeToCSV(List<NewFeatureFixed> commitsList) {
	    	
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("commits.csv"), StandardCharsets.UTF_8))) {
     
			for (NewFeatureFixed cl : commitsList) {
	            	
				StringBuilder oneLine = new StringBuilder();	                
				oneLine.append(cl.getMonth());	        	                
				oneLine.append(CSV_INDEX);	 
				oneLine.append(cl.getYear());		                
				oneLine.append(CSV_SEPARATOR);	                
				oneLine.append(cl.getNumNewFeatureFixed());	 
				bw.write(oneLine.toString());	                
				bw.newLine();        
	        }
			
	        bw.flush();
	        bw.close();
		}
	       
	    catch (IOException e) { logger.info(e.toString());  }
		
	}

}
