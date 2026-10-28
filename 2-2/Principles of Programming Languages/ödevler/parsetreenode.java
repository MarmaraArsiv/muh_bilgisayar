public class ParseTreeNode {
    private String name;
    private String type;
    private Pair data;

    public ParseTreeNode(String type, Pair data) {
        this.type = type;
        this.name = "<" + type + ">";
        if (data != null) {
            this.data = data;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Pair getData() {
        return data;
    }

    public void setData(Pair data) {
        this.data = data;
    }
}
