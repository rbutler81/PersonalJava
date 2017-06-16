package com.kraken.api.userClass;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.codec.binary.Base64;

import helpers.KeyValuePair;



public class KrakenApi {

	protected static String apiKey = "HSrXC6hcPRx5wgzQ7ubt3x9GmH9e/PjuVM7D6kdbgC7oJg5/5Cc4403o";     
    protected static String secretApiKey = "oYfiKfP3eG8TQigjdKJZV6NGLFzt8crs4Tpl7p1BEhf9gHkMGjZyx+PeqTkYqfMfgWPZ74Rac3EIt1ktqrKJCA==";  
    protected static String domain = "https://api.kraken.com";   
    protected static String version = "0"; 
    protected static boolean debug = true;
        
    
    private String privateSig, nonce, data, path, address;
    private KrakenMethods callType;
    
    public String call(KrakenMethods callType){
		List<KeyValuePair> kv = new ArrayList<KeyValuePair>();
		this.callType = callType;
		return call(callType, kv);
	}
	
	public String call(KrakenMethods callType, List<KeyValuePair> params) {
		
		this.callType = callType;
		if (debug) System.out.println("Sending API call...");
	    path = "/" + version + "/" + callType.getType() + "/" + callType.getPath();
	    address = domain + path;
	    
	    if (callType.getType() == "public"){
			if (params.size() > 0){
	        	path = path + "?";
				for (int i = 0; i < params.size(); i++){
	        		KeyValuePair kv = params.get(i);
	        		if (i > 0) path = path + "&"; 
	        		path = path + kv.key + "=" + kv.value;
	        	}
			}
			address = domain + path;
			
		}
	    else if (callType.getType() == "private"){ 
	    	nonce = String.valueOf(System.currentTimeMillis());
	    	data = "nonce=" + nonce;
	    	if (params.size() > 0){
	        	for (int i = 0; i < params.size(); i++){
	        		KeyValuePair kv = params.get(i);
	        		data = data + "&"; 
	        		data = data + kv.key + "=" + kv.value;
	        	}
	        	address = domain + path;
	        	
			}
	    	calcPrivateSig();
	    }
	    if (debug) {System.out.println("Address: " + address); System.out.println("Path: " + path);}
	    if (debug) if (callType.getType() == "private") System.out.println("Data: " + data);
	    
	    return post(address, data);
		
	}
	
	String post(String address, String output) {
	    String jsonResponse = "";
	    HttpsURLConnection c = null;
	    try {
	        URL u = new URL(address); 
	        c = (HttpsURLConnection)u.openConnection();
	        if (callType.getType() == "private"){
	        	c.setRequestMethod("POST");
	        	c.setRequestProperty("API-Key", apiKey);
	        	c.setRequestProperty("API-Sign", privateSig);
	        	c.setDoOutput(true);
	        
	        	DataOutputStream os = new DataOutputStream(c.getOutputStream());
	        	os.writeBytes(output);
	        	os.flush();
	        	os.close();
	        }
	        BufferedReader br = null;
	        if (debug) System.out.println("Connection Request Code: " + c.getResponseCode());
	        if(c.getResponseCode() >= 400) {
	            if (debug) System.out.println("Unable to connect to Kraken API");
	        }
	        br = new BufferedReader(new InputStreamReader((c.getInputStream())));
	        String line;
	        while ((line = br.readLine()) != null)
	            jsonResponse += line;
	    } catch (Exception x) {
	    	if (debug) System.out.println("post() Exception: " + x);
	    } finally {
	        c.disconnect();
	    }
	    return jsonResponse;        
	}
	
	private void calcPrivateSig(){
		privateSig = "";
	    try {	    	
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        md.update((nonce + data).getBytes());
	        Mac mac = Mac.getInstance("HmacSHA512");
	        mac.init(new SecretKeySpec(Base64.decodeBase64(secretApiKey.getBytes()), "HmacSHA512"));
	        mac.update(path.getBytes());
	        privateSig = new String(Base64.encodeBase64(mac.doFinal(md.digest())));
	    } catch(Exception e) {}
	}
	
}
