package coursera.algorithms.datastructs.week1;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Deque;

class Request {

    public Request(int arrival_time, int process_time) {
        this.arrival_time = arrival_time;
        this.process_time = process_time;
    }

    public int arrival_time;
    public int process_time;
}

class Response {

    public Response(boolean dropped, int start_time) {
        this.dropped = dropped;
        this.start_time = start_time;
    }

    public boolean dropped;
    public int start_time;
}

class Buffer {

    public Buffer(int size) {
        this.size = size;
        this.finishTime = new ArrayDeque<>(size);
    }

    public Response Process(Request request) {

        while (finishTime.peek() != null && finishTime.peek() <= request.arrival_time) {
            finishTime.poll();
        }

        if (finishTime.isEmpty()) {
            finishTime.add(request.arrival_time + request.process_time);
            return new Response(false, request.arrival_time);
        }

        if (finishTime.size() == this.size) {
            return new Response(true, -1);
        }
        int lastPacketFinishTime = finishTime.peekLast();
        finishTime.add(lastPacketFinishTime + request.process_time);

        return new Response(false, lastPacketFinishTime);
    }

    private final int size;
    private final Deque<Integer> finishTime;
}

class ProcessPackages {

    private static ArrayList<Request> ReadQueries(Scanner scanner) throws IOException {
        int requests_count = scanner.nextInt();
        ArrayList<Request> requests = new ArrayList<>();
        for (int i = 0; i < requests_count; ++i) {
            int arrival_time = scanner.nextInt();
            int process_time = scanner.nextInt();
            requests.add(new Request(arrival_time, process_time));
        }
        return requests;
    }

    private static ArrayList<Response> ProcessRequests(ArrayList<Request> requests, Buffer buffer) {
        ArrayList<Response> responses = new ArrayList<>();
        for (int i = 0; i < requests.size(); ++i) {
            responses.add(buffer.Process(requests.get(i)));
        }
        return responses;
    }

    private static void PrintResponses(ArrayList<Response> responses) {
        for (int i = 0; i < responses.size(); ++i) {
            Response response = responses.get(i);
            if (response.dropped) {
                System.out.println(-1);
            } else {
                System.out.println(response.start_time);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        int buffer_max_size = scanner.nextInt();
        Buffer buffer = new Buffer(buffer_max_size);

        ArrayList<Request> requests = ReadQueries(scanner);
        ArrayList<Response> responses = ProcessRequests(requests, buffer);
        PrintResponses(responses);
    }
}
