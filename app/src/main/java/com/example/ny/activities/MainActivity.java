package com.example.ny.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ny.R;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		NewsListFragment newsListFragment = new NewsListFragment();
		getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.main_frame, newsListFragment)
				.commit();
	}

	@Override
	public boolean onSupportNavigateUp() {
		onBackPressed();
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
