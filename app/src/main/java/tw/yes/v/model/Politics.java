package tw.yes.v.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("politics")
public class Politics extends ParseObject {

    public String getCandidate() {
        return getString("candidate");
    }

    public String getDescription() {
        return getString("description");
    }

    public String getSource() {
        return getString("source");
    }
}