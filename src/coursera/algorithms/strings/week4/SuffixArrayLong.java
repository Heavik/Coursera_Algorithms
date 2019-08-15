package coursera.algorithms.strings.week4;

import java.util.*;
import java.io.*;

public class SuffixArrayLong {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
    
    private static final int NUMBER_OF_CHARACTERS = 5;

    private int letterToInt(char letter) {
        switch(letter) {
            case '$':
                return 0;
            case 'A':
                return 1;
            case 'C':
                return 2;
            case 'G':
                return 3;
            case 'T':
                return 4;
            default:
                assert(false);
                return -1;
        }
    }
    
    private int[] sortCharacters(String text) {
        int textLength = text.length();
        int[] order = new int[textLength];
        int[] count = new int[NUMBER_OF_CHARACTERS];
        
        for(int i = 0; i < textLength; i++) {
            int letterIndex = letterToInt(text.charAt(i));
            count[letterIndex] = count[letterIndex] + 1;
        }
        
        for(int j = 1; j < count.length; j++) {
            count[j] = count[j] + count[j - 1];
        }
        
        for(int i = textLength - 1; i >= 0; i--) {
            int currentLetterIndex = letterToInt(text.charAt(i));
            count[currentLetterIndex] = count[currentLetterIndex] - 1;
            order[count[currentLetterIndex]] = i;
        }
        
        return order;
    }
    
    private int[] computeCharClasses(String text, int[] order) {
        int textLength = text.length();
        int[] charClass = new int[textLength];
        charClass[order[0]] = 0;
        
        for(int i = 1; i < textLength; i++) {
            if(text.charAt(order[i]) != text.charAt(order[i - 1])) {
                charClass[order[i]] = charClass[order[i - 1]] + 1;
            } else {
                charClass[order[i]] = charClass[order[i - 1]];
            }
        }
        
        return charClass;
    }
    
    private int[] sordDoubled(String text, int currentLength, int[] order, int[] charClass) {
        int textLength = text.length();
        int[] count = new int[textLength];
        int[] newOrder = new int[textLength];
        
        for(int i = 0; i < textLength; i++) {
            count[charClass[i]] = count[charClass[i]] + 1;
        }
        
        for(int j = 1; j < textLength; j++) {
            count[j] = count[j] + count[j - 1];
        }
        
        for(int i = textLength - 1; i >= 0; i--) {
            int start = (order[i] - currentLength + textLength) % textLength;
            int currentClass = charClass[start];
            count[currentClass] = count[currentClass] - 1;
            newOrder[count[currentClass]] = start;
        }
        
        return newOrder;
    }
    
    private int[] updateClasses(int[] newOrder, int[] charClass, int currentLength) {
        int n = newOrder.length;
        int[] newClass = new int[n];
        newClass[newOrder[0]] = 0;
        
        for(int i = 1; i < n; i++) {
            int current = newOrder[i];
            int previous = newOrder[i - 1];
            
            int mid = (current + currentLength) % n;
            int midPrev = (previous + currentLength) % n;
            if(charClass[current] != charClass[previous] || charClass[mid] != charClass[midPrev]) {
                newClass[current] = newClass[previous] + 1;
            } else {
                newClass[current] = newClass[previous];
            }
        }
        
        return newClass;
    }

    // Build suffix array of the string text and
    // return an int[] result of the same length as the text
    // such that the value result[i] is the index (0-based)
    // in text where the i-th lexicographically smallest
    // suffix of text starts.
    public int[] computeSuffixArray(String text) {
        int[] order = sortCharacters(text);
        int[] charClass = computeCharClasses(text, order);
        
        int currentLength = 1;
        int textLength = text.length();
        
        while(currentLength < textLength) {
            order = sordDoubled(text, currentLength, order, charClass);
            charClass = updateClasses(order, charClass, currentLength);
            currentLength *= 2;
        }
        
        return order;
    }


    static public void main(String[] args) throws IOException {
        new SuffixArrayLong().run();
    }

    public void print(int[] x) {
        StringBuilder sb = new StringBuilder(x.length * 2);
        for (int a : x) {
            sb.append(a);
            sb.append(' ');
        }
        System.out.println(sb.toString());
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        int[] suffix_array = computeSuffixArray(text);
        print(suffix_array);
    }
}