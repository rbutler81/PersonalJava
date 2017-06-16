import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.kraken.api.json.pub.TickerResponse;
import com.kraken.api.userClass.KrakenApi;
import com.kraken.api.userClass.KrakenMethods;

import helpers.Console;
import helpers.KeyValuePair;
import helpers.Timer;
import net.pushover.client.PushoverClient;
import net.pushover.client.PushoverException;
import net.pushover.client.PushoverMessage;
import net.pushover.client.PushoverRestClient;


public class Main {

	public static void main(String[] args) {
		
		boolean firstAlert = true;
		
		List<KeyValuePair> kv = new ArrayList<KeyValuePair>();
		Gson g = new Gson();
		KrakenApi k = new KrakenApi();
		kv.add(new KeyValuePair("pair", "XETHZUSD"));		
		
		System.out.println("Price to send alert: ");
		double priceAlert = Double.parseDouble(Console.getConsole());
		System.out.println("Heartbeat time (min): ");
		double heartBeat = Double.parseDouble(Console.getConsole());
		System.out.println();
		
		Timer t1 = new Timer(1000*3);
		Timer t2 = new Timer(1000*60);
		Timer t3 = new Timer(1000*60*heartBeat);
		
		PushoverClient client = new PushoverRestClient();
				
		t1.start();
		t3.start();
		
		while (true){
		
			if (t3.isDone()){
				t3.start();
				try {
					client.pushMessage(PushoverMessage.builderWithApiToken("aq2ishcjtf4hz42f1r3tcr4q7z8fxx")
					        .setUserId("a3KqbKXy1SGBf9l7oLoR3uVvZJmlrN")
					        .setMessage("HeartBeat")
					        .build());
				} catch (PushoverException e1) {
					e1.printStackTrace();
				}
			}
			
			if (t1.isDone()){
				t1.start();
				TickerResponse tr = new TickerResponse();
				try{
					tr = g.fromJson(k.call(KrakenMethods.TickerInfo, kv), TickerResponse.class);
					List<String> p = tr.result.XETHZUSD.last;
					System.out.println("Last Price: " + p.get(0));
					System.out.println();
					double lastPrice = Double.parseDouble((String)p.get(0));
					
					if ((lastPrice <= priceAlert) && (firstAlert || t2.isDone())){
						t2.start();
						firstAlert = false;
						try {
							client.pushMessage(PushoverMessage.builderWithApiToken("aq2ishcjtf4hz42f1r3tcr4q7z8fxx")
							        .setUserId("a3KqbKXy1SGBf9l7oLoR3uVvZJmlrN")
							        .setMessage("ETH Price Alert: " + lastPrice)
							        .build());
						} catch (PushoverException e1) {
							e1.printStackTrace();
						}
					}
					
				}
				catch (Exception e){
					System.out.println(e);
					System.out.println();
				}
				
			}
		}
	}

}
