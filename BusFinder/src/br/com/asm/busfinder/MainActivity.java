package br.com.asm.busfinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private EditText etStreetName;
	private Button btSearch;

	private ListView lvResultRoutes;
	private Manager manager;
	private ProgressDialog progressDialog;
	private TextView tvResultsTitle;
	private AlertDialog alertDialog;
	private AlertDialog noResultsDialog;
	private AlertDialog errorDialog;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.manager = new Manager(this);
        setUpUI();
    }
    

    private void setUpUI() {

        this.etStreetName = (EditText)findViewById(R.id.etStreetName);
        this.btSearch = (Button)findViewById(R.id.btSearch);
        
        this.lvResultRoutes = (ListView)findViewById(R.id.lvResultRoutes);        
        this.tvResultsTitle = (TextView)findViewById(R.id.tvResultsTitle);
        
        this.progressDialog = new ProgressDialog(MainActivity.this); 
        
        this.lvResultRoutes.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				openDetailsActivity(position);
			}
		});
        
        
        this.btSearch.setOnClickListener(new OnClickListener(){
        	
			@Override
			public void onClick(View v)	{
				
				hideSoftKeyboard();
				searchRoutes();
			}
		});
        
        this.tvResultsTitle.setVisibility(View.GONE);
        this.lvResultRoutes.setVisibility(View.GONE);
        
        this.alertDialog = createAlertDialog();
        this.noResultsDialog = createNoResultsDialog();
        this.errorDialog = createErrorDialog();
        this.etStreetName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
				if (i == EditorInfo.IME_ACTION_DONE) {
                    // do your stuff here 
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                } 
                return false;
            }
        });
    }
    

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


	private AlertDialog createNoResultsDialog() {
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this); 
    	builder.setMessage(R.string.no_results_warning); 
    	builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {

				etStreetName.setText("");
				
				showRoteResults(new Route[0]);
				etStreetName.requestFocus();
				alertDialog.dismiss();
			}
    	});	

    	return builder.create();
	}

	
    private AlertDialog createAlertDialog() { 
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this); 
    	builder.setMessage(R.string.empty_key_warning); 
    	builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {

				alertDialog.dismiss();
			}
    	});	

    	return builder.create();
    }
    
    
    public void showAlertDialog(){
    	
    	this.alertDialog.show();
    }

    
    private void hideSoftKeyboard() {
    	
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }
    
	
    private void openDetailsActivity(int position) {
		
		Route route = (Route)lvResultRoutes.getAdapter().getItem(position);
		
	    Intent intent = new Intent(this, RouteDetailsActivity.class);
		intent.putExtra("routeId", route.getRouteId());
		intent.putExtra("route", route.toString());
		startActivity(intent);

	}

	
	private void searchRoutes() {
		
		String streetName = this.etStreetName.getText().toString();
		manager.findRoutes(streetName);
		this.lvResultRoutes.setVisibility(View.VISIBLE);
	}

	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
		
		progressDialog.show(); 

	}


	public void showRoteResults(Route[] routes) {
		
		ArrayAdapter<Route> adapter = new ArrayAdapter<Route>(getBaseContext(), android.R.layout.simple_list_item_1, routes); 
		this.lvResultRoutes.setAdapter(adapter); 
		
		progressDialog.dismiss();
		
	}


	public void showNoResultsDialog() {
		
		this.progressDialog.dismiss();
		this.noResultsDialog.show();
		
	}


	public void showErrorDialog() {
		
		this.errorDialog.show();
	}
}
