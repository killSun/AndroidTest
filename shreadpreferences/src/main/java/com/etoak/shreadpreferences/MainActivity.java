package com.etoak.shreadpreferences;

import android.os.Handler;
import android.preference.Preference;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.etoak.shreadpreferences.service.PreferenceService;


public class MainActivity extends ActionBarActivity {

    private EditText nameEditText;
    private EditText ageEditText;
    private PreferenceService preferenceService;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameEditText = (EditText) this.findViewById(R.id.name);
        ageEditText = (EditText) this.findViewById(R.id.age);
        preferenceService = new PreferenceService(this);

        nameEditText.setText(preferenceService.getPreferemces().get("name"));
        ageEditText.setText(preferenceService.getPreferemces().get("age"));


    }

    public void save(View view){
        Thread thread = new Thread(){
            @Override
            public void run() {
                Log.i("THREADNAME",Thread.currentThread().getName());
                preferenceService.savePreference(nameEditText.getText().toString(), Integer.parseInt(ageEditText.getText().toString()));
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),R.string.saveSuccess,Toast.LENGTH_LONG).show();
                    }
                });
            }
        };
        thread.start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
