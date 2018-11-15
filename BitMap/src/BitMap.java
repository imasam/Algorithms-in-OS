import java.util.Scanner;

public class BitMap {
    protected static int[][] matrix;
    int freeBit;    // 空闲块数

    public BitMap(){
        matrix = new int[8][8];
        freeBit = 64;
    }

    //输出位示图
    public void displayBM(){
        System.out.println("位示图如下：");
        int i, j;
        for (i = 0; i < 8; i++) {
            for (j = 0; j < 8; j++) {
                System.out.print(BitMap.matrix[i][j]);
            }
            System.out.println();
        }
    }

    // 分配
    void malloc(Process head,BitMap bm) {
        Process Process = new Process();
        Process.getName();

        Process.next = head.next;    //头插法插入作业
        if (head.next != null)
            head.next.front = Process;
        head.next = Process;
        Process.front = head;

        Process.getSize();

        if (Process.size > bm.freeBit) {
            System.out.println("剩余空间不足");
        }
        else {
            Process.PT = new int[Process.size];
            int k = 0;
            int flag = 0;    //0:作业未分配完毕  1:作业分配完毕

            for (int i = 0; i < 8 && flag == 0; i++)
                for (int j = 0; j < 8 && flag == 0; j++) {
                    if (bm.matrix[i][j] != 1) {
                        bm.matrix[i][j] = 1;
                        bm.freeBit--;
                        Process.PT[k] = 8 * j + i;  // 建立页表项
                        k++;
                        if (k == Process.size)
                            flag = 1;
                    }
                }
            if(flag == 1)
                System.out.println("分配成功");
        }
    }

    // 释放
    void free(Process head,BitMap bm) {
        String name;

        int flag = 0; // //0:回收失败  1:回收成功

        Process p = head;

        System.out.println("请输入释放的作业名称");
        Scanner scanner = new Scanner(System.in);
        name = (scanner.next());

        while(p != null) {
            if (name.equals(p.name)){      //找到该作业
                flag = 1;

                // 改写位示图
                for (int i = 0; i < p.size; i++){
                    int m = p.PT[i] / 8;
                    int n = p.PT[i] % 8;
                    bm.matrix[m][n] = 0;
                    bm.freeBit++;
                }

                // 从作业链表中删除
                if (p.front != null){
                    if (p.next != null){
                        p.front.next = p.next;
                        p.next.front = p.front;
                        System.gc();
                    }
                    else{
                        p.front.next = null;
                        System.gc();
                    }
                }
                else{
                    p.next.front = null;
                    System.gc();
                }
            }
            if (p.next == null)
                break;
            else
                p = p.next;
        }
        if (flag == 0)
            System.out.println("\n未找到该作业！");
        else {
            System.out.println("\n回收成功!");
        }
    }

    // 输出页表
    void displayPT(Process head) {
        String name;
        int flag = 0;

        System.out.println("请输入作业名称");
        Scanner scanner = new Scanner(System.in);
        name = scanner.next();

        for (Process p = head; p != null && flag == 0; p = p.next){
            if (name.equals(p.name))
            {
                System.out.println("作业名\t页号\t\t块号");
                for (int i = 0; i < p.size; i++){
                    System.out.println(p.name + "\t\t" + i + "\t\t" + p.PT[i]);
                    flag = 1;
                }
            }
        }

        if (flag == 0)
            System.out.println("未找到该作业！");
    }


    public static void main(String[] args){
        BitMap bm = new BitMap();
        //bm.displayBM();
        Process head = new Process();
        while (true)
        {
            String op;
            System.out.print("1：装入新作业\n2：回收内存空间\n3：显示位示图\n" +
                    "4：显示作业页表\n5：退出\n");
            Scanner scanner = new Scanner(System.in);
            op = scanner.next();

            switch (op)
            {
                case "1":
                    bm.malloc(head , bm);
                    break;
                case "2":
                    bm.free(head , bm);
                    break;
                case "3":
                    bm.displayBM();
                    break;
                case "4":
                    bm.displayPT(head);
                    break;
                case "5":
                    System.exit(1);
                    break;
                default:
                    System.out.println("错误输入！");
                    System.out.println("重新输入：");
                    break;
            }
        }

    }
}

