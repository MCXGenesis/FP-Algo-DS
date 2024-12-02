import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;

public class TravelPlan {
    public String cheapestRoute(int [][] graphMatrix, String [] label, int start){

        Map<Integer, String> paths = new HashMap<>();
        LinkedList<Integer> visited = new LinkedList<>();
        PriorityQueue<Integer> waiting = new PriorityQueue<>();
        int [] distance = new int [graphMatrix.length];
        
        for (int i = 0; i < graphMatrix.length; i++) {
            distance[i] = Integer.MAX_VALUE;
            paths.put(i, "");
        }

        distance[start] = 0;
        paths.put(start, label[start]);
        
        waiting.add(start);
        while (!waiting.isEmpty()) {
            int current = waiting.poll();
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);
            for (int i = 0; i < distance.length; i++) {
                if (graphMatrix[current][i]>0 && !visited.contains(i)) {
                    int newDistance = distance[current] + graphMatrix[current][i];
                    if (newDistance < distance[i]) {
                        distance[i] = newDistance;
                    }
                    waiting.add(i);
                    paths.put(i, paths.get(current) + " --> " + label[i]);
                }
            }
        }

        String output = " ";
        for (int i=0; i< distance.length; i++){
            output += "Path: " + paths.get(i) + "\n";
            output += "Cost: " + distance[i] + "\n";
        }
        return output.toString();

        
    }
}

