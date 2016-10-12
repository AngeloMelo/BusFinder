package br.com.asm.busfinder;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class RouteDetailsActivity extends Activity {
	
	private TextView tvRouteName;
	private DetailsManager manager;
	private Button btBack;
	private ListView lvStops;
	private ListView lvDepsWeekday;
	private ListView lvDepsSaturday;
	private ListView lvDepsSunday;
	
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_details);
	
		manager = new DetailsManager(this);
		setUpUI();
		loadData();
	}
	
	
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
	}
	
	
	private void loadData() {
		
		Bundle b = getIntent().getExtras();
		int routeId = b.getInt("routeId");
		
		manager.findStopsByRouteId(routeId);
		manager.findDeparturesByRouteId(routeId);
		
	}

	private void closeActivity() {
		
		finish();
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


	public void showProgressDialog() {
	
		dialog.show(); 
		
	}


	public void showStopResults(String[] stops) {
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, stops); 
		this.lvStops.setAdapter(adapter); 
		
		dialog.dismiss();	
	}



	public void showDepartureResults(List<String> weekdayDeps, List<String> saturdayDeps, List<String> sundayDeps) {

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, weekdayDeps); 
		this.lvDepsWeekday.setAdapter(adapter); 
		
		ArrayAdapter<String> adapterSat = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, saturdayDeps); 
		this.lvDepsSaturday.setAdapter(adapterSat); 
		
		ArrayAdapter<String> adapterSun = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, sundayDeps); 
		this.lvDepsSunday.setAdapter(adapterSun); 
		
		dialog.dismiss();		
	}
}
