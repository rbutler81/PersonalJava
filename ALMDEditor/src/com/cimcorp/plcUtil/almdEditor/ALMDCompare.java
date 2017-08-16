package com.cimcorp.plcUtil.almdEditor;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;



public class ALMDCompare {

	public static String scope(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getScope().equals(a.get(0).getScope()))
				.collect(Collectors.toList());
		if (a.size() == b.size()) return a.get(0).getScope();
		else return "-";
	};
	
	public static String name(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getName().equals(a.get(0).getName()))
				.collect(Collectors.toList());
		if (a.size() == b.size()) return a.get(0).getName();
		else return "-";
	};
	
	public static String description(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getDescription().equals(a.get(0).getDescription()))
				.collect(Collectors.toList());
		if (a.size() == b.size()) return a.get(0).getDescription();
		else return "-";
	};
	
	public static String externalAccess(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().getExternalAccess().equals(a.get(0).getAttributes().getExternalAccess()))
				.collect(Collectors.toList());
		if (a.size() == b.size()) return a.get(0).getAttributes().getExternalAccess();
		else return "-";
	};
	
	public static String severity(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().getSeverity().equals(a.get(0).getAttributes().getSeverity()))
				.collect(Collectors.toList());
		if (a.size() == b.size()) return a.get(0).getAttributes().getSeverity();
		else return "-";
	};
	
	public static String minDurationPRE(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().getMinDurationPRE().equals(a.get(0).getAttributes().getMinDurationPRE()))
				.collect(Collectors.toList());
		if (a.size() == b.size()) return a.get(0).getAttributes().getMinDurationPRE();
		else return "-";
	};
	
	public static String shelveDuration(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().getShelveDuration().equals(a.get(0).getAttributes().getShelveDuration()))
				.collect(Collectors.toList());
		if (a.size() == b.size()) return a.get(0).getAttributes().getShelveDuration();
		else return "-";
	};
	
	public static String maxShelveDuration(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().getMaxShelveDuration().equals(a.get(0).getAttributes().getMaxShelveDuration()))
				.collect(Collectors.toList());
		if (a.size() == b.size()) return a.get(0).getAttributes().getMaxShelveDuration();
		else return "-";
	};
	
	public static int enableIn(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().isEnableIn() == a.get(0).getAttributes().isEnableIn())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isEnableIn()) return 1;
			else return 2;
		}
		else return 0;
	};
	
	public static int in(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().isIn() == a.get(0).getAttributes().isIn())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isIn()) return 1;
			else return 2;
		}
		else return 0;
	};
	
	public static int inFault(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().isInFault() == a.get(0).getAttributes().isInFault())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isInFault()) return 1;
			else return 2;
		}
		else return 0;
	};
	
	public static int inCondition(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().isCondition() == a.get(0).getAttributes().isCondition())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isCondition()) return 1;
			else return 2;
		}
		else return 0;
	};
	
	public static int ackRequired(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().isAckRequired() == a.get(0).getAttributes().isAckRequired())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isAckRequired()) return 1;
			else return 2;
		}
		else return 0;
	};
	
	public static int latched(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().isLatched() == a.get(0).getAttributes().isLatched())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isLatched()) return 1;
			else return 2;
		}
		else return 0;
	};
	
	public static int progAck(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().isProgAck() == a.get(0).getAttributes().isProgAck())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isProgAck()) return 1;
			else return 2;
		}
		else return 0;
	};
	
	public static int operAck(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().isOperAck() == a.get(0).getAttributes().isOperAck())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isOperAck()) return 1;
			else return 2;
		}
		else return 0;
	};
	
	public static int progReset(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().isProgReset() == a.get(0).getAttributes().isProgReset())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isProgReset()) return 1;
			else return 2;
		}
		else return 0;
	};
	
	public static int operReset(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().isOperReset() == a.get(0).getAttributes().isOperReset())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isOperReset()) return 1;
			else return 2;
		}
		else return 0;
	};
	
	public static int progSuppress(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().isProgSuppress() == a.get(0).getAttributes().isProgSuppress())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isProgSuppress()) return 1;
			else return 2;
		}
		else return 0;
	};
	
	public static int operSuppress(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().isOperSuppress() == a.get(0).getAttributes().isOperSuppress())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isOperSuppress()) return 1;
			else return 2;
		}
		else return 0;
	};
	
	public static int progUnsuppress(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().isProgUnsuppress() == a.get(0).getAttributes().isProgUnsuppress())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isProgUnsuppress()) return 1;
			else return 2;
		}
		else return 0;
	};
	
	public static int operUnsuppress(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().isOperUnsuppress() == a.get(0).getAttributes().isOperUnsuppress())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isOperUnsuppress()) return 1;
			else return 2;
		}
		else return 0;
	};
	
	public static int operShelve(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().isOperShelve() == a.get(0).getAttributes().isOperShelve())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isOperShelve()) return 1;
			else return 2;
		}
		else return 0;
	};
	
	public static int progUnshelve(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().isProgUnshelve() == a.get(0).getAttributes().isProgUnshelve())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isProgUnshelve()) return 1;
			else return 2;
		}
		else return 0;
	};
	
	public static int operUnshelve(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().isOperUnshelve() == a.get(0).getAttributes().isOperUnshelve())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isOperUnshelve()) return 1;
			else return 2;
		}
		else return 0;
	};
	
	public static int progDisable(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().isProgDisable() == a.get(0).getAttributes().isProgDisable())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isProgDisable()) return 1;
			else return 2;
		}
		else return 0;
	};
	
	public static int operDisable(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().isOperDisable() == a.get(0).getAttributes().isOperDisable())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isOperDisable()) return 1;
			else return 2;
		}
		else return 0;
	};
	
	public static int progEnable(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().isProgEnable() == a.get(0).getAttributes().isProgEnable())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isProgEnable()) return 1;
			else return 2;
		}
		else return 0;
	};
	
	public static int operEnable(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().isOperEnable() == a.get(0).getAttributes().isOperEnable())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isOperEnable()) return 1;
			else return 2;
		}
		else return 0;
	};
	
	public static int alarmCountReset(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().isAlarmCountReset() == a.get(0).getAttributes().isAlarmCountReset())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isAlarmCountReset()) return 1;
			else return 2;
		}
		else return 0;
	};
	
	public static int useProgTime(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().isUseProgTime() == a.get(0).getAttributes().isUseProgTime())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isUseProgTime()) return 1;
			else return 2;
		}
		else return 0;
	};
	
	public static String assocTag1(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().getAssocTag1().equals(a.get(0).getAttributes().getAssocTag1()))
				.collect(Collectors.toList());
		if (a.size() == b.size()) return a.get(0).getAttributes().getAssocTag1();
		else return "-";
	};
	
	public static String assocTag2(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().getAssocTag2().equals(a.get(0).getAttributes().getAssocTag2()))
				.collect(Collectors.toList());
		if (a.size() == b.size()) return a.get(0).getAttributes().getAssocTag2();
		else return "-";
	};
	
	public static String assocTag3(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().getAssocTag3().equals(a.get(0).getAttributes().getAssocTag3()))
				.collect(Collectors.toList());
		if (a.size() == b.size()) return a.get(0).getAttributes().getAssocTag3();
		else return "-";
	};
	
	public static String assocTag4(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().getAssocTag4().equals(a.get(0).getAttributes().getAssocTag4()))
				.collect(Collectors.toList());
		if (a.size() == b.size()) return a.get(0).getAttributes().getAssocTag4();
		else return "-";
	};
	
	public static String alarmClass(List<ALMD> a){
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().getAlarmClass().equals(a.get(0).getAttributes().getAlarmClass()))
				.collect(Collectors.toList());
		if (a.size() == b.size()) return a.get(0).getAttributes().getAlarmClass();
		else return "-";
	};
	
	public static HashMap<String, String> langDesc(List<ALMD> a){
		
		HashMap<String, String> o = new HashMap<String, String>();
		HashMap<String, Integer> p = new HashMap<String, Integer>();
		HashMap<String, Boolean> q = new HashMap<String, Boolean>();
		
		// count how many entries each language is in
		a.stream()
			.forEach(e -> {
				e.getLangDesc().entrySet().stream()
					.forEach(f -> {
						if (!p.containsKey(f.getKey())){
							p.put(f.getKey(), 1);
						}
						else {
							p.put(f.getKey(), p.get(f.getKey()) + 1);
						}
					});
			});
		
		// make a list of languages that are in every entry
		p.entrySet().stream()
			.forEach(e -> {
				if (e.getValue() == a.size()) q.put(e.getKey(), true);
			});
		
		// build output list 
		a.stream()
			.forEach(e -> {
				e.getLangDesc().entrySet().stream()
					.forEach(f -> {
						q.entrySet().stream()
							.forEach(g -> {
								if (g.getKey().equals(f.getKey())){
									if (!o.containsKey(f.getKey())) o.put(f.getKey(), f.getValue());
									else if (!o.get(f.getKey()).equals(f.getValue())) {
										o.put(f.getKey(), "-");
									}
								}
							});
					});
			});
		
		return o;
	}
	
}
