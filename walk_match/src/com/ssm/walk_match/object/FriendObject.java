package com.ssm.walk_match.object;

public class FriendObject {

	private String img_url;
	private String name;
	public FriendObject()
	{
		
	}
	public FriendObject(String name)
	{
		this.name = name;
	}
	public FriendObject(String img_url, String name) {
		super();
		this.img_url = img_url;
		this.name = name;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
