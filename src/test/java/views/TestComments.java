package views;
import static org.junit.jupiter.api.Assertions.fail;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.Main;
import models.BusinessPlan;
import models.Comment;
import models.MainViewModel;
import models.MainViewTransitionModel;
import models.MyRemoteClient;
import models.MyRemoteImpl;
import models.Person;
import models.Section;
import models.VMOSA;

@ExtendWith(ApplicationExtension.class)
public class TestComments
{
	static MyRemoteImpl server;
	static MyRemoteClient client;
	
	@BeforeAll
	//Initialize server and client 
	static void Initialization()
	{		
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
			BP.root.comments.add(new Comment("collin", "this section is pretty cool"));
			BP.root.content=("this is the vision");
			BP.root.children.get(0).content=("this is the misson");
			BP.addSection(BP.root.children.get(0));
			

			BusinessPlan BP2 = new VMOSA();
			BP2.name="Hoaho";
			BP2.year = 2009;
			BP2.department ="CS";
			BP2.isEditable=true;
			BP2.addSection(BP2.root);

			ArrayList <BusinessPlan> storedBP=new ArrayList<BusinessPlan>();
			storedBP.add(BP);
			storedBP.add(BP2);

			//initialize storedUser
			Person collin = new Person("collin","collin","CS", true);

			ArrayList <Person> storedUser=new ArrayList<Person>();
			storedUser.add(collin);
			
			server.setStoredBP(storedBP);
			server.setStoredUser(storedUser);
			
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
		client = new MyRemoteClient(server);
	}
	
	
	
	@Start //Before
	private void start(Stage stage)
	{
		
		try {

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
			stage.show();
			
		}catch(Exception e) {
			e.printStackTrace();	//print fail
			fail("Fail");
		}
	}
	
	//general input text method
	private void enterText(FxRobot robot, String text, String target)
	{
		robot.clickOn(target);
		robot.write(text);
	}

	private void login(FxRobot robot, String username, String password)
	{
		enterText(robot, username, "#usernameInput");
		enterText(robot, password, "#passwordInput");
		robot.clickOn("#login");
	}

	@Test
	public void testAll(FxRobot robot) {
		try {
			Thread.sleep(1000);
			
			login(robot,"collin","collin");
			
			robot.clickOn("#BPlist");
			
			//Select the first BP and click the copy button
			ListView<BusinessPlan> bpList = (ListView<BusinessPlan>)
				robot.lookup("#BPListView").queryAs(ListView.class);
			bpList.getSelectionModel().clearAndSelect(0);
			robot.clickOn("#copyOnlist");
			
			//Select the root node of the tree and click the view comments button
			TreeView<Section> tree = (TreeView<Section>)
					robot.lookup("#outlineTree").queryAs(TreeView.class);
			tree.getSelectionModel().clearAndSelect(0);
			robot.clickOn("#ViewComment");
			
			//Select the first comment and click the remove comment button
			ListView<String> commentList = (ListView<String>)
					robot.lookup("#CommentList").queryAs(ListView.class);
			Thread.sleep(1500);
			commentList.getSelectionModel().clearAndSelect(0);
			robot.clickOn("#RemoveComment");
			
			//Do it again. (Nothing should happen because the list is empty.)
			commentList.getSelectionModel().clearAndSelect(0);
			robot.clickOn("#RemoveComment");
			
			//leave the page
			robot.clickOn("#CommentMainPage");
			robot.clickOn("#leaveYes");
			
			//Bring up the add comment page
			robot.clickOn("#BPlist");
			bpList = (ListView<BusinessPlan>)
					robot.lookup("#BPListView").queryAs(ListView.class);
			bpList.getSelectionModel().clearAndSelect(0);
			robot.clickOn("#copyOnlist");
				
			tree = (TreeView<Section>)
						robot.lookup("#outlineTree").queryAs(TreeView.class);
			tree.getSelectionModel().clearAndSelect(0);
			robot.clickOn("#AddComment");
			
			//Write and submit a comment
			enterText(robot, "This is another comment", "#commentText");
			robot.clickOn("#submitComment");
			robot.clickOn("#leaveYes");

			//Navigate back to the view comments page.
			//The old comment is gone, and the new comment is visible.
			robot.clickOn("#BPlist");
			bpList = (ListView<BusinessPlan>)
					robot.lookup("#BPListView").queryAs(ListView.class);
			bpList.getSelectionModel().clearAndSelect(0);
			robot.clickOn("#copyOnlist");
				
			tree = (TreeView<Section>)
						robot.lookup("#outlineTree").queryAs(TreeView.class);
			tree.getSelectionModel().clearAndSelect(0);
			robot.clickOn("#ViewComment");
			
			Thread.sleep(2500);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
