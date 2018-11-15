public class BankerAlgorithm {
    public static void main(String[] args) {
        int[][] Max = {{3,2,2},{6,1,3},{3,1,4},{4,2,2}};
        int[][] Allocation = {{1,0,0},{5,1,1},{2,1,1},{0,0,2}};
        int[]	Available = {1,1,2};

        ProcessMatrix max = new ProcessMatrix("Max",Max);
        ProcessMatrix allocation = new ProcessMatrix("Allocation",Allocation);
        ProcessMatrix need = new ProcessMatrix("Need",max,allocation);

        ResourceTable rt = new ResourceTable(max,allocation,need,Available);
        rt.display();

        ProcessRequestQueue prq = new ProcessRequestQueue();

        int i = 0;
        while(i < prq.processRequestQueue.size() &&
                rt.RequestResource(prq.processRequestQueue.get(i).requestProcess,
                        prq.processRequestQueue.get(i).requestResource)) {
            i++;
        }
    }
}
