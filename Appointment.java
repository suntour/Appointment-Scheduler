
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Appointment implements Comparable<Appointment>{

	//Reference variable date which points to GregorianCalendar objects
	public Calendar date;

	//Full date format
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy mm dd HH:mm:ss");

	//String to store appointment information
	private String description = "";

	public Appointment(int year, int month, int day, int hour, int minute, String description){

		//date points to a new GregorianCalendar object
		date = new GregorianCalendar(year,month,day,hour,minute);

		//description will point to the description passed in argument
		setDescription(description);
	}

	//Returns Appointment Description
	public String getDescription(){
		return description;
	}

	//Not sure why this is needed, constructor has description
	public void setDescription(String description){
		this.description = description;
	}

	//Returns appropriate Appointment string to print
	public String print(){
		//Prints the hour, minute, and appointment description, followed by two new lines
		return date.get(Calendar.HOUR_OF_DAY) + ":" + date.get(Calendar.MINUTE) + " " + description;
	}

	public boolean occursOn(int year, int month, int day, int hour, int minute){
		if(date.get(Calendar.YEAR) == year && date.get(Calendar.MONTH) == month && date.get(Calendar.DAY_OF_MONTH) == day && date.get(Calendar.HOUR_OF_DAY) == hour && date.get(Calendar.MINUTE) == minute){
			return true;
		}
		else return false;
	}

	public boolean occursOn(int day, int month, int year){
		if(date.get(Calendar.YEAR) == year && date.get(Calendar.MONTH) == month && date.get(Calendar.DAY_OF_MONTH) == day){
			return true;
		}
		else return false;

	}

	public Calendar getDate(){
		return date;
	}
	
	public String getHM(){
		return date.get(Calendar.HOUR_OF_DAY) + ":" + date.get(Calendar.MINUTE);
	}


	public String getFullDate(){
		return sdf.format(date);
	}

	public int compareTo(Appointment o) {
	    return getDate().compareTo(o.getDate());
	  }

}
