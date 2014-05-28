package com.example.android.slidingtabsbasic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.caldroid.CaldroidFragment;
import com.example.android.common.view.SlidingTabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A basic sample which shows how to use {@link com.example.android.common.view.SlidingTabLayout}
 * to display a custom {@link ViewPager} title strip which gives continuous feedback to the user
 * when scrolling.
 */
public class SlidingTabsBasicFragment extends Fragment {

    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;

    static final String LOG_TAG = "SlidingTabsBasicFragment";
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");



        View view = getActivity().getLayoutInflater().inflate(R.layout.calendar, container, false);
        container.addView(view);

        return inflater.inflate(R.layout.fragment_sample, container, false);
    }

    // BEGIN_INCLUDE (fragment_onviewcreated)
    /**
     * This is called after the {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} has finished.
     * Here we can pick out the {@link View}s we need to configure from the content view.
     *
     * We set the {@link ViewPager}'s adapter to be an instance of {@link ExampleAdapter}. The
     * {@link SlidingTabLayout} is then given the {@link ViewPager} so that it can populate itself.
     *
     * @param view View created in {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // BEGIN_INCLUDE (setup_viewpager)
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        FragmentManager fm = getFragmentManager();
        mViewPager.setAdapter(new ExampleAdapter(fm));
        // END_INCLUDE (setup_viewpager)

        // BEGIN_INCLUDE (setup_slidingtablayout)
        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
        // END_INCLUDE (setup_slidingtablayout)
    }
    // END_INCLUDE (fragment_onviewcreated)


    public class ExampleAdapter extends FragmentStatePagerAdapter {

        public ExampleAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0:
                    return "Home";
                case 1:
                    return "Calendar";
                case 2:
                    return "Gallery";
            }
            return "Item " + (position + 1);
        }


        @Override
        public Fragment getItem(int position) {

            // Return the correct Fragment for each Tab
            // The position corresponds to the position of the Tab
            // So the first Tab has a position of 0, the second Tab has a position of 1...
            switch (position) {

                case 0:
                    BlankFragment blankFragment = new BlankFragment();
                    return blankFragment;

                case 1:
                    caldroidFragment = new CaldroidFragment();

                    Bundle args2 = new Bundle();
                    Calendar cal2 = Calendar.getInstance();
                    args2.putInt(CaldroidFragment.MONTH, cal2.get(Calendar.MONTH) + 1);
                    args2.putInt(CaldroidFragment.YEAR, cal2.get(Calendar.YEAR));
                    args2.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
                    args2.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

                    caldroidFragment.setArguments(args2);

                    setCustomResourceForDates();
                    return caldroidFragment;

                case 2:
                    BlankFragment blankFragment2 = new BlankFragment();
                    return blankFragment2;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3; // 3 Fragments in this example
        }
    }

    private void setCustomResourceForDates() {
        Calendar cal = Calendar.getInstance();

        // Min date is last 7 days
        cal.add(Calendar.DATE, -18);
        Date blueDate = cal.getTime();

        // Max date is next 7 days
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 16);
        Date greenDate = cal.getTime();

        if (caldroidFragment != null) {
            caldroidFragment.setBackgroundResourceForDate(R.color.blue,blueDate);
            caldroidFragment.setBackgroundResourceForDate(R.color.green,greenDate);
            caldroidFragment.setTextColorForDate(R.color.white, blueDate);
            caldroidFragment.setTextColorForDate(R.color.white, greenDate);
        }
    }
}
