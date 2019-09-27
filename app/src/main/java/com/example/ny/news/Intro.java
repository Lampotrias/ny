package com.example.ny.news;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import com.example.ny.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class Intro extends AppCompatActivity {

	private CompositeDisposable compositeDisposable = new CompositeDisposable();

	public static final String SHARED_PREF_NAME = "MY_SHARED_PREF";
	public static final String DATA_KEY = "NEED_INTRO";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences sharedPref = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();

		int bNeedIntro = sharedPref.getInt(DATA_KEY, 2);

		Log.e("test == = = ==  =", String.valueOf(bNeedIntro));
		if (bNeedIntro > 1){
			setContentView(R.layout.activity_intro);
			Disposable disposable = Completable.complete()
					.delay(3, TimeUnit.SECONDS)
					.subscribe(this::StartMainActivity);
			compositeDisposable.add(disposable);
			editor.putInt(DATA_KEY, 1);
		}else{
			Log.e("test == = = ==  =11111", String.valueOf(bNeedIntro));
			editor.putInt(DATA_KEY, ++bNeedIntro);
			editor.apply();
			this.StartMainActivity();
		}
		editor.apply();
	}

	private void StartMainActivity(){
		startActivity(new Intent(this, NewsListActivity.class));
	}

	@Override
	protected void onStop() {
		super.onStop();
		compositeDisposable.dispose();
	}
}
