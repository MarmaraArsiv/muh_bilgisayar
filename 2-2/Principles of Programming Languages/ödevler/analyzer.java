import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Analyzer {
    private final String filePath;
    private final List<Pair> content;

    public Analyzer(String filePath) {
        this.filePath = filePath;

        content = this.ReadFile();

    }

    public String ScanContent() {
        StringBuilder scanned = new StringBuilder();
        for (Pair pair : content) {
            String type = "";
            switch (pair.getKey()) {
                case "(" -> type = "LEFTPAR";
                case ")" -> type = "RIGHTPAR";
                case "[" -> type = "LEFTSQUAREB";
                case "]" -> type = "RIGHTSQUAREB";
                case "{" -> type = "LEFTCURLYB";
                case "}" -> type = "RIGHTCURLYB";
                default -> {
                    String str = pair.getKey();
                    boolean isDecimal = isDecimal(str) || isHexNumber(str) || isFractional(str);
                    if (isDecimal) {
                        type = "NUMBER";
                    } else if (str.charAt(0) == '\"') {
                        type = "STRING";
                    } else {
                        switch (str) {
                            case "define" -> type = "DEFINE";
                            case "let" -> type = "LET";
                            case "cond" -> type = "COND";
                            case "if" -> type = "IF";
                            case "begin" -> type = "BEGIN";
                            case "true", "false" -> type = "BOOLEAN";
                            default -> {
                                if (str.charAt(0) == '\'') {
                                    type = "CHAR";
                                } else {
                                    if (!isValidIdentifier(str)) {
                                        String errorMessage = String.format("LEXICAL ERROR [%s]: Invalid token '%s'.\n", pair.getValue(), str);
                                        scanned.append(errorMessage);
                                    } else {
                                        type = "IDENTIFIER";
                                    }
                                }
                            }
                        }
                    }
                }
            }
            pair.setType(type);
            scanned.append(type).append(" ").append(pair.getValue()).append("\n");
        }
        return scanned.toString();
    }

    private boolean isFractional(String str) {
        // Remove leading and trailing whitespaces
        str = str.trim();

        // Check if the input is empty or null
        if (str.isEmpty() || str == null) {
            return false;
        }

        // Check if the input starts with a plus or minus sign
        // If yes, remove it for further validation
        boolean hasSign = false;
        if (str.charAt(0) == '-' || str.charAt(0) == '+') {
            hasSign = true;
            str = str.substring(1);
        }


        // Split the input by dot (.) to check if it has only one dot
        String[] parts = str.split("\\.");
        if (parts.length != 2) {
            return false;
        }


        // Check if the parts are numeric
        boolean isNumeric1 = isNumeric(parts[0]);
        boolean isNumeric2 = isNumeric(parts[1]);

        // Check if both parts are numeric and if the input has a dot
        // If yes, return true, else return false


        return isNumeric1 && isNumeric2;


    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDecimal(String input) {

        // Remove any leading or trailing whitespaces
        input = input.trim();

        // Check if the input starts with a '+' or '-' sign
        if (input.startsWith("+") || input.startsWith("-")) {
            // Remove the sign from the input string
            input = input.substring(1);
        }

        // Check if the input is empty after removing the sign
        if (input.isEmpty()) {
            return false;
        }

        // Loop through each character in the input string
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            // If any character is not a digit, return false
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        // If all characters are digits, return true
        return true;
    }

    // Used StringBuilder to optimize iterated concatenate operation
    private List<Pair> ReadFile() {
        StringBuilder rawTextBuilder = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                rawTextBuilder.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String rawText = rawTextBuilder.toString();


        List<Pair> result = new ArrayList<>();
        int currentLine = 1;
        int currentIndex = 1;

        boolean isComp = false;                         // Tokens with multiple characters -> compound
        String currentCompound = "";
        int compIndex = 0;

        boolean stringCheck = false;                    // This becomes true when the current character is " and stringCheck == false
        boolean decimalCheck = false;
        boolean hexCheck = false;
        int textLength = rawText.length();
        for (int i = 0; i < textLength; i++) {
            char currentChar = rawText.charAt(i);
            char nextChar = ' ';
            if (i + 1 < textLength) {
                nextChar = rawText.charAt(i + 1);
            }


            switch (currentChar) {                       //Special literals
                case '\n':
                    currentLine++;
                case '\r':
                    if (hexCheck) {
                        result.add(new Pair(currentCompound, currentLine + ":" + compIndex));
                        hexCheck = false;
                        currentCompound = "";
                    }
                    currentIndex = 1;
                    continue;
                case ' ':
                    if (hexCheck) {
                        result.add(new Pair(currentCompound, currentLine + ":" + compIndex));
                        hexCheck = false;
                        currentCompound = "";
                    }
                    currentIndex++;
                    continue;
                case '+':
                case '-':
                    if (isComp) break;
                    if (nextChar != ' ' && nextChar != '\n') {
                        decimalCheck = true;
                        currentCompound += currentChar;
                        compIndex = currentIndex;
                    } else {
                        result.add(new Pair(String.valueOf(currentChar), currentLine + ":" + currentIndex));
                    }

                    currentIndex++;
                    continue;

            }

            String identifierPrefixes = "!*/:<=>?";        // If the current char is prefix of an identifier... (check the IDENTIFIER part of the document)
            if (identifierPrefixes.indexOf(currentChar) != -1 && currentCompound.length() == 0) {
                if (isIdentifierChar(nextChar)) {
                    isComp = true;
                    currentCompound += currentChar;
                    compIndex = currentIndex;
                    currentIndex++;
                    continue;
                }
            }


            if ((Character.isLetter(currentChar) || isComp) && currentChar != '\"') {
                if (hexCheck) {
                    currentCompound += currentChar;
                    if (!Character.isLetter(nextChar) && !Character.isDigit(nextChar)) {     // Unless the first element was \" ...
                        result.add(new Pair(currentCompound, currentLine + ":" + compIndex));
                        hexCheck = false;
                        currentCompound = "";
                    }
                } else {
                    if (isComp) {                            // If the token starts with a letter and continues with another letter, it is a compound
                        currentCompound += currentChar;
                        if (!((currentChar == 'e' || currentChar == 'E') && decimalCheck)) {
                            if (!isIdentifierChar(nextChar) && !stringCheck) {     // Unless the first element was \" ...
                                result.add(new Pair(currentCompound, currentLine + ":" + compIndex));
                                isComp = false;
                                decimalCheck = false;
                                currentCompound = "";
                            }
                        }

                    } else {                                // currentChar is first letter of a compound
                        if (Character.isLetter(nextChar)) {
                            currentCompound += currentChar;
                            isComp = true;
                            if (!stringCheck) {
                                compIndex = currentIndex;
                            }
                        } else {
                            result.add(new Pair(String.valueOf(currentChar), currentLine + ":" + currentIndex));
                        }
                    }
                }
            } else if (Character.isDigit(currentChar)) {
                if (Character.isDigit(nextChar)) {
                    currentCompound += currentChar;

                    if (!decimalCheck && !hexCheck) {
                        compIndex = currentIndex;
                        decimalCheck = true;
                    }
                } else if (!decimalCheck && !hexCheck && nextChar == 'x') {     // If it is the first element of the token and the next char is x...
                    hexCheck = true;
                    compIndex = currentIndex;
                    currentCompound += currentChar;
                    currentCompound += nextChar;
                    currentIndex++;
                    i++;                // Skipping x
                } else {
                    if (isComp) {
                        currentCompound += currentChar;
                    } else if (isIdentifierChar(nextChar) && !hexCheck) {
                        currentCompound += currentChar;
                        isComp = true;
                        compIndex = currentIndex;
                    } else if (!hexCheck) {
                        if (decimalCheck && nextChar != '.') {
                            currentCompound += currentChar;
                            result.add(new Pair(String.valueOf(currentCompound), currentLine + ":" + compIndex));
                            decimalCheck = false;
                            currentCompound = "";
                        } else {
                            if (nextChar == '.') {   // For fractional numbers
                                if (!decimalCheck) {
                                    decimalCheck = true;
                                    compIndex = currentIndex;
                                }
                                isComp = true;
                                currentCompound += currentChar;
                            } else {
                                result.add(new Pair(String.valueOf(currentChar), currentLine + ":" + currentIndex));
                            }
                        }
                    }


                }
            } else if (currentChar == '\"') {            // Similar to compound but it must start and end with \"
                currentCompound += currentChar;
                if (stringCheck) {

                    result.add(new Pair(currentCompound, currentLine + ":" + compIndex));
                    stringCheck = false;
                    isComp = false;
                    currentCompound = "";
                } else {                                // First quotation mark
                    stringCheck = true;
                    compIndex = currentIndex;
                }
            } else {  // Everything else
                if (hexCheck) {
                    result.add(new Pair(currentCompound, currentLine + ":" + compIndex));
                    hexCheck = false;
                    currentCompound = "";


                }
                if (currentChar == '\'') {
                    result.add(new Pair(String.valueOf(currentChar) + nextChar + rawText.charAt(i + 2), currentLine + ":" + currentIndex));
                    i += 2;
                    currentIndex += 2;

                } else {
                    result.add(new Pair(String.valueOf(currentChar), currentLine + ":" + currentIndex));
                }


            }

            currentIndex++;


        }

        return result;
    }

    private boolean isHexNumber(String input) {
        // Check if input starts with "0x" and has at least one additional character
        if (input.length() < 3 || !input.substring(0, 2).equalsIgnoreCase("0x")) {
            return false;
        }

        // Check if all characters after "0x" are (0-9, A-F, a-f)
        for (int i = 2; i < input.length(); i++) {
            char c = input.charAt(i);
            if (!(c >= '0' && c <= '9') && !(c >= 'A' && c <= 'F') && !(c >= 'a' && c <= 'f')) {
                return false;
            }
        }

        return true;
    }


    private boolean isIdentifierChar(char c) {
        if (Character.isLetter(c) || Character.isDigit(c) || c == '_')
            return true;

        return false;
    }


    private boolean isValidIdentifier(String str) {
        if (str.isEmpty() || Character.isDigit(str.charAt(0))) {
            // If the string is empty or starts with a digit, it's not a valid identifier
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            char current = str.charAt(i);
            char next = ' ';
            if (i + 1 < str.length()) {
                next = str.charAt(i + 1);
            }

            if ((current == '+' && next == '-') || (current == '-' && next == '+')) {
                return false;
            }
        }

        return true;
    }


    public List<Pair> getContent() {
        return content;
    }

}
