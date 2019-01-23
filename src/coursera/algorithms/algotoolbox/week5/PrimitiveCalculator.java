package coursera.algorithms.algotoolbox.week5;

import java.util.*;

public class PrimitiveCalculator {
    private static List<Integer> greedySequence(int n) {
        List<Integer> sequence = new ArrayList<Integer>();
        while (n >= 1) {
            sequence.add(n);
            if (n % 3 == 0) {
                n /= 3;
            } else if (n % 2 == 0) {
                n /= 2;
            } else {
                n -= 1;
            }
        }
        Collections.reverse(sequence);
        return sequence;
    }
    
    private static int selectValue(int[] sequenceArr, int currentNum) {
        int value1 = currentNum % 3 == 0 ? sequenceArr[currentNum / 3] : Integer.MAX_VALUE;
        int value2 = currentNum % 2 == 0 ? sequenceArr[currentNum / 2] : Integer.MAX_VALUE;
        int value3 = sequenceArr[currentNum - 1];
        
        int value;
        
        if(value1 <= value2 && value1 <= value3) {
            value = currentNum / 3;
        } else if(value2 <= value1 && value2 <= value3) {
            value = currentNum / 2;
        } else {
            value = currentNum - 1;
        }
        
        return value;
    }
    
    private static List<Integer> optimalSequence(int n) {
        List<Integer> sequence = new ArrayList<>();
       
        int[] sequenceArr = new int[n + 1];
        sequenceArr[0] = 0;
              
        for(int i = 1; i <= n; i++) {
            int value = selectValue(sequenceArr, i);
            sequenceArr[i] = sequenceArr[value] + 1;
        }
        
        sequence.add(n);
        while(n > 0) {
            int value = selectValue(sequenceArr, n);
            if(value != 0) {
                sequence.add(value);
            }
            n = value;
        }
        
        Collections.reverse(sequence);
        return sequence;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> sequence = optimalSequence(n);
        System.out.println(sequence.size() - 1);
        StringBuilder sb = new StringBuilder();
        for (Integer x : sequence) {
            sb.append(x);
            sb.append(" ");
        }
        
        System.out.print(sb.toString());
    }
}