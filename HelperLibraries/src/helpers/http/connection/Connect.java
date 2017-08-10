package helpers.http.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Connect {

	public static String httpsGet(String address, boolean debug) {
	    String jsonResponse = "";
	    HttpsURLConnection c = null;
	    try {
	        URL u = new URL("https://" + address); 
	        c = (HttpsURLConnection)u.openConnection();
	        c.setConnectTimeout(10000);
	        c.setReadTimeout(15000);
	        c.setRequestMethod("GET");
	        c.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
	        
	        BufferedReader br = null;
	        if (debug) System.out.println("Connection Request Code: " + c.getResponseCode());
	        if(c.getResponseCode() >= 400) {
	            if (debug) System.out.println("Unable to connect to API");
	        }
	        
	        br = new BufferedReader(new InputStreamReader((c.getInputStream())));
	        String line;
	        while ((line = br.readLine()) != null) jsonResponse += line;
	        if (debug) System.out.println(jsonResponse);
	        
	    } catch (Exception e) {
	    	if (debug) System.out.println("get() Exception: " + e);
	    } finally {
	        c.disconnect();
	    }

	    return jsonResponse;        
	}
}
