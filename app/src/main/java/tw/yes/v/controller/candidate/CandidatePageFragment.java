package tw.yes.v.controller.candidate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

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
import tw.yes.v.model.Politics;
//import tw.yes.v.model.Candidate;

@EFragment(R.layout.fragment_candidate_page)
public class CandidatePageFragment extends Fragment {

    @FragmentArg
    int position;

    @ViewById(R.id.profile)
    ImageView imageViewProfile;

    @ViewById(R.id.listview_politics)
    ListView listviewPolitices;

    private ArrayList<HashMap<String, Object>> listData = new ArrayList<HashMap<String, Object>>();
    private PoliticsAdapter listAdapter;

    @AfterViews
    void initViews() {

        ParseQuery<Candidate> query = ParseQuery.getQuery("candidate");
        query.whereEqualTo("number", position + 1);
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<Candidate>() {
            @Override
            public void done(List<Candidate> candidates, ParseException e) {
                Candidate candidate = candidates.get(0);

                ParseFile profile = candidate.getParseFile("profile");
                if (profile != null) {
                    profile.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] bytes, ParseException e) {
                            if (e == null) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                imageViewProfile.setImageBitmap(bitmap);
                            } else {

                            }
                        }
                    });
                }

                ParseQuery<Politics> politicsParseQuery = ParseQuery.getQuery("politics");
                politicsParseQuery.whereEqualTo("candidate", candidate.getName());
                politicsParseQuery.fromLocalDatastore();
                politicsParseQuery.findInBackground(new FindCallback<Politics>() {
                    @Override
                    public void done(List<Politics> politicses, ParseException e) {
                        listData.clear();
                        for (Politics politics : politicses) {
                            HashMap<String, Object> hashMap = new HashMap<String, Object>();
                            hashMap.put("politics", politics.getDescription());
                            hashMap.put("source", politics.getSource());
                            listData.add(hashMap);
                        }
                        listAdapter = new PoliticsAdapter(getActivity(), listData);
                        listviewPolitices.setAdapter(listAdapter);
                    }
                });
            }
        });

    }

    public class PoliticsAdapter extends SimpleAdapter {

        public PoliticsAdapter(Context context, List<? extends Map<String, ?>> data) {
            super(
                    context,
                    data,
                    R.layout.listitem_politics,
                    new String[]{"politics", "source"},
                    new int[]{R.id.textview_politics, R.id.textview_source}
            );
            setViewBinder(new VB());
        }

        class VB implements ViewBinder {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if (view.getId() == R.id.textview_source) {
                    ((TextView) view).setText(Html.fromHtml((String) data));
                    ((TextView) view).setMovementMethod(LinkMovementMethod.getInstance());
                    return true;
                }
                return false;
            }
        }
    }
}
