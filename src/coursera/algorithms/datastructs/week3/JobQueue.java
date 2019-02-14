package coursera.algorithms.datastructs.week3;

import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class JobQueue {

    private int numWorkers;
    private int[] jobs;

    private int[] assignedWorker;
    private long[] startTime;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

    private void assignJobsNaive() {
        // TODO: replace this code with a faster algorithm.
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];
        long[] nextFreeTime = new long[numWorkers];
        for (int i = 0; i < jobs.length; i++) {
            int duration = jobs[i];
            int bestWorker = 0;
            for (int j = 0; j < numWorkers; ++j) {
                if (nextFreeTime[j] < nextFreeTime[bestWorker]) {
                    bestWorker = j;
                }
            }
            assignedWorker[i] = bestWorker;
            startTime[i] = nextFreeTime[bestWorker];
            nextFreeTime[bestWorker] += duration;
        }
    }

    private void assignJobs() {
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];

        PriorityQueue<WorkingThread> workingQueue = new PriorityQueue<>(numWorkers);
        for (int i = 0; i < numWorkers; i++) {
            workingQueue.add(new WorkingThread(i));
        }
        for (int i = 0; i < jobs.length; i++) {
            int duration = jobs[i];
            WorkingThread bestWorker = workingQueue.poll();

            assignedWorker[i] = bestWorker.getIndex();
            startTime[i] = bestWorker.getFinishTime();
            bestWorker.setFinishTime(duration);
            workingQueue.add(bestWorker);
        }
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
    }

    class WorkingThread implements Comparable<WorkingThread> {

        private final int index;
        private long finishTime = 0;

        public WorkingThread(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }

        public void setFinishTime(long duration) {
            this.finishTime += duration;
        }

        public long getFinishTime() {
            return this.finishTime;
        }

        @Override
        public int compareTo(WorkingThread anotherThread) {
            if (this.finishTime > anotherThread.finishTime) {
                return 1;
            }
            if (this.finishTime < anotherThread.finishTime) {
                return -1;
            }

            if (this.index < anotherThread.index) {
                return -1;
            }
            if (this.index > anotherThread.index) {
                return 1;
            }
            return 0;
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
