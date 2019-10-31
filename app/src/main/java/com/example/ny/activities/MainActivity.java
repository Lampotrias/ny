package com.example.ny.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.ny.R;
import com.example.ny.utils.SyncService;

public class MainActivity extends AppCompatActivity implements ClickerResponder{

	private static boolean isTwoPanel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		savedInstanceState = null;
		Log.e("test111", "MainActivity::OnCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		isTwoPanel = findViewById(R.id.frame_detail) != null;

		if (savedInstanceState == null) {
			NewsListFragment newsListFragment = new NewsListFragment();
			getSupportFragmentManager()
					.beginTransaction()
					.addToBackStack(null)
					.add(R.id.frame_list, newsListFragment, "tList")
					.commit();
			newsListFragment.setInterface(this);
			if (isTwoPanel()){
				NewsDetailsFragment newsDetailsFragment = NewsDetailsFragment.newInstance(0);
				getSupportFragmentManager()
						.beginTransaction()
						.addToBackStack(null)
						.replace(R.id.frame_detail, newsDetailsFragment)
						.commit();
			}
		}

		Intent inService = new Intent(getApplicationContext(), SyncService.class);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
			getApplicationContext().startForegroundService(inService);
		else
			getApplicationContext().startService(inService);
	}

	@Override
	public boolean onSupportNavigateUp() {
		if (!isTwoPanel())
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

	@Override
	public void OnClick(int id) {
		NewsDetailsFragment newsDetailsFragment = NewsDetailsFragment.newInstance(id);

		if (isTwoPanel()){
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.frame_detail, newsDetailsFragment)
					.commit();
		}else {
			getSupportFragmentManager()
					.beginTransaction()
					.addToBackStack(null)
					.replace(R.id.frame_list, newsDetailsFragment)
					.commit();
		}
	}

	public static boolean isTwoPanel() {
		return isTwoPanel;
	}
}
