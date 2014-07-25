package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}
	public final static String EXTRA_MESSAGE = "com.example.myapp.MESSAGE";
	public final static String EXTRA_MESSAGE2 = "com.example.myapp.MESSAGE2";
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void sendMessage(View view) {
	    // Do something in response to button
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		final EditText editText = extracted();
		String message = editText.getText().toString();
		//String message2 = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		final EditText editText2 = (EditText)findViewById(R.id.editText1);
		
		String message2 = editText2.getText().toString();
		 intent.putExtra(EXTRA_MESSAGE2, message2);
			
		 startActivity(intent);
	}

	private EditText extracted() {
		final EditText editText = (EditText) findViewById(R.id.EditText01);
		return editText;
	}
	
}
