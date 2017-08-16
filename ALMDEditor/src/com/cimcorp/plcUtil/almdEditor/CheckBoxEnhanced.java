package com.cimcorp.plcUtil.almdEditor;

import javafx.scene.control.CheckBox;

public class CheckBoxEnhanced {

	
	public static void init(CheckBox chk){
		
		
	}
	
	public static void reset(CheckBox chk){
		chk.setIndeterminate(false);
		chk.setSelected(false);
		chk.setDisable(true);
	}
	
	public static void setState(CheckBox chk, int state){
		
		chk.setDisable(false);
		
		if (state == 1) {
			chk.setAllowIndeterminate(false);
			chk.setSelected(true);
			chk.setIndeterminate(false);
		}
		else if (state == 2) {
			chk.setAllowIndeterminate(false);
			chk.setSelected(false);
			chk.setIndeterminate(false);
		}
		else {
			chk.setAllowIndeterminate(true);
			chk.setSelected(false);
			chk.setIndeterminate(true);
		}
	}
}
