public class Graph {
    private int [][] matrix;
    private int size;

    Graph(int size){
        this.matrix = new int [size][size];
        this.size = size;
    }

    void addEdge (int v1, int v2, int value){
        matrix[v1][v2] = value;
        matrix[v2][v1] = value;
    }

    int [][] getGraphMatrix(){
        return matrix;
    }
}
