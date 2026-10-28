import java.util.List;
import java.util.Objects;

public class Parser {
    private List<Pair> tokens;
    private int currentIndex;
    private ParseTree parseTree;
    private ParseTree currentTree;

    public Parser(List<Pair> tokens) {
        this.tokens = tokens;
        this.currentIndex = 0;
        this.parseTree = new ParseTree(null);
        this.currentTree = this.parseTree;
    }

    public void parse() {
        if (currentIndex > tokens.size()) {
            printError("Token index is out of the bounds.");
        }
        invokeNonTerminal("Program");
    }

    /**
     * <Program> -> ε | <TopLevelForm> <Program>
     */
    private void program() {
        addNode("Program");
        if (currentIndex < tokens.size()) {
            invokeNonTerminal("TopLevelForm");
            invokeNonTerminal("Program");
        } else {
            addNode("___");
        }
    }

    /**
     * <TopLevelForm> -> ( <SecondLevelForm> )
     */
    private void topLevelForm() {
        addNode("TopLevelForm");
        match("LEFTPAR");
        invokeNonTerminal("SecondLevelForm");
        match("RIGHTPAR");

    }

    /**
     * <SecondLevelForm> -> <Definition> | ( <FunCall> )
     */
    private void secondLevelForm() {
        addNode("SecondLevelForm");
        if (checkToken("DEFINE")) {
            invokeNonTerminal("Definition");
        } else {
            match("LEFTPAR");
            invokeNonTerminal("FunCall");
            match("RIGHTPAR");
        }
    }

    /**
     * <Definition> -> DEFINE <DefinitionRight>
     */
    private void definition() {
        match("DEFINE");
        addNode("Definition");
        invokeNonTerminal("DefinitionRight");
    }

    /**
     * <DefinitionRight> -> IDENTIFIER <Expression> | ( IDENTIFIER <ArgList> ) <Statements>
     */
    private void definitionRight() {
        addNode("DefinitionRight");
        if (checkToken("IDENTIFIER")) {
            match("IDENTIFIER");
            invokeNonTerminal("Expression");
        } else {
            match("LEFTPAR");
            invokeNonTerminal("ArgList");
            match("RIGHTPAR");
            invokeNonTerminal("Statements");
        }
    }

    /**
     * <ArgList> -> ε | IDENTIFIER <ArgList>
     */
    private void argList() {
        addNode("ArgList");
        if (checkToken("IDENTIFIER")) {
            match("IDENTIFIER");
            invokeNonTerminal("ArgList");
        } else {
            addNode("___");
        }
    }

    /**
     * <Statements> -> <Expression> | <Definition> <Statements>
     */
    private void statements() {
        addNode("Statements");
        if (!checkToken("DEFINE")) {
            invokeNonTerminal("Expression");
        } else {
            invokeNonTerminal("Definition");
            invokeNonTerminal("Statements");
        }
    }

    /**
     * <Expressions> -> ε | <Expression> <Expressions>
     */
    private void expressions() {
        addNode("Expressions");
        if (!checkToken("RIGHTPAR")) {
            invokeNonTerminal("Expression");
            invokeNonTerminal("Expressions");
        } else {
            addNode("___");
        }
    }

    /**
     * <Expression> -> IDENTIFIER | NUMBER | CHAR | BOOLEAN | STRING | ( <Expr> )
     */
    private void expression() {
        addNode("Expression");
        if (checkToken("IDENTIFIER") || checkToken("NUMBER") ||
                checkToken("CHAR") || checkToken("BOOLEAN") ||
                checkToken("STRING")) {
            match(tokens.get(currentIndex).getType());
        } else if (checkToken("LEFTPAR")) {
            match("LEFTPAR");
            invokeNonTerminal("Expr");
            match("RIGHTPAR");
        }
    }

    /**
     * <Expr> -> <LetExpression> | <CondExpression> | <IfExpression> | <BeginExpression> | <FunCall>
     */
    private void expr() {
        addNode("Expr");
        if (checkToken("LET")) {
            invokeNonTerminal("LetExpression");
        } else if (checkToken("COND")) {
            invokeNonTerminal("CondExpression");
        } else if (checkToken("IF")) {
            invokeNonTerminal("IfExpression");
        } else if (checkToken("BEGIN")) {
            invokeNonTerminal("BeginExpression");
        } else {
            invokeNonTerminal("FunCall");
        }
    }

    /**
     * <FunCall> -> IDENTIFIER <Expressions>
     */
    private void funCall() {
        addNode("FunCall");
        match("IDENTIFIER");
        invokeNonTerminal("Expressions");
    }

    /**
     * <LetExpression> -> LET <LetExpr>
     */
    private void letExpression() {
        addNode("LetExpression");
        match("LET");
        invokeNonTerminal("LetExpr");
    }

    /**
     * <LetExpr> -> ( <VarDefs> ) <Statements> | IDENTIFIER ( <VarDefs> ) <Statements>
     */
    private void letExpr() {
        addNode("LetExpr");
        if (checkToken("LEFTPAR")) {
            match("LEFTPAR");
            invokeNonTerminal("VarDefs");
            match("RIGHTPAR");
            invokeNonTerminal("Statements");
        } else {
            match("IDENTIFIER");
            match("LEFTPAR");
            invokeNonTerminal("VarDefs");
            match("RIGHTPAR");
            invokeNonTerminal("Statements");
        }
    }

    /**
     * <VarDefs> -> ( IDENTIFIER <Expression> ) <VarDef>
     */
    private void varDefs() {
        addNode("VarDefs");
        match("LEFTPAR");
        match("IDENTIFIER");
        invokeNonTerminal("Expression");
        match("RIGHTPAR");
        invokeNonTerminal("VarDef");
    }

    /**
     * <VarDef> -> ε | <VarDefs>
     */
    private void varDef() {
        addNode("VarDef");
        if (checkToken("LEFTPAR")) {
            invokeNonTerminal("VarDefs");
        } else {
            addNode("___");
        }
    }

    /**
     * <CondExpression> -> COND <CondBranches>
     */
    private void condExpression() {
        match("COND");
        invokeNonTerminal("CondBranches");
    }

    /**
     * <CondBranches> -> ( <Expression> <Statements> ) <CondBranches>
     */
    private void condBranches() {
        if (checkToken("LEFTPAR")) {
            match("LEFTPAR");
            invokeNonTerminal("Expression");
            invokeNonTerminal("Statements");
            match("RIGHTPAR");
            invokeNonTerminal("CondBranches");
        }
    }

    /**
     * <CondBranch> -> ε | ( <Expression> <Statements> )
     */
    private void condBranch() {
        if (checkToken("LEFTPAR")) {
            match("LEFTPAR");
            invokeNonTerminal("Expression");
            invokeNonTerminal("Statements");
            match("RIGHTPAR");
        } else {
            addNode("___");
        }
    }

    /**
     * <IfExpression> -> IF <Expression> <Expression> <EndExpression>
     */
    private void ifExpression() {
        addNode("IfExpression");
        match("IF");
        invokeNonTerminal("Expression");
        invokeNonTerminal("Expression");
        invokeNonTerminal("EndExpression");
    }

    /**
     * <EndExpression> -> ε | <Expression>
     */
    private void endExpression() {
        addNode("EndExpression");
        if (!checkToken("RIGHTPAR")) {
            invokeNonTerminal("Expression");
        } else {
            addNode("___");
        }
    }

    /**
     * <BeginExpression> -> BEGIN <Statements>
     */
    private void beginExpression() {
        addNode("BeginExpression");
        match("BEGIN");
        invokeNonTerminal("Statements");
    }

    private void invokeNonTerminal(String nonTerminal) {
        ParseTree parentTree = currentTree;
        switch (nonTerminal) {
            case "Program":
                program();
                break;
            case "TopLevelForm":
                topLevelForm();
                break;
            case "SecondLevelForm":
                secondLevelForm();
                break;
            case "Definition":
                definition();
                break;
            case "DefinitionRight":
                definitionRight();
                break;
            case "ArgList":
                argList();
                break;
            case "Statements":
                statements();
                break;
            case "Expressions":
                expressions();
                break;
            case "Expression":
                expression();
                break;
            case "Expr":
                expr();
                break;
            case "FunCall":
                funCall();
                break;
            case "LetExpression":
                letExpression();
                break;
            case "LetExpr":
                letExpr();
                break;
            case "VarDefs":
                varDefs();
                break;
            case "VarDef":
                varDef();
                break;
            case "CondExpression":
                condExpression();
                break;
            case "CondBranches":
                condBranches();
                break;
            case "CondBranch":
                condBranch();
                break;
            case "IfExpression":
                ifExpression();
                break;
            case "EndExpression":
                endExpression();
                break;
            case "BeginExpression":
                beginExpression();
                break;
        }
        currentTree = parentTree;
    }

    private void match(String expectedToken) {
        if (currentIndex >= tokens.size()) {
            Pair previousToken = tokens.get(currentIndex - 1);
            Pair expected = new Pair(expectedToken, previousToken.row + ":" + (previousToken.col + 1));
            printError(expected, expectedToken);
        }
        Pair token = tokens.get(currentIndex);
        if (!token.getType().equals(expectedToken)) {
            printError(token, expectedToken);
        }
        switch (token.getType()) {
            case "IDENTIFIER":
            case "NUMBER":
            case "LEFTPAR":
            case "RIGHTPAR":
            case "IF":
                addChild(token.getType());
                break;
        }
        currentIndex++;
    }

    private void printError(String error){
        System.out.println(error);
        System.exit(1);
    }

    private void printError(Pair currentToken, String expectedToken) {
        if (Objects.equals(expectedToken, "LEFTPAR")) {
            expectedToken = "(";
        } else if (Objects.equals(expectedToken, "RIGHTPAR")) {
            expectedToken = ")";
        }
        String error = String.format("SYNTAX ERROR [%s]: '%s' is expected", currentToken.getValue(), expectedToken);
        ParseTreePrinter printer = new ParseTreePrinter(this.getParseTree());
        System.out.println(error);
        System.exit(1);
    }

    private Pair getCurrentToken() {
        if (currentIndex >= tokens.size()) {
            return null;
        }
        return tokens.get(currentIndex);
    }

    private boolean checkToken(String expectedToken) {
        if (currentIndex >= tokens.size()) {
            return false;
        }

        return tokens.get(currentIndex).getType().equals(expectedToken);
    }

    private ParseTreeNode createNode(String type) {
        return new ParseTreeNode(type, getCurrentToken());
    }

    private void addNode(String type) {
        this.currentTree = this.currentTree.addChildUpdateTree(this.createNode(type));
    }

    private void addChild(String type) {
        this.currentTree.addChild(this.createNode(type));
    }

    public List<Pair> getTokens() {
        return tokens;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public ParseTree getParseTree() {
        return parseTree;
    }

    public ParseTree getCurrentTree() {
        return currentTree;
    }
}
