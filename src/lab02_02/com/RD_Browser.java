package lab02_02.com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class RD_Browser extends Application {

	List<String> record = new LinkedList<>(); 
	List<String> bookmark = new LinkedList<>(); 
	TextField AddressBox ;
	Label Url ;
	
	
	Button btn_Refresh;
	Button btn_Home;
	Button btn_Open;
	//book mark icons property
	
	WebView WV;
	ChoiceBox<String> BM = new ChoiceBox<String>();



	private void keepRecord(){
		int  check = 0;
		String address = WV.getEngine().getLocation();
		if (AddressBox.getText() != ""){
			for(int i =0; i<record.size(); i++){
				if(record.get(i) == address){
					check = 1;
				}
				
			}
			
		}
		
		if(check == 0){
			record.add(address);
		}
	}
	
	

    private void writeBookMarks(){
        File record = new File("BookMarks.CSV");
        try (BufferedWriter pWriter = new BufferedWriter(new FileWriter(record,true))) {
        	for (int i =0; i< bookmark.size(); i++){
        		String n = bookmark.get(i) + "\n";
        		pWriter.write(n);
        	}
            
            pWriter.close();
        }catch (FileNotFoundException e) {
           System.out.println("File not Found");
        } catch (IOException e) {
            System.out.println("ERROR OCCOUR WHILE FILE IS BEING WRITTING");
        }
        System.out.println("FILE HAS BEEN WRITTEN SUCCESSFULY");
    }

	private void addBookMark(String Address){
		int c =0;
		for(int i =0; i<bookmark.size(); i++){
			if(bookmark.get(i) == Address){
				c =1;
			}
		}
		
		if (c == 0){
			bookmark.add(Address);
        	BM.getItems().add(Address);
		}
	}

    private void readBookMarks(){
        try 
        {
            String line;
            File record = new File("BookMarks.CSV");
            BufferedReader BR = new BufferedReader(new FileReader(record));
                                                                                                            
            while ((line = BR.readLine()) != null) {

                addBookMark(line);
               

            }
             BR.close();
             record.delete();
                

        }
        catch (IOException | NumberFormatException e) {
           
        } // end of Catch
        
    }
    
	
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Image img1 = new Image("star1.png");
		Image img2 = new Image("star2.png");

	    ImageView i2 = new ImageView(img2);
		Label pic_l = new Label("", i2);
		//back icon property
		Image back1 = new Image(("back1.png"));
		Image back2 = new Image(("back2.png"));
	  
	    ImageView b2 = new ImageView(back2);
		Label back_l = new Label("", b2);
		//for word icon property
		Image forward1 = new Image(("Forward1.png"));
		Image forward2 = new Image(("Forward2.png"));
	 
	    ImageView f2 = new ImageView(forward2);
		Label for_l = new Label("", f2);
	
		HBox  HB = new HBox();
		 WV = new WebView();
		BorderPane Root = new BorderPane();
		Button btn_go = new Button("GO");
		btn_Refresh = new Button("Refresh");
		btn_Home = new Button("Home");
		btn_Open= new Button("Open");
		Url = new Label("  URL ");
		Url.setFont(Font.font("Times New Roman", FontWeight.BOLD, 16));
		
		
		AddressBox = new TextField();
		
		HB.getChildren().addAll(back_l,for_l, Url, AddressBox,pic_l);

	
	
		HB.getChildren().add(btn_go);
		HB.getChildren().add(btn_Refresh);
		HB.getChildren().add(btn_Home);
		HB.getChildren().add(btn_Open);
		HB.getChildren().add(BM);
		HBox.setHgrow(AddressBox, Priority.ALWAYS);
		
		Root.setTop(HB);
		Root.setCenter(WV);
		WV.getEngine().load("http://www.unitec.ac.nz");
		
		Scene scene = new Scene(Root);
	
		primaryStage.setTitle("RD Browser");
		primaryStage.setScene(scene);
		primaryStage.show();
		this.readBookMarks();
		//icons
		
		//book mark button event
		pic_l.setOnMouseEntered(new EventHandler<MouseEvent>(){
			 public void handle(MouseEvent e) {
			        pic_l.setScaleX(1.5);
			        pic_l.setScaleY(1.5);
			        i2.setImage(img1);
			      }
		});
		
		 pic_l.setOnMouseExited(new EventHandler<MouseEvent>() {
		      @Override
		      public void handle(MouseEvent e) {
		        pic_l.setScaleX(1);
		        pic_l.setScaleY(1);
		        i2.setImage(img2);
		    
		      }
		    });
		 
		//back icon events
		 back_l.setOnMouseEntered(new EventHandler<MouseEvent>(){
			 public void handle(MouseEvent e) {
			        b2.setImage(back1);
			      }
		});
		
		 back_l.setOnMouseExited(new EventHandler<MouseEvent>() {
		      @Override
		      public void handle(MouseEvent e) {
		        b2.setImage(back2);
		    
		      }
		    });
		 
		//forward icon events
		 for_l.setOnMouseEntered(new EventHandler<MouseEvent>(){
			 public void handle(MouseEvent e) {
			        f2.setImage(forward1);
			      }
		});
		
		 for_l.setOnMouseExited(new EventHandler<MouseEvent>() {
		      @Override
		      public void handle(MouseEvent e) {
		        f2.setImage(forward2);
		    
		      }
		    });
		 
		 
		 //address book enter key event
		 AddressBox.setOnKeyPressed(new EventHandler<KeyEvent>(){
			 @Override
			 public void handle(KeyEvent keyEvent){
				 if(keyEvent.getCode() == KeyCode.ENTER){
					 WV.getEngine().load(AddressBox.getText().toString());
				 }
				 
			 }
			 
		 });
		 
		 
		 
		 back_l.setOnMouseClicked(new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent mouseEvent) {
			        if (record.size() !=0){
			        	int position = record.indexOf(WV.getEngine().getLocation());
			        	if (position > 0){
			        		position--;
				        	WV.getEngine().load(record.get(position));
			        	}
			        }
			    }
			});
		 //mouse click event for forward icon
		 for_l.setOnMouseClicked(new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent mouseEvent) {
			        if (record.size() !=0){
			        	int position = record.indexOf(WV.getEngine().getLocation());
			        	if (position != record.size()){
			        		position++;
				        	WV.getEngine().load(record.get(position));
			        	}
			        }
			    }
			});
		
		//book mark icon event
		pic_l.setOnMouseClicked(new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent mouseEvent) {
			        String address = WV.getEngine().getLocation();
			        if (address != ""){
			        	addBookMark(address);
			        
			        }
			    }
			});
		
		//button click
		 btn_go.setOnAction(new EventHandler<ActionEvent>(){
			 public void handle(ActionEvent e){
				 WV.getEngine().load(AddressBox.getText().toString());
				 AddressBox.setText(WV.getEngine().getLocation());
			 }
		 });
		 
//button click event for btn_go
		 
		 btn_Home.setOnAction(new EventHandler<ActionEvent>(){
			 public void handle(ActionEvent e){
				 WV.getEngine().load("https://www.google.co.nz/?gfe_rd=cr&ei=-OODWcXRGKXr8AeFk4GYAQ&gws");
				 AddressBox.setText(WV.getEngine().getLocation());
			 }
		 });
		 
//refresh button events
		 btn_Refresh.setOnAction(new EventHandler<ActionEvent>(){
			 public void handle(ActionEvent e){
				 AddressBox.setText(WV.getEngine().getLocation());
				 WV.getEngine().load(AddressBox.getText().toString());
				 System.out.println("list");
				 for(int i =0; i< record.size();i++){
					 System.out.println(record.get(i));
				 }
				
			 }
		 });

		 btn_Open.setOnAction(new EventHandler<ActionEvent>(){
			 public void handle(ActionEvent e){
				 
				 FileChooser fileChooser = new FileChooser();

				    // Set extension filter
				   

				    // Show save file dialog
				    File file = fileChooser.showOpenDialog(primaryStage);
				    String path;
					try {
						path = file.toURI().toURL().toString();
						   WV.getEngine().load(path);
						   keepRecord();

					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch(NullPointerException e2){
						WV.getEngine().load("https://www.google.co.nz/?gfe_rd=cr&ei=-OODWcXRGKXr8AeFk4GYAQ&gws");
						 AddressBox.setText(WV.getEngine().getLocation());
					}
				  
				
			 }
		 });
		//chooSe book click event
		
		BM.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				WV.getEngine().load(bookmark.get(newValue.intValue()));
				System.out.println(bookmark.get(newValue.intValue()));
			}
			
		});
		
		 WV.getEngine().getLoadWorker().stateProperty().addListener(
			        new ChangeListener<State>() {
			            public void changed(ObservableValue ov, State oldState, State newState) {
			                if (newState == State.SUCCEEDED) {
			                AddressBox.setText(WV.getEngine().getLocation());
			                   keepRecord();
			                    
			                }
			            }
			        }); 

		 primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		      public void handle(WindowEvent we) {
		         writeBookMarks();
		      }
		  }); 
		
		
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}


	

}
