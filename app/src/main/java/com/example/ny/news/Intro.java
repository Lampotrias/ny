package com.example.ny.news;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.ny.R;
import com.example.ny.activities.ScreenSlidePageFragment;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.relex.circleindicator.CircleIndicator;

public class Intro extends AppCompatActivity {

	private CompositeDisposable compositeDisposable = new CompositeDisposable();

	public static final String SHARED_PREF_NAME = "MY_SHARED_PREF";
	public static final String DATA_KEY = "NEED_INTRO";

	private ViewPager mPager;
	private PagerAdapter pagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences sharedPref = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();

		int bNeedIntro = sharedPref.getInt(DATA_KEY, 2);
		bNeedIntro = 2;
		if (bNeedIntro > 1){
			setContentView(R.layout.activity_intro);
			Disposable disposable = Completable.complete()
					.delay(100, TimeUnit.SECONDS)
					.subscribe(this::StartMainActivity);
			compositeDisposable.add(disposable);
			editor.putInt(DATA_KEY, 1);
		}else{
			editor.putInt(DATA_KEY, ++bNeedIntro);
			editor.apply();
			this.StartMainActivity();
		}
		editor.apply();

		mPager = findViewById(R.id.pager);
		pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(pagerAdapter);

		CircleIndicator indicator = findViewById(R.id.indicator);
		indicator.setViewPager(mPager);

	}

	private void StartMainActivity(){
		startActivity(new Intent(this, NewsListActivity.class));
	}

	@Override
	protected void onStop() {
		super.onStop();
		compositeDisposable.dispose();
	}
	/////////////////////////////////////////////////////////////

	private static class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		private static int NUM_ITEMS = 3;

		public ScreenSlidePagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position){
				case 0:
					return ScreenSlidePageFragment.newInstance(R.drawable.intro1);
				case 1:
					return ScreenSlidePageFragment.newInstance(R.drawable.intro2);
				case 2:
					return ScreenSlidePageFragment.newInstance(R.drawable.intro3);
					default:
						return null;
			}
		}

		@Override
		public int getCount() {
			return NUM_ITEMS;
		}
	}
}
