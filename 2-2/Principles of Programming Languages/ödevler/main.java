public class Main {
    private static final String path = "./lexical.txt";

    public static void main(String[] args) {
        Analyzer analyzer = new Analyzer(path);
        System.out.println(analyzer.ScanContent());
        Parser parser = new Parser(analyzer.getContent());
        parser.parse();
        ParseTreePrinter printer = new ParseTreePrinter(parser.getParseTree());
    }
}