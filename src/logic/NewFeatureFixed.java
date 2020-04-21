package logic;

public class NewFeatureFixed {
	
	private int numNewFeatureFixed;
	private int month;
	private int year;
	
	public NewFeatureFixed(int year, int month) {
		
		this.setMonth(month);
		this.setYear(year);
		this.numNewFeatureFixed = 0;
		
	}

	public void incrementNFF() {
		numNewFeatureFixed++;
	}
	
	public int getNumNewFeatureFixed() {
		return numNewFeatureFixed;
	}
	
	public void setNumNewFeatureFixed(int numNewFeatureFixed) {
		this.numNewFeatureFixed = numNewFeatureFixed;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

}
