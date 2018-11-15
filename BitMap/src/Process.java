import java.util.Scanner;

public class Process {
    protected String name;    //作业名
    protected int PT[] ;      //页表
    protected int size;       //作业所需空间
    //作业为双链表
    protected Process next ;
    protected Process front ;

    public Process(){
        next = null ;
        front = null ;
    }

    public void getName(){
        System.out.println("请输入作业名称");
        Scanner scanner = new Scanner(System.in);
        name = (scanner.next());
    }

    public void getSize(){
        System.out.println("请输入作业所需内存大小");
        Scanner scanner = new Scanner(System.in);
        size = (scanner.nextInt());
    }
}
