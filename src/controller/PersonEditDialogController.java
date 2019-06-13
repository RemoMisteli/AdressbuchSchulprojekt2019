package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Person;
import util.DateUtil;

/**
 * Dialog to edit details of a person.
 * 
 * @author Marco Jakob
 */
public class PersonEditDialogController {

	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField streetField;
	@FXML
	private TextField postalCodeField;
	@FXML
	private TextField cityField;
	@FXML
	private TextField birthdayField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField phoneNumberField;
	@FXML
	private RadioButton radioButtonMale;
	@FXML
	private RadioButton radioButtonFemale;
	@FXML
	private ToggleGroup toggleGroupGender;

	private Stage dialogStage;
	private Person person;
	private boolean okClicked = false;

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
	}

	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the person to be edited in the dialog.
	 * 
	 * @param person
	 */
	public void setPerson(Person person) {
		this.person = person;

		firstNameField.setText(person.getFirstName());
		lastNameField.setText(person.getLastName());
		streetField.setText(person.getStreet());
		postalCodeField.setText(Integer.toString(person.getPostalCode()));
		cityField.setText(person.getCity());

		birthdayField.setText(DateUtil.format(person.getBirthday()));
		birthdayField.setPromptText("dd.mm.yyyy");
		emailField.setText(person.getEmail());
		phoneNumberField.setText(person.getPhoneNumber());
		

        if(person.getChosenRadioButtonAsString().equals("Male")) {
       	   toggleGroupGender.selectToggle(radioButtonMale);
       	 
           }else if(person.getChosenRadioButtonAsString().equals("Female")) {
        	   toggleGroupGender.selectToggle(radioButtonFemale);
           };

	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			person.setFirstName(firstNameField.getText());
			person.setLastName(lastNameField.getText());
			person.setStreet(streetField.getText());
			person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
			person.setCity(cityField.getText());
			person.setBirthday(DateUtil.parse(birthdayField.getText()));
			person.setEmail(emailField.getText());
			person.setPhoneNumber(phoneNumberField.getText());

			RadioButton selectedGender = (RadioButton) toggleGroupGender.getSelectedToggle();
			person.setSelectedRadioButton(selectedGender.getId());
			
			okClicked = true;
			dialogStage.close();
		}

	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
			errorMessage += "Gib einen Vornamen ein!\n";
		}
		if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
			errorMessage += "Gib einen Nachnamen ein!\n";
		}
		if (streetField.getText() == null || streetField.getText().length() == 0) {
			errorMessage += "Gieb die Strasse an!\n";
		}

		if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
			errorMessage += "Gib eine PLZ ein!\n";
		} else {
			// try to parse the postal code into an int.
			try {
				Integer.parseInt(postalCodeField.getText());
			} catch (NumberFormatException e) {
				errorMessage += "PLZ nicht korrekt, nur Ganze Zahlen!\n";
			}
		}

		if (cityField.getText() == null || cityField.getText().length() == 0) {
			errorMessage += "Gib einen Ort ein!\n";
		}

		if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
			errorMessage += "Gib den Geburtstag an!\n";
		} else {
			if (!DateUtil.validDate(birthdayField.getText())) {
				errorMessage += "Format ist nicht korrekt!(dd.mm.yyyy)\n";
			} // email validiren
		}
			if (emailField.getText() == null || emailField.getText().length() == 0) {
				errorMessage += "Gib eine E-mail an!\n";
			} else {
				// e-mail format
				String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

				if (emailField.getText().matches(regex) == false) {
					errorMessage += "E-mail Format ist nicht korrekt!\n";
				}
			}
			// Validate phoneNumber
			if (phoneNumberField.getText() == null || phoneNumberField.getText().length() == 0) {
				errorMessage += "Gib eine Telefon Nummer an!\n";
			} else {
				// try to parse the phone Number in a long
				try {
					Long.parseLong(phoneNumberField.getText().replaceAll("\\s+", ""));
				} catch (NumberFormatException e) {
					errorMessage += "Telefonnummer nicht korrekt!\n";
				}

			}
			
			//Validate is a Gender selected
			RadioButton selectedGender = (RadioButton) toggleGroupGender.getSelectedToggle();
			if (selectedGender == null) {
				errorMessage += "Geben SIe ein Geschlecht an!\n";
			}
		

	if(errorMessage.length()==0)

	{
		return true;
	}else
	{
		// Show the error message.
		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(dialogStage);
		alert.setTitle("Falsche Eingabe");
		alert.setHeaderText("Bitte korrigieren Sie die ungültigen Felder");
		alert.setContentText(errorMessage);

		alert.showAndWait();

		return false;
	}
}}