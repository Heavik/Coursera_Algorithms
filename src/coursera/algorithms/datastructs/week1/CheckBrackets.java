package coursera.algorithms.datastructs.week1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Stack;

class Bracket {

    Bracket(char type, int position) {
        this.type = type;
        this.position = position;
    }

    boolean match(char c) {
        if (this.type == '[' && c == ']') {
            return true;
        }
        if (this.type == '{' && c == '}') {
            return true;
        }
        if (this.type == '(' && c == ')') {
            return true;
        }
        return false;
    }

    char type;
    int position;
}

class CheckBrackets {

    public static void main(String[] args) throws IOException {
        InputStreamReader inputStream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(inputStream);
        String text = reader.readLine();

        Stack<Bracket> openingBracketsStack = new Stack<>();
        for (int position = 0; position < text.length(); ++position) {
            char next = text.charAt(position);

            if (next == '(' || next == '[' || next == '{') {
                openingBracketsStack.push(new Bracket(next, position + 1));
            }

            if (next == ')' || next == ']' || next == '}') {
                if (openingBracketsStack.empty()) {
                    openingBracketsStack.push(new Bracket(next, position + 1));
                    break;
                }
                Bracket currentOpenedBracket = openingBracketsStack.pop();
                if (!currentOpenedBracket.match(next)) {
                    openingBracketsStack.push(new Bracket(next, position + 1));
                    break;
                }
            }
        }

        if (!openingBracketsStack.empty()) {
            Bracket incorrectBracket = openingBracketsStack.pop();
            System.out.println(incorrectBracket.position);
            return;
        }

        System.out.println("Success");
    }
}
