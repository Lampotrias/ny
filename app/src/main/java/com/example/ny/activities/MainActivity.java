package com.example.ny.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.ny.R;

public class MainActivity extends AppCompatActivity implements ClickerResponder{

	private static boolean isTwoPanel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
		}
		else {
			NewsListFragment newsListFragment = (NewsListFragment) getSupportFragmentManager().findFragmentByTag("tList");
			if (newsListFragment != null){
				getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.frame_list, newsListFragment, "tList")
						.commit();
				newsListFragment.setInterface(this);
			}


			NewsDetailsFragment newsDetailsFragment = (NewsDetailsFragment) getSupportFragmentManager().findFragmentByTag("tDetail");
			if (newsDetailsFragment != null){
				getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.frame_detail, newsDetailsFragment, "tDetail")
						.commit();
				newsListFragment.setInterface(this);
			}
		}
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
					//.addToBackStack(null)
					.replace(R.id.frame_detail, newsDetailsFragment, "tDetail")
					.commit();
		}else {
			getSupportFragmentManager()
					.beginTransaction()
					.addToBackStack(null)
					.replace(R.id.frame_list, newsDetailsFragment,"tDetail")
					.commit();
		}
	}

	public static boolean isTwoPanel() {
		return isTwoPanel;
	}
}
