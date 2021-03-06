package main;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.BusinessPlan;
import models.Comment;
import models.MainViewModel;
import models.MainViewTransitionModel;
import models.MyRemoteClient;
import models.MyRemoteImpl;
import models.Person;
import models.VMOSA;
import views.LoginController;
import views.MainController;

public class Main extends Application {
	
	static MyRemoteImpl server;
	static MyRemoteClient client;

	@Override
	public void start(Stage stage) throws Exception {
		try
		{		
			Registry registry = LocateRegistry.createRegistry(1099);

			server = new MyRemoteImpl();

			//initialize storedBP
			BusinessPlan BP = new VMOSA();
			BP.name="Giao";
			BP.year = 2020;
			BP.department ="CS";
			BP.isEditable=false;
			BP.addSection(BP.root);
			BP.root.content=("this is the vision");
			BP.root.comments.add(new Comment("collin", "this section is pretty cool"));
			BP.root.children.get(0).content=("this is the misson");
			BP.addSection(BP.root.children.get(0));

			BusinessPlan BP2 = new VMOSA();
			BP2.name="Hoaho";
			BP2.year = 2009;
			BP2.department ="CS";
			BP2.isEditable=true;
			BP2.addSection(BP2.root);
			
			BusinessPlan BP3 = new VMOSA();
			BP3.name="GiaoMatch";
			BP3.year = 2021;
			BP3.department ="CS";
			BP3.isEditable=false;
			BP3.addSection(BP3.root);
			BP3.root.content=("this is the vision");
			BP3.root.comments.add(new Comment("collin", "this section is pretty cool"));
			BP3.root.children.get(0).content=("this is the misson");
			BP3.addSection(BP3.root.children.get(0));
			
			BusinessPlan BP4 = new VMOSA();
			BP4.name="GiaoRootDoesNotMatch";
			BP4.year = 2022;
			BP4.department ="CS";
			BP4.isEditable=false;
			BP4.addSection(BP4.root);
			BP4.root.content=("this is the vision");
			BP4.root.children.get(0).content=("this is the misson");
			BP4.addSection(BP4.root.children.get(0));

			ArrayList <BusinessPlan> storedBP=new ArrayList<BusinessPlan>();
			storedBP.add(BP);
			storedBP.add(BP2);
			storedBP.add(BP3);
			storedBP.add(BP4);

			//initialize storedUser
			Person collin=new Person("collin","collin","CS", true);

			ArrayList <Person> storedUser=new ArrayList<Person>();
			storedUser.add(collin);

			server.setStoredBP(storedBP);
			server.setStoredUser(storedUser);

		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
		client = new MyRemoteClient(server);


		//set initial stage and view
		FXMLLoader loader0 = new FXMLLoader();
		loader0.setLocation(Main.class.getResource("../views/MainPageShell.fxml")); 

		BorderPane viewM = loader0.load();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../views/login.fxml")); 

		BorderPane view = loader.load();

		MainViewModel model = new MainViewModel(client,view);

		LoginController cont = loader.getController();
		MainController contM = loader0.getController();
		
		MainViewTransitionModel vm =new MainViewTransitionModel(viewM,model); 
		contM.setModel(vm);
		
	    cont.setModel(model);
	    cont.setParent(viewM, contM);

		Scene s = new Scene(view);
		stage.setScene(s);
		stage.setTitle("BPViewer");
		stage.show();
		
	}
	public static void main (String [] args)
	{
		launch(args); 
	}
}