package controller;

import java.awt.Button;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.text.View;

import application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.Person;
import util.DateUtil;

public class PersonOverviewController {
	
	
	String pattern = "dd-MM-yyyy";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	  @FXML
	    private TableView<Person> personTable;
	    @FXML
	    private TableColumn<Person, String> firstNameColumn;
	    @FXML
	    private TableColumn<Person, String> lastNameColumn;
	    @FXML
	    private TableColumn<Person, LocalDate> birthdayColumn;
 
	    @FXML
	    private Label firstNameLabel;
	    @FXML
	    private Label lastNameLabel;
	    @FXML
	    private Label streetLabel;
	    @FXML
	    private Label postalCodeLabel;
	    @FXML
	    private Label cityLabel;
	    @FXML
	    private Label birthdayLabel;
	    @FXML
	    private Label email;
	    @FXML
	    private Label phoneNumber;
	    @FXML
	    private RadioButton radioButtonMale;
	    @FXML
	    private RadioButton radioButtonFemale;
	    @FXML
	    private ToggleGroup gender;
	    @FXML
	    private GridPane personDetails;
	  
	    
	    

	    // Reference to the main application.
	    private MainApp mainApp;
	    DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	    /**
	     * The constructor.
	     * The constructor is called before the initialize() method.
	     */
	    public PersonOverviewController() {
	    }

	    /**
	     * Initializes the controller class. This method is automatically called
	     * after the fxml file has been loaded.
	     */
	    @FXML
	    private void initialize() {
	        // Initialize the person table with the two columns.
	        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
	        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
	      
	        birthdayColumn.setCellValueFactory(cellData ->cellData.getValue().birthdayProperty());
	        
	        birthdayColumn.setCellFactory(column -> {
	             return new TableCell<Person, LocalDate>() {
	                protected void updateItem(LocalDate item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if(item == null) {
	                    	setText("");
	                    }else {
	                    	setText(myDateFormatter.format(item));//format the Date in the right format
	                    }

	                }
	            };
	        });
	        
	        
	        // Clear person details.
	        showPersonDetails(null);

	        // Listen for selection changes and show the person details when changed.
	        personTable.getSelectionModel().selectedItemProperty().addListener(
	                (observable, oldValue, person) -> showPersonDetails(person));
	        
	       
	    }
	    

	    /**
	     * Is called by the main application to give a reference back to itself.
	     * 
	     * @param mainApp
	     */
	    public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;

	        // Add observable list data to the table
	        personTable.setItems(mainApp.getPersonData());
	    }
	    
	    /**
	     * Fills all text fields to show details about the person.
	     * If the specified person is null, all text fields are cleared.
	     * 
	     * @param person the person or null
	     */
	    private void showPersonDetails(Person person) {
	        if (person != null) {
	        	personDetails.setVisible(true);
	        	radioButtonFemale.setVisible(true);
	            radioButtonMale.setVisible(true);
	            // Fill the labels with info from the person object.
	            firstNameLabel.setText(person.getFirstName());
	            lastNameLabel.setText(person.getLastName());
	            streetLabel.setText(person.getStreet());
	            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
	            cityLabel.setText(person.getCity());
	            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
	            email.setText(person.getEmail());
                phoneNumber.setText(person.getPhoneNumber().replaceFirst("(\\d{3})(\\d{3})(\\d{2})(\\d{2})(\\d+)", "$1 $2 $3 $4 $5"));//ad the space between the Numbers

             if(person.getChosenRadioButtonAsString().equals("Male")) {
	        	   gender.selectToggle(radioButtonMale);
	        	 
	            }else if(person.getChosenRadioButtonAsString().equals("Female")) {
	            	 gender.selectToggle(radioButtonFemale);
	            };
	            
	        } else {
	            // Person is null, remove all the text.
	            firstNameLabel.setText("");
	            lastNameLabel.setText("");
	            streetLabel.setText("");
	            postalCodeLabel.setText("");
	            cityLabel.setText("");
	            birthdayLabel.setText("");
	            phoneNumber.setText("");
	            email.setText("");
	            radioButtonFemale.setVisible(false);
	            radioButtonMale.setVisible(false);
	            personDetails.setVisible(false);
	          
	        }
	    }
	    
	    /**
	     * Called when the user clicks on the delete button.
	     */
	    @FXML
	    private void handleDeletePerson() {
	        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
	        if (selectedIndex >= 0) {
	            personTable.getItems().remove(selectedIndex);
	        } else {
	            // Nothing selected.
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.initOwner(mainApp.getPrimaryStage());
	            alert.setTitle("Kontakt löschen");
	            alert.setHeaderText("Kein Kontakt ausgewält");
	            alert.setContentText("Bitte wälen Sie einen Kontakt aus.");
	            
	            alert.showAndWait();
	        }
	    }
	    
	    /**
	     * Called when the user clicks the new button. Opens a dialog to edit
	     * details for a new person.
	     */
	    @FXML
	    private void handleNewPerson() {
	        Person tempPerson = new Person();
	        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
	        if (okClicked) {
	            mainApp.getPersonData().add(tempPerson);
	        }
	    }

	    /**
	     * Called when the user clicks the edit button. Opens a dialog to edit
	     * details for the selected person.
	     */
	    @FXML
	    private void handleEditPerson() {
	        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
	        if (selectedPerson != null) {
	            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
	            if (okClicked) {
	                showPersonDetails(selectedPerson);
	            }

	        } else {
	            // Nothing selected.
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.initOwner(mainApp.getPrimaryStage());
	            alert.setTitle("Kontakt Editieren");
	            alert.setHeaderText("Kein Kontakt ausgewählt");
	            alert.setContentText("Bitte wähle eine Kontakt aus");
	            
	            alert.showAndWait();
	        }
	    }
	  
	}