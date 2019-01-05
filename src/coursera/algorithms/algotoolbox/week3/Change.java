package coursera.algorithms.algotoolbox.week3;

import java.util.Scanner;

public class Change {
    private static int getChange(int m) {
        
        final int ten = 10, five = 5, one = 1;
        int changes = 0;
        
        while(m > 0) {
            if (m >= ten) m -= ten;
            else if (m >= five) m -= five;
            else m -= one;
            
            changes++;
        }
        
        return changes;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        System.out.println(getChange(m));
    }
}