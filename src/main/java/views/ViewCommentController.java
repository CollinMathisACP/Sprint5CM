package views;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.BPMainModel;
import models.BYBPlan;
import models.BusinessPlan;
import models.CNTRAssessment;
import models.Section;
import models.VMOSA;
import models.Comment;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;



public class ViewCommentController
{
		
	@FXML
	private Button removeComment;
	
	@FXML
	private Button commentMainPage;
	
	@FXML
	private ListView<String> commentListView;
	
	BPMainModel model;
	public ObservableList<String> commentList= FXCollections.observableArrayList();
	Stage currentStage;
	
	public void setModel(BPMainModel newModel)
	{
    	model = newModel;
    	    	
    	ArrayList<Comment> commentDup = model.currentSection.comments;
    	for(int i = 0; i < commentDup.size(); i++)
    	{
    		Comment current = commentDup.get(i);
    		commentList.add(current.getUsername() + "\n" + current.getContent());
    	}
    	
    	commentListView.setItems(commentList);
    }
	
	@FXML
	void onClickRemoveComment(ActionEvent event)
	{
    	String clickedComment = commentListView.getSelectionModel().getSelectedItem();
    	if(clickedComment != null)
    	{
    		ObservableList<String> currentComments = commentListView.getItems();
    		for(int i = 0; i < currentComments.size(); i++)
    		{
    			if(clickedComment.equals(currentComments.get(i)))
    			{
    				currentComments.remove(i);
    				model.removeComment(clickedComment);
    			}
    		}
    		
    		commentListView.setItems(commentList);
    	}
		
	}
	
	@FXML
	void onClickMainPage(ActionEvent event)
	{
		
    	Stage stage = (Stage) commentMainPage.getScene().getWindow();
        currentStage = stage;
    			
    	//show Leave Confirmation window
    	model.showLeaveConfirm(stage);
	}
}


