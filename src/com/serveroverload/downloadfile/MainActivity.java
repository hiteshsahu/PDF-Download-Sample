package com.serveroverload.downloadfile;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends FragmentActivity {

	ViewPager mViewPager;
	private Button mNextBtn;
	private Button mSkipBtn, mFinishBtn;

	private ImageView zero, one, two;
	private ImageView[] indicators;
	
	public static String currentWebURl = "https://bitcoin.org/bitcoin.pdf" ;

	int lastLeftValue = 0;
	int page = 0; // to track page position
	private SectionsPagerAdapter mSectionsPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mNextBtn = (Button) findViewById(R.id.intro_btn_next);

		mSkipBtn = (Button) findViewById(R.id.intro_btn_skip);
		mFinishBtn = (Button) findViewById(R.id.intro_btn_finish);

		zero = (ImageView) findViewById(R.id.intro_indicator_0);
		one = (ImageView) findViewById(R.id.intro_indicator_1);
		two = (ImageView) findViewById(R.id.intro_indicator_2);

		indicators = new ImageView[] { zero, one, two };

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.container);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager.setCurrentItem(page);
		updateIndicators(page);

		final int color1 = ContextCompat.getColor(this, R.color.cyan);
		final int color2 = ContextCompat.getColor(this, R.color.orange);
		final int color3 = ContextCompat.getColor(this, R.color.green);

		final int[] colorList = new int[] { color1, color2, color3 };

		final ArgbEvaluator evaluator = new ArgbEvaluator();

		mViewPager
				.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageScrolled(int position,
							float positionOffset, int positionOffsetPixels) {

						/*
						 * color update
						 */
						int colorUpdate = (Integer) evaluator.evaluate(
								positionOffset, colorList[position],
								colorList[position == 2 ? position
										: position + 1]);
						mViewPager.setBackgroundColor(colorUpdate);

					}

					@Override
					public void onPageSelected(int position) {

						page = position;

						updateIndicators(page);

						switch (position) {
						case 0:
							mViewPager.setBackgroundColor(color1);
							break;
						case 1:
							mViewPager.setBackgroundColor(color2);
							break;
						case 2:
							mViewPager.setBackgroundColor(color3);
							break;
						}

						mNextBtn.setVisibility(position == 2 ? View.GONE
								: View.VISIBLE);
						mFinishBtn.setVisibility(position == 2 ? View.VISIBLE
								: View.GONE);

					}

					@Override
					public void onPageScrollStateChanged(int state) {

					}
				});

		mNextBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				page += 1;
				mViewPager.setCurrentItem(page, true);
			}
		});

		mSkipBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				finish();
			}
		});

		mFinishBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				// update 1st time pref

			}
		});

	}

	void updateIndicators(int position) {
		for (int i = 0; i < indicators.length; i++) {
			indicators[i]
					.setBackgroundResource(i == position ? R.drawable.indicator_selected
							: R.drawable.indicator_unselected);
		}
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).

			switch (position) {
			case 0:

				return new PlaceholderFragment();

			case 1:

				return new PDFListFragment();

			default:
				return new UniversalWebViewFragment();
			}

		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return "SECTION 1";
			case 1:
				return "SECTION 2";
			case 2:
				return "SECTION 3";
			}
			return null;
		}

	}

}
