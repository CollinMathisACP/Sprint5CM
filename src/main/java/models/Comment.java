package models;

public class Comment
{
	public String username;
	public String content;
	
	public Comment(String usr, String con)
	{
		this.username = usr;
		this.content = con;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
