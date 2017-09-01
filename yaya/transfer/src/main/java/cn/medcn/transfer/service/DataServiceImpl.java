package cn.medcn.transfer.service;

import cn.medcn.transfer.model.writeable.MedCategory;
import cn.medcn.transfer.service.writeable.WriteAbleMedCategoryService;
import cn.medcn.transfer.utils.FileUtils;
import cn.medcn.transfer.utils.StringUtils;
import cn.medcn.transfer.utils.WordUtils;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

import java.io.File;

/**
 * Created by lixuan on 2017/8/23.
 */
public abstract class DataServiceImpl {

    protected String fileBase = "D:\\upload\\";

    protected abstract WriteAbleMedCategoryService getWriteAbleMedCategoryService();

    protected abstract void parseContent(File file, String categoryId);

    protected abstract String getRootCategoryId();

    protected ActiveXComponent app;

    protected Dispatch docs;

    protected final String parseCategory(File dir, String parentId, int sort) {
        MedCategory category = new MedCategory();
        category.setName(dir.getName());
        category.setPreId(parentId);
        category.setSort(sort);
        category.setLeaf(FileUtils.isLeaf(dir));
        category.setId(StringUtils.getNowStringID());
        getWriteAbleMedCategoryService().transferCategory(category);
        return category.getId();
    }

    protected final void parse(File file, String parentId, int sort){
        if (file.isDirectory()){
            //解析栏目
            String categoryId = parseCategory(file, parentId, sort);
            File[] files = file.listFiles();
            if (files != null){
                int subSort = 1;
                for (File subFile : files){
                    parse(subFile, categoryId, subSort);
                }
            }
        } else {
            parseContent(file, parentId);
        }
    }



    public final void transfer(String dirPath){
        File dir = new File(dirPath);
        int sort = 1;
        app = new ActiveXComponent("Word.Application");
        docs = app.getProperty("Documents").toDispatch();
        for (File file : dir.listFiles()){
            parse(file, getRootCategoryId(), sort);
            sort ++;
        }
        app.invoke("Quit", WordUtils.DOC_NOT_SAVE_CHANGE);
    }
}
