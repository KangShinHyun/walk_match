package com.ssm.walk_match.object;

public class HistoryObject {

	private String time;
	private String man1;
	private String man2;
	private String name1;
	private String name2;
	private String step1;
	private String step2;
	private String nation1;
	private String nation2;
	
	public HistoryObject(String time, String man1, String man2, String step1,
			String step2, String nation1, String nation2) {
		super();
		this.time = time;
		this.man1 = man1;
		this.man2 = man2;
		this.step1 = step1;
		this.step2 = step2;
		this.nation1 = nation1;
		this.nation2 = nation2;
	}
	
	public HistoryObject(String time, String man1, String man2, String name1,
			String name2, String step1, String step2, String nation1,
			String nation2) {
		super();
		this.time = time;
		this.man1 = man1;
		this.man2 = man2;
		this.name1 = name1;
		this.name2 = name2;
		this.step1 = step1;
		this.step2 = step2;
		this.nation1 = nation1;
		this.nation2 = nation2;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getMan1() {
		return man1;
	}
	public void setMan1(String man1) {
		this.man1 = man1;
	}
	public String getMan2() {
		return man2;
	}
	public void setMan2(String man2) {
		this.man2 = man2;
	}
	public String getStep1() {
		return step1;
	}
	public void setStep1(String step1) {
		this.step1 = step1;
	}
	public String getStep2() {
		return step2;
	}
	public void setStep2(String step2) {
		this.step2 = step2;
	}
	public String getNation1() {
		return nation1;
	}
	public void setNation1(String nation1) {
		this.nation1 = nation1;
	}
	public String getNation2() {
		return nation2;
	}
	public void setNation2(String nation2) {
		this.nation2 = nation2;
	}
	

}
