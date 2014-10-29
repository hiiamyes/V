package tw.yes.v.controller.candidate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.yes.v.R;
import tw.yes.v.model.Candidate;

@EFragment(R.layout.fragment_candidate_page)
public class CandidatePageFragment extends Fragment {

    @FragmentArg
    Candidate mCandidate;

    @ViewById(R.id.profile)
    ImageView imageviewProfile;

    @ViewById(R.id.listview_politics)
    ListView listviewPolitices;

    private ArrayList<HashMap<String, String>> listData = new ArrayList<HashMap<String, String>>();
    private PoliticsAdapter listAdapter;

    @AfterViews
    void initViews() {

        ParseFile profile = mCandidate.getProfile();
        if (profile != null) {
            profile.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, ParseException e) {
                    if (e == null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        imageviewProfile.setImageBitmap(bitmap);
                    } else {

                    }
                }
            });
        }

        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("politics", "politicspoliticspoliticspoliticspoliticspoliticspoliticspoliticspoliticspoliticspoliticspoliticspoliticspoliticspoliticspoliticspoliticspoliticspoliticspoliticspolitics");
        listData.add(hashMap);
        listData.add(hashMap);
        listData.add(hashMap);
        listAdapter = new PoliticsAdapter(getActivity(), listData);
        listviewPolitices.setAdapter(listAdapter);
    }

    float startY;

    public class PoliticsAdapter extends SimpleAdapter {

        public PoliticsAdapter(Context context, List<? extends Map<String, ?>> data) {
            super(
                    context,
                    data,
                    R.layout.listitem_politics,
                    new String[]{"politics"},
                    new int[]{R.id.textview_politics}
            );
        }
    }

}
