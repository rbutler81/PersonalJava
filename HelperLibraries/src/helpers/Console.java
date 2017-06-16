package helpers;


import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Console {

	public static String getConsole(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print(">> ");
        String in = "";
        try{
        	in = br.readLine();
        }
        catch (Exception e){}
        return in;
	}
}
