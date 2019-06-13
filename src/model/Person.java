package model;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import util.LocalDateAdapter;

public class Person {

	private final StringProperty firstName;
	private final StringProperty lastName;
	private final StringProperty street;
	private final IntegerProperty postalCode;
	private final StringProperty city;
	private final ObjectProperty<LocalDate> birthday;
	private final StringProperty email;
	private final SimpleStringProperty phoneNumber;
	private RadioButton radioButtonMale;
	private RadioButton radioButtonFemale;
	private ToggleGroup gender;

	// TODO add gender

	/**
	 * Default constructor.
	 */
	public Person() {
		this(null, null);
	}

	/**
	 * Constructor with some initial data.
	 * 
	 * @param firstName
	 * @param lastName
	 */
	public Person(String firstName, String lastName) {
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);

		// Some initial dummy data, just for convenient testing.
		this.street = new SimpleStringProperty("strasse 13");
		this.postalCode = new SimpleIntegerProperty(1234);
		this.city = new SimpleStringProperty("Solothurn");
		this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
		this.email = new SimpleStringProperty("example@gmail.ch");
		this.phoneNumber = new SimpleStringProperty("076 220 19 70");

		this.gender = new ToggleGroup();
		this.radioButtonMale = new RadioButton();
		this.radioButtonFemale = new RadioButton();
		this.radioButtonMale.setToggleGroup(gender);
		this.radioButtonFemale.setToggleGroup(gender);

	}

	public Person(String firstName, String lastName, String street, Integer postalCode, String city, LocalDate birthday,
			String email, String phoneNumber, String genderValue) {
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.street = new SimpleStringProperty(street);
		this.postalCode = new SimpleIntegerProperty(postalCode);
		this.city = new SimpleStringProperty(city);
		this.birthday = new SimpleObjectProperty<LocalDate>(birthday);
		this.email = new SimpleStringProperty(email);
		this.phoneNumber = new SimpleStringProperty(phoneNumber);
		this.gender = new ToggleGroup();
		this.radioButtonMale = new RadioButton();
		this.radioButtonFemale = new RadioButton();
		this.radioButtonMale.setToggleGroup(gender);
		this.radioButtonFemale.setToggleGroup(gender);
		setSelectedRadioButton(genderValue);

	}

	public String getFirstName() {
		return firstName.get();
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public StringProperty firstNameProperty() {
		return firstName;
	}

	public String getLastName() {
		return lastName.get();
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public StringProperty lastNameProperty() {
		return lastName;
	}

	public String getStreet() {
		return street.get();
	}

	public void setStreet(String street) {
		this.street.set(street);
	}

	public StringProperty streetProperty() {
		return street;
	}

	// postalCode
	public int getPostalCode() {
		return postalCode.get();
	}

	public void setPostalCode(int postalCode) {
		this.postalCode.set(postalCode);
	}

	public IntegerProperty postalCodeProperty() {
		return postalCode;
	}

	public String getCity() {
		return city.get();
	}

	public void setCity(String city) {
		this.city.set(city);
	}

	public StringProperty cityProperty() {
		return city;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getBirthday() {
		return birthday.get();
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday.set(birthday);
	}

	public ObjectProperty<LocalDate> birthdayProperty() {
		return birthday;
	}

	// E-mail
	public String getEmail() {
		return email.get();
	}

	public void setEmail(String email) {
		this.email.set(email);
	}

	public StringProperty emailProperty() {
		return email;
	}

	// phone Number
	public String getPhoneNumber() {
		return phoneNumber.get();
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber.set(phoneNumber);
	}

	public SimpleStringProperty phoneNumberProperty() {
		return phoneNumber;
	}

	// gender Radiobutton
	public String getChosenRadioButtonAsString() {
		if (radioButtonMale.isSelected()) {
			return "Male";
		} else {
			return "Female";
		
		}
	}

	public void setSelectedRadioButton(String radioButtonText) {
		if (radioButtonText.equals("Male")) {
			radioButtonMale.setSelected(true);
		} else if (radioButtonText.equals("Female")) {
			radioButtonFemale.setSelected(true);
		}
	}

}