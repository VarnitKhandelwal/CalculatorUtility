import java.util.ArrayList;
import java.util.Stack;

class CalculatorUtility {
    ArrayList<Character> operators;

    public CalculatorUtility() {
        operators = new ArrayList<>();
        operators.add('^');
        operators.add('/');
        operators.add('%');
        operators.add('*');
        operators.add('+');
        operators.add('-');
    }


    public String findPostfix(String inputExpr) {
        StringBuilder postfixExpr = new StringBuilder();
        ArrayList<Character> al = new ArrayList<>();
        al.add('(');
        char ch;
        inputExpr = inputExpr + ")";
        for (int i = 0; i < inputExpr.length(); i++) {
            ch = inputExpr.charAt(i);
            if (ch == '(')
                al.add('(');
            else if (operators.contains(ch)) {
                al.add(ch);
                postfixExpr.append(pop(al, ch));
            } else if (ch == ')')
                postfixExpr.append(pop(al));
            else {
                while (ch != '(' && ch != ')' && !operators.contains(ch)) {
                    postfixExpr.append(ch);
                    i = i + 1;
                    ch = inputExpr.charAt(i);
                }
                postfixExpr.append(" ");
                i = i - 1;
            }
        }
        return postfixExpr.toString();
    }


    boolean isOperator(char ch) {
        return operators.contains(ch);
    }


    String pop(ArrayList<Character> charactersList, char ch) {
        StringBuilder temp = new StringBuilder();
        for (int i = charactersList.size() - 1; charactersList.get(i) != '(' && i >= 0; i--) {
            if (operators.indexOf(charactersList.get(i)) < operators.indexOf(ch)) {
                temp.append(charactersList.get(i));
                charactersList.remove(i);
            }
        }
        return temp.toString();
    }

    String pop(ArrayList<Character> al) {
        StringBuilder temp = new StringBuilder();
        for (int i = al.size() - 1; al.get(i) != '(' && i >= 0; i--) {
            temp.append(al.get(i));
            al.remove(i);
        }
        al.remove(al.size() - 1);
        return temp.toString();
    }


    public float evaluatePostfix(String inputP) {
        Stack<Float> stack = new Stack<>();
        char ch;
        float inputA;
        float inputB;
        for (int i = 0; i < inputP.length(); i++) {
            ch = inputP.charAt(i);

            if (operators.contains(ch)) {
                inputB = stack.pop();
                inputA = stack.pop();
                switch (ch) {
                    case '^':
                        stack.push((float) Math.pow(inputA, inputB));
                        break;
                    case '/':
                        stack.push(inputA / inputB);
                        break;
                    case '%':
                        stack.push(inputA % inputB);
                        break;
                    case '*':
                        stack.push(inputA * inputB);
                        break;
                    case '+':
                        stack.push(inputA + inputB);
                        break;
                    case '-':
                        stack.push(inputA - inputB);
                        break;
                }
            } else {
                StringBuilder temp = new StringBuilder();
                while (!operators.contains(ch) && ch != ' ') {
                    temp.append(ch);
                    i = i + 1;
                    ch = inputP.charAt(i);
                }
                stack.push((Float.parseFloat(temp.toString())));
            }

        }
        return stack.pop();
    }

    public static void main(String[] args) {
        System.out.println("write the expression to calculate: ");
        String inputExpr = "2+7%8*12/2";
        CalculatorUtility calculatorUtility = new CalculatorUtility();
        String postfix = calculatorUtility.findPostfix(inputExpr);
        System.out.println("Postfix expression :" + postfix);
        float result = calculatorUtility.evaluatePostfix(postfix);
        System.out.println("Result of " + inputExpr + " is : " + result);
    }
}
