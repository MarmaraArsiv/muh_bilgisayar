public class Pair {
    private String type;
    private final String key;
    private String value;
    public int row;
    public int col;

    public Pair(String key, String value) {
        this.type = "";
        this.key = key;
        this.value = value;
        String[] valueSplit = value.split(":", 2);
        this.row = Integer.parseInt(valueSplit[0]);
        this.col = Integer.parseInt(valueSplit[1]);
    }

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }
}