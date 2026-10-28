import java.util.ArrayList;
import java.util.List;

public class ParseTree {
    private ParseTreeNode data;
    private List<ParseTree> children;
    private ParseTree parent;

    public ParseTree(ParseTreeNode data) {
        this.data = data;
        this.children = new ArrayList<>();
        this.parent = null;
    }

    public ParseTreeNode getData() {
        return data;
    }

    public List<ParseTree> getChildren() {
        return children;
    }

    public ParseTree getParent() {
        return parent;
    }

    public void setParent(ParseTree parent) {
        this.parent = parent;
    }

    public void addChild(ParseTreeNode node) {
        ParseTree child = new ParseTree(node);
        child.setParent(this);
        children.add(child);
    }

    public ParseTree addChildUpdateTree(ParseTreeNode node) {
        ParseTree child = new ParseTree(node);
        child.setParent(this);
        children.add(child);
        return child;
    }

}