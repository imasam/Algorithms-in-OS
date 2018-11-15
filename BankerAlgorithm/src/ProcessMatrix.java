// 矩阵形式存储max、allocation、need
public class ProcessMatrix {
    String name;	//进程组的名字
    int[][] array = new int[4][3];

    /////////////////////////////////////////////////////////////////////
    // 初始化
    ProcessMatrix() {
        for(int i = 0;i < this.array.length;i++)
            for(int j = 0;j < this.array[0].length;j++) {
                this.array[i][j] = 0;
            }
    }

    // 初始化max,allocation
    ProcessMatrix(String name,int array[][]){
        this.name=name;

        for(int i = 0;i < array.length;i++)
            for(int j = 0;j < array[i].length;j++) {
                this.array[i][j] = array[i][j];
            }
    }

    // 初始化need = max - allocation
    ProcessMatrix(String name,ProcessMatrix max,ProcessMatrix allocation) {
        this.name = name;

        for (int i = 0; i < this.array.length; i++)
            for (int j = 0; j < this.array[0].length; j++) {
                this.array[i][j] = max.array[i][j] - allocation.array[i][j];
            }
    }
    /////////////////////////////////////////////////////////////////////

    // 用数组 赋值 进程组的第n行
    void Equals(int n,int[] array) {
        for(int i = 0;i < this.array[n].length;i++)
            this.array[n][i] = array[i];
    }
    /////////////////////////////////////////////////////////////////////

    // 该进程组第i行 = ProcessMatrix a的第j行 + ProcessMatrix b的第k行
    void EqualsPlus(int i,ProcessMatrix a,int j,ProcessMatrix b,int k) {
        for(int m = 0;m < this.array[0].length;m++)
            this.array[i][m] = a.array[j][m] + b.array[k][m];
    }

    // 该进程组第requestProcess行元素 = 原来的元素 + 数组requestResource内的元素
    void EqualsPlus(int requestProcess,int[] requestResource) {
        for(int i = 0;i < this.array[0].length;i++)
            this.array[requestProcess][i] = this.array[requestProcess][i] + requestResource[i];
    }
    /////////////////////////////////////////////////////////////////////

    // 该进程组第requestProcess行元素 = 原来的元素 - 数组requestResource内的元素
    void EqualsSub(int requestProcess,int[] requestResource) {
        for(int i = 0;i < this.array[0].length;i++)
            this.array[requestProcess][i] = this.array[requestProcess][i] - requestResource[i];
    }
    /////////////////////////////////////////////////////////////////////

    // 该进程组第i行元素 <= 进程组ProcessMatrix第j行元素
    boolean SmallerOrEqual(int i,ProcessMatrix ProcessMatrix,int j) {
        for(int k = 0;k < this.array[i].length;k++) {
            if(this.array[i][k] > ProcessMatrix.array[j][k])
                return false;
        }
        return true;
    }
    /////////////////////////////////////////////////////////////////////

    // 该进程组的第i行元素 >= 数组的各项元素
    boolean BiggerOrEqual(int i, int[] requestResource) {
        for(int j = 0;j < this.array[0].length;j++) {
            if(this.array[i][j] < requestResource[j])
                return false;
        }
        return true;
    }
}

