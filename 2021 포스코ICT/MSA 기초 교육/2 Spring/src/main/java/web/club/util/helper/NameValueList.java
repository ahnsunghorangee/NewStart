package web.club.util.helper;

import java.util.ArrayList;
import java.util.List;

public class NameValueList {
    //
    private List<NameValue> nameValues;

    public NameValueList() {
        //
        this.nameValues = new ArrayList<>();
    }

    public void addNameValue(String name, String value) {
        //
        NameValue nameValue = new NameValue(name, value);
        nameValues.add(nameValue);
    }

    public List<NameValue> getNameValues() {
        return nameValues;
    }
}
