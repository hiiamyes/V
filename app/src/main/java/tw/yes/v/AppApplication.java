package tw.yes.v;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParsePush;

import tw.yes.v.model.Candidate;

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Candidate.class);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "CK3JOXdcsoOt1fCk4VG4bbM09nmh2bdUnuuVB5kk", "F4i7OkP4eoiXXWustFp2OGYAUjfgV5SVncJF3T2C");

        ParsePush.subscribeInBackground("yes");
        ParsePush.subscribeInBackground("");

    }
}
