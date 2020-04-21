package logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

public class MyGit {
	
	private Logger logger;
    private static final String PATH = "BahirRep";
    private static final String URI = "https://github.com/apache/bahir.git";
    
    //Controlla che il ticketID sia unico nel confronto
    public boolean checkTicket(String message, String ticketId) {
    	
    	if (message.contains(ticketId)) {
       		for (int i = 0; i < 10; i++) {	
    			if (message.contains(ticketId + Integer.toString(i))) {	return false; }	
    		}
    		return true;
    	}
    	else { return false; } 
    }
    
    //Clona e ricerca ultima commit per i relativi ticketID
    public List<Date> cloneAndSearch(List<String> jsonList) throws IOException {
    	
    	List<Date> commitList = new ArrayList<>();
    	
    	try {
			
			//Clona la Repository
			Git gitRepo = Git.cloneRepository()
						 		.setURI(URI)
						 		.setDirectory(new File(PATH))
						 		.call(); 
			
			//Restituisce la lista delle ultime commit
			commitList = searchLastCommit(jsonList, gitRepo);				
    	}	
    	catch (GitAPIException e) {	logger.info(e.toString());}			
    	
    	try { FileUtils.deleteDirectory(new File(PATH)); }	
	
    	catch(IOException e) { logger.info(e.toString()); }
    	
		return commitList;
    	
    }

	public List<Date> searchLastCommit(List<String> jsonList,  Git gitRepo) throws IOException {
		
		List<Date> commitList = new ArrayList<>();
		
		try {
	
			//Crea un Logger
			logger =  Logger.getLogger(logic.MyGit.class.getName());
		
			for (int i = 0; i < jsonList.size(); i++) {
				
				//Data inizializzata al 01:00:00 1970
				Date maxDate = new Date(0L);
				
				//Prendi tutte le commit
				Iterable<RevCommit> commits = gitRepo.log().all().call();
			
				//Per tutte le commit
				for (RevCommit commit : commits) {
				
					//Controlla se le commit contengono TicketsID
					if (checkTicket(commit.getFullMessage(),jsonList.get(i))) { 
					
						//Stampa un messaggio di Log
						logger.log(Level.INFO, "commitID=" + commit.getId() + "\nMESSAGE: " + commit.getFullMessage() + " " + commit.getAuthorIdent().getWhen() + "\nDATE:" + commit.getCommitTime());
						
						//Per ogni ticket prendo l'ultima commit						
						if (commit.getCommitTime()*1000L > maxDate.getTime()) {	maxDate.setTime(commit.getCommitTime()*1000L); }	
						
					}				
				}			
				commitList.add(new Date(maxDate.getTime()));		
			}
			
			//Chiudi la Repository
			gitRepo.close();
		}
			
		catch (GitAPIException e) { e.printStackTrace(); }
		
		return commitList;				
	} 
	
}
