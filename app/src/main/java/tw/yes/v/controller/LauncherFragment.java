package tw.yes.v.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import tw.yes.v.R;
import tw.yes.v.model.Candidate;

@EFragment(R.layout.fragment_launcher)
public class LauncherFragment extends Fragment {

    private static final String TAG = LauncherFragment.class.getSimpleName();


    @ViewById(R.id.pager)
    ViewPager mPager;

    private CandidatePagerAdapter mPagerAdapter;

    @AfterViews
    void initViews() {

        setHasOptionsMenu(true);

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
                        mPager.setAdapter(mPagerAdapter);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.launcher_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                refresh();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public class CandidatePagerAdapter extends FragmentStatePagerAdapter {

        private List<Candidate> mCandidates;

        public CandidatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setCandidates(List<Candidate> candidates) {
            mCandidates = candidates;
        }

        @Override
        public Fragment getItem(int position) {
            return CandidatePageFragment_.builder().mCandidate(mCandidates.get(position)).build();
        }

        @Override
        public int getCount() {
            return mCandidates.size();
        }
    }


}
