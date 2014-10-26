package tw.yes.v.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import tw.yes.v.R;
import tw.yes.v.model.Candidate;

@EFragment(R.layout.fragment_candidate_page)
public class CandidatePageFragment extends Fragment {

    @FragmentArg
    Candidate mCandidate;


    @ViewById(R.id.profile)
    ImageView mProfile;

    @AfterViews
    void initViews() {

        ParseFile profile = mCandidate.getProfile();
        if (profile != null) {
            profile.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, ParseException e) {
                    if (e == null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        mProfile.setImageBitmap(bitmap);
                    } else {

                    }
                }
            });
        }
    }

}
