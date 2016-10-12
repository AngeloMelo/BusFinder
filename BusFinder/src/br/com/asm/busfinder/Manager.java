package br.com.asm.busfinder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.text.TextUtils;

public class Manager {

	private MainActivity mainActivityRef;

	public Manager(MainActivity mainActivityRef) 
	{
		this.mainActivityRef = mainActivityRef;
	}
	
	public void findRoutes(String streetName)
	{
		if(TextUtils.isEmpty(streetName))
		{
			this.mainActivityRef.showAlertDialog();
			
			return;
		}
		
		try	{
			
			new BusFinderTask().execute(streetName);
		}
		catch(Exception e)
		{
			this.mainActivityRef.showErrorDialog();
		}
	}

	private class BusFinderTask extends AsyncTask<String, Void, String[]>
	{
		private Exception error;
		
		@Override 
		protected void onPreExecute() { 
			
			mainActivityRef.showProgressDialog();
		}

		@Override
		protected String[] doInBackground(String... params) {
			
			try{
				
				String key = params[0];
								
				JSONArray resultingRoutes = AppGluRequester.findRoutesByStopName(key);
				
				String[] routes = new String[resultingRoutes.length()];
				
				for (int i = 0; i < resultingRoutes.length(); i++) {
					
					JSONObject route = resultingRoutes.getJSONObject(i); 
					
					routes[i] = route.toString(); 
				}
				
				return routes; 				
			}
			catch(Exception e){
				
				this.error = e;
				return null;
				
			}
		}
		
		/**
		 * TODO
		 */
		@Override 
		protected void onPostExecute(String[] results) { 

			if(this.error != null){
				
				mainActivityRef.showErrorDialog();
				return;
			}
				
			
			Route[] routes = new Route[results.length];
			
			if(results != null){
				
				if(results.length == 0){
					
					mainActivityRef.showNoResultsDialog();
				}
				else{
					
					try {
						
						for(int i = 0; i< results.length; i++)
						{
							String strJson = results[i];
							JSONObject jsonObject;
							jsonObject = new JSONObject(strJson);
							int id = jsonObject.getInt("id");
							String shortName = jsonObject.getString("shortName"); 
							String longName = jsonObject.getString("longName"); 
							
							Route route = new Route();
							route.setLongName(longName);
							route.setShortName(shortName);
							route.setRouteId(id);
							
							routes[i] = route;
						}
						
						mainActivityRef.showRoteResults(routes);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} 
		}		
	}
}
