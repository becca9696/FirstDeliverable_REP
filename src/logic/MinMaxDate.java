package logic;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MinMaxDate {

	public Date[] minMaxCommit(List<Date> lastCommit){
		
		Date[] dates = new Date[] {
			    new Date(),
			    new Date(0L),
		};
	
		//Trova data più "vecchia" e più "recente" delle new festure fixed
		for (int i = 0; i < lastCommit.size(); i++) {
			
			//Prendo la data più grande
			if(lastCommit.get(i).getTime() > dates[1].getTime()) { dates[1].setTime(lastCommit.get(i).getTime()); }		
								
			////Prendo la data più picola
			else if (lastCommit.get(i).getTime() < dates[0].getTime() && (lastCommit.get(i).toInstant().atZone(ZoneId.of("CET")).toLocalDate()).getYear() != 1970) { dates[0].setTime(lastCommit.get(i).getTime()); }		
			
		}
		return dates;
	}
	
	public List<NewFeatureFixed> fillNewFeatureFixed(Date [] minMmaxLastCommit, List<Date> lastCommit) {
		
		List<NewFeatureFixed> nff = new ArrayList<>();
		
		int month;
		int year;
		
		//Trovo il numero di mesi
		int nffSize = ((minMmaxLastCommit[1].toInstant().atZone(ZoneId.of("CET")).toLocalDate()).getYear()*12 + (minMmaxLastCommit[1].toInstant().atZone(ZoneId.of("CET")).toLocalDate()).getMonthValue()) - 
				       ((minMmaxLastCommit[0].toInstant().atZone(ZoneId.of("CET")).toLocalDate()).getYear()*12 + (minMmaxLastCommit[0].toInstant().atZone(ZoneId.of("CET")).toLocalDate()).getMonthValue()) + 1;
		
		month = (minMmaxLastCommit[0].toInstant().atZone(ZoneId.of("CET")).toLocalDate()).getMonthValue();
		year = (minMmaxLastCommit[0].toInstant().atZone(ZoneId.of("CET")).toLocalDate()).getYear();
		
		for (int i = 0; i < nffSize; i++) {
			
			nff.add(new NewFeatureFixed(year, month));
			month++;
			
			if (month > 12) {
				
				month = 1;
				year++;	
			}
		}
		
		
		for (NewFeatureFixed n: nff) {
		
			for (int i = 0; i < lastCommit.size(); i++) {
			
				if ((lastCommit.get(i).toInstant().atZone(ZoneId.of("CET")).toLocalDate()).getYear() == n.getYear() && (lastCommit.get(i).toInstant().atZone(ZoneId.of("CET")).toLocalDate()).getMonthValue() == n.getMonth()) { n.incrementNFF(); }	
			}
		}
		return nff;
	}

}
