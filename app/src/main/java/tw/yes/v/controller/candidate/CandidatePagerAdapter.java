package tw.yes.v.controller.candidate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import tw.yes.v.model.Candidate;

public class CandidatePagerAdapter extends FragmentPagerAdapter {

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

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
