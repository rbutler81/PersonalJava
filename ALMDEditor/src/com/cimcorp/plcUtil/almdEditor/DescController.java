package com.cimcorp.plcUtil.almdEditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DescController {

	private Controller mainController;
	
	@FXML private TextArea txtLangDesc;
	@FXML private TextField txtNewLang;
	@FXML private TextField txtFixedWidth;
	@FXML private TextField txtNumOfDig;
	@FXML private TextField txtDecPlaces;
	
	@FXML private Button btnOK;
	@FXML private Button btnAddDesc;
	
	@FXML private ComboBox<String> comboSel;
	@FXML private ComboBox<String> comboLeftFill;
	
	@FXML private Label lblNumOfDigits;
	@FXML private Label lblDecPlaces;
	@FXML private Label lblCharacters;
	@FXML private Label lblLeftFill;
	
	@FXML private CheckBox chkFixedWidth;
	@FXML private CheckBox chkBool;
	@FXML private CheckBox chkString;
	@FXML private CheckBox chkReal;
	
	@FXML private ListView<String> lstLang;
	private Map<String, String> langDescMap;
	private ObservableList<String> langOList;
	
	private Stage thisStage;

	public Controller getMainController() {
		return mainController;
	}

	public void setMainController(Controller mainController) {
		this.mainController = mainController;
	}

	public Stage getStage() {
		return thisStage;
	}

	public void setStage(Stage thisStage) {
		this.thisStage = thisStage;
	}

	public ObservableList<String> getLangOList() {
		return langOList;
	}

	public void setLangOList(ObservableList<String> langOList) {
		this.langOList = langOList;
	}

	public ListView<String> getLstLang() {
		return lstLang;
	}

	public void setLstLang(ListView<String> lstLang) {
		this.lstLang = lstLang;
	}
	
	public Map<String, String> getLangDescMap() {
		return langDescMap;
	}

	public void setLangDescMap(Map<String, String> langDescMap) {
		this.langDescMap = langDescMap;
	}

	@FXML
    public void initialize() {
		langDescMap = new HashMap<String, String>();
		
		List<CheckBox> chkList = new ArrayList<CheckBox>();
		chkList.add(chkBool);
		chkList.add(chkString);
		chkList.add(chkReal);
		multiChkOneSelected(chkList);
		chkBool.setSelected(true);
		for (CheckBox e : chkList) {e.setVisible(false);}
		
		txtLangDesc.textProperty().addListener((observable, oldVal, newVal) -> {
			langDescMap.put(lstLang.getSelectionModel().getSelectedItem(), newVal);
			});
		
		lstLang.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
			txtLangDesc.setText(langDescMap.get(newVal));
		});
		
		ObservableList<String> comboItems = FXCollections.observableArrayList("Alarm Name", "Condition Name", "Input Value", "Limit Value"
				, "Severity", "Tag 1", "Tag 2", "Tag 3", "Tag 4" );
		comboSel.setItems(comboItems);
		
		comboSel.valueProperty().addListener((observable, oldVal, newVal) -> {
			for (CheckBox e : chkList) {e.setVisible(false);}
			if (newVal.equals("Alarm Name") || newVal.equals("Condition Name")){
				groupOneVis();
			}
			else {
				groupTwoVis();
				if (newVal.equals("Severity")) {txtDecPlaces.setDisable(true); lblDecPlaces.setDisable(true);}
				else if (newVal.contains("Tag")) { 
					for (CheckBox e : chkList) {e.setVisible(true);}
					if (chkFixedWidth.isSelected())	{txtFixedWidth.setDisable(false); lblCharacters.setDisable(false);}
					else {txtFixedWidth.setDisable(true); lblCharacters.setDisable(true);}
					if (chkBool.isSelected()) boolSelected();
					else if (chkString.isSelected()) stringSelected();
					else if (chkReal.isSelected()) realSelected();
				}
				else {txtDecPlaces.setDisable(false); lblDecPlaces.setDisable(false);}
			}
		});
		
		comboSel.getSelectionModel().select(5);
		
		ObservableList<String> comboLF = FXCollections.observableArrayList("None", "Spaces", "Zero");
		comboLeftFill.setItems(comboLF);
		comboLeftFill.getSelectionModel().select(0);
		
		chkFixedWidth.selectedProperty().addListener((observable, oldVal, newVal) -> {
			if (newVal) {txtFixedWidth.setDisable(false); lblCharacters.setDisable(false);}
			else {txtFixedWidth.setDisable(true); lblCharacters.setDisable(true);}
		});
		txtFixedWidth.setDisable(true);
		txtFixedWidth.setText("12");
		lblCharacters.setDisable(true);
		txtNumOfDig.setText("8");
		txtDecPlaces.setText("3");
		
		textFieldOnlyNum(txtFixedWidth, 3);
		textFieldOnlyNum(txtNumOfDig, 2);
		textFieldOnlyNum(txtDecPlaces, 1);
		
	}
	
	 @FXML
	 void onAddBtn(ActionEvent event) {
		if (!txtNewLang.getText().equals("") && !langDescMap.containsKey(txtNewLang.getText())){	 
	 		langOList.add(txtNewLang.getText());
	 		langDescMap.put(txtNewLang.getText(), "");
	 		lstLang.getSelectionModel().select(txtNewLang.getText());
	 		txtNewLang.setText("");
		}
		else txtNewLang.setText("");
	}
	 
	 @FXML
	 void onRemoveBtn(ActionEvent event) {
		 if (langOList != null){
			 if (langOList.size() > 0){
				 langDescMap.remove(langOList.get(lstLang.getSelectionModel().getSelectedIndex()));
				 langOList.remove(lstLang.getSelectionModel().getSelectedIndex());
			 }
		 }
	 }
	 
	 @FXML
	 void onCancelBtn(ActionEvent event) {
		 thisStage.close();
	 }
	 
	 @FXML
	 void onOKBtn(ActionEvent event) {
		 if (langDescMap.containsKey("en-US")) mainController.getTxtDescription().setText(langDescMap.get("en-US"));
		 else mainController.getTxtDescription().setText("");
		 if (langOList.size() == 0){
			 langDescMap.clear();
			 langOList.clear();
		 }
		 thisStage.close();
	 }
	 
	 @FXML
	 void onAddDescBtn(ActionEvent event) {
		if (langOList.size() > 0) {	 
		 	if (comboSel.getSelectionModel().getSelectedItem().equals("Alarm Name") 
					 || comboSel.getSelectionModel().getSelectedItem().equals("Condition Name")) 
				 txtLangDesc.setText(txtLangDesc.getText() + fixedWidth(comboSel.getSelectionModel().getSelectedItem()));
			 else if (comboSel.getSelectionModel().getSelectedItem().equals("Input Value") 
					 || comboSel.getSelectionModel().getSelectedItem().equals("Limit Value"))
				 txtLangDesc.setText(txtLangDesc.getText() + digitsWithDecimals(comboSel.getSelectionModel().getSelectedItem()));
			 else if (comboSel.getSelectionModel().getSelectedItem().equals("Severity"))
				 txtLangDesc.setText(txtLangDesc.getText() + digitsNoDecimals(comboSel.getSelectionModel().getSelectedItem()));
			 else {
				 if (chkBool.isSelected()) txtLangDesc.setText(txtLangDesc.getText() + digitsNoDecimals(comboSel.getSelectionModel().getSelectedItem()));
				 else if (chkString.isSelected()) txtLangDesc.setText(txtLangDesc.getText() + fixedWidth(comboSel.getSelectionModel().getSelectedItem()));
				 else txtLangDesc.setText(txtLangDesc.getText() + digitsWithDecimals(comboSel.getSelectionModel().getSelectedItem()));
			 }
		}
	 }
	 
	 @FXML
	 void onChkBool(ActionEvent event) {boolSelected();}
	 
	 @FXML
	 void onChkString(ActionEvent event) {stringSelected();}
	 
	 @FXML
	 void onChkReal(ActionEvent event) {realSelected();}
	 
	 private void groupOneVis(){
		chkFixedWidth.setDisable(false);
		if (chkFixedWidth.isSelected()) {txtFixedWidth.setDisable(false); lblCharacters.setDisable(false);}
		else  {txtFixedWidth.setDisable(true); lblCharacters.setDisable(true);}
		lblNumOfDigits.setDisable(true);
		txtNumOfDig.setDisable(true);
		lblDecPlaces.setDisable(true);
		txtDecPlaces.setDisable(true);
		lblLeftFill.setDisable(true);
		comboLeftFill.setDisable(true);
	 }
	 
	 private void groupTwoVis(){
		txtFixedWidth.setDisable(true);
		if (comboSel.getSelectionModel().getSelectedItem().contains("Tag")) chkFixedWidth.setDisable(false);
		else chkFixedWidth.setDisable(true);
		lblCharacters.setDisable(true);
		lblNumOfDigits.setDisable(false);
		txtNumOfDig.setDisable(false);
		lblDecPlaces.setDisable(false);
		txtDecPlaces.setDisable(false);
		lblLeftFill.setDisable(false);
		comboLeftFill.setDisable(false);
	}
	 
	private String fixedWidth(String sel){
		sel = sel.replaceAll("\\s+","");
		if (!chkFixedWidth.isSelected()) return "/*S:0 %" + sel + "*/"; 
		else {
			int x;
			String a = txtFixedWidth.getText();
			if (a.equals("")) a = "0";
			x = Integer.parseInt(a);
			if (x > 0 && x <= 255){
				return "/*S:" + x + " %" + sel + "*/";
			}
			else return "";
		}
	}
	
	private String digitsWithDecimals(String sel){
		sel = sel.replaceAll("\\s+","");
		String numDigits = txtNumOfDig.getText();
		String decPlaces = txtDecPlaces.getText();
		if (numDigits.equals("")) numDigits = "0";
		if (decPlaces.equals("")) decPlaces = "0";
		int num = Integer.parseInt(numDigits);
		int dec = Integer.parseInt(decPlaces);
		if (dec < 8 && num >= 5 && num <= 25 && dec <= (num - 2)){
			return "/*N:" + num + " %" + sel + " " + leftFill() + " DP:" + dec + "*/";
		}
		else return "";
	}
	
	private String digitsNoDecimals(String sel){
		sel = sel.replaceAll("\\s+","");
		String numDigits = txtNumOfDig.getText();
		if (numDigits.equals("")) numDigits = "0";
		int num = Integer.parseInt(numDigits);
		if (num >= 1 && num <= 25){
			return "/*N:" + num + " %" + sel + " " + leftFill() + " DP:0*/";
		}
		else return "";
	}
	
	private String leftFill(){
		if (comboLeftFill.getSelectionModel().getSelectedItem().equals("None")) return "NOFILL";
		else if (comboLeftFill.getSelectionModel().getSelectedItem().equals("Spaces")) return "SPACEFILL";
		else return "ZEROFILL";
	}
	 
	private static void textFieldOnlyNum(TextField textField, int max){
			textField.textProperty().addListener((obs, oldVal, newVal) -> {
				if (newVal.length() > oldVal.length()){
					boolean number = false;
					for (String e : numbers){
						if (e.equals(newVal.substring(newVal.length() - 1))) number = true;
					}
				if (newVal.length() > max || !number) textField.setText(oldVal);
				}
			});
	}
	
	private static void multiChkOneSelected(List<CheckBox> chks){
		for (int x = 0; x < chks.size(); x++){
			final int xx = x;
			CheckBox w = chks.get(x);
			w.selectedProperty().addListener((obs, oldVal, newVal) -> {
				if (newVal){
					for (int i = 0; i < chks.size(); i++){
						if (i != xx){
							chks.get(i).setSelected(false);
						}
					}
				}
				else {
					boolean allOff = true;
					for (int i = 0; i < chks.size(); i++){
						if (chks.get(i).isSelected()) allOff = false;
					}
					if (allOff) chks.get(xx).setSelected(true);
				}
			});
		}
	}
		
	private void boolSelected() {
		txtFixedWidth.setDisable(true);
		chkFixedWidth.setDisable(true);
		lblCharacters.setDisable(true);
		lblNumOfDigits.setDisable(false);
		txtNumOfDig.setDisable(false);
		lblDecPlaces.setDisable(true);
		txtDecPlaces.setDisable(true);
		lblLeftFill.setDisable(false);
		comboLeftFill.setDisable(false);
	}
	
	private void stringSelected() {
		txtFixedWidth.setDisable(false);
		chkFixedWidth.setDisable(false);
		lblCharacters.setDisable(false);
		lblNumOfDigits.setDisable(true);
		txtNumOfDig.setDisable(true);
		lblDecPlaces.setDisable(true);
		txtDecPlaces.setDisable(true);
		lblLeftFill.setDisable(true);
		comboLeftFill.setDisable(true);
	}
	
	private void realSelected(){
		txtFixedWidth.setDisable(true);
		chkFixedWidth.setDisable(true);
		lblCharacters.setDisable(true);
		lblNumOfDigits.setDisable(false);
		txtNumOfDig.setDisable(false);
		lblDecPlaces.setDisable(false);
		txtDecPlaces.setDisable(false);
		lblLeftFill.setDisable(false);
		comboLeftFill.setDisable(false);
	}
	
	private static final String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	
}
