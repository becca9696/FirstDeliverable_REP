package logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;

public class Main {
	
	public static void main(String[] args) {
			
		List<String> jsonList = new ArrayList<>();	
		List<Date> lastCommit = new ArrayList<>();
		Date [] minMaxLastCommit = new Date[2];
		List<NewFeatureFixed> monthlyCommit = new ArrayList<>();
		
		MyGit gitOp = new MyGit();
		MinMaxDate minMaxDate = new MinMaxDate();
		OperatorCSV opCSV = new OperatorCSV();
		
		Logger logger = Logger.getLogger("logfileMain.txt");
		
		RetrieveTicketsID retriveTicjetsID = new RetrieveTicketsID();
				
		try {
			
			//Retrive dei Ticket
			jsonList = retriveTicjetsID.retriveTickets();
			
			logger.log(Level.INFO, "----retriveTickets() output: \n" );
			
			for (int i = 0; i < jsonList.size(); i++) {
				
				logger.log(Level.INFO, "	TicketID: "  + jsonList.get(i));	
				
			}
			
			//Lista ultime commit
			lastCommit = gitOp.cloneAndSearch(jsonList);
			
			logger.log(Level.INFO, "----cloneAndSearch() output: \n" );
			
			for (int i = 0; i < lastCommit.size(); i++) {
			
				logger.log(Level.INFO, "	TicketID: "  + jsonList.get(i) + ": " + lastCommit.get(i));			
				
			}
			
			minMaxLastCommit = minMaxDate.minMaxCommit(lastCommit);
			
			logger.log(Level.INFO, "---minMaxCommit() output: \n");
			
			//Array contentnte data più "vecchia" e recente.
			for (int i = 0; i < 2; i++) {
				
				logger.log(Level.INFO, "	" + minMaxLastCommit[i].toString());
				
			}	
			
			monthlyCommit = minMaxDate.fillNewFeatureFixed(minMaxLastCommit, lastCommit);
			
			logger.log(Level.INFO, "----fillNewFeatureFixed() output: \n");
			
			for (NewFeatureFixed n: monthlyCommit) {
				
				logger.log(Level.INFO, "	DATE: " + n.getYear() + "/" + n.getMonth() + ":" + n.getNumNewFeatureFixed());	
				
			}
			
			opCSV.writeToCSV(monthlyCommit);
			
			logger.log(Level.INFO, "----writeToCSV() done:\n");
			
		} catch (JSONException | IOException e) { e.printStackTrace(); }
		
	}

}
