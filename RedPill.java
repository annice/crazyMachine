package com.rally.redpill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.ReadableInstant;
import org.joda.time.Seconds;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;
import org.joda.time.Years;
import org.joda.time.field.MillisDurationField;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.joda.time.tz.UTCProvider;
import java.util.HashMap;

	public class RedPill {
		
     static HashMap<String, Integer> hmapfeb=new HashMap<String, Integer>();
	  private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }

	  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	    
	      jsonText="{\nrallyCollection:"+jsonText+"\n}";
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	  }

	  public static void main(String[] args) throws IOException, JSONException, ParseException {
	    
		  JSONObject json = readJsonFromUrl("http://maccherone.com/share/1000-snapshots-overlap-with-Feb-2012.json");
	  
	    JSONArray arr=json.getJSONArray("rallyCollection");
	 
	    DateTimeFormatter dtf=ISODateTimeFormat.dateTime();
	    LocalDateTime ldtfrom=null;
	    LocalDateTime ldtto=null;
	    int ch=0;
		do{
			System.out.println("--Menu--All duration values are presented in number of hours!");
			System.out.println("1.During the month of February 2012, how long is spent on each piece of work");
			System.out.println("2.What if we only count time(in February) during the working hours of Mon-Fri, 9am-5pm (Zulu time)?");
			System.out.println("3.How long is spent in total in each ScheduleState (across all pieces of work), taking account of working hours?");
			System.out.println("Enter Your Choice");
		Scanner input = new Scanner(System.in);
		
		Iterator it=null;
		int choice=input.nextInt();
	    switch (choice)
	    {
	    case 1:
	    	hmapfeb=new HashMap<String, Integer>();
	    for(int j=0;j<arr.length();j++){
	    	json=(JSONObject) arr.get(j);
	    	
	    		 ldtfrom=dtf.parseLocalDateTime(json.get("_ValidFrom").toString());
	    		   ldtto=dtf.parseLocalDateTime(json.get("_ValidTo").toString());
	    		  
	    		   calculateDurationinFeb( ldtfrom, ldtto,json.get("ObjectID").toString(),json.get("_ValidTo").toString());
	    	   	   
	    }
	    
	    
	     it = hmapfeb.entrySet().iterator();
	    while (it.hasNext()) {
	    	Map.Entry pairs = (Map.Entry)it.next();
	        System.out.println(pairs.getKey() + " = " + Integer.parseInt(pairs.getValue().toString())/(3600));
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    break;
	    
	    
	    case 2:
	    	hmapfeb=new HashMap<String, Integer>();

	    	for(int j=0;j<arr.length();j++){
		    	json=(JSONObject) arr.get(j);
		    	
		    		 ldtfrom=dtf.parseLocalDateTime(json.get("_ValidFrom").toString());
		    		   ldtto=dtf.parseLocalDateTime(json.get("_ValidTo").toString());
		    		  
		    		   calculateDurationinFebWorkingHours( ldtfrom, ldtto,json.get("ObjectID").toString(),json.get("_ValidTo").toString());
				
		    }
		    
		    
		     it = hmapfeb.entrySet().iterator();
		    while (it.hasNext()) {
		    	Map.Entry pairs = (Map.Entry)it.next();
		        System.out.println(pairs.getKey() + " = " + Integer.parseInt(pairs.getValue().toString())/(3600));
		        it.remove(); // avoids a ConcurrentModificationException
		    }
		    break;
	    case 3:
	    	hmapfeb=new HashMap<String, Integer>();
	    	for(int j=0;j<arr.length();j++){
		    	json=(JSONObject) arr.get(j);
		    	double durval=0;
		    	
		    		 ldtfrom=dtf.parseLocalDateTime(json.get("_ValidFrom").toString());
		    		   ldtto=dtf.parseLocalDateTime(json.get("_ValidTo").toString());
		    		   calculateSchedStateWorkingHours(ldtfrom, ldtto,json.get("_ValidTo").toString(),json.get("ScheduleState").toString());
				
		    }
		    
		    
		     it = hmapfeb.entrySet().iterator();
		    while (it.hasNext()) {
		    	Map.Entry pairs = (Map.Entry)it.next();
		        System.out.println(pairs.getKey() + " = " + Integer.parseInt(pairs.getValue().toString())/(3600));
		        it.remove(); // avoids a ConcurrentModificationException
		    }
		    break;
		    
		   default:
			   System.out.println("Invalid input,Please enter between 1-4");
		    }
		    
	    System.out.println("Do you want to enter the menu again,Press 1 to continue,or 0 to exit");
		 ch=input.nextInt();
		}while(ch==1);
	    
	  }
	  
	  /**
	   * 
	   * @param ldtfrom
	   * @param ldtto
	   * @param objid
	   * @param valid_to
	   * 
	   * Calculate the total duration of work done in February on each piece of work
	   */
	  
		 public static void calculateDurationinFeb(LocalDateTime ldtfrom,LocalDateTime ldtto,String objid,String valid_to){
			 int durval=0;
			 DateTime dtto=ldtto.toDateTime();
			 DateTime dtfrom=ldtfrom.toDateTime();
			 if("9999-01-01T00:00:00.000Z".equals(valid_to)){
				 dtto= new DateTime(System.currentTimeMillis(), DateTimeZone.UTC);
			 }
			 while(dtfrom.getMillis()<dtto.getMillis()){
			   if(dtfrom.getMonthOfYear()==2){
			 
				   LocalDateTime ldtfrom1= ldtfrom.withTime(00, 00, 00, 000);
				   LocalDateTime ldtfrom2= ldtfrom.withTime(23, 59, 59, 999);
				   Seconds sec=Seconds.secondsBetween(ldtfrom1, ldtfrom2);
				   durval=durval+(Integer)sec.getSeconds();
			   }
			   ldtfrom=ldtfrom.plusDays(1);
			   dtfrom=ldtfrom.toDateTime();
			 }
		 
				  if(hmapfeb.containsKey(objid)){
					  int val=Integer.parseInt(hmapfeb.get(objid).toString());
					  hmapfeb.put(objid, (val+durval));
				  }else{
					  hmapfeb.put(objid,durval);
				  }
		 }
	 
	/**
	 *  
	 * @param ldtfrom
	 * @param ldtto
	 * @param objid
	 * @param valid_to
	 * alculate the total duration of work done in February (Only Working hours) on each piece of work
	 */
	 public static void calculateDurationinFebWorkingHours(LocalDateTime ldtfrom,LocalDateTime ldtto,String objid,String valid_to){
		 int durval=0;
		 DateTime dtto=ldtto.toDateTime();
		 DateTime dtfrom=ldtfrom.toDateTime();
		 if("9999-01-01T00:00:00.000Z".equals(valid_to)){
			 dtto= new DateTime(System.currentTimeMillis(), DateTimeZone.UTC);
		 }
		 while(dtfrom.getMillis()<dtto.getMillis()){
		   if(dtfrom.getMonthOfYear()==2)
		   if(dtfrom.dayOfWeek().get()!=5 && dtfrom.dayOfWeek().get()!=6){
			   LocalDateTime ldtfrom1= ldtfrom.withTime(9, 00, 00, 000);
			   LocalDateTime ldtfrom2= ldtfrom.withTime(17, 00, 00, 000);
			   Seconds sec=Seconds.secondsBetween(ldtfrom1, ldtfrom2);
			   durval=durval+(Integer)sec.getSeconds();
		   }
		   ldtfrom=ldtfrom.plusDays(1);
		   dtfrom=ldtfrom.toDateTime();
		 }
			  if(hmapfeb.containsKey(objid)){
				  int val=Integer.parseInt(hmapfeb.get(objid).toString());
				  hmapfeb.put(objid, (val+durval));
			  }else{
				  hmapfeb.put(objid, durval);
			  }
	 }
	 
/**
 *  
 * @param ldtfrom
 * @param ldtto
 * @param valid_to
 * @param state
 * alculate the total duration of work done on each scheduled state(Only working hours)
 */
	 public static void calculateSchedStateWorkingHours(LocalDateTime ldtfrom,LocalDateTime ldtto,String valid_to,String state){
		 int durval=0;
		 DateTime dtto=ldtto.toDateTime();
		 DateTime dtfrom=ldtfrom.toDateTime();
		 if("9999-01-01T00:00:00.000Z".equals(valid_to)){
			 dtto= new DateTime(System.currentTimeMillis(), DateTimeZone.UTC);
		 }
		 while(dtfrom.getMillis()<dtto.getMillis()){
		   if(dtfrom.getMonthOfYear()==2)
		   if(dtfrom.dayOfWeek().get()!=5 && dtfrom.dayOfWeek().get()!=6){
			   LocalDateTime ldtfrom1= ldtfrom.withTime(9, 00, 00, 000);
			   LocalDateTime ldtfrom2= ldtfrom.withTime(17, 00, 00, 000);
			   Seconds sec=Seconds.secondsBetween(ldtfrom1, ldtfrom2);
			   durval=durval+(Integer)sec.getSeconds();
		   }
		   ldtfrom=ldtfrom.plusDays(1);
		   dtfrom=ldtfrom.toDateTime();
		 }
			  if(hmapfeb.containsKey(state)){
				  int val=Integer.parseInt(hmapfeb.get(state).toString());
				  hmapfeb.put(state, (val+durval));
			  }else{
				  hmapfeb.put(state, durval);
			  }
	 }
	}
	
