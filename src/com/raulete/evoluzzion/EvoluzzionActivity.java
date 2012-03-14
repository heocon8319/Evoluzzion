package com.raulete.evoluzzion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

public class EvoluzzionActivity extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinished) { }
            public void onFinish() {
            	openJigsawsListActivity(null);
            }
         }.start();
    }
    
    public void openJigsawsListActivity(View view){
    	startActivity(new Intent(this, JigsawsListActivity.class));
    	finish();
    }
    
    
}