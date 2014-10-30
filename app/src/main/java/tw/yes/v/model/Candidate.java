package tw.yes.v.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import org.json.JSONArray;

@ParseClassName("candidate")
public class Candidate extends ParseObject {


    public String getName() {
        return getString("name");
    }

    public ParseFile getProfile() {
        return getParseFile("profile");
    }

    public JSONArray getPolitics() {
        return getJSONArray("politics");
    }

    public int getNumber() {
        return getInt("number");
    }
}