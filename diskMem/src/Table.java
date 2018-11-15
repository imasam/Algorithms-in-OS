public class Table {
    protected int start;   // 起始空闲块号
    protected int length;  // 空闲区长度
    protected int statusFlag;    // 1:未分配 0:空表目

    protected Table next ;
    protected Table front ;

    public Table(){
        next = null ;
        front = null;

        statusFlag = 1; // 默认为未分配块
    }

    public void display(Table head) {
        Table t = head;
        int i = 0;
        String status;

        if (t.next == null) {
            System.out.println("---------");
            System.out.println("空闲表为空");
            System.out.println("---------");
        } else {
            System.out.println("------------------------------------------------");
            System.out.println("序号\t\t起始空闲块号\t\t空闲区长度\t状态");
            while (t != null) {
                if (t.length > 0) {
                    if (t.statusFlag == 0)
                        status = "空表目"; //0: 空表目
                    else
                        status = "未分配"; //1: 未分配

                    //System.out.println("statusFlag=" + t.statusFlag);
                    System.out.println(i + "\t\t\t" + t.start + "\t\t\t" + t.length + "\t\t\t" + status);
                }
                t = t.next;
                i++;
            }
            System.out.println("------------------------------------------------");
        }
    }
}
