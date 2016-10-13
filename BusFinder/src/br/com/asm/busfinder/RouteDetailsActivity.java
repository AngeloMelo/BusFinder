package br.com.asm.busfinder;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * class used to maintain the route details activity
 * @author angelo
 *
 */
public class RouteDetailsActivity extends Activity {
	
	private TextView tvRouteName;
	private DetailsManager manager;
	private Button btBack;
	private ListView lvStops;
	private ListView lvDepsWeekday;
	private ListView lvDepsSaturday;
	private ListView lvDepsSunday;
	private AlertDialog errorDialog;
	
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_details);
	
		manager = new DetailsManager(this);
		setUpUI();
		loadData();
	}
	
    /**
     * Configures the UI fields and creates the dialogs used in RouteDetailsActivity
     */
	private void setUpUI() {
					
		Bundle b = getIntent().getExtras();
		
		tvRouteName = (TextView) findViewById(R.id.tvRouteName);
		tvRouteName.setText("Route: " + b.getString("route"));

		btBack = (Button) findViewById(R.id.btBack);
		lvStops = (ListView) findViewById(R.id.lvStops);
		
		lvDepsWeekday = (ListView) findViewById(R.id.lvDepsWeekday);
		lvDepsSaturday = (ListView) findViewById(R.id.lvDepsSaturday);
		lvDepsSunday = (ListView) findViewById(R.id.lvDepsSunday);
		
		btBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				closeActivity();
				
			}
		});
		dialog = new ProgressDialog(RouteDetailsActivity.this); 
		
		this.errorDialog = createErrorDialog();
	}
	
	/**
	 * creates an alertDialog to show an error message
	 * @return
	 */
	private AlertDialog createErrorDialog() {

    	AlertDialog.Builder builder = new AlertDialog.Builder(this); 
    	builder.setMessage(R.string.error_warning); 
    	builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {

				errorDialog.dismiss();
			}
    	});	

    	return builder.create();
	}
	

    /**
     * Fires search procedures on manager to load the route details
     */
	private void loadData() {
		
		Bundle b = getIntent().getExtras();
		int routeId = b.getInt("routeId");
		
		manager.findStopsByRouteId(routeId);
		manager.findDeparturesByRouteId(routeId);
	}

	/**
	 * Closes the details activity
	 */
	private void closeActivity() {
		
		finish();
	}

	/**
	 * Shows the progress dialog while a request is performed
	 */
	public void showProgressDialog() {
		
		dialog.show(); 
	}


	/**
	 * Populates the stops listView with a resulting stop list
	 * @param routes
	 */
	public void showStopResults(String[] stops) {
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.stops_list, stops); 
		this.lvStops.setAdapter(adapter); 
		
		dialog.dismiss();	
	}


	/**
	 * Populates the each listView used to show the resulting timetable for a route
	 * @param routes
	 */
	public void showDepartureResults(List<String> weekdayDeps, List<String> saturdayDeps, List<String> sundayDeps) {

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.schedule_list, weekdayDeps); 
		this.lvDepsWeekday.setAdapter(adapter); 
		
		ArrayAdapter<String> adapterSat = new ArrayAdapter<String>(getBaseContext(), R.layout.schedule_list, saturdayDeps); 
		this.lvDepsSaturday.setAdapter(adapterSat); 
		
		ArrayAdapter<String> adapterSun = new ArrayAdapter<String>(getBaseContext(), R.layout.schedule_list, sundayDeps); 
		this.lvDepsSunday.setAdapter(adapterSun); 
		
		dialog.dismiss();		
	}

	/**
	 * Shows a message informing that an error occurred
	 */
	public void showErrorDialog() {
		
		this.errorDialog.show();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.route_details, menu);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
