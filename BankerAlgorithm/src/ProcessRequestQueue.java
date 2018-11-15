// 接收输入，得到进程与相应的资源请求
import java.util.ArrayList;
import java.util.Scanner;

public class ProcessRequestQueue {
    ArrayList<ProcessRequest> processRequestQueue = new ArrayList<ProcessRequest>();

    ProcessRequestQueue() {
        System.out.println("输入申请资源的进程数：");
        int count;
        Scanner scanner = new Scanner(System.in);
        count = scanner.nextInt();

        for (int i = 0; i < count; i++) {
            this.processRequestQueue.add(new ProcessRequest());
        }

        for (int i = 0; i < count; i++) {
            System.out.println("----------------------");
            System.out.println("输入申请资源的进程编号：");
            this.processRequestQueue.get(i).requestProcess = scanner.nextInt() - 1;

            System.out.println("输入进程 P" + (this.processRequestQueue.get(i).requestProcess + 1)
                    + " 对资源 R1,R2,R3 的需求");
            for (int j = 0; j < this.processRequestQueue.get(0).requestResource.length; j++) {
                this.processRequestQueue.get(i).requestResource[j] = scanner.nextInt();
            }
            System.out.println("----------------------");
        }
    }
}