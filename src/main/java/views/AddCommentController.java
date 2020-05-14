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



public class AddCommentController
{
		
	@FXML
	private Button submitComment;
	
	@FXML
	private Button cancelComment;
	
	@FXML
	private TextArea commentText;
	
	BPMainModel model;
	Stage currentStage;
	
	public void setModel(BPMainModel newModel)
	{
    	model = newModel;
    }
	
	@FXML
	void onClickSubmitComment(ActionEvent event)
	{
    	String content = commentText.getText();
    	String username = model.client.getLoginPerson().username;
    	Comment newComment = new Comment(username, content);
    	model.currentSection.getComments().add(newComment);
    	
    	Stage stage = (Stage) submitComment.getScene().getWindow();
        currentStage = stage;
    			
    	//show Leave Confirmation window
    	model.showLeaveConfirm(stage);
		
	}
	
	@FXML
	void onClickCancelComment(ActionEvent event)
	{
		Stage stage = (Stage) submitComment.getScene().getWindow();
        currentStage = stage;
    			
    	//show Leave Confirmation window
    	model.showLeaveConfirm(stage);
	}
}


