import static java.lang.Thread.sleep;
import java.

public class CPUScheduling {
    public PCB wHead;   //未结束的进程头指针
    public PCB eHead;   //已结束的进程头指针

    public CPUScheduling(){
        this.wHead = null;
        this.eHead = null;
    }

    //建立循环队列
    public void insert(String name) {
        PCB tmp = new PCB(name);
        PCB p = wHead;

        if(wHead == null){
            wHead = tmp;
            tmp.next = wHead;
        }else{
            while(p.next != wHead){
                p = p.next;
            }
            p.next = tmp;
            tmp.next = wHead;
        }
    }

    //建立已结束进程的队列
    public void insertE(PCB ptr) {
        PCB p = eHead;
        PCB lp = ptr;
        PCB tmp = new PCB(ptr.name);
        tmp.status = "E";
        if(ptr.next == ptr){
            if(eHead == null){
                eHead = tmp ;
                tmp.next = null;
            }
            else {
                while (p.next != null)
                    p = p.next;
                p.next = tmp;
                tmp.next = null;
            }
        }
        else{
            while(lp.next != ptr)
                lp = lp.next;
            lp.next = ptr.next;

            if(eHead == null){
                eHead = tmp ;
                tmp.next = null;
            }
            else{
                while(p.next != null)
                    p = p.next;
                p.next = tmp;
                tmp.next = null;
            }
        }
    }

    //输出结束队列
    public void displayE(){
        PCB p = eHead;
        if(p == null){
            System.out.println("NO END PROCESS");
        }
        while (p != null){
            System.out.println(p.name + " : " + p.status);
            p = p.next;
        }
        System.out.println("---------");
    }

    //输出队列
    public void displayQueue(PCB ptr){
        PCB p = ptr.next ;
        System.out.print("Process queue: ");
        if(p == null){
            System.out.println("EMPTY QUEUE");
        }
        //遍历循环队列
        while (p != ptr){
            if(!p.status.equals("E")){
                System.out.print("-->" + p.name);
            }
            p = p.next;
        }
        System.out.println("\n----------------------------------");
    }

    //输出就绪队列
    public void displayR(PCB ptr){
        PCB p = ptr.next ;
        if(p == p.next && p.status.equals("E") ){
            System.out.println("NO READY PROCESS");
        }
        while (p != ptr){
            System.out.println(p.name + " : " + p.status);
            p = p.next;
        }
        System.out.println("---------");
    }

    //判断就绪或结束
    public void RorE(PCB ptr){
        if(ptr.runT == ptr.requireT){
            ptr.status = "E";
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CPUScheduling cs = new CPUScheduling();
        cs.insert("Q1");
        cs.insert("Q2");
        cs.insert("Q3");
        cs.insert("Q4");
        cs.insert("Q5");

        PCB ptr = cs.wHead ;

        for(int i = 0;i < 5;i++)
        {
            ptr.init();
            ptr = ptr.next;
        }

        while (cs.wHead != null){
            ptr.run();           // 进程运行
            cs.RorE(ptr);        // 判断进程状态

            cs.displayR(ptr);    // 打印就绪队列
            cs.displayE();       // 打印结束队列
            cs.displayQueue(ptr);// 打印进程队列

            if(ptr.status.equals("E")){
                cs.insertE(ptr);   // 将结束状态的进程加入结束队列
            }

            if(ptr != ptr.next)
                ptr = ptr.next;
            else if(ptr == ptr.next && ptr.status.equals("E")){
                cs.wHead = null;
            }
            sleep(2000);
        }
        System.out.println("END!");   //结束
    }
}
