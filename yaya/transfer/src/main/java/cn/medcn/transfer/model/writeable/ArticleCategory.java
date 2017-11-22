package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.DoctorSuggestedCategory;
import cn.medcn.transfer.model.readonly.PharmacistRecommendCategory;
import cn.medcn.transfer.model.readonly.SymptomReadOnly;
import cn.medcn.transfer.utils.StringUtils;

/**
 * Created by Liuchangling on 2017/11/17.
 */

public class ArticleCategory {

    private String id;
    /**栏目名称*/
    private String name;
    /**父ID*/
    private String preId;
    /**序号*/
    private Integer sort;
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


    // 对症找药目录数据
    public static ArticleCategory build(SymptomReadOnly symptom, boolean leaf, String preId, Integer sort){
        ArticleCategory category = new ArticleCategory();
        if (symptom != null){
            category.setId(StringUtils.getNowStringID());
            category.setLeaf(leaf);
            category.setName(symptom.getName());
            category.setPreId(preId);
            category.setSort(sort);
        }
        return category;
    }

    // 医师建议目录数据
    public static ArticleCategory build(DoctorSuggestedCategory suggestedCategory, boolean leaf, String preId, Integer sort) {
        ArticleCategory category = new ArticleCategory();
        if (suggestedCategory != null){
            category.setId(StringUtils.getNowStringID());
            category.setLeaf(leaf);
            category.setName(suggestedCategory.getName());
            category.setPreId(preId);
            category.setSort(sort);
        }
        return category;
    }

    // 药师建议目录数据
    public static ArticleCategory build(PharmacistRecommendCategory recCategory, boolean leaf, String preId, Integer sort) {
        ArticleCategory category = new ArticleCategory();
        if (recCategory != null){
            category.setId(StringUtils.getNowStringID());
            category.setLeaf(leaf);
            category.setName(recCategory.getName());
            category.setPreId(preId);
            category.setSort(sort);
        }
        return category;
    }
}
