package com.ssm.walk_match.object;

public class RankObject {

	private int world_num;//¿ùµå·©Å·
	private String fri_num; //Ä£±¸·©Å·
	private String img;
	private String name;
	private String percent;
	private int win;
	private int match;
	private int nation;
	public RankObject(int world_num, String img, String name, int win,
			int match, int nation) {
		super();
		this.world_num = world_num;
		this.img = img;
		this.name = name;
		this.win = win;
		this.match = match;
		this.nation = nation;
	}

	public RankObject(int world_num, String fri_num, String img, String name,
			String percent, int win, int match, int nation) {
		super();
		this.world_num = world_num;
		this.fri_num = fri_num;
		this.img = img;
		this.name = name;
		this.percent = percent;
		this.win = win;
		this.match = match;
		this.nation = nation;
	}

	public int getWorld_num() {
		return world_num;
	}

	public void setWorld_num(int world_num) {
		this.world_num = world_num;
	}

	public String getFri_num() {
		return fri_num;
	}

	public void setFri_num(String fri_num) {
		this.fri_num = fri_num;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getMatch() {
		return match;
	}

	public void setMatch(int match) {
		this.match = match;
	}

	public int getNation() {
		return nation;
	}

	public void setNation(int nation) {
		this.nation = nation;
	}

	
}
