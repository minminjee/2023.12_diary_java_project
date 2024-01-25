package dto;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class Today {
	LocalDate now = LocalDate.now();
	
	int year = now.getYear();
	String monthS = now.getMonth().toString();
	int monthV = now.getMonthValue();
	int date = now.getDayOfMonth();
	String day = now.getDayOfWeek().toString();
	
	public Today selday (String a) {
		String date[] = a.split("/");
		this.now = LocalDate.of
				(Integer.parseInt(date[0]), 
				Integer.parseInt(date[1]),
				Integer.parseInt(date[2]));
		return this;
	}
	
	public void prt() {
		System.out.print(monthS+"월");
		System.out.println(date+"일");
		System.out.println(day+"요일");
	}

	
	public String getMonthS() {
		return monthS;
	}

	public void setMonthS(String monthS) {
		this.monthS = monthS;
	}

	public int getMonthV() {
		return monthV;
	}

	public void setMonthV(int monthV) {
		this.monthV = monthV;
	}

	public LocalDate getNow() {
		return now;
	}

	public void setNow(LocalDate now) {
		this.now = now;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	
	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	



	

}
