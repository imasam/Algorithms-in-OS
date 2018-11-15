public class PageTable {
    public int pageNo;
    public int blockNo;

    public PageTable()  {
        pageNo = 0;
        blockNo = 0;
    }

    public PageTable(int _pageNo, int _blockNo)  {
        pageNo = _pageNo;
        blockNo = _blockNo;
    }
}
