import java.util.Scanner;

public class File {
    protected String name ; // 文件名
    protected int size ;    // 所需块数

    protected int phyNum ;  // 物理记录号 = 空闲块号 % 6
    protected int traNum ;  // 磁道号 =（空闲块号 / 6）% 20 即盘面号
    protected int cylNum ;  // 柱面号 =（空闲块号 / 6）/ 20

    protected File next ;
    protected File front ;

    public File(){
        next = null ;
        front = null ;
    }

    public void getName(){
        System.out.println("请输入文件名称");
        Scanner scanner = new Scanner(System.in);
        name = scanner.next();
    }

    public void getSize(){
        System.out.println("请输入文件所需块数");
        Scanner scanner = new Scanner(System.in);
        size = scanner.nextInt();
    }

    public void getPhyNum(int n){
        phyNum = n % 6; // 物理记录号 = 空闲块号 % 6
    }

    public void getTraNum(int n){
        traNum = ( n / 6 ) % 20;    // 磁道号 =（空闲块号 / 6）% 20 即盘面号
    }

    public void getCylNum(int n){
        cylNum = ( n / 6 ) / 20;     // 柱面号 =（空闲块号 / 6）/ 20
    }

    public void display(File head){
        File f = head.next;

        System.out.println("------------------------------------------------");
        System.out.println("文件名\t\t物理记录号\t\t磁道号\t\t柱面号");
        while(f != null){
            System.out.println(f.name + "\t\t\t" + f.phyNum + "\t\t\t\t" + f.traNum + "\t\t\t\t" + f.cylNum);
            f = f.next;
        }
        System.out.println("------------------------------------------------");
    }
}
