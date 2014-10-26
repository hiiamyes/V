package tw.yes.v.controller;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import tw.yes.v.R;
import tw.yes.v.model.Candidate;

@EFragment(R.layout.fragment_launcher)
@OptionsMenu(R.menu.launcher_menu)
public class LauncherFragment extends Fragment
        implements ActionBar.TabListener {

    private static final String TAG = LauncherFragment.class.getSimpleName();

    private LauncherFragment mLauncherFragment;
    private ActionBar mActionBar;

    @ViewById(R.id.pager)
    ViewPager mPager;

    private CandidatePagerAdapter mPagerAdapter;

    @AfterViews
    void initViews() {

        mLauncherFragment = this;

        setHasOptionsMenu(true);

        mActionBar = getActivity().getActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mActionBar.setSelectedNavigationItem(position);
            }
        });

        ParseQuery<Candidate> query = ParseQuery.getQuery("candidate");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<Candidate>() {
            @Override
            public void done(List<Candidate> candidates, ParseException e) {
                if (e != null) {

                } else {
                    if (candidates.size() == 0) {
                        refresh();
                    } else {
                        mPagerAdapter = new CandidatePagerAdapter(getFragmentManager());
                        mPagerAdapter.setCandidates(candidates);
                        mPager.setOffscreenPageLimit(candidates.size());
                        mPager.setAdapter(mPagerAdapter);

                        for (Candidate candidate : candidates) {
                            mActionBar.addTab(mActionBar.newTab().setText(candidate.getName()).setTabListener(mLauncherFragment));
                        }
                    }
                }
            }
        });
    }

    void refresh() {
        ParseQuery<Candidate> queryW = ParseQuery.getQuery("candidate");
        queryW.findInBackground(new FindCallback<Candidate>() {
            public void done(List<Candidate> candidates, ParseException e) {
                if (e != null) {
                } else {
                    ParseObject.pinAllInBackground(candidates);
                    mPagerAdapter.setCandidates(candidates);
                    mPagerAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OptionsItem(R.id.refresh)
    void menuRefresh() {
        refresh();
    }

    /**
     * Tab
     */
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }


}
