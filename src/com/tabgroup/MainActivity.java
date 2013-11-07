package com.tabgroup;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.tabgroup.TabGroup.OnTabSelectionChanged;
import com.tabview.R;

public class MainActivity extends Activity implements OnTabSelectionChanged{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TabGroup tabView = (TabGroup) findViewById(R.id.tabs);
		/*View view1 = LayoutInflater.from(this).inflate(R.layout.tabview, null);
		View view2 = LayoutInflater.from(this).inflate(R.layout.tabview, null);
		View view3 = LayoutInflater.from(this).inflate(R.layout.tabview, null);
		view1.setId(R.id.tab1);
		view2.setId(R.id.tab2);
		view3.setId(R.id.tab3);
		tabView.addView(view1);
		tabView.addView(view2);
		tabView.addView(view3);*/
		tabView.setSelectionChangedListener(this);
		tabView.setCurrentTab(0);
	}

	@Override
	public void onTabSelectionChanged(View view, boolean clicked) {
		System.out.println("onTabSelectionChanged id="+view.getId()+";"+clicked);
	}

}
