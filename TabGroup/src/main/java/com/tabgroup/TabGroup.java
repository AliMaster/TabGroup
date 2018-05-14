package com.tabgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;

public class TabGroup extends LinearLayout {
	private OnTabChangedListener mSelectionChangedListener;
    private int mSelectedTab = -1;
	public TabGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	public void addView(View child, ViewGroup.LayoutParams params) {
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
            final LayoutParams lp = new LayoutParams(0,ViewGroup.LayoutParams.FILL_PARENT, 1.0f);
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
        	if (mSelectionChangedListener != null) {
        		if (mSelectedTab != mTabIndex) {
            		getChildTabViewAt(mSelectedTab).setSelected(false);
    			}
            	getChildTabViewAt(mTabIndex).setSelected(true);
        		mSelectionChangedListener.onTabSelectionChanged(mSelectedTab,mTabIndex);
        		mSelectedTab = mTabIndex;
        	}
        }

	}
    public int getTabCount() {
        return getChildCount();
    }
    public View getChildTabViewAt(int index) {
        return getChildAt(index);
    }
    /**
     * you must set a default tabindex after init UI
     * @param index
     */
	public void setCurrentTab(int index) {
        if (index < 0 || index >= getTabCount() || index == mSelectedTab) {
            return;
        }

        if (mSelectionChangedListener != null) {
        	 if (mSelectedTab != -1) {
                 getChildTabViewAt(mSelectedTab).setSelected(false);
             }
        	getChildTabViewAt(index).setSelected(true);
			mSelectionChangedListener.onTabSelectionChanged(mSelectedTab,index);
			mSelectedTab = index;
		}
        if (isShown()) {
            sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_SELECTED);
        }
    }
    /**
     * 当前tab和目标tab一样也会切换
     * @param index
     */
	public void forceSetCurrentTab(int index){
		if (index < 0 || index >= getTabCount()) {
            return;
        }

        if (mSelectionChangedListener != null) {
        	if (mSelectedTab != -1) {
                getChildTabViewAt(mSelectedTab).setSelected(false);
            }
        	getChildTabViewAt(index).setSelected(true);
			mSelectionChangedListener.onTabSelectionChanged(mSelectedTab,index);
			mSelectedTab = index;
		}
        if (isShown()) {
            sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_SELECTED);
        }
	}
	/**
	 * 现在不再判断lastPosition和currentPosition是否是一样的，交给回调去判断
	 * @author p_jwcao
	 */
	public static interface OnTabChangedListener {
		void onTabSelectionChanged(int lastPosition, int currentPosition);
	}
	public void setOnTabChangedListener(
			OnTabChangedListener mSelectionChangedListener) {
		this.mSelectionChangedListener = mSelectionChangedListener;
	}
}
