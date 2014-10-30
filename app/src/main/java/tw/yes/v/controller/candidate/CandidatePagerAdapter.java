package tw.yes.v.controller.candidate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CandidatePagerAdapter extends FragmentPagerAdapter {

    public CandidatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    private int count = 0;

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        return CandidatePageFragment_.builder().position(position).build();
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
