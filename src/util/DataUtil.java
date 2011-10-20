package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {
	
	public static Date stringBrToDate(String dataBrasileira) {  
		   
		Date data = null;  
		
		try 
		{  
			DateFormat dtOutput = new SimpleDateFormat("dd/MM/yyyy");
			data = dtOutput.parse(dataBrasileira);
		}
		catch (ParseException e) 
		{
			e.printStackTrace();  
		}
		return data;  
	}
	
	public static String dateUsToStringBr(Date dataAmericana) {  
		 
		String dataString = "";
	    
	    try 
	    {  
	    	SimpleDateFormat in  = new SimpleDateFormat("yyyy-MM-dd");  
	    	SimpleDateFormat out = new SimpleDateFormat("dd/MM/yyyy");  
	    	  
	    	dataString = out.format(in.parse(dataAmericana.toString()));

	    } 
	    catch (ParseException e) 
		{
			e.printStackTrace();  
		}
	    
	    return dataString;	    
	} 
	
}