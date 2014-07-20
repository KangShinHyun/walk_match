package com.ssm.walk_match.object;

import java.util.ArrayList;

public class LoginObject {

	private String name;
	private String email;
	private String img;
	private int nation;
	private boolean checkLogin = false;
	private String pwd;
	private int friend = 0;
	private int victory = 0;
	private int worldNum = 0;
	private static LoginObject object = new LoginObject();
	private LoginObject()
	{
		
	}
	private LoginObject(String name)
	{
		this.name = name;
	}
	private LoginObject(String name, String email, String img, int nation) {
		super();
		this.name = name;
		this.email = email;
		this.img = img;
		this.nation = nation;
	}
	public static LoginObject getInstance()
	{
		return object;
	}
	
	public boolean isCheckLogin() {
		return checkLogin;
	}
	public void setCheckLogin(boolean checkLogin) {
		this.checkLogin = checkLogin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public int getNation() {
		return nation;
	}
	public void setNation(int nation) {
		this.nation = nation;
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
	public boolean isLogin()
	{
		if(this.email == null || this.email.equals(""))
		{
			checkLogin = false;
		}
		else 
		{
			checkLogin = true;
		}
		return checkLogin;
	}
	public void setLogin(String id)
	{
		this.email = id;
		checkLogin = true;
	}
	public void setLogin(String id,String pwd)
	{
		this.email = id;
		this.pwd = pwd;
		checkLogin = true;
	}
	public void setLogin(String name,String email, String img, int nation)
	{
		this.email = email;
		this.img = img;
		this.nation = nation;
		this.name = name;
		checkLogin = true;
	}
	public void setLogin(String name,String email, String img, int nation,String pwd)
	{
		this.email = email;
		this.img = img;
		this.nation = nation;
		this.name = name;
		checkLogin = true;
		this.pwd  = pwd;
	}
	public void setLogin(String name,String email, String img, int nation,String pwd,int firend, int victory, int worldNum)
	{
		this.email = email;
		this.img = img;
		this.nation = nation;
		this.name = name;
		checkLogin = true;
		this.pwd  = pwd;
		this.friend = friend;
		this.victory = victory;
		this.worldNum = worldNum;
	}
	
	public void setLogout()
	{
		this.email = "";
		this.img = "";
		this.name = "";
		this.nation = -1;
		this.pwd  = "";
		checkLogin = false;
		this.friend = 0;
		this.victory = 0;
		this.worldNum = 0;
	}

}
