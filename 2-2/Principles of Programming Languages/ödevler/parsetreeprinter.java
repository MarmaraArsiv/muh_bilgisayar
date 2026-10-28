public class ParseTreePrinter {
    private ParseTree tree;
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    public ParseTreePrinter(ParseTree tree) {
        this.tree = tree;
        printTreeHelper(this.tree.getChildren().get(0), 0);
    }

    private void printTreeHelper(ParseTree tree, int indentLevel) {
        if (tree == null || tree.getData() == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\t".repeat(Math.max(0, indentLevel)));
        switch (tree.getData().getType()) {
            case "LEFTPAR", "RIGHTPAR", "DEFINE", "IDENTIFIER", "LET", "NUMBER", "IF" ->
                    System.out.println(sb + tree.getData().getType() + " (" + ANSI_RED + tree.getData().getData().getKey() + ANSI_RESET + ")");
            case "___" -> System.out.println(sb + tree.getData().getType());
            default -> System.out.println(sb + tree.getData().getName());
        }
        for (ParseTree child : tree.getChildren()) {
            printTreeHelper(child, indentLevel + 1);
        }
    }
}
