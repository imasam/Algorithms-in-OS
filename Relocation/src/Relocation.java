import java.util.Scanner;

public class Relocation {
    final int MAXSIZE = 100;

    public int pageLength;     // 页长
    public int pageNo;         // 页表项数
    public int process;        // 进程大小
    public int logicalAddr;    // 进程逻辑地址

    public PageTable[] PT = new PageTable[MAXSIZE]; // 页表类
    public LogicalAddress LogAdd;                   // 逻辑地址类

    // 输入进程信息
    public void inputPT(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("输入进程大小");
        process = scanner.nextInt();

        System.out.println("输入页长");
        pageLength = scanner.nextInt();
    }

    //  输入逻辑地址
    public void inputLA() {
        System.out.println("输入逻辑地址");
        Scanner scanner = new Scanner(System.in);
        logicalAddr = scanner.nextInt();
        LogAdd = new LogicalAddress(logicalAddr/pageLength,logicalAddr % pageLength);
    }

    public void init(){
        int i,temp;

        if(process < pageLength) {
            System.out.println("不合法的初始化！");
            System.out.println("进程大小不能比页长小！");
            System.out.println("重新输入：");
            inputPT();
        }

        pageNo = process/pageLength;    //计算页号

        for(i = 0;i < pageNo;i++){
            temp = (int)(Math.random() * 100);  // 随机生成对应块号
            PT[i] = new PageTable(i,temp);
        }
    }

    public void trans(){
        int i = 0;
        int flag = 0;   // 0:查询失败 1:查询成功
        int blockNo = 0;

        if(LogAdd.pageNo >= pageNo){
            System.out.println("所查询的逻辑地址不在该页内！");
            System.out.println("重新输入：");
        }

        while(i < pageNo) {
            if (PT[i].pageNo == LogAdd.pageNo) {
                blockNo = PT[i].blockNo;
                flag = 1;
                break;
            } else
                i++;
        }
        if(flag == 1) {
            //System.out.println("blockNo=" + blockNo + "LogAdd.pageAdd = " + LogAdd.pageAdd);
            System.out.println("物理地址为：" + (blockNo * pageLength + LogAdd.pageAdd));
        }
    }

    public void output(){
        System.out.println("-----页表------");
        System.out.println("页号\t\t\t块号");
        System.out.println("---------------");
        for(int i = 0;i < pageNo;i++){
            System.out.print(PT[i].pageNo + "\t\t\t");
            System.out.println(PT[i].blockNo);
        }
        System.out.println("---------------");;
    }

    public static void main(String[] args) {
        String op;
        Relocation re = new Relocation();

        System.out.println("页面重定位\n");
        while (true) {
            System.out.println("1: 输入初始信息\n2: 查看生成页表\n3: 逻辑地址转换物理地址\n4: 退出");
            System.out.println("--------------");
            Scanner scanner = new Scanner(System.in);
            op = scanner.next();

            switch (op) {
                case "1":
                    re.inputPT();
                    re.init();
                    System.out.println("--------------");
                    break;
                case "2":
                    re.output();
                    System.out.println("--------------");
                    break;
                case "3":
                    re.inputLA();
                    re.trans();
                    System.out.println("--------------");
                    break;
                case "4":
                    System.out.println("--------------");
                    System.exit(0);
                    break;
                default:
                    System.out.println("错误输入！");
                    System.out.println("重新输入：");
                    System.out.println("--------------");
            }
        }
    }
}
