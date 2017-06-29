package com.quadrigacx.bot;

import java.io.IOException;
import java.net.MalformedURLException;

import com.quadrigacx.exchange.threads.CommonData;
import com.quadrigacx.exchange.bot.Messages;
import com.quadrigacx.exchange.helpers.UI;
import com.quadrigacx.exchange.threads.BuySellJuggleControlThread;
import com.quadrigacx.exchange.threads.WebOrderBookGetter;

import helpers.Console;
import helpers.RateLimiter;


public class Main {

	public static void main(String[] args) throws InterruptedException, MalformedURLException, IOException {
	
	RateLimiter rl = new RateLimiter(2200);
		
	Messages.chooseBook();
	String book = UI.getBook(Integer.parseInt(Console.getConsole()));
	System.out.println();
	Messages.setProfit();
	String minProfit = Console.getConsole();
	System.out.println();
	System.out.println("Setting some things up...");
	System.out.println();
	
	CommonData cd = new CommonData(book, rl);
	cd.getBotParams().setMinDesiredProfit(minProfit);
	
	Thread control = new Thread(new BuySellJuggleControlThread(cd), "BuySellControl");
	Thread webData = new Thread(new WebOrderBookGetter(cd), "WebPageData");
	
	control.start();
	webData.start();
		
	webData.join();
	control.join();
	
	System.out.println();
	System.out.println("Done!!");
	Messages.avgBuySellPrices(cd);
	
	}
}

