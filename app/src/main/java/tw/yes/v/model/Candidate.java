package tw.yes.v.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.Serializable;

@ParseClassName("candidate")
public class Candidate extends ParseObject implements Serializable {

    public String getName() {
        return getString("name");
    }

    public ParseFile getProfile() {
        return getParseFile("profile");
    }
}