package com.tabgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;

public class TabGroup extends LinearLayout {
	private OnTabSelectionChanged mSelectionChangedListener;
    private int mSelectedTab = -1;
	public TabGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	public void addView(View child, android.view.ViewGroup.LayoutParams params) {
		initChildView(child);
		super.addView(child, params);
		child.setClickable(true);
        child.setOnClickListener(new TabClickListener(getTabCount() - 1));
	}
	@Override
    public void addView(View child) {
        initChildView(child);
        super.addView(child);
        child.setClickable(true);
        child.setOnClickListener(new TabClickListener(getTabCount() - 1));
    }
	private void initChildView(View child){
		if (child.getLayoutParams() == null) {
            final LinearLayout.LayoutParams lp = new LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
            lp.setMargins(0, 0, 0, 0);
            child.setLayoutParams(lp);
        }
        // Ensure you can navigate to the tab with the keyboard, and you can touch it
        child.requestFocus();
        child.setFocusable(true);
	}
	//registered with each tab indicator so we can notify tab host
	private class TabClickListener implements OnClickListener {
	
		private final int mTabIndex;

        private TabClickListener(int tabIndex) {
            mTabIndex = tabIndex;
        }

        public void onClick(View v) {
        	if (mSelectedTab != mTabIndex) {
        		
        		getChildTabViewAt(mSelectedTab).setSelected(false);
        		getChildTabViewAt(mTabIndex).setSelected(true);
        		mSelectedTab = mTabIndex;
        		if (mSelectionChangedListener != null) {
        			mSelectionChangedListener.onTabSelectionChanged(v.getId());
        		}
			}
        }  
		
	}
    public int getTabCount() {
        return getChildCount();
    }
    public View getChildTabViewAt(int index) {
        return getChildAt(index);
    }
	public void setCurrentTab(int index) {
        if (index < 0 || index >= getTabCount() || index == mSelectedTab) {
            return;
        }

        if (mSelectedTab != -1) {
            getChildTabViewAt(mSelectedTab).setSelected(false);
        }
        mSelectedTab = index;
        getChildTabViewAt(mSelectedTab).setSelected(true);

        if (isShown()) {
            sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_SELECTED);
        }
    }
	public static interface OnTabSelectionChanged {
		void onTabSelectionChanged(int checkedid);
	}
	public void setSelectionChangedListener(
			OnTabSelectionChanged mSelectionChangedListener) {
		this.mSelectionChangedListener = mSelectionChangedListener;
	}
}
