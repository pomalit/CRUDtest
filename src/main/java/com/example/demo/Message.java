package com.example.demo;

public class Message {
	private int id;
	private String user;
	private String content;
	
	public Message() {
		super();
	}
	
	public Message(int id, String user, String content) {
		super();
		this.id = id;
		this.user = user;
		this.content = content;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
