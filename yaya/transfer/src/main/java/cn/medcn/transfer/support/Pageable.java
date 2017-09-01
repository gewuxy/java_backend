package cn.medcn.transfer.support;

public class Pageable {

    public Pageable(){

    }

    public Pageable(int pageNum, int pageSize) {
        this.pageNum = pageNum == 0 ? 1 : pageNum;
        this.pageSize = pageSize;
    }

    public Pageable(int pageNum, int pageSize, Object condition) {
        this.pageNum = pageNum == 0 ? 1 : pageNum;
        this.pageSize = pageSize;
        this.condition = condition;
    }

    private int pageNum = 1;

    private int pageSize = 15;

    private Object condition;

    private int startRow;

    public int getStartRow(){
        return (pageNum-1)*pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Object getCondition() {
        return condition;
    }

    public void setCondition(Object condition) {
        this.condition = condition;
    }


    public static void main(String[] args) {
        Pageable pageable = new Pageable(3, 10);
        System.out.println(pageable.getStartRow());
    }
}
