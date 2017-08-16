import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {

	public static List<String[]> read(String file, String separator) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = "";
		List<String[]> o = new ArrayList<String[]>();
		
		while ((line = br.readLine()) != null){
				String[] s = line.split(separator);
				o.add(s);
		}
		br.close();
		
		return o;
	}
	
	public static void write(List<String[]> c, String file, String separator) throws IOException {
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		int arraySize = Array.getLength(c.get(0));
				
		for (int i = 0; i < c.size(); i++){
			
			String line = "";
			for (int j = 0; j < arraySize; j++){
				
				line = line + c.get(i)[j];
				if (j < (arraySize - 1)) line = line + separator;
			}
			line = line + "\n";
			bw.write(line);
		}
		
		bw.close();
	}
	
	@SuppressWarnings("unchecked")
	public static void writeObject(List<? extends CSVWriter> l, String file, String separator) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		
		Class<?> cls = l.get(0).getClass();
		Object obj = cls.newInstance();
		Method m = cls.getMethod("toCSV", List.class);
		List<String[]> i = (List<String[]>) m.invoke(obj, l);
		write(i, file, separator);
		
	}
}
