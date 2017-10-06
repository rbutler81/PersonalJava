package com.cimcorp.plcUtil.almdEditor;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.cimcorp.plcUtil.almdEditor.csv.CSVLines;
import com.cimcorp.plcUtil.almdEditor.csv.CSVUtil;
import com.cimcorp.plcUtil.almdEditor.csv.CSVWriter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

public class ALMD implements CSVWriter {

	private StringProperty scope;
	private StringProperty name;
	private StringProperty description;
	private ALMDAttributes attributes;
	private StringProperty languages;
	private ObservableMap<String, String> langDesc;

	public ALMD() {
		this.scope = new SimpleStringProperty("");
		this.languages = new SimpleStringProperty("");
		this.name = new SimpleStringProperty("");
		this.description = new SimpleStringProperty("");
		this.attributes = new ALMDAttributes();
		this.langDesc = FXCollections.observableHashMap();
		this.langDesc.addListener((MapChangeListener<String, String>) m -> {
			genLanguages();
		});
	}

	public ALMD(String scope, String name, String description, ALMDAttributes attributes,
			Map<String, String> langDesc) {
		this.scope = new SimpleStringProperty(scope);
		this.name = new SimpleStringProperty(name);
		this.description = new SimpleStringProperty(description);
		this.attributes = attributes;
		this.langDesc = FXCollections.observableHashMap();

		for (Entry<String, String> e : langDesc.entrySet()) {
			this.langDesc.put(e.getKey(), e.getValue());
		}

		genLanguages();

		this.langDesc.addListener((MapChangeListener<String, String>) m -> {
			genLanguages();
		});
	}

	public ALMD(ALMD almd) {
		this.name = new SimpleStringProperty(almd.getName());
		this.scope = new SimpleStringProperty(almd.getScope());
		this.description = new SimpleStringProperty(almd.getDescription());
		this.attributes = new ALMDAttributes(almd.getAttributes());
		this.langDesc = FXCollections.observableHashMap();

		for (Entry<String, String> e : almd.getLangDesc().entrySet()) {
			this.langDesc.put(e.getKey(), e.getValue());
		}

		genLanguages();

		this.langDesc.addListener((MapChangeListener<String, String>) m -> {
			genLanguages();
		});
	}

	public Map<String, String> getLangDesc() {
		return langDesc;
	}

	public void setLangDesc(Map<String, String> langDesc) {
		this.langDesc = FXCollections.observableHashMap();
		for (Entry<String, String> e : langDesc.entrySet()) {
			this.langDesc.put(e.getKey(), e.getValue());
		}
		this.langDesc.addListener((MapChangeListener<String, String>) m -> {
			genLanguages();
		});
		genLanguages();
	}

	public StringProperty scopeProperty() {
		return this.scope;
	}

	public String getScope() {
		return this.scope.get();
	}

	public void setScope(String scope) {
		this.scopeProperty().set(scope);
		;
	}

	public StringProperty nameProperty() {
		return this.name;
	}

	public String getName() {
		return this.nameProperty().get();
	}

	public void setName(String name) {
		this.nameProperty().set(name);
	}

	public StringProperty descriptionProperty() {
		return this.description;
	}

	public String getDescription() {
		return description.get();
	}

	public void setDescription(String description) {
		this.descriptionProperty().set(description);
	}

	public ALMDAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(ALMDAttributes attributes) {
		this.attributes = attributes;
	}

	public StringProperty languagesProperty() {
		return languages;
	}

	public String getLanguages() {
		return languages.get();
	}

	// Properties from ALMDAttributes that need to binded to UI
	public BooleanProperty latchedProperty() {
		return getAttributes().latchedProperty();
	}

	public BooleanProperty ackReqProperty() {
		return getAttributes().ackRequiredProperty();
	}

	public StringProperty onDelayProperty() {
		return getAttributes().minDurationPREProperty();
	}

	public StringProperty alarmClassProperty() {
		return getAttributes().alarmClassProperty();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String[]> toCSV(List<?> l) {

		List<String[]> r = new ArrayList<String[]>();

		r.add(new String[] { "remark", "\"CSV-Import-Export\"" });
		r.add(new String[] { "remark",
				"\"Date = " + new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy").format(new Date()) + "\"" });
		r.add(new String[] { "remark", "\"Version = RSLogix 5000 v24.00\"" });
		r.add(new String[] { "remark", "\"Owner = Windows User\"" });
		r.add(new String[] { "remark", "\"Company = \"" });
		r.add(new String[] { "0.3" });

		if (l.size() > 0) {
			List<ALMD> a = (List<ALMD>) l;
			a = a.stream().sorted(Comparator.comparing(ALMD::getScope).thenComparing(ALMD::getName))
					.collect(Collectors.toList());

			String lastScope = "";
			boolean firstLoop = true;

			for (ALMD e : a) {

				if (firstLoop || !lastScope.equals(e.getScope())) {
					if (firstLoop)
						firstLoop = false;
					lastScope = e.getScope();
					r.add(new String[] { "TYPE", "SCOPE", "NAME", "DESCRIPTION", "DATATYPE", "SPECIFIER",
							"ATTRIBUTES" });
				}

				r.add(new String[] { "TAG", e.getScope(), e.getName(), "\"\"", "\"ALARM_DIGITAL\"", "\"\"",
						e.getAttributes().toString() });

				if (!e.getDescription().equals("")) {
					r.add(new String[] { "ALMMSG:en-US", e.getScope(), e.getName(), "\"" + e.getDescription() + "\"",
							"", "\"AM\"" });
				}

				for (Entry<String, String> b : e.getLangDesc().entrySet()) {
					if (!b.getKey().equals("en-US")) {
						r.add(new String[] { "ALMMSG:" + b.getKey(), e.getScope(), e.getName(),
								"\"" + b.getValue() + "\"", "", "\"AM\"" });
					}
				}
			}
		}
		return r;
	}

	private void genLanguages() {

		languages = new SimpleStringProperty("");

		if (langDesc.entrySet().size() > 0) {
			List<String> languageList = langDesc.entrySet().stream().filter(e -> e.getKey() != null)
					.sorted(Map.Entry.comparingByKey()).map(e -> e.getKey())
					.filter(e -> !e.equals("en-US") && !e.equals("")).filter(e -> e != null)
					.collect(Collectors.toList());

			for (int i = 0; i < languageList.size(); i++) {
				languages.set(languages.get() + languageList.get(i));
				if (languageList.size() - i > 1) {
					languages.set(languages.get() + ", ");
				}
			}
		}
	}

	public static List<ALMD> findALMDS(List<String[]> list) {

		// Filter the list down to only ALMD instructions and group the
		// associated lines into a map
		Map<String, CSVLines> m = new HashMap<String, CSVLines>();
		list.stream().filter(p -> Array.getLength(p) > 5).filter(p -> !p[1].endsWith("AOI"))
				.filter(p -> (p[4].contains("ALARM_DIGITAL") || p[0].contains("ALMMSG:"))).forEach(p -> {
					if (p[4].contains("ALARM_DIGITAL")) {
						CSVLines l = new CSVLines();
						if (m.containsKey(p[1] + p[2])) {
							l.setLines(m.get(p[1] + p[2]).getLines());
						}
						l.setLine(1, p);
						m.put(p[1] + p[2], l);
					} else if (p[0].contains("ALMMSG:")) {
						CSVLines l = new CSVLines();
						if (m.containsKey(p[1] + p[2])) {
							l.setLines(m.get(p[1] + p[2]).getLines());
						}
						int i = 2;
						boolean done = false;
						while (!done) {
							if (l.getLine(i) == null) {
								done = true;
							} else
								i++;
						}
						l.setLine(i, p);
						m.put(p[1] + p[2], l);
					}
				});

		// Strip off extra CSV container data and merge any columns that were
		// split because there was a "," in the "description" field
		Map<String, CSVLines> n = new HashMap<String, CSVLines>();
		m.entrySet().stream().forEach(p -> {

			CSVLines c = new CSVLines();

			if (p.getValue().getLine(1) != null) {

				String[] line1 = p.getValue().getLine(1);
				int s = Array.getLength(line1);
				line1[6] = line1[6].substring(2);
				for (int i = 7; i < s; i++) {
					line1[i] = line1[i].trim();
				}
				int k = line1[s - 1].indexOf(")\"");
				line1[s - 1] = line1[s - 1].substring(0, k);

				c.setLine(1, line1);

				if (p.getValue().getNumberOfLines() > 1) {

					for (int x = 2; x <= p.getValue().getNumberOfLines(); x++) {

						String[] nextLine = p.getValue().getLine(x);
						if ((nextLine != null) && (Array.getLength(nextLine) > 6)) {
							nextLine = CSVUtil.mergeElements(nextLine, ",", "\"", "\"");
						}
						c.setLine(x, nextLine);
					}
				}
				n.put(p.getKey(), c);
			}

		});

		// Create ALMD objects from cleaned CSV data
		List<ALMD> lo = n.entrySet().stream().map(e -> {
			ALMDAttributes aa = ALMDAttributes.parseAttributes(e.getValue().getLine(1));
			String scope = e.getValue().getLine(1)[1];
			String name = e.getValue().getLine(1)[2];
			String desc = "";
			Map<String, String> langDesc = new HashMap<String, String>();

			for (int x = 2; x <= e.getValue().getNumberOfLines(); x++) {

				String lang = e.getValue().getLine(x)[0].substring(7);
				String _desc = e.getValue().getLine(x)[3];
				_desc = _desc.substring(1, _desc.length() - 1);
				langDesc.put(lang, _desc);

				if (lang.equals("en-US"))
					desc = _desc;
			}
			return new ALMD(scope, name, desc, aa, langDesc);
		}).collect(Collectors.toList());

		return lo;
	}

}
