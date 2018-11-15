public class LogicalAddress {
    public int pageNo;  //逻辑地址对应的页号
    public int pageAdd; //逻辑地址对应的页内地址

    public LogicalAddress() {
        pageNo = 0;
        pageAdd = 0;
    }

    public LogicalAddress(int _pageNo, int _pageAdd) {
        pageNo = _pageNo;
        pageAdd = _pageAdd;
    }
}
