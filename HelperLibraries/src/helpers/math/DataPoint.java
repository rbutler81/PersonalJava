package helpers.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import helpers.BigDec;
import helpers.csvUtil.CSVWriter;

public class DataPoint implements CSVWriter, BigDecMap{

	private BigDecimal time;
	private BigDecimal dataPoint;
	private BigDecimal timeDelta = null;
	private Map<Integer, BigDecimal> derivatives;
	private Map<Integer, BigDecimal> aux;
	
	public DataPoint() {
		this.time = new BigDecimal("0").setScale(0, RoundingMode.HALF_UP);
		this.dataPoint = new BigDecimal("0").setScale(0, RoundingMode.HALF_UP);
		this.derivatives = new HashMap<Integer, BigDecimal>();
		this.aux = new HashMap<Integer, BigDecimal>();
	}
	
	public DataPoint(double time, BigDecimal dataPoint) {
		this.time = new BigDecimal(time).setScale(1, RoundingMode.HALF_UP);
		this.dataPoint = dataPoint;
		this.derivatives = new HashMap<Integer, BigDecimal>();
		this.aux = new HashMap<Integer, BigDecimal>();
	}
	
	public DataPoint(DataPoint dp) {
		
		this.time = dp.getTime();
		this.dataPoint = dp.getDataPoint();
		this.timeDelta = dp.getTimeDelta();
		
		Map<Integer, BigDecimal> m = new HashMap<Integer, BigDecimal>();
		for (Entry<Integer, BigDecimal> e : dp.getDerivatives().entrySet()) {
			int k = e.getKey();
			BigDecimal v = e.getValue();
			m.put(k, v);
		}
		this.derivatives = m;
		
		Map<Integer, BigDecimal> n = new HashMap<Integer, BigDecimal>();
		for (Entry<Integer, BigDecimal> e : dp.getAux().entrySet()) {
			int k = e.getKey();
			BigDecimal v = e.getValue();
			n.put(k, v);
		}
		this.aux = n;
	}

	public BigDecimal getTime() { return time; }

	public DataPoint setTime(BigDecimal time) { this.time = time; return this; }

	public BigDecimal getDataPoint() { return dataPoint; }

	public DataPoint setDataPoint(BigDecimal dataPoint) { this.dataPoint = dataPoint; return this; }
	
	public Map<Integer, BigDecimal> getDerivatives() { return derivatives; }

	public DataPoint setDerivatives(Map<Integer, BigDecimal> derivatives) { this.derivatives = derivatives; return this; }
	
	public DataPoint setTimeDelta(BigDecimal d) { this.timeDelta = d; return this; }
	
	public BigDecimal getTimeDelta() { return this.timeDelta; };
	
	public Map<Integer, BigDecimal> getAux() { return aux; }

	public DataPoint setAux(Map<Integer, BigDecimal> aux) { this.aux = aux; return this; }

	public static List<DataPoint> calcDelta(List<DataPoint> l) {
		
		DataPoint dp = new DataPoint(l.get(0));
		
		List<DataPoint> r = new ArrayList<DataPoint>();
		r.add(dp);
		for (int i = 1; i < l.size(); i++) {
			l.get(i).setTimeDelta(l.get(i).getTime().subtract(l.get(i - 1).getTime()));
			dp = new DataPoint(l.get(i));
			r.add(dp);
		}
		return r;
	}
	
	public static void calcDerivatives(List<DataPoint> l, int upTo) {
		
		for (DataPoint e : l) {
			e.getDerivatives().clear();
		}
		
		for (int i = 1; i <= upTo; i++) {
			for (int j = i; j < l.size(); j++) {
				if (i == 1) {
					l.get(j).getDerivatives().put(i, 
							CalcOps.calcDerivative(l.get(j - 1).getTime(), l.get(j).getTime(), l.get(j - 1).getDataPoint(), l.get(j).getDataPoint()));
				}
				else {
					l.get(j).getDerivatives().put(i, 
							CalcOps.calcDerivative(l.get(j - 1).getTime(), l.get(j).getTime(), l.get(j - 1).getDerivatives().get(i - 1), l.get(j).getDerivatives().get(i - 1)));
				}
			}
		}
	}
	
	public static List<DataPoint> removeDeltasLT(List<DataPoint> l, double lt, int scale) {
		
		List<DataPoint> r = new ArrayList<DataPoint>();
		
		r = calcDelta(l);
		
		r = r.stream()
			.filter(e -> e.getTimeDelta() == null || BigDec.GT(e.getTimeDelta(), BigDec.valueOf(lt, scale)))
			.collect(Collectors.toList());
		
		r = calcDelta(r);
		
		return r;
		
	}

	public static List<DataPoint> subListByTime(List<DataPoint> l, BigDecimal t) {
		
		List<DataPoint> r = new ArrayList<DataPoint>();
		int lastFound = 0;
		BigDecimal nextTarget = l.get(0).getTime().add(t);
		r.add(new DataPoint().setTime(l.get(0).getTime()).setDataPoint(BigDec.zero()));
		
		for (int i = 0; i < l.size(); i++) {
			if (BigDec.LE(nextTarget, l.get(i).getTime())) {
				DataPoint dp = new DataPoint().setTime(l.get(i).getTime());
				dp.setDataPoint(BigDec.percentDiff(l.get(lastFound).getDataPoint(), l.get(i).getDataPoint()));
				r.add(dp);
				
				nextTarget = l.get(i).getTime().add(t);
				lastFound = i;
			}
		}
		return r;
	}
	
	@Override
	public Map<Integer, BigDecimal> toBigDecMap() {
		Map<Integer, BigDecimal> m = new HashMap<Integer, BigDecimal>();
		
		derivatives.entrySet().stream()
			.sorted(Map.Entry.comparingByKey())
			.forEach(e -> m.put(e.getKey() - 1, e.getValue()));
		
		aux.entrySet().stream()
			.sorted(Map.Entry.comparingByKey())
			.forEach(e -> m.put(e.getKey() + derivatives.entrySet().size() - 1, e.getValue()));
		
		m.put(100, time);
		m.put(101, dataPoint);
		
		return m;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String[]> toCSV(List<?> l) {
		
		List<String[]> r = new ArrayList<String[]>();
		List<DataPoint> dp = null;
		
		if (l.size() > 0) { 
		
			dp = (List<DataPoint>) l;
			
			int derivatives = dp.get(dp.size() - 1).getDerivatives().entrySet().size();
			if (derivatives < 0) derivatives = 0;
			int aux = dp.get(dp.size() - 1).getAux().entrySet().size();
			if (aux < 0) aux = 0;
			
			String[] l1 = new String[2 + derivatives + aux];
			l1[0] = "time";
			l1[1] = "dataPoint";
			if (derivatives > 0) {
				for (int i = 2; i < 2 + derivatives; i++) {
					int j = i - 1;
					l1[i] = "Derivative " + j;
				}
			}
			if (aux > 0) {
				for (int i = 2 + derivatives; i < 2 + derivatives + aux; i++) {
					int j = i - 1 - derivatives;
					l1[i] = "Aux " + j;
				}
			}
			r.add(l1);
			
			int maxAux = 0;
			for (DataPoint e : dp) {
				if (e.getAux().entrySet().size() > maxAux) maxAux = e.getAux().entrySet().size();
			}
			
			for (DataPoint e : dp) {
				
				String[] l2 = new String[2 + derivatives + maxAux];
				l2[0] = e.getTime().toPlainString();
				l2[1] = e.getDataPoint().toPlainString();
				for (int i = 2; i < 2 + derivatives + aux; i++) {
					l2[i] = "";
				}
				
				for (int i = 1; i <= e.getDerivatives().entrySet().size(); i++) {
					l2[i + 1] = e.getDerivatives().get(i).toPlainString();
				}
				
				for (Entry<Integer, BigDecimal> f : e.getAux().entrySet()) {
					l2[1 + derivatives + f.getKey()] = f.getValue().toPlainString();
				}
				r.add(l2);
			}
		}
		return r;
	}

	
}


