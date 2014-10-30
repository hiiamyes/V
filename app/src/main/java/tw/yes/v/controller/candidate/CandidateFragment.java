package tw.yes.v.controller.candidate;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import tw.yes.v.model.Politics;

@EFragment(R.layout.fragment_candidate)
@OptionsMenu(R.menu.launcher_menu)
public class CandidateFragment extends Fragment
        implements ActionBar.TabListener {

    private static final String TAG = CandidateFragment.class.getSimpleName();

    private CandidateFragment mCandidateFragment;
    private ActionBar mActionBar;

    @ViewById(R.id.progress)
    RelativeLayout mProgress;
    @ViewById(R.id.pager)
    ViewPager mPager;

    private CandidatePagerAdapter mPagerAdapter;

    @AfterViews
    void initViews() {

        mCandidateFragment = this;

        setHasOptionsMenu(true);

        mActionBar = getActivity().getActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mActionBar.setTitle("");

        mPagerAdapter = new CandidatePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mActionBar.setSelectedNavigationItem(position);
            }
        });


        ParseQuery<Candidate> query = ParseQuery.getQuery("candidate");
        query.orderByAscending("number");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<Candidate>() {
            @Override
            public void done(List<Candidate> candidates, ParseException e) {
                if (e != null) {
//                    Toast.makeText(getActivity(), "噢喔～有點問題喔！", Toast.LENGTH_LONG).show();
                    refresh();
                } else {
                    if (candidates.size() == 0 || candidates.get(0).getNumber() == 0) {
                        refresh();
                    } else {
                        mPagerAdapter.setCount(candidates.size());
                        mPagerAdapter.notifyDataSetChanged();
                        mPager.setOffscreenPageLimit(candidates.size());
                        mActionBar.removeAllTabs();
                        for (Candidate candidate : candidates) {
                            mActionBar.addTab(mActionBar.newTab().setText(candidate.getName()).setTabListener(mCandidateFragment));
                        }
                    }
                }
            }
        });
    }

    void refresh() {
        mProgress.setVisibility(View.VISIBLE);
        ParseQuery<Candidate> query = ParseQuery.getQuery("candidate");
        query.orderByAscending("number");
        query.findInBackground(new FindCallback<Candidate>() {
            public void done(final List<Candidate> candidates, ParseException e) {
                if (e != null) {
                    mProgress.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "噢喔～有點問題喔！\n請確認您的網路狀態", Toast.LENGTH_LONG).show();
                } else {
                    ParseObject.pinAllInBackground(candidates);
                    ParseQuery<Politics> queryPolitics = ParseQuery.getQuery("politics");
                    queryPolitics.findInBackground(new FindCallback<Politics>() {
                        @Override
                        public void done(List<Politics> politicses, ParseException e) {
                            ParseObject.pinAllInBackground(politicses);
                            mProgress.setVisibility(View.GONE);
                            mPagerAdapter.setCount(candidates.size());
                            mPagerAdapter.notifyDataSetChanged();
                            mPager.setOffscreenPageLimit(candidates.size());
                            mActionBar.removeAllTabs();
                            for (Candidate candidate : candidates) {
                                mActionBar.addTab(mActionBar.newTab().setText(candidate.getName()).setTabListener(mCandidateFragment));
                            }
                        }
                    });
                }
            }
        });
    }


    @OptionsItem(R.id.refresh)
    void menuRefresh() {
        refresh();
    }

    @OptionsItem(R.id.feedback)
    void menuFeedback() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:hiiamyes.app.v@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "給 V 的意見回饋");
        intent.putExtra(Intent.EXTRA_TEXT, "內容：\n");
        startActivity(intent);
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
