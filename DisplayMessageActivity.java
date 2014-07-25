package com.example.myapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity {

	
	public List<String> hotelarray=new ArrayList<String>();
	public int length = 0;
	public double[] price;
	public double[] average;
	double prclimit = 0;
	double avglimit = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the message from the intent
		    Intent intent = getIntent();
		    String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		    //@SuppressWarnings("unused")
		   String m2 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);
			
		
		   avglimit = Double.parseDouble(message);
		   prclimit = Double.parseDouble(m2);
		   
			Log.v("MyApp","I am here");
			int j=0;
		//	 new ProgressTask(MainActivity.this).execute();
		Parser a = new Parser();
		a.execute();
		try {
			a.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 TextView textView1 = new TextView(this);
		  
		 TextView selection = (TextView) findViewById(R.id.textView1);
		String mssg = "";
			System.out.println("and here is ok ");  
			for(int i = 0;i<length;i++){
				if(price[i]!=-1){
				
					mssg += "  " + hotelarray.get(i).replaceAll( " Smart Deal"," ") + " with score " + average[i] + " & price "+price[i]+"€"+"\n";
				
				}
				
			}

			if(mssg == "")   mssg = "Sorry no results.Try again!";
			 textView1.setText(mssg);
			 setContentView(textView1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_display_message, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	 class Parser extends AsyncTask<Void,Void, Void>{
			//   task.get(1000, TimeUnit.MILLISECONDS);	       
		 	   String res ="";
			   List<String> avgarray = new ArrayList<String>();
			   List<String> pricearray = new ArrayList<String>();
				Elements table;
				//@SuppressWarnings("unused")
				
				Elements table2;
					int j = 0;
					int pricecount=0;
			
					final Calendar c = Calendar.getInstance();
				    
				    int mDay = c.get(Calendar.DAY_OF_MONTH);
				    int month_int = c.get(Calendar.MONTH);
				    String month = String.valueOf(month_int+1);
					 String CheckInDay = String.valueOf(mDay);
					 String CheckOutDay = String.valueOf(mDay+1);
			
					int offsetval = 0;
					String offset=String.valueOf(offsetval);
				
					private ProgressDialog dialog;
					
		
					
				@Override
				protected Void doInBackground(Void... arg0) { 
						Document doc = null;
						int flag = 0;
						int count = 0;
						try {
							while(true){
							res = "http://www.booking.com/searchresults.en-gb.html?sid=39bc701e4168191d3d485fbadd6a6dd9;dcid=1;ac_pageview_id=110a4c3b45a100df;checkin_monthday="+CheckInDay+";checkin_year_month=2013-"+month+";checkout_monthday="+CheckOutDay+";checkout_year_month=2013-"+month+";class_interval=1;csflt=%7B%7D;dest_id=-829252;dest_type=city;inac=0;order=score;radius=1;redirected_from_city=0;redirected_from_landmark=0;review_score_group=empty;score_min=0;si=ai%2Cco%2Cci%2Cre%2Cdi;ss=Thessalon%C3%ADki%2C%20Greece;ss_all=0;ssb=empty;rows=20;offset="+offset;

							doc = Jsoup.connect(res).userAgent("Mozilla").get();
							
							table = doc.select("table[class=hotellist]  ");
						
						Iterator<org.jsoup.nodes.Element> it2 = table.select("span.average").iterator();

						while(it2.hasNext()){
							

							avgarray.add(it2.next().text());
							
							  if((Double.parseDouble(avgarray.get(length)))<avglimit){
								  flag = 1;
								  break;
							  }

							  length++;

							  if(!it2.hasNext()){ 
							    break;
							  }
			
							 
							}
						System.out.println("::::::::::"+month);
						System.out.println("::::::::::"+length);
						count=0;
						Iterator<org.jsoup.nodes.Element> it = table.select("h3").iterator();
							while(it.hasNext()){
							
								hotelarray.add(it.next().text());
				
								  if(!it.hasNext()||count==length){ 
								    break;
								  }
								 
								  count++;
								}
							

							Iterator<org.jsoup.nodes.Element> it3 = doc.select("div.room_details :eq(0) td.roomprice strong").iterator();
							count=0;

						while(it3.hasNext()){
								
								pricearray.add(it3.next().text());
						
								 if(!it3.hasNext()||count==length){ 
								    break;
								  }
						
								 count++;
						}	 
							
						if(flag == 1) break;
							j++;
						
							offsetval += 20;
							offset=String.valueOf(offsetval);
						
						
							}
						
							
						
				
							average = new double[length];
							price = new double[length];
					
							for(int i=0;i<length;i++){
								average[i]=(Double.parseDouble(avgarray.get(i)));
				
							price[i]=Double.parseDouble(pricearray.get(i).substring(2));
								
							}
						int turns = length-1;
						while(turns>0){
						 for(int j=turns;j>=0;j--)	{
							
							if(price[turns]>prclimit||price[turns]>price[j]){
								
								price[turns]=-1;
							
							}
							if(average[turns]==average[j]){
								if(price[turns]>price[j]) price[turns]=-1;
								
								else if(price[turns]<price[j]) price[j] = -1;
								
							}
						 }
						 turns--;
						 if(turns==0&&price[turns]>prclimit) price[turns]=-1;
						}
						
						
						} catch (IOException e) {
							// TODO Auto-generated catch block
								e.printStackTrace();
							
				
						}		
						return null;	
		   
		  
		   }
	 } 
}
