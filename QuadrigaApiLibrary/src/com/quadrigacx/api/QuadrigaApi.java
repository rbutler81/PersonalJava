package com.quadrigacx.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;



import helpers.KeyValuePair;

public class QuadrigaApi {

	protected static boolean debug = false;
	
	protected static String client = "4401";
	protected static String apiKey = "YubkdTpuCW";
	protected static String apiSecret = "197084474569da5d818c680aad965b2d";
		
	protected static String domain = "https://api.quadrigacx.com";
	protected static String version = "v2";
	     
    private QuadrigaMethods callType;
    
    String nonce = "";
    String signature = "";
    String data = "";
    List<KeyValuePair> params = new ArrayList<KeyValuePair>();
    
    private String path = "";
    private String address = "";
    
    
    public String call(QuadrigaMethods callType) throws Exception{
		List<KeyValuePair> kv = new ArrayList<KeyValuePair>();
		this.callType = callType;
		return call(callType, kv);
	}
	
	public String call(QuadrigaMethods callType, List<KeyValuePair> params) throws Exception {
		this.params = params;
		this.callType = callType;
		
		if (debug) System.out.println("Sending API call...");
	    
		path = "/" + version + "/" + callType.getPath();
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
	    	signature = Signature.encode(apiSecret, nonce + client + apiKey);
	    	
	    	data = buildJson();
	    	
	    }
	    if (debug) {System.out.println("Address: " + address); System.out.println("Path: " + path);}
	    if (debug) if (callType.getType() == "private") System.out.println("Json String: " + data);
	    
	    return post(address, data);
		
	}
	
	private String post(String address, String output) {
	    String jsonResponse = "";
	    HttpsURLConnection c = null;
	    try {
	        URL u = new URL(address); 
	        c = (HttpsURLConnection)u.openConnection();
	        c.setConnectTimeout(10000);
	        c.setReadTimeout(15000);
	        if (callType.getType() == "private"){
	        	c.setRequestMethod("POST");
	        	c.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
	        	c.setRequestProperty("Content-Type", "application/json; charset=utf-8");
	        	
	        	c.setDoOutput(true);
	        	c.setDoInput(true);
	        		        
	        	BufferedWriter os = new BufferedWriter(new OutputStreamWriter(c.getOutputStream()));
	        	//DataOutputStream os = new DataOutputStream(c.getOutputStream());
	        	os.write(output);
	        	os.flush();
	        	os.close();
	        }
	        else if (callType.getType() == "public"){
	        	c.setRequestMethod("GET");
	        	c.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
	        }
	        
	        BufferedReader br = null;
	        if (debug) System.out.println("Connection Request Code: " + c.getResponseCode());
	        if(c.getResponseCode() >= 400) {
	            if (debug) System.out.println("Unable to connect to Quadriga API");
	        }
	        
	        br = new BufferedReader(new InputStreamReader((c.getInputStream())));
	        String line;
	        while ((line = br.readLine()) != null)
	            jsonResponse += line;
	    } catch (Exception e) {
	    	if (debug) System.out.println("post() Exception: " + e);
	    } finally {
	        c.disconnect();
	    }

return jsonResponse;        
	}
	
	private String buildJson(){
		
		String i = "{";
		
		i = i + "\"key\":" + "\"" + apiKey + "\",";
		i = i + "\"nonce\":" + nonce + ",";
		i = i + "\"signature\":" + "\"" + signature + "\"";
		
		if (params.size() > 0){
			i = i + ",";
			for (int x = 0; x < params.size(); x++){
				i = i + "\"" + params.get(x).getKey() + "\"" + ":" + "\"" + params.get(x).getValue() + "\"";
				if (x < params.size() - 1){
					i = i + ",";
				}
			}
		}
		
		i = i + "}";
		
		return i;
	}
	
}
