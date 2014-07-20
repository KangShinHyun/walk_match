package com.ssm.walk_match.object;

public class RankObject {

	private String world_num;//¿ùµå·©Å·
	private String fri_num; //Ä£±¸·©Å·
	private String img;
	private String name;
	private String percent;
	private String win;
	private String match;
	private String culture_img;
	
	public RankObject(String world_num, String fri_num, String img,
			String name, String percent, String win, String match,
			String culture_img) {
		super();
		this.world_num = world_num;
		this.fri_num = fri_num;
		this.img = img;
		this.name = name;
		this.percent = percent;
		this.win = win;
		this.match = match;
		this.culture_img = culture_img;
	}
	
	public String getWorld_num() {
		return world_num;
	}

	public void setWorld_num(String world_num) {
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
	public String getWin() {
		return win;
	}
	public void setWin(String win) {
		this.win = win;
	}
	public String getMatch() {
		return match;
	}
	public void setMatch(String match) {
		this.match = match;
	}
	public String getCulture_img() {
		return culture_img;
	}
	public void setCulture_img(String culture_img) {
		this.culture_img = culture_img;
	}
	

}
