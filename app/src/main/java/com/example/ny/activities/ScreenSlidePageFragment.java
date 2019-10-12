package com.example.ny.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ny.R;

public class ScreenSlidePageFragment extends Fragment {

	private int resId;
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		resId = getArguments().getInt("PAGE", 1);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_intro, container, false);
		ImageView imageView = rootView.findViewById(R.id.imageView);
		imageView.setImageResource(resId);

		return rootView;
	}

	public static ScreenSlidePageFragment newInstance(int resId){
		ScreenSlidePageFragment screenSlidePageFragment = new ScreenSlidePageFragment();
		Bundle args = new Bundle();
		args.putInt("PAGE", resId);
		screenSlidePageFragment.setArguments(args);
		return screenSlidePageFragment;
	}
}
