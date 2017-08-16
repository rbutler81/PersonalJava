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
	private boolean enableIn = false;
	private boolean in = false;
	private boolean inFault = false;
	private boolean condition = false;
	private BooleanProperty ackRequired;
	private BooleanProperty latched;
	private boolean progAck = false;
	private boolean operAck = false;
	private boolean progReset = false;
	private boolean operReset = false;
	private boolean progSuppress = false;
	private boolean operSuppress = false;
	private boolean progUnsuppress = false;
	private boolean operUnsuppress = false;
	private boolean operShelve = false;
	private boolean progUnshelve = false;
	private boolean operUnshelve = false;
	private boolean progDisable = false;
	private boolean operDisable = false;
	private boolean progEnable = false;
	private boolean operEnable = false;
	private boolean alarmCountReset = false;
	private boolean useProgTime = false;
	private String assocTag1 = "";
	private String assocTag2 = "";
	private String assocTag3 = "";
	private String assocTag4 = "";
	private StringProperty alarmClass;
	
	public ALMDAttributes(){
		this.latched = new SimpleBooleanProperty(false);
		this.ackRequired = new SimpleBooleanProperty(false);
		this.minDurationPRE = new SimpleStringProperty("0");
		this.alarmClass = new SimpleStringProperty("");
	};
	
	public ALMDAttributes(String externalAccess, String severity, String minDurationPRE, String shelveDuration,
			String maxShelveDuration, boolean enableIn, boolean in, boolean inFault, boolean condition,
			boolean ackRequired, boolean latched, boolean progAck, boolean operAck, boolean progReset,
			boolean operReset, boolean progSuppress, boolean operSuppress, boolean progUnsuppress,
			boolean operUnsuppress, boolean operShelve, boolean progUnshelve, boolean operUnshelve, boolean progDisable,
			boolean operDisable, boolean progEnable, boolean operEnable, boolean alarmCountReset, boolean useProgTime,
			String assocTag1, String assocTag2, String assocTag3, String assocTag4, String alarmClass) {
		
		this.externalAccess = externalAccess;
		this.severity = severity;
		this.minDurationPRE = new SimpleStringProperty(minDurationPRE);
		this.shelveDuration = shelveDuration;
		this.maxShelveDuration = maxShelveDuration;
		this.enableIn = enableIn;
		this.in = in;
		this.inFault = inFault;
		this.condition = condition;
		this.ackRequired = new SimpleBooleanProperty(ackRequired);
		this.latched = new SimpleBooleanProperty(latched);
		this.progAck = progAck;
		this.operAck = operAck;
		this.progReset = progReset;
		this.operReset = operReset;
		this.progSuppress = progSuppress;
		this.operSuppress = operSuppress;
		this.progUnsuppress = progUnsuppress;
		this.operUnsuppress = operUnsuppress;
		this.operShelve = operShelve;
		this.progUnshelve = progUnshelve;
		this.operUnshelve = operUnshelve;
		this.progDisable = progDisable;
		this.operDisable = operDisable;
		this.progEnable = progEnable;
		this.operEnable = operEnable;
		this.alarmCountReset = alarmCountReset;
		this.useProgTime = useProgTime;
		this.assocTag1 = assocTag1;
		this.alarmClass = new SimpleStringProperty(alarmClass);
	}
	
	public ALMDAttributes(ALMDAttributes a) {
		
		this.externalAccess = a.getExternalAccess();
		this.severity = a.getSeverity();
		this.minDurationPRE = new SimpleStringProperty(a.getMinDurationPRE());
		this.shelveDuration = a.getShelveDuration();
		this.maxShelveDuration = a.getMaxShelveDuration();
		this.enableIn = a.isEnableIn();
		this.in = a.isIn();
		this.inFault = a.isInFault();
		this.condition = a.isCondition();
		this.ackRequired = new SimpleBooleanProperty(a.isAckRequired());
		this.latched = new SimpleBooleanProperty(a.isLatched());
		this.progAck = a.isProgAck();
		this.operAck = a.isOperAck();
		this.progReset = a.isProgReset();
		this.operReset = a.isOperReset();
		this.progSuppress = a.isProgSuppress();
		this.operSuppress = a.isOperSuppress();
		this.progUnsuppress = a.isProgUnsuppress();
		this.operUnsuppress = a.isOperUnsuppress();
		this.operShelve = a.isOperShelve();
		this.progUnshelve = a.isProgUnshelve();
		this.operUnshelve = a.isOperUnshelve();
		this.progDisable = a.isProgDisable();
		this.operDisable = a.isOperDisable();
		this.progEnable = a.isProgEnable();
		this.operEnable = a.isOperEnable();
		this.alarmCountReset = a.isAlarmCountReset();
		this.useProgTime = a.isUseProgTime();
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
	public StringProperty minDurationPREProperty(){
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
	public boolean isEnableIn() {
		return enableIn;
	}
	public void setEnableIn(boolean enableIn) {
		this.enableIn = enableIn;
	}
	public boolean isIn() {
		return in;
	}
	public void setIn(boolean in) {
		this.in = in;
	}
	public boolean isInFault() {
		return inFault;
	}
	public void setInFault(boolean inFault) {
		this.inFault = inFault;
	}
	public boolean isCondition() {
		return condition;
	}
	public void setCondition(boolean condition) {
		this.condition = condition;
	}
	public boolean isAckRequired() {
		return ackRequired.get();
	}
	public void setAckRequired(boolean ackRequired) {
		this.ackRequiredProperty().set(ackRequired);
	}
	public BooleanProperty ackRequiredProperty(){
		return this.ackRequired;
	}
	public boolean isLatched() {
		return latched.get();
	}
	public void setLatched(boolean latched) {
		this.latchedProperty().set(latched);
	}
	public BooleanProperty latchedProperty(){
		return this.latched;
	}
	public boolean isProgAck() {
		return progAck;
	}
	public void setProgAck(boolean progAck) {
		this.progAck = progAck;
	}
	public boolean isOperAck() {
		return operAck;
	}
	public void setOperAck(boolean operAck) {
		this.operAck = operAck;
	}
	public boolean isProgReset() {
		return progReset;
	}
	public void setProgReset(boolean progReset) {
		this.progReset = progReset;
	}
	public boolean isOperReset() {
		return operReset;
	}
	public void setOperReset(boolean operReset) {
		this.operReset = operReset;
	}
	public boolean isProgSuppress() {
		return progSuppress;
	}
	public void setProgSuppress(boolean progSuppress) {
		this.progSuppress = progSuppress;
	}
	public boolean isOperSuppress() {
		return operSuppress;
	}
	public void setOperSuppress(boolean operSuppress) {
		this.operSuppress = operSuppress;
	}
	public boolean isProgUnsuppress() {
		return progUnsuppress;
	}
	public void setProgUnsuppress(boolean progUnsuppress) {
		this.progUnsuppress = progUnsuppress;
	}
	public boolean isOperUnsuppress() {
		return operUnsuppress;
	}
	public void setOperUnsuppress(boolean operUnsuppress) {
		this.operUnsuppress = operUnsuppress;
	}
	public boolean isOperShelve() {
		return operShelve;
	}
	public void setOperShelve(boolean operShelve) {
		this.operShelve = operShelve;
	}
	public boolean isProgUnshelve() {
		return progUnshelve;
	}
	public void setProgUnshelve(boolean progUnshelve) {
		this.progUnshelve = progUnshelve;
	}
	public boolean isOperUnshelve() {
		return operUnshelve;
	}
	public void setOperUnshelve(boolean operUnshelve) {
		this.operUnshelve = operUnshelve;
	}
	public boolean isProgDisable() {
		return progDisable;
	}
	public void setProgDisable(boolean progDisable) {
		this.progDisable = progDisable;
	}
	public boolean isOperDisable() {
		return operDisable;
	}
	public void setOperDisable(boolean operDisable) {
		this.operDisable = operDisable;
	}
	public boolean isProgEnable() {
		return progEnable;
	}
	public void setProgEnable(boolean progEnable) {
		this.progEnable = progEnable;
	}
	public boolean isOperEnable() {
		return operEnable;
	}
	public void setOperEnable(boolean operEnable) {
		this.operEnable = operEnable;
	}
	public boolean isAlarmCountReset() {
		return alarmCountReset;
	}
	public void setAlarmCountReset(boolean alarmCountReset) {
		this.alarmCountReset = alarmCountReset;
	}
	public boolean isUseProgTime() {
		return useProgTime;
	}
	public void setUseProgTime(boolean useProgTime) {
		this.useProgTime = useProgTime;
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
	public StringProperty alarmClassProperty(){
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

	public static ALMDAttributes parseAttributes(String[] l){
		
		ALMDAttributes a = new ALMDAttributes(); 
		
		for (String s : l){
			
			if ((s.startsWith("ExternalAccess"))) a.setExternalAccess(getString(s));
			else if ((s.startsWith("Severity"))) a.setSeverity(getString(s));
			else if ((s.startsWith("MinDurationPRE"))) a.setMinDurationPRE(getString(s));
			else if ((s.startsWith("ShelveDuration"))) a.setShelveDuration(getString(s));
			else if ((s.startsWith("MaxShelveDuration"))) a.setMaxShelveDuration(getString(s));
			else if ((s.startsWith("EnableIn"))) a.setEnableIn(getBool(s));
			else if ((s.startsWith("In"))) a.setIn(getBool(s));
			else if ((s.startsWith("InFault"))) a.setInFault(getBool(s));
			else if ((s.startsWith("Condition"))) a.setCondition(getBool(s));
			else if ((s.startsWith("AckRequired"))) a.setAckRequired(getBool(s));
			else if ((s.startsWith("Latched"))) a.setLatched(getBool(s));
			else if ((s.startsWith("ProgAck"))) a.setProgAck(getBool(s));
			else if ((s.startsWith("OperAck"))) a.setOperAck(getBool(s));
			else if ((s.startsWith("ProgReset"))) a.setProgReset(getBool(s));
			else if ((s.startsWith("OperReset"))) a.setOperReset(getBool(s));
			else if ((s.startsWith("ProgSuppress"))) a.setProgSuppress(getBool(s));
			else if ((s.startsWith("OperSuppress"))) a.setOperSuppress(getBool(s));
			else if ((s.startsWith("ProgUnsuppress"))) a.setProgUnsuppress(getBool(s));
			else if ((s.startsWith("OperUnsuppress"))) a.setOperUnsuppress(getBool(s));
			else if ((s.startsWith("OperShelve"))) a.setOperShelve(getBool(s));
			else if ((s.startsWith("ProgUnshelve"))) a.setProgUnshelve(getBool(s));
			else if ((s.startsWith("OperUnshelve"))) a.setOperUnshelve(getBool(s));
			else if ((s.startsWith("ProgDisable"))) a.setProgDisable(getBool(s));
			else if ((s.startsWith("OperDisable"))) a.setOperDisable(getBool(s));
			else if ((s.startsWith("ProgEnable"))) a.setProgEnable(getBool(s));
			else if ((s.startsWith("OperEnable"))) a.setOperEnable(getBool(s));
			else if ((s.startsWith("AlarmCountReset"))) a.setAlarmCountReset(getBool(s));
			else if ((s.startsWith("UseProgTime"))) a.setUseProgTime(getBool(s));
			else if ((s.startsWith("AssocTag1"))) a.setAssocTag1(getString(s));
			else if ((s.startsWith("AssocTag2"))) a.setAssocTag2(getString(s));
			else if ((s.startsWith("AssocTag3"))) a.setAssocTag3(getString(s));
			else if ((s.startsWith("AssocTag4"))) a.setAssocTag4(getString(s));
			else if ((s.startsWith("AlarmClass"))) a.setAlarmClass(getString(s));
			
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
		String t =  s.substring(i, k);
		if (t.startsWith("\"\"")) return t.substring(2, t.length() - 2);
		else return t;
	}
	
	@SuppressWarnings("unused")
	private static int getInt(String s) {
		int i = s.indexOf(":=") + 3;
		int k = s.length();
		String t =  s.substring(i, k);
		if (t.startsWith("\"\"")) t =  t.substring(2, t.length() - 2);
		return Integer.parseInt(t);
	}
	
	@Override
	public String toString(){
		String r = "\"(";
		r = r + "ExternalAccess := " + getExternalAccess() + ", "
			+ "Severity := " + getSeverity() + ", "
			+ "MinDurationPRE := " + getMinDurationPRE() + ", "
			+ "ShelveDuration := " + getShelveDuration() + ", "
			+ "MaxShelveDuration := " + getMaxShelveDuration() + ", "
			+ "ProgTime := \"\"DT#1969-12-31-19:00:00.000000(UTC-05:00)\"\", "
			+ "EnableIn := " + isEnableIn() + ", "
			+ "In := " + isIn() + ", "
			+ "InFault := " + isInFault() + ", "
			+ "Condition := " + isCondition() + ", "
			+ "AckRequired := " + isAckRequired() + ", "
			+ "Latched := " + isLatched() + ", "
			+ "ProgAck := " + isProgAck() + ", "
			+ "OperAck := " + isOperAck() + ", "
			+ "ProgReset := " + isProgReset() + ", "
			+ "OperReset := " + isOperReset() + ", "
			+ "ProgSuppress := " + isProgSuppress() + ", "
			+ "OperSuppress := " + isOperSuppress() + ", "
			+ "ProgUnsuppress := " + isProgUnsuppress() + ", "
			+ "OperUnsuppress := " + isOperUnsuppress() + ", "
			+ "OperShelve := " + isOperShelve() + ", "
			+ "ProgUnshelve := " + isProgUnshelve() + ", "
			+ "OperUnshelve := " + isOperUnshelve() + ", "
			+ "ProgDisable := " + isProgDisable() + ", "
			+ "OperDisable := " + isOperDisable() + ", "
			+ "ProgEnable := " + isProgEnable() + ", "
			+ "OperEnable := " + isOperEnable() + ", "
			+ "AlarmCountReset := " + isAlarmCountReset() + ", "
			+ "UseProgTime := " + isUseProgTime() + ", ";
		
		if (!getAssocTag1().equals("")) r = r + "AssocTag1 := \"\"" + getAssocTag1() + "\"\", ";
		if (!getAssocTag2().equals("")) r = r + "AssocTag2 := \"\"" + getAssocTag2() + "\"\", ";
		if (!getAssocTag3().equals("")) r = r + "AssocTag3 := \"\"" + getAssocTag3() + "\"\", ";
		if (!getAssocTag4().equals("")) r = r + "AssocTag4 := \"\"" + getAssocTag4() + "\"\", ";
		
		if (!getAlarmClass().equals("")) r = r + "AlarmClass := " + "\"\"" + getAlarmClass() + "\"\", ";  
		
		r = r.trim();
		r = r.substring(0, r.length() - 1);
		return r + ")\"";
	}
	
}
