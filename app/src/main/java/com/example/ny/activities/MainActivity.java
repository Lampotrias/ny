package com.example.ny.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.ny.R;
import com.example.ny.utils.Utils;

public class MainActivity extends AppCompatActivity {

	private MenuItem mSpinnerItem = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu (Menu menu) {

		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_option, menu);

		/*
		mSpinnerItem = menu.findItem( R.id.action_bar_spinner);
		View view = mSpinnerItem.getActionView();

		if (view instanceof Spinner){
			final Spinner spinner = (Spinner) view;

			ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, Utils.GetCategoryArray());
			spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(spinnerAdapter);

			spinner.setSelection(0);

			spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					if (position > 0){
						loadItemsByCategory(Utils.GetCategoryNameByID(position));
					}else{

					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {

				}
			});
		}*/

		return true;
	}

	@Override
	public boolean onOptionsItemSelected ( MenuItem item ) {
		switch ( item.getItemId () ) {
			case R.id.about_activity:
				startActivity ( new Intent( this , AboutActivity.class) );
			default:
				return super.onOptionsItemSelected ( item );
		}

	}
}
