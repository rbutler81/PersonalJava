package com.cimcorp.plcUtil.almdEditor;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cimcorp.plcUtil.almdEditor.csv.CSVUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller {

	@FXML
	private Button btnExport;
	@FXML
	private Button btnImport;
	@FXML
	private Button btnAlarmsImport;
	@FXML
	private Button btnApplyEdit;
	@FXML
	private Button btnCancelEdit;
	@FXML
	private Button btnAdd;
	@FXML
	private Button btnRemove;
	@FXML
	private Button btnDuplicate;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnDescDetails;
	@FXML
	private Button btnDisplayCode;

	@FXML
	private TableView<ALMD> tableView;
	@FXML
	private TableColumn<ALMD, String> tblColName;
	@FXML
	private TableColumn<ALMD, String> tblColScope;
	@FXML
	private TableColumn<ALMD, String> tblColDescription;
	@FXML
	private TableColumn<ALMD, String> tblColOnDelay;
	@FXML
	private TableColumn<ALMD, Boolean> tblColLatched;
	@FXML
	private TableColumn<ALMD, Boolean> tblColAckReq;
	@FXML
	private TableColumn<ALMD, String> tblColClass;
	@FXML
	private TableColumn<ALMD, String> tblColLanguage;

	private List<TextField> txtFieldList;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtScope;
	@FXML
	private TextField txtDescription;
	@FXML
	private TextField txtClass;
	@FXML
	private TextField txtSeverity;
	@FXML
	private TextField txtOnDelay;
	@FXML
	private TextField txtShelveDuration;
	@FXML
	private TextField txtMaxShelveDuration;
	@FXML
	private TextField txtAssoc1;
	@FXML
	private TextField txtAssoc2;
	@FXML
	private TextField txtAssoc3;
	@FXML
	private TextField txtAssoc4;
	@FXML
	private TextField txtDupContains;
	@FXML
	private TextField txtDupWith;
	@FXML
	private TextField txtAsset;
	@FXML
	private TextField txtResetBit;
	@FXML
	private TextField txtAlarmBit;

	@FXML
	private ComboBox<String> comboType;

	private List<Label> lblList;
	@FXML
	private Label lblName;
	@FXML
	private Label lblScope;
	@FXML
	private Label lblDescription;
	@FXML
	private Label lblClass;
	@FXML
	private Label lblSeverity;
	@FXML
	private Label lblOnDelay;
	@FXML
	private Label lblShelveDuration;
	@FXML
	private Label lblMaxShelveDuration;
	@FXML
	private Label lblAssoc1;
	@FXML
	private Label lblAssoc2;
	@FXML
	private Label lblAssoc3;
	@FXML
	private Label lblAssoc4;
	@FXML
	private Label lblAsset;
	@FXML
	private Label lblResetBit;
	@FXML
	private Label lblType;
	@FXML
	private Label lblAlarmBit;

	private List<CheckBox> chkList;
	@FXML
	private CheckBox chkLatched;
	@FXML
	private CheckBox chkAckReq;
	@FXML
	private CheckBox chkDupReplace;
	@FXML
	private CheckBox chkIncrement;

	private ObservableList<ALMD> almdOList;
	private List<String> descDetailsLoaded = new ArrayList<String>();

	KeyCombination keyComboCtrlD = new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN);
	KeyCombination keyComboCtrlN = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
	KeyCombination keyComboCtrlE = new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN);
	KeyCombination keyComboCtrlS = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
	KeyCombination keyComboCtrlQ = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN);

	private Stage descStage;
	private DescController descController;

	private ObservableList<String> langOList;
	private Map<String, String> langDescMap;

	public void setDescStage(Stage s) {
		this.descStage = s;
	}

	public void setDescController(DescController d) {
		this.descController = d;
	}

	public Map<String, String> getLangDescMap() {
		return langDescMap;
	}

	public void setLangDescMap(Map<String, String> langDescMap) {
		this.langDescMap = langDescMap;
	}

	public TextField getTxtDescription() {
		return txtDescription;
	}

	public void setTxtDescription(TextField txtDescription) {
		this.txtDescription = txtDescription;
	}

	@FXML
	public void initialize() {

		findDupReplaceDisable();

		almdOList = FXCollections.observableArrayList(new ArrayList<ALMD>());
		tableView.setItems(almdOList);

		langOList = FXCollections.observableArrayList(new ArrayList<String>());

		lblList = new ArrayList<Label>();
		lblList.add(lblName);
		lblList.add(lblScope);
		lblList.add(lblDescription);
		lblList.add(lblClass);
		lblList.add(lblSeverity);
		lblList.add(lblOnDelay);
		lblList.add(lblShelveDuration);
		lblList.add(lblMaxShelveDuration);
		lblList.add(lblAssoc1);
		lblList.add(lblAssoc2);
		lblList.add(lblAssoc3);
		lblList.add(lblAssoc4);
		lblList.add(lblAsset);
		lblList.add(lblResetBit);
		lblList.add(lblType);
		lblList.add(lblAlarmBit);

		txtFieldList = new ArrayList<TextField>();
		txtFieldList.add(txtName);
		txtFieldList.add(txtScope);
		txtFieldList.add(txtDescription);
		txtFieldList.add(txtClass);
		txtFieldList.add(txtSeverity);
		txtFieldList.add(txtOnDelay);
		txtFieldList.add(txtShelveDuration);
		txtFieldList.add(txtMaxShelveDuration);
		txtFieldList.add(txtAssoc1);
		txtFieldList.add(txtAssoc2);
		txtFieldList.add(txtAssoc3);
		txtFieldList.add(txtAssoc4);
		txtFieldList.add(txtAsset);
		txtFieldList.add(txtResetBit);
		txtFieldList.add(txtAlarmBit);

		chkList = new ArrayList<CheckBox>();
		chkList.add(chkLatched);
		chkList.add(chkAckReq);

		resetEdits();

		ObservableList<String> comboTypeItems = FXCollections.observableArrayList("Alarm", "Fault", "Both");
		comboType.setItems(comboTypeItems);
		comboType.getSelectionModel().select(0);

		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		tblColName.setCellValueFactory(new PropertyValueFactory<ALMD, String>("name"));
		tblColName.setCellFactory(TextFieldTableCell.<ALMD> forTableColumn());

		tblColLanguage.setCellValueFactory(new PropertyValueFactory<ALMD, String>("languages"));
		tblColLanguage.setStyle("-fx-alignment: CENTER;");

		tblColScope.setCellValueFactory(new PropertyValueFactory<ALMD, String>("scope"));
		tblColScope.setStyle("-fx-alignment: CENTER;");
		tblColScope.setCellFactory(TextFieldTableCell.<ALMD> forTableColumn());

		tblColDescription.setCellValueFactory(new PropertyValueFactory<ALMD, String>("description"));
		tblColDescription.setCellFactory(TextFieldTableCell.<ALMD> forTableColumn());

		tblColOnDelay.setCellValueFactory(new PropertyValueFactory<ALMD, String>("onDelay"));
		tblColOnDelay.setCellFactory(TextFieldTableCell.<ALMD> forTableColumn());
		tblColOnDelay.setStyle("-fx-alignment: CENTER;");

		tblColLatched.setCellValueFactory(new PropertyValueFactory<ALMD, Boolean>("latched"));
		tblColLatched.setCellFactory(tc -> new CheckBoxTableCell<>());
		tblColLatched.setStyle("-fx-alignment: CENTER;");

		tblColAckReq.setCellValueFactory(new PropertyValueFactory<ALMD, Boolean>("ackReq"));
		tblColAckReq.setCellFactory(tc -> new CheckBoxTableCell<>());
		tblColAckReq.setStyle("-fx-alignment: CENTER;");

		tblColClass.setCellValueFactory(new PropertyValueFactory<ALMD, String>("alarmClass"));
		tblColClass.setCellFactory(TextFieldTableCell.<ALMD> forTableColumn());
		tblColClass.setStyle("-fx-alignment: CENTER;");

		textFieldOnlyNum(txtOnDelay, 20);
		textFieldOnlyNum(txtSeverity, 10);
		textFieldOnlyNum(txtClass, 10);
		textFieldOnlyNum(txtShelveDuration, 20);
		textFieldOnlyNum(txtMaxShelveDuration, 20);
		textFieldOnlyNumLTVal(txtAlarmBit, 2, 33);

		txtDupWith.textProperty().addListener((obs, oldVal, newVal) -> {
			txtDupWithEnabler(newVal);
		});

		txtDupWithEnabler(txtDupWith.getText());
	}

	private void txtDupWithEnabler(String newVal) {
		if (isNumber(newVal) && chkIncrement.isDisable()) {
			chkIncrement.setDisable(false);
		} else if (!isNumber(newVal) && !chkIncrement.isDisable()) {
			chkIncrement.setDisable(true);
		}
	}

	@FXML
	void onImportBtn(ActionEvent event) {

		try {
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
			File selectedFile = fc.showOpenDialog(null);

			if (selectedFile != null) {
				List<String[]> csvData = CSVUtil.read(selectedFile.toString(), ",");
				List<ALMD> almdList = ALMD.findALMDS(csvData);

				almdOList = FXCollections.observableArrayList(almdList);
				tableView.setItems(almdOList);
			}
		} catch (Exception e) {
			Popup.displayMsg("Could not load CSV file");
		}
	}

	@FXML
	void onAlarmsImportBtn(ActionEvent event) {

		try {
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
			File selectedFile = fc.showOpenDialog(null);

			if (selectedFile != null) {
				List<String[]> csvData = CSVUtil.read(selectedFile.toString(), ",");
				List<ALMD> almdList = ALMD.parseSpreadsheet(csvData);

				almdOList = FXCollections.observableArrayList(almdList);
				tableView.setItems(almdOList);
			}
		} catch (Exception e) {
			Popup.displayMsg("Could not load CSV file: ");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@FXML
	void onExportBtn(ActionEvent event) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, InstantiationException, IOException {

		if (almdOList.size() > 0) {
			FileChooser fc = new FileChooser();
			File selectedFile = fc.showSaveDialog(null);

			if (selectedFile != null) {

				List<ALMD> almdList = almdOList.stream().map(e -> new ALMD(e.getScope(), e.getName(),
						e.getDescription(), e.getAttributes(), e.getLangDesc())).collect(Collectors.toList());

				CSVUtil.writeObject(almdList, selectedFile.toString(), ",");
			}
		}
	}

	@FXML
	void onDisplayCodeBtn(ActionEvent event) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, InstantiationException, IOException {

		if (almdOList.size() > 0) {
			String almdBranch = "";
			String alarmBranch = "";
			for (ALMD a : almdOList) {
				almdBranch = almdBranch + a.generateALMDBranch();
				alarmBranch = alarmBranch + a.generateAlarmFaultBranch();
			}

			Popup.display(almdBranch + alarmBranch);
		}
	}

	@FXML
	void onDuplicateBtn(ActionEvent event) {
		duplicate(tableView.getSelectionModel().getSelectedItems());
		txtDupWithEnabler(txtDupWith.getText());
	}

	@FXML
	void onEditBtn(ActionEvent event) {
		loadEdit(tableView.getSelectionModel().getSelectedItems());
	}

	@FXML
	void onAddBtn(ActionEvent event) {
		newEntry();
	}

	@FXML
	void onRemoveBtn(ActionEvent event) {
		delete(tableView.getSelectionModel().getSelectedItems());
	}

	@FXML
	void onDescDetailsBtn(ActionEvent event) {

		boolean eng = false;
		if (descController.getLangDescMap().containsKey("en-US") && !txtDescription.getText().equals("")) {
			descController.getLangDescMap().put("en-US", txtDescription.getText());
			eng = true;
		} else if (descController.getLangDescMap().containsKey("en-US") && txtDescription.getText().equals("")) {
			descController.getLangDescMap().remove("en-US");
			langDescMap = descController.getLangDescMap();
			langOList.clear();
		} else if (!descController.getLangDescMap().containsKey("en-US") && !txtDescription.getText().equals("")) {
			langDescMap.put("en-US", txtDescription.getText());
			eng = true;
		}

		if (langDescMap.entrySet().size() != langOList.size()) {
			langOList.clear();
			langDescMap.entrySet().stream().forEach(e -> {
				if (e.getKey() != null) {
					langOList.add(e.getKey());
				}
			});
		}

		descController.getLstLang().setItems(langOList);
		descController.setLangOList(langOList);
		
		descDetailsLoaded.clear();
		langOList.stream().forEach(p -> {
			descDetailsLoaded.add(p);
		});

		if (eng) {
			boolean done = false;
			int i = 0;
			while (!done) {
				if (langOList.get(i).equals("en-US")) {
					descController.getLstLang().getSelectionModel().select(i);
					done = true;
				} else
					i++;
			}
		} else if (langOList.size() > 0)
			descController.getLstLang().getSelectionModel().select(0);

		descStage.show();
	}

	@FXML
	void onKeyPress(KeyEvent event) {

		if (event.getCode() == KeyCode.DELETE) {
			if (!btnRemove.isDisabled())
				delete(tableView.getSelectionModel().getSelectedItems());
		} else if (keyComboCtrlN.match(event)) {
			if (!btnAdd.isDisabled())
				newEntry();
		} else if (keyComboCtrlD.match(event)) {
			if (!btnDuplicate.isDisabled())
				duplicate(tableView.getSelectionModel().getSelectedItems());
		} else if (keyComboCtrlE.match(event)) {
			if (!btnEdit.isDisabled())
				loadEdit(tableView.getSelectionModel().getSelectedItems());
		} else if (keyComboCtrlQ.match(event)) {
			if (!btnApplyEdit.isDisabled())
				resetEdits();
		} else if (keyComboCtrlS.match(event)) {
			if (!btnApplyEdit.isDisabled()) {
				applyEdits(tableView.getSelectionModel().getSelectedItems());
				resetEdits();
			}
		}
	}

	@FXML
	void onApplyEditBtn(ActionEvent event) {
		applyEdits(tableView.getSelectionModel().getSelectedItems());
		resetEdits();
	}

	@FXML
	void onCancelEditBtn(ActionEvent event) {
		resetEdits();
	}

	@FXML
	void onDupReplaceChk(ActionEvent event) {
		findDupReplaceDisable();
	}

	private void findDupReplaceDisable() {
		if (chkDupReplace.isSelected()) {
			txtDupContains.setDisable(false);
			txtDupWith.setDisable(false);
			chkIncrement.setDisable(false);
		} else {
			txtDupContains.setDisable(true);
			txtDupWith.setDisable(true);
			chkIncrement.setDisable(true);
		}
	}

	private void delete(ObservableList<ALMD> selection) {

		almdOList.removeAll(selection);
		if (almdOList.size() > 0) {
			tableView.getSelectionModel().clearAndSelect(0);
		}
		resetEdits();
	}

	private void newEntry() {

		resetEdits();

		ALMD almd = new ALMD();
		List<ALMD> newEntries = new ArrayList<ALMD>();
		String name = "NewEntry";

		for (ALMD e : almdOList) {
			if (e.getName().contains(name) && !e.getName().contains("duplicate"))
				newEntries.add(e);
		}

		int v = newEntries.size();

		boolean done = false;
		while (!done) {
			if (v == 0)
				done = true;
			else {
				boolean exists = false;
				for (ALMD a : newEntries) {
					if (a.getName().equals(name + v)) {
						exists = true;
					}
				}
				if (exists)
					v++;
				else {
					done = true;
					name = name + v;
				}
			}
		}

		almd.setName(name);
		almdOList.add(almd);
	}

	private void duplicate(ObservableList<ALMD> selection) {

		resetEdits();

		String name = "_duplicate";
		for (ALMD f : selection) {

			if (chkDupReplace.isSelected() && !txtDupContains.getText().equals("")) {
				ALMD n = new ALMD(f);
				ALMDAttributes att = new ALMDAttributes(f.getAttributes());
				n.setAttributes(att);
				String aName = n.getName();
				String desc = n.getDescription();
				String asset = n.getAsset();
				String resetBit = n.getResetBit();

				aName = aName.replaceAll(txtDupContains.getText(), txtDupWith.getText());
				desc = desc.replaceAll(txtDupContains.getText(), txtDupWith.getText());
				asset = asset.replaceAll(txtDupContains.getText(), txtDupWith.getText());
				resetBit = resetBit.replaceAll(txtDupContains.getText(), txtDupWith.getText());

				n.getLangDesc().entrySet().stream().forEach(m -> {
					m.setValue(m.getValue().replaceAll(txtDupContains.getText(), txtDupWith.getText()));
				});

				n.setName(aName);
				n.setDescription(desc);
				n.setAsset(asset);
				n.setResetBit(resetBit);
				almdOList.add(n);
			}

			else {

				List<ALMD> newEntries = new ArrayList<ALMD>();

				for (ALMD e : almdOList) {
					if (e.getName().startsWith(f.getName() + name))
						newEntries.add(e);
				}

				boolean done = false;
				int v = newEntries.size();
				while (!done) {
					if (v == 0)
						done = true;
					else {
						boolean exists = false;
						for (ALMD a : newEntries) {
							if (a.getName().equals(f.getName() + name + v)) {
								exists = true;
							}
						}
						if (exists)
							v++;
						else {
							done = true;
							name = name + v;
						}
					}
				}
				ALMD n = new ALMD(f);
				ALMDAttributes a = new ALMDAttributes(f.getAttributes());
				n.setName(n.getName() + name);
				n.setAttributes(a);
				almdOList.add(n);
			}
		}

		if (isNumber(txtDupWith.getText()) && !chkDupReplace.isDisable() && chkIncrement.isSelected()) {
			Integer i = Integer.parseInt(txtDupWith.getText());
			i++;
			txtDupWith.setText(i.toString());
		}
	}

	private void loadEdit(ObservableList<ALMD> selection) {

		if (almdOList.size() > 0 && selection.size() > 0) {

			resetEdits();

			tableView.setDisable(true);
			btnImport.setDisable(true);
			btnExport.setDisable(true);
			btnAlarmsImport.setDisable(true);
			btnDisplayCode.setDisable(true);
			btnApplyEdit.setDisable(false);
			btnDescDetails.setDisable(false);
			btnCancelEdit.setDisable(false);
			btnAdd.setDisable(true);
			btnRemove.setDisable(true);
			btnDuplicate.setDisable(true);
			btnEdit.setDisable(true);
			txtDupContains.setDisable(true);
			txtDupWith.setDisable(true);
			chkDupReplace.setDisable(true);
			chkIncrement.setDisable(true);

			for (TextField e : txtFieldList) {
				e.setDisable(false);
			}

			for (Label e : lblList) {
				e.setDisable(false);
			}

			comboType.setDisable(false);

			txtName.setText(ALMDCompare.name(selection));
			txtScope.setText(ALMDCompare.scope(selection));
			txtDescription.setText(ALMDCompare.description(selection));
			txtSeverity.setText(ALMDCompare.severity(selection));
			txtClass.setText(ALMDCompare.alarmClass(selection));
			txtSeverity.setText(ALMDCompare.severity(selection));
			txtOnDelay.setText(ALMDCompare.minDurationPRE(selection));
			txtShelveDuration.setText(ALMDCompare.shelveDuration(selection));
			txtMaxShelveDuration.setText(ALMDCompare.maxShelveDuration(selection));
			txtAssoc1.setText(ALMDCompare.assocTag1(selection));
			txtAssoc2.setText(ALMDCompare.assocTag2(selection));
			txtAssoc3.setText(ALMDCompare.assocTag3(selection));
			txtAssoc4.setText(ALMDCompare.assocTag4(selection));
			txtAsset.setText(ALMDCompare.asset(selection));
			txtResetBit.setText(ALMDCompare.resetBit(selection));
			txtAlarmBit.setText(ALMDCompare.alarmBit(selection));
			ALMDCompare.type(selection, comboType);

			CheckBoxEnhanced.setState(chkLatched, ALMDCompare.latched(selection));
			CheckBoxEnhanced.setState(chkAckReq, ALMDCompare.ackRequired(selection));

			langOList.clear();
			descController.setLangDescMap(ALMDCompare.langDesc(tableView.getSelectionModel().getSelectedItems()));
			langDescMap = descController.getLangDescMap();

		}
	}

	private void resetEdits() {

		chkList.stream().forEach(e -> CheckBoxEnhanced.reset(e));

		tableView.setDisable(false);
		btnImport.setDisable(false);
		btnAlarmsImport.setDisable(false);
		btnDisplayCode.setDisable(false);
		btnExport.setDisable(false);
		btnApplyEdit.setDisable(true);
		btnCancelEdit.setDisable(true);
		btnDescDetails.setDisable(true);
		btnAdd.setDisable(false);
		btnRemove.setDisable(false);
		btnDuplicate.setDisable(false);
		btnEdit.setDisable(false);
		chkDupReplace.setDisable(false);
		findDupReplaceDisable();
		if (chkDupReplace.isDisable())
			txtDupWithEnabler(txtDupWith.getText());

		for (TextField e : txtFieldList) {
			e.setDisable(true);
			e.setText("");
		}
		for (Label e : lblList) {
			e.setDisable(true);
		}

		comboType.setDisable(true);
	}

	private void applyEdits(ObservableList<ALMD> selection) {

		selection.stream().forEach(e -> {

			if (!txtName.getText().equals("-"))
				e.setName(txtName.getText());
			if (!txtScope.getText().equals("-"))
				e.setScope(txtScope.getText());
			if (!txtDescription.getText().equals("-"))
				e.setDescription(txtDescription.getText());
			if (!txtClass.getText().equals("-"))
				e.getAttributes().setAlarmClass(txtClass.getText());
			if (!txtSeverity.getText().equals("-"))
				e.getAttributes().setSeverity(txtSeverity.getText());
			if (!txtOnDelay.getText().equals("-"))
				e.getAttributes().setMinDurationPRE(txtOnDelay.getText());
			if (!txtShelveDuration.getText().equals("-"))
				e.getAttributes().setShelveDuration(txtShelveDuration.getText());
			if (!txtMaxShelveDuration.getText().equals("-"))
				e.getAttributes().setMaxShelveDuration(txtMaxShelveDuration.getText());
			if (!txtAssoc1.getText().equals("-"))
				e.getAttributes().setAssocTag1(txtAssoc1.getText());
			if (!txtAssoc2.getText().equals("-"))
				e.getAttributes().setAssocTag2(txtAssoc2.getText());
			if (!txtAssoc3.getText().equals("-"))
				e.getAttributes().setAssocTag3(txtAssoc3.getText());
			if (!txtAssoc4.getText().equals("-"))
				e.getAttributes().setAssocTag4(txtAssoc4.getText());
			if (!txtAsset.getText().equals("-"))
				e.setAsset(txtAsset.getText());
			if (!txtResetBit.getText().equals("-"))
				e.setResetBit(txtResetBit.getText());
			if (!txtAlarmBit.getText().equals("-"))
				e.setAlarmBit(txtAlarmBit.getText());
			if (!comboType.getSelectionModel().getSelectedItem().equals("-")) {
				if (comboType.getSelectionModel().getSelectedItem().equals("Alarm")) {
					e.setType("A");
				} else if (comboType.getSelectionModel().getSelectedItem().equals("Fault")) {
					e.setType("F");
				} else if (comboType.getSelectionModel().getSelectedItem().equals("Both")) {
					e.setType("B");
				}
			}

			if (langDescMap == null || langDescMap.entrySet().size() == 0) {
				langDescMap = new HashMap<String, String>();
				if (!e.getDescription().equals("") && !e.getDescription().equals("-")) {
					langDescMap.put("en-US", txtDescription.getText());
				}
			}

			langDescMap.entrySet().stream().forEach(p -> {
				if (p.getValue().equals("-") && e.getLangDesc().containsKey(p.getKey())) {
				} else if (p.getValue().equals("-") && !e.getLangDesc().containsKey(p.getKey())) {
					e.getLangDesc().put(p.getKey(), p.getValue());
				} else if (!p.getValue().equals("-")) {
					e.getLangDesc().put(p.getKey(), p.getValue());
				}
			});
			
			List<String> toRemove = new ArrayList<String>();
			descDetailsLoaded.stream().forEach(p -> {
				if (!langOList.contains(p)) {
					toRemove.add(p);
				}
			});
			
			
			for (String s : toRemove) {
				e.getLangDesc().remove(s);
			}

			if (!chkLatched.isIndeterminate())
				e.getAttributes().setLatched(chkLatched.isSelected());
			if (!chkAckReq.isIndeterminate())
				e.getAttributes().setAckRequired(chkAckReq.isSelected());
		});

		langOList.clear();
		langDescMap.clear();
		descDetailsLoaded.clear();
		tableView.refresh();
	}

	private static final String[] numbers = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "-" };
	private static final String[] numbersOnly = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

	private static boolean isNumber(String e) {

		boolean rVal = true;
		rVal = !e.equals("");

		for (char ch : e.toCharArray()) {
			if (rVal) {
				boolean charNum = false;
				for (String f : numbersOnly) {
					if (!charNum) {
						charNum = (f.equals(Character.toString(ch)));
					}
				}
				rVal = charNum;
			}
		}

		return rVal;
	}

	private static void textFieldOnlyNum(TextField textField, int max) {
		textField.textProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal.length() > oldVal.length()) {
				boolean number = false;
				for (String e : numbers) {
					if (e.equals(newVal.substring(newVal.length() - 1)))
						number = true;
				}
				if (newVal.length() > max || !number)
					textField.setText(oldVal);
				if (newVal.length() > 1 && newVal.contains("-"))
					textField.setText(oldVal);
				if (newVal.length() > 1 && newVal.startsWith("0"))
					textField.setText(oldVal);
			}
		});
	}

	private static void textFieldOnlyNumLTVal(TextField textField, int max, int LT) {
		textField.textProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal.length() > oldVal.length()) {
				boolean number = false;
				for (String e : numbers) {
					if (e.equals(newVal.substring(newVal.length() - 1)))
						number = true;
				}
				if (newVal.length() > max || !number)
					textField.setText(oldVal);
				if (newVal.length() > 1 && newVal.contains("-"))
					textField.setText(oldVal);
				if (!newVal.contains("-")) {
					if (Integer.parseInt(newVal) >= LT)
						textField.setText(oldVal);
				}
				if (newVal.length() > 1 && newVal.startsWith("0"))
					textField.setText(oldVal);
			}
		});
	}
}
