package com.ssm.walk_match.object;

public class MessageObject {

	private String toMan; //보내는사람
	private String fromMan; //받는사람
	private String content; //내용
	private String time;
	private String img;
	public MessageObject(String toMan, String fromMan, String content) {
		super();
		this.toMan = toMan;
		this.fromMan = fromMan;
		this.content = content;
	}
	
	public MessageObject(String toMan, String fromMan, String content,
			String time) {
		super();
		this.toMan = toMan;
		this.fromMan = fromMan;
		this.content = content;
		this.time = time;
	}
	
	public MessageObject(String toMan, String fromMan, String content,
			String time, String img) {
		super();
		this.toMan = toMan;
		this.fromMan = fromMan;
		this.content = content;
		this.time = time;
		this.img = img;
	}

	public String getToMan() {
		return toMan;
	}
	public void setToMan(String toMan) {
		this.toMan = toMan;
	}
	public String getFromMan() {
		return fromMan;
	}
	public void setFromMan(String fromMan) {
		this.fromMan = fromMan;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	

}
