package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.ClinicalGuideCategory;
import cn.medcn.transfer.model.readonly.MedClass;
import cn.medcn.transfer.utils.StringUtils;

/**
 * Created by lixuan on 2017/8/21.
 */
public class MedCategory {

    protected String id;
    /**栏目名称*/
    protected String name;
    /**父节点ID*/
    protected String preId;
    /**排序号*/
    protected Integer sort;
    /**是否是叶子节点*/
    private Boolean leaf;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreId() {
        return preId;
    }

    public void setPreId(String preId) {
        this.preId = preId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }


    public static MedCategory build(MedClass medClass, boolean leaf, String preId){
        MedCategory category = new MedCategory();
        if (medClass != null){
            category.setId(StringUtils.getNowStringID());
            category.setLeaf(leaf);
            category.setName(medClass.getName());
            category.setPreId(preId);
        }

        return category;
    }

    public static MedCategory build(ClinicalGuideCategory category, boolean leaf, String preId, Integer sort){
        if (category != null){
            MedCategory medCategory = new MedCategory();
            medCategory.setId(StringUtils.getNowStringID());
            medCategory.setLeaf(leaf);
            medCategory.setName(category.getName());
            medCategory.setPreId(preId);
            medCategory.setSort(sort);
            return medCategory;
        }

        return null;
    }
}
