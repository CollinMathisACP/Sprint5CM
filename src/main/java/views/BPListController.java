package views;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import models.BusinessPlan;
import models.MainViewModel;
import models.ViewTransitionModelInterface;

public class BPListController {

	MainViewModel model; 
	ViewTransitionModelInterface supermodel;
	public ObservableList<BusinessPlan> BPList= FXCollections.observableArrayList();
	
    public void setModel(MainViewModel newModel,ViewTransitionModelInterface vm)
    {
      model=newModel;
      supermodel=vm;
      ArrayList<BusinessPlan> Dulplicate=model.client.askForAllBP();
		for (int i=0; i<Dulplicate.size();i++){
			BPList.add(Dulplicate.get(i));
		}
	
		BPListView.setItems(BPList);
		
		//allow multiple selection for the compare button
		BPListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    
    @FXML
    private Button clone;

    @FXML
    private Button copy;
    
    @FXML
    private Button compareBPs;

    @FXML
    private ListView<BusinessPlan> BPListView;
    
    
    @FXML
    void onClickClone(ActionEvent event) {
    	BusinessPlan clickedBP=BPListView.getSelectionModel().getSelectedItem();
    	//System.out.println("Clone Clicked on " + clickedBP);
    	if(clickedBP!=null) {
    		model.client.askForBP(clickedBP.year);
            model.showCloneView(supermodel);
    	}
    }
    

    @FXML
    void onClickCopy(ActionEvent event) {
    	BusinessPlan clickedBP=BPListView.getSelectionModel().getSelectedItem();
    	//System.out.println("Copy Clicked on " + clickedBP);
    	
    	if(clickedBP!=null) {
    		//set selected BP to currentBP
    		model.client.askForBP(clickedBP.year);
    		//System.out.println(model.client.getCurrentBP());
    		//close current BPList window
    		Stage stage = (Stage) copy.getScene().getWindow();
    		stage.close();
    		//show BPMainView window
            model.showCopyView(); 
        }
    }
    
    @FXML
    void onClickCompare(ActionEvent event)
    {
        ObservableList<BusinessPlan> selectedItems = BPListView.getSelectionModel().getSelectedItems();
        model.setSelectedPlans(selectedItems);
    	
    	//close current BPList window        
		Stage stage = (Stage) compareBPs.getScene().getWindow();
		stage.close();
		
		//show Compare window
        model.showCompareView(model);
    }
}
