package coursera.algorithms.datastructs.week4;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class HashChains {

    private FastScanner in;
    private PrintWriter out;
    // store all strings in one list
    private List<String> elems;
    // for hash function
    private int bucketCount;
    private int prime = 1000000007;
    private int multiplier = 263;

    private Chain[] hashTable;

    public static void main(String[] args) throws IOException {
        new HashChains().processQueries();
    }

    private int hashFunc(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i) {
            hash = (hash * multiplier + s.charAt(i)) % prime;
        }
        return (int) hash % bucketCount;
    }

    private Query readQuery() throws IOException {
        String type = in.next();
        if (!type.equals("check")) {
            String s = in.next();
            return new Query(type, s);
        } else {
            int ind = in.nextInt();
            return new Query(type, ind);
        }
    }

    private void writeSearchResult(boolean wasFound) {
        out.println(wasFound ? "yes" : "no");
        // Uncomment the following if you want to play with the program interactively.
        // out.flush();
    }

    private void processQueryNaive(Query query) {
        switch (query.type) {
            case "add":
                if (!elems.contains(query.s)) {
                    elems.add(0, query.s);
                }
                break;
            case "del":
                if (elems.contains(query.s)) {
                    elems.remove(query.s);
                }
                break;
            case "find":
                writeSearchResult(elems.contains(query.s));
                break;
            case "check":
                for (String cur : elems) {
                    if (hashFunc(cur) == query.ind) {
                        out.print(cur + " ");
                    }
                }
                out.println();
                // Uncomment the following if you want to play with the program interactively.
                // out.flush();
                break;
            default:
                throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    private void add(Query query) {
        if (find(query.s)) {
            return;
        }

        int index = hashFunc(query.s);
        Chain hashChain = hashTable[index];
        Chain newChain = new Chain(query.s, hashChain);
        hashTable[index] = newChain;
    }

    private void delete(String str) {
        int index = hashFunc(str);
        Chain hashChain = hashTable[index];
        Chain prevChain = null;
        while (hashChain != null) {
            if (str.equals(hashChain.value)) {
                if (prevChain != null) {
                    prevChain.next = hashChain.next;
                } else {
                    hashTable[index] = hashChain.next;
                }
                break;
            }
            prevChain = hashChain;
            hashChain = hashChain.next;
        }
    }

    private void check(int index) {
        Chain hashChain = hashTable[index];
        while (hashChain != null) {
            out.print(hashChain.value + " ");
            hashChain = hashChain.next;
        }
        out.println();
    }

    private boolean find(String str) {
        boolean found = false;
        int index = hashFunc(str);
        Chain hashChain = hashTable[index];
        while (hashChain != null) {
            if (str.equals(hashChain.value)) {
                found = true;
                break;
            }
            hashChain = hashChain.next;
        }
        return found;
    }

    private void processQuery(Query query) {
        switch (query.type) {
            case "add":
                add(query);
                break;
            case "del":
                delete(query.s);
                break;
            case "find":
                writeSearchResult(find(query.s));
                break;
            case "check":
                check(query.ind);
                break;
            default:
                throw new IllegalArgumentException("Unknown query: " + query.type);
        }
    }

    public void processQueries() throws IOException {
        elems = new ArrayList<>();
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        bucketCount = in.nextInt();
        int queryCount = in.nextInt();
        hashTable = new Chain[bucketCount];
        for (int i = 0; i < queryCount; ++i) {
            processQuery(readQuery());
        }
        out.close();
    }

    static class Query {

        String type;
        String s;
        int ind;

        public Query(String type, String s) {
            this.type = type;
            this.s = s;
        }

        public Query(String type, int ind) {
            this.type = type;
            this.ind = ind;
        }
    }

    static class Chain {

        String value;
        Chain next;

        public Chain(String value, Chain next) {
            this.value = value;
            this.next = next;
        }
    }

    static class FastScanner {

        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
