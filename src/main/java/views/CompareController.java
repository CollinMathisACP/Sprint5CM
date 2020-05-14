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
import models.MainViewModel;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;



public class CompareController
{
		
	@FXML
	private Text nameA;
	@FXML
	private Text yearA;
	@FXML
	private Text depA;
	@FXML
	private Text typeA;
	@FXML
	private Text editableA;
	
	@FXML
	private Text nameB;
	@FXML
	private Text yearB;
	@FXML
	private Text depB;
	@FXML
	private Text typeB;
	@FXML
	private Text editableB;
	
	@FXML
	private ListView<String> listA;
	
	@FXML
	private ListView<String> listB;
	
	@FXML
	private Button mainPage;
	
	MainViewModel model;
	Stage currentStage;
	
	public ObservableList<String> listAVals= FXCollections.observableArrayList();
	public ObservableList<String> listBVals= FXCollections.observableArrayList();

	
	public void setModel(MainViewModel newModel)
	{
    	model = newModel;
    	
    	//plan A
    	BusinessPlan a = model.selectedPlans.get(0);
    	nameA.setText(a.name);
    	yearA.setText(Integer.toString(a.year));
    	depA.setText(a.department);
    	typeA.setText(a.type);
    	if(a.isEditable)
    		editableA.setText("Editable");
    	else
    		editableA.setText("Not Editable");
    	
    	
    	//plan B
    	BusinessPlan b = model.selectedPlans.get(1);
    	nameB.setText(b.name);
    	yearB.setText(Integer.toString(b.year));
    	depB.setText(b.department);
    	typeB.setText(b.type);
    	if(b.isEditable)
    		editableB.setText("Editable");
    	else
    		editableB.setText("Not Editable");
    	
    	//onitialize listA
    	ArrayList<Section> unmatchedA = model.getUnmatchedSections(a, b);
    	for(Section e: unmatchedA)
    	{
    		listAVals.add(e.name);
    	}
    	
    	//initialize listB
    	ArrayList<Section> unmatchedB = model.getUnmatchedSections(b, a);
    	for(Section e: unmatchedB)
    	{
    		listBVals.add(e.name);
    	}
    	
    	listA.setItems(listAVals);
    	listB.setItems(listBVals);
    	
    }
	
	@FXML
	void onClickMainPage(ActionEvent event)
	{
		
    	Stage stage = (Stage) mainPage.getScene().getWindow();
        currentStage = stage;
    			
    	//show Leave Confirmation window
    	model.showLeaveConfirm(stage);
	}
}


