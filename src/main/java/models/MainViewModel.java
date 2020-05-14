package models;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import views.BPMainController;
import views.CloneWindowController;
import views.CompareController;
import views.LeaveController;

public class MainViewModel {

	public MyRemoteClient client;
	public BorderPane mainview;
	public ObservableList<BusinessPlan> selectedPlans;

	public MainViewModel(MyRemoteClient wowclient, BorderPane view) {
		this.client=wowclient;
		this.mainview=view;
	}
	
	public void setSelectedPlans(ObservableList<BusinessPlan> plans)
	{
		this.selectedPlans = plans;
	}

	public void showCloneView(ViewTransitionModelInterface vm) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainViewModel.class
				.getResource("../views/CloneWindow.fxml"));
		try {
			BorderPane view = loader.load();
			CloneWindowController cont = loader.getController();
			cont.setModel(this,vm);

			Stage stage = new Stage();
			Scene s = new Scene(view);
			stage.setScene(s);
			stage.setTitle("Clone Page");
			stage.show();
		} catch (IOException e) {
			System.out.println("erroe");
			e.printStackTrace();
		}

	}

	//switch to BPMainView window
	public void showCopyView() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainViewModel.class
				.getResource("../views/BPMainView.fxml"));
		try {
			BorderPane view = loader.load();
			System.out.println(client.getCurrentBP());
			
			BPMainModel model = new BPMainModel(client, view);
			BPMainController cont = loader.getController();

			cont.setModel(model);
		
			Stage stage = new Stage();
			Scene s = new Scene(view);
			stage.setScene(s);
			stage.setTitle("BPViewer");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void showCompareView(MainViewModel model)
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainViewModel.class
				.getResource("../views/CompareView.fxml"));
		try
		{
			BorderPane view = loader.load();
			
			CompareController cont = loader.getController();

			cont.setModel(model);
		
			Stage stage = new Stage();
			Scene s = new Scene(view);
			stage.setScene(s);
			stage.setTitle("Compare");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
	
	//show leave confirmation window
		public void showLeaveConfirm(Stage stageP) {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainViewModel.class
					.getResource("../views/LeaveView.fxml"));
			try {
				BorderPane view = loader.load();

				BPMainModel model = new BPMainModel(client, view);
				LeaveController cont = loader.getController();

				cont.setModel(model,stageP);
			
				Stage stage = new Stage();
				Scene s = new Scene(view);
				stage.setScene(s);
				stage.setTitle("Leave Confirmtion");
				stage.show();
				
			} catch (IOException e) {
				e.printStackTrace();

			}	
		}
	
	public ArrayList<Section> getUnmatchedSections(BusinessPlan a, BusinessPlan b)
	{
		ArrayList<Section> aList = a.treeToList();
		ArrayList<Section> bList = b.treeToList();
		ArrayList<Section> results = new ArrayList<Section>();
		
		for(int i = 0; i < aList.size(); i++)
		{
			System.out.println(aList.get(i).name);
			if(!bList.contains(aList.get(i)))
				results.add(aList.get(i));
		}
		
		return results;
	}
		
}
