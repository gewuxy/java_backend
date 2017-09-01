package cn.medcn.common.pagination;

import com.github.pagehelper.Page;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by lixuan on 2017/1/11.
 */
@Data
@NoArgsConstructor
public class MyPage<T>{
    private int pageNum;
    private int pageSize;
    private int startRow;
    private int endRow;
    private long total;
    private int pages;
    private String orderBy;
    private List<T> dataList;

    public  static  MyPage page2Mypage(Page page){
        MyPage myPage = new MyPage();
        myPage.dataList = page.getResult();
        myPage.pageNum = page.getPageNum();
        myPage.endRow = page.getEndRow();
        myPage.pages = page.getPages();
        myPage.total = page.getTotal();
        myPage.pageSize = page.getPageSize();
        myPage.orderBy = page.getOrderBy();
        myPage.startRow = page.getStartRow();
        return myPage;
    }

}
