/**
 * OWASP GoatDroid Project
 * 
 * This file is part of the Open Web Application Security Project (OWASP)
 * GoatDroid project. For details, please see
 * https://www.owasp.org/index.php/Projects/OWASP_GoatDroid_Project
 *
 * Copyright (c) 2012 - The OWASP Foundation
 * 
 * GoatDroid is published by OWASP under the GPLv3 license. You should read and accept the
 * LICENSE before you use, modify, and/or redistribute this software.
 * 
 * @author Jack Mannino (Jack.Mannino@owasp.org https://www.owasp.org/index.php/User:Jack_Mannino)
 * @author Walter Tighzert
 * @created 2012
 */
package org.owasp.goatdroid.fourgoats.base;

import java.util.ArrayList;
import java.util.HashMap;
import org.owasp.goatdroid.fourgoats.R;
import org.owasp.goatdroid.fourgoats.activities.About;
import org.owasp.goatdroid.fourgoats.activities.AdminHome;
import org.owasp.goatdroid.fourgoats.activities.Home;
import org.owasp.goatdroid.fourgoats.activities.Login;
import org.owasp.goatdroid.fourgoats.activities.Preferences;
import org.owasp.goatdroid.fourgoats.activities.ViewProfile;
import org.owasp.goatdroid.fourgoats.db.UserInfoDBHelper;
import org.owasp.goatdroid.fourgoats.misc.Constants;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.rest.login.LoginRequest;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class BaseTabsViewPagerActivity extends SherlockFragmentActivity {

	ViewPager mViewPager;
	protected TabsAdapter mTabsAdapter;
	TextView tabCenter;
	TextView tabText;
	protected Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabsviewpager);

		// mViewPager = new ViewPager(this);
		// mViewPager.setId(R.id.pager);
		mViewPager = (ViewPager) findViewById(R.id.pager);

		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mTabsAdapter = new TabsAdapter(this, mViewPager);

		getSupportActionBar().setIcon(R.drawable.ic_main);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			getActionBar().setHomeButtonEnabled(true);
			getActionBar().setDisplayHomeAsUpEnabled(true);
		} else {
			getSupportActionBar().setHomeButtonEnabled(true);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	public static class TabsAdapter extends FragmentPagerAdapter implements
			ActionBar.TabListener, ViewPager.OnPageChangeListener {
		private final Context mContext;
		private final ActionBar mActionBar;
		private final ViewPager mViewPager;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

		static final class TabInfo {
			private final Class<?> clss;
			private final Bundle args;

			TabInfo(Class<?> _class, Bundle _args) {
				clss = _class;
				args = _args;
			}
		}

		public TabsAdapter(SherlockFragmentActivity activity, ViewPager pager) {
			super(activity.getSupportFragmentManager());
			mContext = activity;
			mActionBar = activity.getSupportActionBar();
			mViewPager = pager;
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);
		}

		public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
			TabInfo info = new TabInfo(clss, args);
			tab.setTag(info);
			tab.setTabListener(this);
			mTabs.add(info);
			mActionBar.addTab(tab);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

		@Override
		public Fragment getItem(int position) {
			TabInfo info = mTabs.get(position);
			return Fragment.instantiate(mContext, info.clss.getName(),
					info.args);
		}

		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		public void onPageSelected(int position) {
			mActionBar.setSelectedNavigationItem(position);
		}

		public void onPageScrollStateChanged(int state) {
		}

		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			Object tag = tab.getTag();
			for (int i = 0; i < mTabs.size(); i++) {
				if (mTabs.get(i) == tag) {
					mViewPager.setCurrentItem(i);
				}
			}
		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		context = this.getApplicationContext();
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == android.R.id.home) {
			UserInfoDBHelper homeUIDH = new UserInfoDBHelper(context);
			Intent homeIntent;
			try {
				if (homeUIDH.getIsAdmin())
					homeIntent = new Intent(BaseTabsViewPagerActivity.this,
							AdminHome.class);
				else
					homeIntent = new Intent(BaseTabsViewPagerActivity.this,
							Home.class);
			} finally {
				homeUIDH.close();
			}
			startActivity(homeIntent);
			return true;
		} else if (itemId == R.id.preferences) {
			Intent intent = new Intent(BaseTabsViewPagerActivity.this,
					Preferences.class);
			startActivity(intent);
			return true;
		} else if (itemId == R.id.viewMyProfile) {
			Intent profileIntent = new Intent(BaseTabsViewPagerActivity.this,
					ViewProfile.class);
			Bundle bundle = new Bundle();
			UserInfoDBHelper profileUIDH = new UserInfoDBHelper(context);
			String userName = profileUIDH.getUserName();
			profileUIDH.close();
			bundle.putString("userName", userName);
			profileIntent.putExtras(bundle);
			startActivity(profileIntent);
			return true;
		} else if (itemId == R.id.logOut) {
			LogOutAsyncTask task = new LogOutAsyncTask();
			task.execute(null, null);
			return true;
		} else if (itemId == R.id.about) {
			Intent aboutIntent = new Intent(BaseTabsViewPagerActivity.this,
					About.class);
			startActivity(aboutIntent);
			return true;
		}
		return true;
	}

	public class LogOutAsyncTask extends
			AsyncTask<Void, Void, HashMap<String, String>> {
		protected HashMap<String, String> doInBackground(Void... params) {

			LoginRequest rest = new LoginRequest(context);
			UserInfoDBHelper uidh = new UserInfoDBHelper(context);
			HashMap<String, String> logoutInfo = new HashMap<String, String>();

			try {
				logoutInfo = rest.logOut(uidh.getSessionToken());
				uidh.deleteInfo();
			} catch (Exception e) {
				logoutInfo.put("errors", e.getMessage());
				logoutInfo.put("success", "false");
			} finally {
				uidh.close();
			}
			return logoutInfo;
		}

		public void onPostExecute(HashMap<String, String> results) {
			if (results.get("success").equals("true")) {
				Intent intent = new Intent(context, Login.class);
				startActivity(intent);
			} else if (results.get("errors").equals(Constants.INVALID_SESSION)) {
				Utils.makeToast(context, Constants.INVALID_SESSION,
						Toast.LENGTH_LONG);
				Intent intent = new Intent(context, Login.class);
				startActivity(intent);
			} else {
				Utils.makeToast(context, results.get("errors"),
						Toast.LENGTH_LONG);
			}
		}
	}
}
