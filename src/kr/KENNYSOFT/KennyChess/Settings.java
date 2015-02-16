package kr.KENNYSOFT.KennyChess;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class Settings extends PreferenceActivity
{
	Toolbar mToolbar;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		
		findPreference("about").setOnPreferenceClickListener(mAboutClick);
		findPreference("update").setOnPreferenceClickListener(mUpdateClick);
		findPreference("link").setOnPreferenceClickListener(mLinkClick);
	}
	
	public void setContentView(int layoutResID)
	{
		ViewGroup contentView=(ViewGroup)LayoutInflater.from(this).inflate(R.layout.settings,new LinearLayout(this),false);
		mToolbar=(Toolbar)contentView.findViewById(R.id.settings_toolbar);
		mToolbar.setTitle(getTitle());
		mToolbar.setNavigationOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				finish();
			}
		});
		ViewGroup contentWrapper=(ViewGroup)contentView.findViewById(R.id.settings_preference);
		LayoutInflater.from(this).inflate(layoutResID,contentWrapper,true);
		getWindow().setContentView(contentView);
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	OnPreferenceClickListener mAboutClick=new OnPreferenceClickListener()
	{
		public boolean onPreferenceClick(Preference preference)
		{
			((TextView)new AlertDialog.Builder(Settings.this).setIcon(R.drawable.ic_launcher).setTitle(R.string.full_name).setMessage(R.string.information).setPositiveButton(android.R.string.ok,null).show().findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
			return true;
		}
	};
	
	OnPreferenceClickListener mUpdateClick=new OnPreferenceClickListener()
	{
		public boolean onPreferenceClick(Preference preference)
		{
			new AlertDialog.Builder(Settings.this).setTitle(R.string.preference_update).setMessage(R.string.update).setPositiveButton(android.R.string.ok,null).show();
			return true;
		}
	};
	
	OnPreferenceClickListener mLinkClick=new OnPreferenceClickListener()
	{
		public boolean onPreferenceClick(Preference preference)
		{
			startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=kr.KENNYSOFT.KennyChess")));
			return true;
		}
	};
}