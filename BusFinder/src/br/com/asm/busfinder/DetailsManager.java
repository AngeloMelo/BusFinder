package br.com.asm.busfinder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class DetailsManager {

	private RouteDetailsActivity activityRef;

	public DetailsManager(RouteDetailsActivity mainActivityRef){
		
		this.activityRef = mainActivityRef;
	}
	
	public void findStopsByRouteId(int routeId)	{
		
		new StopsFinderTask().execute(routeId);
	}
		
	public void findDeparturesByRouteId(int routeId) {

		new DeparturesFinderTask().execute(routeId);		
	}

	/**
	 * 
	 * @author angelo
	 * An AsyncTask for requesting in parallel the stops of a route
	 */
	private class StopsFinderTask extends AsyncTask<Integer, Void, String[]> {

		private Exception error = null;
		
		@Override 
		protected void onPreExecute() { 
			
			activityRef.showProgressDialog();
		}

		@Override
		protected String[] doInBackground(Integer... params) {
			
			try{
				
				int key = params[0];
								
				JSONArray resultingStops = AppGluRequester.findStopsByRouteId(key);
				
				String[] stops = new String[resultingStops.length()];
				
				for (int i = 0; i < resultingStops.length(); i++) {
					
					JSONObject stop = resultingStops.getJSONObject(i); 
					
					stops[i] = stop.getString("sequence") + " - " + stop.getString("name"); 
				}
				return stops; 
				
			}
			catch(Exception e){
				
				this.error = e;
				return null;
				
			}
		}
		

		@Override 
		protected void onPostExecute(String[] results) { 
			
			if(this.error != null){
				
				activityRef.showErrorDialog();
				return;
			}

			if(results != null){ 

				activityRef.showStopResults(results);
			} 
		}		
	}


	/**
	 * 
	 * @author angelo
	 * An AsyncTask for requesting in parallel the departures' schedule of a route
	 */
	private class DeparturesFinderTask extends AsyncTask<Integer, Void, String[]> {

		private Exception error = null;
		
		@Override 
		protected void onPreExecute() { 
			
			activityRef.showProgressDialog();
		}

		@Override
		protected String[] doInBackground(Integer... params) {
			
			try{
				
				int key = params[0];
								
				JSONArray resultingDepartures = AppGluRequester.findDeparturesByRouteId(key);
				
				String[] departures = new String[resultingDepartures.length()];
				
				for (int i = 0; i < resultingDepartures.length(); i++) {
					
					JSONObject departure = resultingDepartures.getJSONObject(i); 
					
					departures[i] = departure.toString(); 
				}
				return departures; 
				
			}
			catch(Exception e){
				
				this.error = e;
				return null;
			}
		}
		
		
		@Override 
		protected void onPostExecute(String[] results) { 
			
			if(this.error != null){
				
				activityRef.showErrorDialog();
				return;
			}

			List<String> weekdayDeps = new ArrayList<String>();
			List<String> saturdayDeps = new ArrayList<String>();
			List<String> sundayDeps = new ArrayList<String>();
			
			if(results != null){ 

				try {
				
					for(int i = 0; i< results.length; i++)
					{
						String strJson = results[i];
						JSONObject jsonObject = new JSONObject(strJson);
						
						String calendarType = jsonObject.getString("calendar");
						String time = jsonObject.getString("time");
						
						if(calendarType.equals("WEEKDAY")){
							
							weekdayDeps.add(time);
						}
						else if(calendarType.equals("SATURDAY")){
							
							saturdayDeps.add(time);
						}
						else if(calendarType.equals("SUNDAY")){
							
							sundayDeps.add(time);
						}
					}

					activityRef.showDepartureResults(weekdayDeps, saturdayDeps, sundayDeps);
				
				} catch (JSONException e) {
					
					activityRef.showErrorDialog();
					return;
				}
			} 
		}		
	}
}
