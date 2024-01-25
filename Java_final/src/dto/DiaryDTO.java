package dto;

public class DiaryDTO { //하루 일기 정의함
	String date = null;
	String mood = null;
	String memo = null;
	int moodNum = 0;

	
	public int getMoodNum() {
		return moodNum;
	}
	public void setMoodNum(int moodNum) {
		this.moodNum = moodNum;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMood() {
		return mood;
	}
	public void setMood(String mood) {
		this.mood = mood;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

}
