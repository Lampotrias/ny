package com.example.ny.activities;

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

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.relex.circleindicator.CircleIndicator;

public class Intro extends AppCompatActivity {

	private final CompositeDisposable compositeDisposable = new CompositeDisposable();

	private static final String SHARED_PREF_NAME = "MY_SHARED_PREF";
	private static final String DATA_KEY = "NEED_INTRO";

	private ViewPager mPager;
	private PagerAdapter pagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.e("test111", "Intro::OnCreate");
		super.onCreate(savedInstanceState);

		SharedPreferences sharedPref = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();

		boolean bNeedIntro = sharedPref.getBoolean(DATA_KEY, true);
		bNeedIntro = false;
		if (bNeedIntro){
			setContentView(R.layout.activity_intro);
			Disposable disposable = Completable.complete()
					.delay(5, TimeUnit.SECONDS)
					.subscribe(this::StartMainActivity);
			compositeDisposable.add(disposable);
			editor.putBoolean(DATA_KEY, false);
			editor.apply();

			mPager = findViewById(R.id.pager);
			pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
			mPager.setAdapter(pagerAdapter);

			CircleIndicator indicator = findViewById(R.id.indicator);
			indicator.setViewPager(mPager);
		}else{
			editor.putBoolean(DATA_KEY, true);
			editor.apply();
			StartMainActivity();
		}
	}

	private void StartMainActivity(){
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}

	@Override
	protected void onStop() {
		super.onStop();
		compositeDisposable.dispose();
	}

	private static class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		private final static int NUM_ITEMS = 3;

		ScreenSlidePagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
		}

		@NotNull
		@Override
		public Fragment getItem(int position) {
			switch (position){
				case 0:
					return IntroSliderFragment.newInstance(R.drawable.intro1);
				case 1:
					return IntroSliderFragment.newInstance(R.drawable.intro2);
				case 2:
					return IntroSliderFragment.newInstance(R.drawable.intro3);
			}
			return IntroSliderFragment.newInstance(R.drawable.intro1);
		}

		@Override
		public int getCount() {
			return NUM_ITEMS;
		}
	}
}
