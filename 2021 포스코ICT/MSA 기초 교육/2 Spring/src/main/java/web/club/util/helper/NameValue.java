package web.club.util.helper;

public class NameValue {
    //
    private String name;
    private String value;

    public NameValue() { }

    public NameValue(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        //
        StringBuilder builder = new StringBuilder();

        builder.append("NameValue{")
                .append("name='").append(name).append('\'')
                .append(", value='").append(value).append('\'')
                .append('}');

        return builder.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
