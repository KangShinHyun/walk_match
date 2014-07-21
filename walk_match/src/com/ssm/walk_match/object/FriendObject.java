package com.ssm.walk_match.object;

public class FriendObject {

	private String email;
	private String img;
	private String name;
	private int friend;
	private int victory;
	private int worldNum;
	private int nation;
	private int match;
	private String vs;
	private int step;
	public FriendObject(String email, String img, String name, int friend,
			int victory, int worldNum, int nation, int match) {
		super();
		this.email = email;
		this.img = img;
		this.name = name;
		this.friend = friend;
		this.victory = victory;
		this.worldNum = worldNum;
		this.nation = nation;
		this.match = match;
	}
	
	public FriendObject(String email, String img, String name, int friend,
			int victory, int worldNum, int nation, int match, String vs,
			int step) {
		super();
		this.email = email;
		this.img = img;
		this.name = name;
		this.friend = friend;
		this.victory = victory;
		this.worldNum = worldNum;
		this.nation = nation;
		this.match = match;
		this.vs = vs;
		this.step = step;
	}

	public String getVs() {
		return vs;
	}

	public void setVs(String vs) {
		this.vs = vs;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public int getFriend() {
		return friend;
	}
	public void setFriend(int friend) {
		this.friend = friend;
	}
	public int getVictory() {
		return victory;
	}
	public void setVictory(int victory) {
		this.victory = victory;
	}
	public int getWorldNum() {
		return worldNum;
	}
	public void setWorldNum(int worldNum) {
		this.worldNum = worldNum;
	}
	public int getNation() {
		return nation;
	}
	public void setNation(int nation) {
		this.nation = nation;
	}
	public int getMatch() {
		return match;
	}
	public void setMatch(int match) {
		this.match = match;
	}
	
	

}
