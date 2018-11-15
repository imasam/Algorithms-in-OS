import java.util.Scanner;

public class DiskMem {
    File fHead;
    Table tHead;    //空闲区表

    public DiskMem(){
        fHead = new File();
        tHead = new Table();
        Table t = new Table();
        t.start = 0;
        t.length = 1000;
        t.statusFlag = 1;
        t.next = tHead.next;
        // 加入空闲区表
        tHead.next = t;
        t.front = tHead;
    }

    public void allocDisk(File fHead,Table tHead){
        File f = new File();
        f.getName();
        f.getSize();

        Table t;
        int flag = 0;   // 0:分配磁盘失败 1:分配磁盘成功

        for(t = tHead;t != null && flag == 0;t = t.next){
            if(f.size <= t.length){
                if(fHead.next == null){
                    fHead.next = f;
                    f.front = fHead;
                }
                else {
                    File ff = fHead;
                    while(ff.next != null)
                        ff = ff.next;
                    ff.next = f;
                    f.front = ff;
                }
                f.getCylNum(t.start);
                f.getPhyNum(t.start);
                f.getTraNum(t.start);
                flag = 1;

                t.start = t.start + f.size;
                t.length = t.length - f.size;

                if(t.length == 0){
                    if(t.next != null)  // 有下一条记录则连接上一条和下一条
                       t.front.next = t.next;
                    if(t.next == null){ // 无下一条记录则从表中删除该记录
                        t = null;
                        break;
                    }
                }
            }
        }
        if(flag == 0)
            System.out.println("磁盘空间分配失败！");
    }

    public void freeDisk(File fHead,Table tHead){
        String name;
        int flag = 0; // 0:未回收成功 1:回收成功
        File f = fHead;

        System.out.println("请输入进程名称");
        Scanner scanner = new Scanner(System.in);
        name = scanner.next();

        while(f != null)
        {
            if (name.equals(f.name)){
                //释放的内容生成表项
                Table mergeTable = new Table();
                mergeTable.start = (f.cylNum * 20 + f.traNum) *6 + f.phyNum; //（柱面号 * 20 + 磁道号）* 6 + 物理记录号
                mergeTable.length = f.size;
                //mergeTable.statusFlag = 1; // 可分配

                Table t = tHead;
                int mergeFlag = 0;  // 0:不需要合并 1：需要合并
                while(t != null){
                    if((t.start + t.length) == mergeTable.start){
                        t.length += mergeTable.length;
                        mergeFlag = 1;
                    }
                    else if((mergeTable.start + mergeTable.length) == t.start){
                        t.start = mergeTable.start;
                        t.length = t.length + mergeTable.length;
                        mergeFlag = 1;
                    }
                    else
                        t = t.next;
                }

                if(mergeFlag == 0){
                    t = tHead;
                    while(t.next != null)
                        t = t.next;
                    // 插入
                    t.next = mergeTable;
                    mergeTable.front = t;
                }
                flag = 1;

                // 释放该文件
                if (f.front != null){
                    if (f.next != null){
                        f.front.next = f.next;
                        f.next.front = f.front;
                        System.gc();
                    }
                    else{
                        f.front.next = null;
                        System.gc();
                    }
                }
                else{
                    f.next.front = null;
                    System.gc();
                }
            }
            if (f.next == null)
                break;
            else
                f = f.next;
        }
        if (flag == 0)
            System.out.println("\n未找到该文件!");
        else {
            System.out.println("\n磁盘空间回收成功!");
        }
    }

    public static void main(String[] args){
        DiskMem dm = new DiskMem();

        while (true)
        {
            String op;
            System.out.print("\n1：添加文件，请求磁盘空间\n2：删除文件，回收磁盘空间\n" +
                    "3：输出空闲表\n4：输出文件物理地址\n5：退出\n");
            Scanner scanner = new Scanner(System.in);
            op = scanner.next();

            switch (op)
            {
                case "1":
                    dm.allocDisk(dm.fHead,dm.tHead);
                    break;
                case "2":
                    dm.freeDisk(dm.fHead,dm.tHead);
                    break;
                case "3":
                    dm.tHead.display(dm.tHead);
                    break;
                case "4":
                    dm.fHead.display(dm.fHead);
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

