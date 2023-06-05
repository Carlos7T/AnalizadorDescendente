
package equipo.sqldescendente;

import java.util.Stack;
import java.util.HashMap;
import java.util.Map;
public class SQLDescendente {

  private static final Map<String, Map<String, String>> TABLE = new HashMap<>();

static {
    // Estado Q
    Map<String, String> qRow = new HashMap<>();
    qRow.put("Select", "D");
    TABLE.put("Q", qRow);

    // Estado D
    Map<String, String> dRow = new HashMap<>();
    dRow.put("Distinct", "P");
    dRow.put("identificador", "P");
    TABLE.put("D", dRow);

    // Estado P
    Map<String, String> pRow = new HashMap<>();
    pRow.put("*", "");
    pRow.put("identificador", "A");
    TABLE.put("P", pRow);

    // Estado A
    Map<String, String> aRow = new HashMap<>();
    aRow.put("identificador", "A2A1");
    TABLE.put("A", aRow);

    // Estado A1
    Map<String, String> a1Row = new HashMap<>();
    a1Row.put(",", "A");
    a1Row.put("$", "");
    TABLE.put("A1", a1Row);

    // Estado A2
    Map<String, String> a2Row = new HashMap<>();
    a2Row.put("identificador", "A3");
    TABLE.put("A2", a2Row);

    // Estado A3
    Map<String, String> a3Row = new HashMap<>();
    a3Row.put("identificador", "");
    a3Row.put(".", "A");
    a3Row.put("$", "");
    TABLE.put("A3", a3Row);

    // Estado T
    Map<String, String> tRow = new HashMap<>();
    tRow.put("identificador", "T2T1");
    TABLE.put("T", tRow);

    // Estado T1
    Map<String, String> t1Row = new HashMap<>();
    t1Row.put(",", "T");
    t1Row.put("$", "");
    TABLE.put("T1", t1Row);

    // Estado T2
    Map<String, String> t2Row = new HashMap<>();
    t2Row.put("identificador", "T3");
    TABLE.put("T2", t2Row);

    // Estado T3
    Map<String, String> t3Row = new HashMap<>();
    t3Row.put("identificador", "");
    t3Row.put("$", "");
    TABLE.put("T3", t3Row);
}

    private static boolean analyze(String input) {
        Stack<String> stack = new Stack<>();
        stack.push("$");
        stack.push("Q");

        int index = 0;
        String currentSymbol = String.valueOf(input.charAt(index));

        while (!stack.isEmpty()) {
            String top = stack.peek();

            if (isTerminal(top)) {
                if (top.equals(currentSymbol)) {
                    stack.pop();
                    index++;
                    if (index < input.length()) {
                        currentSymbol = String.valueOf(input.charAt(index));
                    } else {
                        currentSymbol = "$";
                    }
                } else {
                    return false;
                }
            } else {
                Map<String, String> row = TABLE.get(top);
                String action = row.get(currentSymbol);

                if (action == null) {
                    return false;
                }

                if (action.isEmpty()) {
                    stack.pop();
                } else {
                    stack.pop();
                    String[] symbols = action.split("");
                    for (int i = symbols.length - 1; i >= 0; i--) {
                        stack.push(symbols[i]);
                    }
                }
            }
        }

        return true;
    }

    private static boolean isTerminal(String symbol) {
        return symbol.matches("[A-Z]\\d*|\\*|\\.|,|\\$|id");
    }

    private static int getRow(String symbol) {
        return symbol.charAt(0) - 'A';
    }

    private static int getColumn(String inputSymbol) {
        switch (inputSymbol) {
            case "select":
                return 0;
            case "distinct":
                return 1;
            case "*":
                return 2;
            case ",":
                return 3;
            case ".":
                return 4;
            case "identificador":
                return 5;
            case "from":
                return 6;
            case "$":
                return 7;
            default:
                return 8;
        }
    }

    public static void main(String[] args) {
        String input = "select * from id";
        boolean isValid = analyze(input);
        if (isValid) {
            System.out.println("La cadena es válida.");
        } else {
            System.out.println("La cadena no es válida.");
        }
    }
}

