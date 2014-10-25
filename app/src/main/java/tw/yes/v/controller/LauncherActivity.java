package tw.yes.v.controller;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.parse.ParseAnalytics;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import tw.yes.v.R;

@EActivity(R.layout.activity_launcher)
public class LauncherActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    @AfterViews
    void initViews() {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new LauncherFragment_()).commit();
    }


}
