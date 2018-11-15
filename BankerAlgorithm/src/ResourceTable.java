// 资源分配表
public class ResourceTable {
    ProcessMatrix max;
    ProcessMatrix allocation;
    ProcessMatrix need;
    int[] available = new int[3];
    boolean[] finish = {false,false,false,false};
    ProcessMatrix work;

    int safeCount = 0;	// safeCount > 0表示安全状态，safeCount为递归后得到可分配方案个数

    int[] path = new int[4];		// 用于输出安全序列

    //初始化进程分配表
    ResourceTable(ProcessMatrix max,ProcessMatrix allocation,ProcessMatrix need,int available[]) {
        this.max = max;
        this.allocation = allocation;
        this.need = need;
        this.work = new ProcessMatrix();

        for(int i = 0;i < available.length;i++) {
            this.available[i] = available[i];
        }
    }

    // 第requestProcess个资源 申请资源 包括对其的输入检测、不安全状态下的提示输出
    boolean RequestResource(int requestProcess, int requestResource[]) {
        // Need >= Request
        if(!this.need.BiggerOrEqual(requestProcess, requestResource)) {
            System.out.printf("进程 P%d 的 Request%d(%d,%d,%d) > Needed，不合法!\n",
                    (requestProcess + 1),(requestProcess + 1),
                    requestResource[0],requestResource[1],requestResource[2]);
            return false;
        }
        else {
            // Available >= Request
            if(!this.AvailableBiggerOrEqualRequest(requestResource)) {
                System.out.printf("进程 P%d 的 Request%d(%d,%d,%d) 没有足够的资源，需要等待!\n",
                        (requestProcess + 1),(requestProcess + 1),
                        requestResource[0],requestResource[1],requestResource[2]);
                return false;
            }
            else {
                // 试探性分配
                this.AvailableEqualsSub(requestResource);
                this.allocation.EqualsPlus(requestProcess, requestResource);
                this.need.EqualsSub(requestProcess, requestResource);
                this.safeCheck(0,requestProcess,requestResource);
                // 若分配完为不安全状态
                if(this.safeCount == 0) {
                    System.out.printf("进程 P%d 的 Request%d(%d,%d,%d) 不安全!\n",
                            (requestProcess + 1),(requestProcess + 1),
                            requestResource[0],requestResource[1],requestResource[2]);
                    return false;
                }
                //若分配完为安全状态
                else {
                    this.IfAllocationEqualsMax(requestProcess);
                    this.display();
                    this.safeCount = 0;
                    return true;
                }
            }
        }
    }

    // 进程是否分配到所需所有资源
    void IfAllocationEqualsMax(int requestProcess) {
        boolean equal = true;

        for(int i = 0;i < this.max.array[0].length;i++) {
            if(this.max.array[requestProcess][i] != this.allocation.array[requestProcess][i])
                equal = false;
        }

        if(equal) {
            for(int i = 0;i < this.max.array[0].length;i++) {
                this.available[i] = this.available[i] + this.allocation.array[requestProcess][i];
                this.allocation.array[requestProcess][i] = 0;
            }
        }
    }

    // Available数组的每一项 >= requestResource
    boolean AvailableBiggerOrEqualRequest(int[] requestResource) {
        for(int i = 0;i < this.available.length;i++) {
            if(this.available[i] < requestResource[i])
                return false;
        }
        return true;
    }

    // Available数组的值 = Available数组的值 - requestResource各项的值
    void AvailableEqualsSub(int[] requestResource) {
        for(int i = 0;i < this.available.length;i++)
            this.available[i] = this.available[i] - requestResource[i];
    }

    // 递归对当前资源分配进行安全性检查
    void safeCheck(int step,int requestProcess,int requestResource[]) {
        int di;		// 记录查找进程的Finish是否为false
        // 如果当前为安全状态
        if(step == this.max.array.length) {
            this.safeCount++;
            // 递归检查安全性将会找到所有安全序列，此处当找到一种时即输出
            if(this.safeCount == 1)
                System.out.printf("Request%d(%d,%d,%d) 安全！\n",
                        (requestProcess + 1),requestResource[0],requestResource[1],requestResource[2]);
        }
        // 如果当前为不安全状态
        else {
            // 如果是当前是安全性检查的第一步，讲work赋值为Available的内容
            if(step == 0) {
                this.work.Equals(step, this.available);
            }
            // di用于标记  遍历所有进程
            di = 0;
            // 循环查找所有Finish为false的进程
            while(di < this.max.array.length) {
                if(!this.finish[di] && (this.need.SmallerOrEqual(di, work, step))) {
                    this.finish[di] = true;
                    this.path[step] = di;
                    step++;

                    // 下一个work = 上一个的work + allocation
                    if(step < this.max.array.length)
                        this.work.EqualsPlus(step, this.work, step-1, this.allocation, di);

                    // 递归调用安全性检查方法
                    this.safeCheck(step,requestProcess,requestResource);

                    //恢复递归调用前的状态
                    step--;
                    this.finish[di] = false;
                }
                di++;
            }
        }
    }

    //打印进程分配表
    void display() {
        System.out.printf("%-20s%-14s%-14s%-14s%-14s\n"," ","Max","Allocation","Need","Available");

        System.out.printf("%-10s%-10s"," ","Resource");
        for(int i = 0;i < 4;i++) {
            for(int j = 0;j < 3;j++) {
                System.out.printf("%s%-3d","R",j + 1);
            }
            System.out.printf("%-2s","");
        }
        System.out.println("");

        System.out.printf("%-5s%-5s\n","","Process");

        System.out.printf("%-62s","");
        for(int i=0;i<3;i++) {
            System.out.printf("%-4d",this.available[i]);
        }
        System.out.println("");

        for(int i = 0;i < 4;i++) {
            System.out.printf("%-7s%s%-12d"," ","P",i + 1);
            for(int j = 0;j < 3;j++) {
                switch(j) {
                    case 0:
                        for(int k = 0;k < 3;k++) {
                            System.out.printf("%-4d", this.max.array[i][k]);
                        }
                        break;
                    case 1:
                        for(int k = 0;k < 3;k++) {
                            System.out.printf("%-4d", this.allocation.array[i][k]);
                        }
                        break;
                    default:
                        for(int k = 0;k < 3;k++) {
                            System.out.printf("%-4d", this.need.array[i][k]);
                        }
                        break;
                }
                System.out.printf("%-2s", "");
            }
            System.out.println("");
        }
    }
}
