package com.raulete.evoluzzion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.raulete.evoluzzion.models.Jigsaw;
import com.raulete.evoluzzion.models.Step;

public class StepActivity extends Activity {

	public static String STEP_ID = "step_id";
	private long step_id;
	private Step step;
	private Jigsaw jigsaw;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_layout);
        setStepId();
    }
	
	public void onResume(){
		super.onResume();
		populateStep();
	}
	
	public void deleteStep(View view){
		new AlertDialog.Builder(this)
		.setTitle(R.string.step_delete)
		.setMessage(R.string.sure)
		.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface dialog, int which) { StepActivity.this.deleteStepAndFinish(); }
		})
        .setNegativeButton(R.string.no, null)
        .show();
	}
	
	public void shareStep(View view){
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("image/jpeg");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, getStringToShare());
		sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(step.image_uri));  
		String share = (String)getResources().getText(R.string.button_step_share);
		startActivity(Intent.createChooser(sharingIntent, share));
	}
	
	public String getStringToShare(){
		String share = "";
		if(step.comment.equals("")){
			share = jigsaw.name + " - " + step.name + ". #Evoluzzion";
		}else{
			share = jigsaw.name + " - " + step.name + ". " + step.comment + " #Evoluzzion";
		}
		return share;
	}
	
	public void populateStep(){
		step.populateItem(this);
	}
	
	public void setStepId(){
		Intent intent = getIntent();
		step_id = intent.getLongExtra(STEP_ID, 0);
		step = new Step(this);
		step.read(step_id);
		jigsaw = new Jigsaw(this);
		jigsaw.read(step.jigsaw_id);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.step_menu, menu);
        return true;
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_jigsaw_delete:
        	deleteStepAndFinish();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
	public void deleteStepAndFinish(){
		new Step(this).delete(step_id);
    	finish();
	}
}
