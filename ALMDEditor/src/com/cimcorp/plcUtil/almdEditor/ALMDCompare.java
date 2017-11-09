package com.cimcorp.plcUtil.almdEditor;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ALMDCompare {

	public static String scope(List<ALMD> a) {
		List<ALMD> b = a.stream().filter(e -> e.getScope().equals(a.get(0).getScope())).collect(Collectors.toList());
		if (a.size() == b.size())
			return a.get(0).getScope();
		else
			return "-";
	};

	public static String name(List<ALMD> a) {
		List<ALMD> b = a.stream().filter(e -> e.getName().equals(a.get(0).getName())).collect(Collectors.toList());
		if (a.size() == b.size())
			return a.get(0).getName();
		else
			return "-";
	};

	public static String description(List<ALMD> a) {
		List<ALMD> b = a.stream().filter(e -> e.getDescription().equals(a.get(0).getDescription()))
				.collect(Collectors.toList());
		if (a.size() == b.size())
			return a.get(0).getDescription();
		else
			return "-";
	};

	public static String externalAccess(List<ALMD> a) {
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().getExternalAccess().equals(a.get(0).getAttributes().getExternalAccess()))
				.collect(Collectors.toList());
		if (a.size() == b.size())
			return a.get(0).getAttributes().getExternalAccess();
		else
			return "-";
	};

	public static String severity(List<ALMD> a) {
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().getSeverity().equals(a.get(0).getAttributes().getSeverity()))
				.collect(Collectors.toList());
		if (a.size() == b.size())
			return a.get(0).getAttributes().getSeverity();
		else
			return "-";
	};

	public static String minDurationPRE(List<ALMD> a) {
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().getMinDurationPRE().equals(a.get(0).getAttributes().getMinDurationPRE()))
				.collect(Collectors.toList());
		if (a.size() == b.size())
			return a.get(0).getAttributes().getMinDurationPRE();
		else
			return "-";
	};

	public static String shelveDuration(List<ALMD> a) {
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().getShelveDuration().equals(a.get(0).getAttributes().getShelveDuration()))
				.collect(Collectors.toList());
		if (a.size() == b.size())
			return a.get(0).getAttributes().getShelveDuration();
		else
			return "-";
	};

	public static String maxShelveDuration(List<ALMD> a) {
		List<ALMD> b = a.stream().filter(
				e -> e.getAttributes().getMaxShelveDuration().equals(a.get(0).getAttributes().getMaxShelveDuration()))
				.collect(Collectors.toList());
		if (a.size() == b.size())
			return a.get(0).getAttributes().getMaxShelveDuration();
		else
			return "-";
	};

	public static int ackRequired(List<ALMD> a) {
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().isAckRequired() == a.get(0).getAttributes().isAckRequired())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isAckRequired())
				return 1;
			else
				return 2;
		} else
			return 0;
	};

	public static int latched(List<ALMD> a) {
		List<ALMD> b = a.stream().filter(e -> e.getAttributes().isLatched() == a.get(0).getAttributes().isLatched())
				.collect(Collectors.toList());
		if (a.size() == b.size()) {
			if (a.get(0).getAttributes().isLatched())
				return 1;
			else
				return 2;
		} else
			return 0;
	};

	public static String assocTag1(List<ALMD> a) {
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().getAssocTag1().equals(a.get(0).getAttributes().getAssocTag1()))
				.collect(Collectors.toList());
		if (a.size() == b.size())
			return a.get(0).getAttributes().getAssocTag1();
		else
			return "-";
	};

	public static String assocTag2(List<ALMD> a) {
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().getAssocTag2().equals(a.get(0).getAttributes().getAssocTag2()))
				.collect(Collectors.toList());
		if (a.size() == b.size())
			return a.get(0).getAttributes().getAssocTag2();
		else
			return "-";
	};

	public static String assocTag3(List<ALMD> a) {
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().getAssocTag3().equals(a.get(0).getAttributes().getAssocTag3()))
				.collect(Collectors.toList());
		if (a.size() == b.size())
			return a.get(0).getAttributes().getAssocTag3();
		else
			return "-";
	};

	public static String assocTag4(List<ALMD> a) {
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().getAssocTag4().equals(a.get(0).getAttributes().getAssocTag4()))
				.collect(Collectors.toList());
		if (a.size() == b.size())
			return a.get(0).getAttributes().getAssocTag4();
		else
			return "-";
	};

	public static String alarmClass(List<ALMD> a) {
		List<ALMD> b = a.stream()
				.filter(e -> e.getAttributes().getAlarmClass().equals(a.get(0).getAttributes().getAlarmClass()))
				.collect(Collectors.toList());
		if (a.size() == b.size())
			return a.get(0).getAttributes().getAlarmClass();
		else
			return "-";
	};

	public static HashMap<String, String> langDesc(List<ALMD> a) {

		HashMap<String, String> o = new HashMap<String, String>();
		HashMap<String, Integer> p = new HashMap<String, Integer>();
		HashMap<String, Boolean> q = new HashMap<String, Boolean>();

		// count how many entries each language is in
		a.stream().forEach(e -> {
			e.getLangDesc().entrySet().stream().forEach(f -> {
				if (!p.containsKey(f.getKey())) {
					p.put(f.getKey(), 1);
				} else {
					p.put(f.getKey(), p.get(f.getKey()) + 1);
				}
			});
		});

		// make a list of languages that are in every entry
		p.entrySet().stream().forEach(e -> {
			if (e.getValue() == a.size())
				q.put(e.getKey(), true);
		});

		// build output list
		a.stream().forEach(e -> {
			e.getLangDesc().entrySet().stream().forEach(f -> {
				q.entrySet().stream().forEach(g -> {
					if (g.getKey().equals(f.getKey())) {
						if (!o.containsKey(f.getKey()))
							o.put(f.getKey(), f.getValue());
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
