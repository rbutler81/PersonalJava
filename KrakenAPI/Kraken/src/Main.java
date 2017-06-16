import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kraken.api.json.BalanceResponse;
import com.kraken.api.userClass.KrakenApi;
import com.kraken.api.userClass.KrakenMethods;

import helpers.KeyValuePair;



public class Main {

	public static void main(String[] args) throws JsonProcessingException, IOException {
	
		List<KeyValuePair> kv = new ArrayList<KeyValuePair>();
		KrakenApi k = new KrakenApi();
		ObjectMapper mapper = new ObjectMapper();
		
		//Display Balance
		kv.clear();
		BalanceResponse sr = mapper.readValue(k.call(KrakenMethods.Balance), BalanceResponse.class);
		System.out.println();
		System.out.println("Balances:");
		for (int i = 0; i < sr.getResult().getAsset().size(); i++){
			System.out.println(sr.getResult().getAsset().get(i).getAsset() + " : " + sr.getResult().getAsset().get(i).getBalance());
		}
		System.out.println();
		
		//Add Order
		/*kv.clear();
		kv.add(new KeyValuePair("pair", "XETHZUSD"));
		kv.add(new KeyValuePair("type", "sell"));
		kv.add(new KeyValuePair("ordertype", "limit"));
		kv.add(new KeyValuePair("volume", "0.01"));
		kv.add(new KeyValuePair("price", "250.00"));
		//kv.add(new KeyValuePair("oflags", "viqc"));  //volume in base currency
		//kv.add(new KeyValuePair("oflags", "fcib"));  //fee in base currency
		//kv.add(new KeyValuePair("close[ordertype]", "limit"));
		//kv.add(new KeyValuePair("close[price]", "25.00"));
		//kv.add(new KeyValuePair("validate", "true"));
		System.out.println(k.call(KrakenMethods.AddOrder, kv));
		System.out.println();*/
		
		//Check Open Orders
		/*System.out.println(k.call(KrakenMethods.OpenOrders));
		System.out.println();*/
		
		//Cancel Order
		/*kv.clear();
		kv.add(new KeyValuePair("txid", "OTJIEG-2ZQRN-YOZUDM"));
		System.out.println(k.call(KrakenMethods.CancelOrder, kv));
		System.out.println();*/
		
		//Testing Below
		//String test = "{\"error\":[],\"result\":{\"open\":{\"OTJIEG-2ZQRN-YOZUDM\":{\"refid\":null,\"userref\":null,\"status\":\"open\",\"opentm\":1495207339.1093,\"starttm\":0,\"expiretm\":0,\"descr\":{\"pair\":\"ETHUSD\",\"type\":\"sell\",\"ordertype\":\"limit\",\"price\":\"250.00000\",\"price2\":\"0\",\"leverage\":\"none\",\"order\":\"sell 0.01000000 ETHUSD @ limit 250.00000\"},\"vol\":\"0.01000000\",\"vol_exec\":\"0.00000000\",\"cost\":\"0.00000\",\"fee\":\"0.00000\",\"price\":\"0.00000\",\"misc\":\"\",\"oflags\":\"fciq\"}}}}";

		//System.out.println(k.call(KrakenMethods.OpenOrders));
		/*OpenOrderResponse br = mapper.readValue(test, OpenOrderResponse.class);
		OpenOrder ood = br.getOrderDetails("OTJIEG-2ZQRN-YOZUDM");*/
		
		kv.clear();
		kv.add(new KeyValuePair("asset", "XETH"));
		kv.add(new KeyValuePair("method", "Ether (Hex)"));
		System.out.println(k.call(KrakenMethods.DepositAddresses, kv));
				
		System.out.println();
		System.out.println("Done");
	
	}

}
