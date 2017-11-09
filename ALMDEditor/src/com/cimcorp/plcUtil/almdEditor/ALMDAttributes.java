package com.cimcorp.plcUtil.almdEditor;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ALMDAttributes {

	private String externalAccess = "Read/Write";
	private String severity = "0";
	private StringProperty minDurationPRE;
	private String shelveDuration = "0";
	private String maxShelveDuration = "0";
	private BooleanProperty ackRequired;
	private BooleanProperty latched;
	private String assocTag1 = "";
	private String assocTag2 = "";
	private String assocTag3 = "";
	private String assocTag4 = "";
	private StringProperty alarmClass;

	public ALMDAttributes() {
		this.latched = new SimpleBooleanProperty(false);
		this.ackRequired = new SimpleBooleanProperty(false);
		this.minDurationPRE = new SimpleStringProperty("0");
		this.alarmClass = new SimpleStringProperty("");
	};

	public ALMDAttributes(String externalAccess, String severity, String minDurationPRE, String shelveDuration,
			String maxShelveDuration, boolean ackRequired, boolean latched, String assocTag1, String assocTag2,
			String assocTag3, String assocTag4, String alarmClass) {

		this.externalAccess = externalAccess;
		this.severity = severity;
		this.minDurationPRE = new SimpleStringProperty(minDurationPRE);
		this.shelveDuration = shelveDuration;
		this.maxShelveDuration = maxShelveDuration;
		this.ackRequired = new SimpleBooleanProperty(ackRequired);
		this.latched = new SimpleBooleanProperty(latched);
		this.assocTag1 = assocTag1;
		this.alarmClass = new SimpleStringProperty(alarmClass);
	}

	public ALMDAttributes(ALMDAttributes a) {

		this.externalAccess = a.getExternalAccess();
		this.severity = a.getSeverity();
		this.minDurationPRE = new SimpleStringProperty(a.getMinDurationPRE());
		this.shelveDuration = a.getShelveDuration();
		this.maxShelveDuration = a.getMaxShelveDuration();
		this.ackRequired = new SimpleBooleanProperty(a.isAckRequired());
		this.latched = new SimpleBooleanProperty(a.isLatched());
		this.assocTag1 = a.getAssocTag1();
		this.assocTag2 = a.getAssocTag2();
		this.assocTag3 = a.getAssocTag3();
		this.assocTag4 = a.getAssocTag4();
		this.alarmClass = new SimpleStringProperty(a.getAlarmClass());
	}

	public String getExternalAccess() {
		return externalAccess;
	}

	public void setExternalAccess(String externalAccess) {
		this.externalAccess = externalAccess;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getMinDurationPRE() {
		return minDurationPRE.get();
	}

	public void setMinDurationPRE(String minDurationPRE) {
		this.minDurationPREProperty().set(minDurationPRE);
	}

	public StringProperty minDurationPREProperty() {
		return this.minDurationPRE;
	}

	public String getShelveDuration() {
		return shelveDuration;
	}

	public void setShelveDuration(String shelveDuration) {
		this.shelveDuration = shelveDuration;
	}

	public String getMaxShelveDuration() {
		return maxShelveDuration;
	}

	public void setMaxShelveDuration(String maxShelveDuration) {
		this.maxShelveDuration = maxShelveDuration;
	}

	public boolean isAckRequired() {
		return ackRequired.get();
	}

	public void setAckRequired(boolean ackRequired) {
		this.ackRequiredProperty().set(ackRequired);
	}

	public BooleanProperty ackRequiredProperty() {
		return this.ackRequired;
	}

	public boolean isLatched() {
		return latched.get();
	}

	public void setLatched(boolean latched) {
		this.latchedProperty().set(latched);
	}

	public BooleanProperty latchedProperty() {
		return this.latched;
	}

	public String getAssocTag1() {
		return assocTag1;
	}

	public void setAssocTag1(String assocTag1) {
		this.assocTag1 = assocTag1;
	}

	public String getAlarmClass() {
		return alarmClass.get();
	}

	public void setAlarmClass(String alarmClass) {
		this.alarmClassProperty().set(alarmClass);
	}

	public StringProperty alarmClassProperty() {
		return this.alarmClass;
	}

	public String getAssocTag2() {
		return assocTag2;
	}

	public void setAssocTag2(String assocTag2) {
		this.assocTag2 = assocTag2;
	}

	public String getAssocTag3() {
		return assocTag3;
	}

	public void setAssocTag3(String assocTag3) {
		this.assocTag3 = assocTag3;
	}

	public String getAssocTag4() {
		return assocTag4;
	}

	public void setAssocTag4(String assocTag4) {
		this.assocTag4 = assocTag4;
	}

	public static ALMDAttributes parseAttributes(String[] l) {

		ALMDAttributes a = new ALMDAttributes();

		for (String s : l) {

			if ((s.startsWith("ExternalAccess")))
				a.setExternalAccess(getString(s));
			else if ((s.startsWith("Severity")))
				a.setSeverity(getString(s));
			else if ((s.startsWith("MinDurationPRE")))
				a.setMinDurationPRE(getString(s));
			else if ((s.startsWith("ShelveDuration")))
				a.setShelveDuration(getString(s));
			else if ((s.startsWith("MaxShelveDuration")))
				a.setMaxShelveDuration(getString(s));
			else if ((s.startsWith("AckRequired")))
				a.setAckRequired(getBool(s));
			else if ((s.startsWith("Latched")))
				a.setLatched(getBool(s));
			else if ((s.startsWith("AssocTag1")))
				a.setAssocTag1(getString(s));
			else if ((s.startsWith("AssocTag2")))
				a.setAssocTag2(getString(s));
			else if ((s.startsWith("AssocTag3")))
				a.setAssocTag3(getString(s));
			else if ((s.startsWith("AssocTag4")))
				a.setAssocTag4(getString(s));
			else if ((s.startsWith("AlarmClass")))
				a.setAlarmClass(getString(s));

		}
		return a;
	}

	private static boolean getBool(String s) {
		int i = s.indexOf(":=") + 3;
		int k = s.length();
		String t = s.substring(i, k);
		return Boolean.parseBoolean(t);
	}

	private static String getString(String s) {
		int i = s.indexOf(":=") + 3;
		int k = s.length();
		String t = s.substring(i, k);
		if (t.startsWith("\"\""))
			return t.substring(2, t.length() - 2);
		else
			return t;
	}

	@SuppressWarnings("unused")
	private static int getInt(String s) {
		int i = s.indexOf(":=") + 3;
		int k = s.length();
		String t = s.substring(i, k);
		if (t.startsWith("\"\""))
			t = t.substring(2, t.length() - 2);
		return Integer.parseInt(t);
	}

	@Override
	public String toString() {
		String r = "\"(";
		r = r + "ExternalAccess := " + getExternalAccess() + ", " + "Severity := " + getSeverity() + ", "
				+ "MinDurationPRE := " + getMinDurationPRE() + ", " + "ShelveDuration := " + getShelveDuration() + ", "
				+ "MaxShelveDuration := " + getMaxShelveDuration() + ", "
				+ "ProgTime := \"\"DT#1969-12-31-19:00:00.000000(UTC-05:00)\"\", " + "EnableIn := " + "false" + ", "
				+ "In := " + "false" + ", " + "InFault := " + "false" + ", " + "Condition := " + "false" + ", "
				+ "AckRequired := " + isAckRequired() + ", " + "Latched := " + isLatched() + ", " + "ProgAck := "
				+ "false" + ", " + "OperAck := " + "false" + ", " + "ProgReset := " + "false" + ", " + "OperReset := "
				+ "false" + ", " + "ProgSuppress := " + "false" + ", " + "OperSuppress := " + "false" + ", "
				+ "ProgUnsuppress := " + "false" + ", " + "OperUnsuppress := " + "false" + ", " + "OperShelve := "
				+ "false" + ", " + "ProgUnshelve := " + "false" + ", " + "OperUnshelve := " + "false" + ", "
				+ "ProgDisable := " + "false" + ", " + "OperDisable := " + "false" + ", " + "ProgEnable := " + "false"
				+ ", " + "OperEnable := " + "false" + ", " + "AlarmCountReset := " + "false" + ", " + "UseProgTime := "
				+ "false" + ", ";

		if (!getAssocTag1().equals(""))
			r = r + "AssocTag1 := \"\"" + getAssocTag1() + "\"\", ";
		if (!getAssocTag2().equals(""))
			r = r + "AssocTag2 := \"\"" + getAssocTag2() + "\"\", ";
		if (!getAssocTag3().equals(""))
			r = r + "AssocTag3 := \"\"" + getAssocTag3() + "\"\", ";
		if (!getAssocTag4().equals(""))
			r = r + "AssocTag4 := \"\"" + getAssocTag4() + "\"\", ";

		if (!getAlarmClass().equals(""))
			r = r + "AlarmClass := " + "\"\"" + getAlarmClass() + "\"\", ";

		r = r.trim();
		r = r.substring(0, r.length() - 1);
		return r + ")\"";
	}

}
