package models;

import java.io.Serializable;
import java.util.ArrayList;

public class Section implements Serializable
{

	private static final long serialVersionUID = 3004879294918214266L;
	public String name;
	public String content = "";
	public Section parent = null;
	public ArrayList<Section> children = new ArrayList<Section>();
	public ArrayList<Comment> comments = new ArrayList<Comment>();
	
	// default constructor for XML
	public Section()
	{
		this("default");
	}

	public Section(String name)
	{
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	public String showContent() {
		return name+":\n"+content;
	}
	
	// getters and setters
	public Section getParent()
	{
		return parent;
	}

	public void setParent(Section parent)
	{
		this.parent = parent;
	}

	// this is used for XMl encoder, but we can never change the children of a
	// Section
	public void setChildren(ArrayList<Section> children)
	{
		this.children = children;
	}

	public String getName()
	{
		return name;
	}

	// this is used for XML encoder, but we can never change the name of a Section
	public void setName(String name)
	{
		this.name = name;
	}

	public ArrayList<Section> getChildren()
	{
		return children;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}
	// end of getters and setters

	// add child to the array list
	public void addChild(Section child)
	{
		children.add(child);
		child.parent=this;
	}

	// remove child from the array list
	public void deleteChild(Section child)
	{
		children.remove(child);
	}
	
	public ArrayList<Comment> getComments()
	{
		return comments;
	}
	
	public boolean equals(Object other)
	{
		Section other2 = (Section) other;
	
		if(!this.name.equals(other2.name))
			return false;
		if(!this.content.equals(other2.content))
			return false;
		if(this.children.size() != other2.children.size())
			return false;
		if(this.comments.size() != other2.comments.size())
			return false;
		
		for(int i = 0; i < this.children.size(); i++)
		{
			if(!(this.children.get(i).name.equals(other2.children.get(i).name)))
				return false;
		}
		
		for(int i = 0; i < this.comments.size(); i++)
		{
			if(!(this.comments.get(i).username.equals(other2.comments.get(i).username)))
				return false;
			
			if(!(this.comments.get(i).content.equals(other2.comments.get(i).content)))
				return false;
		}
		
		return true;
	}
}
