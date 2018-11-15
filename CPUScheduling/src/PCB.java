import java.util.Scanner;

public class PCB {
    protected String name;   // 进程名
    protected PCB next;      // 指向下一进程
    protected int requireT;  // 要求运行时间
    protected int runT;      // 已运行时间
    protected String status; // 进程状态

    public PCB(String n){
        status = "R"; // 就绪状态：R
        runT = 0;
        name = n ;
    }

    public void init(){
        System.out.println("Enter time " + name +" requires");
        Scanner scanner = new Scanner(System.in);
        requireT = scanner.nextInt();
    }

    public void run(){
        System.out.println(name + ":running");
        System.out.println("---------");
        runT += 1;  // 已运行时间+1
    }
}
