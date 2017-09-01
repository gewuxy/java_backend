package cn.medcn.meet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Liuchangling on 2017/7/26.
 * 资源目录 实体
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_infinity_tree")
public class InfinityTree implements Serializable {

    @Id
    private String id;
    // 目录名称
    private String infinityName;
    // 父节点ID
    private String preid;
    // 序号
    private Integer sort;
    // 挂载资源类型 1 会议 2 会议资料 3 会议视频 4 其它资料
    private Integer mountType;
    // 是否隐藏
    private boolean hide;
    // 单位号ID
    private Integer userId;
    // 是否子节点 0 父节点 1 子节点
    private boolean leaf;

    @Transient
    // 目录类型 文件夹 、资源
    private Integer type;

    @Transient
    private List<InfinityTree> treeList;

    @Transient
    private String typeName;
    public String getTypeName(){
        if(this.type != null){
            return InfinityTree.InfinityType.values()[this.type].getLable();
        }else{
            return null;
        }
    }

    @Transient
    // 目录下的会议总数
    private Integer meetCount = 0;

    /* 资源类型 */
    public enum InfinityMountType {
        MEET(0,"会议"),
        MEET_MATERIAL(1,"会议资料"),
        MEET_VIDEO(2,"会议视频"),
        OTHER_MATERIAL(3,"其它资料");

        private Integer mountType;
        private String lable;

        public Integer getMountType() {
            return mountType;
        }

        public String getLable() {
            return lable;
        }

        InfinityMountType(Integer mountType, String lable) {
            this.mountType = mountType;
            this.lable = lable;
        }
    }

    /* 目录类型 */
    public enum InfinityType {
        Folder(0,"文件夹"),
        Resource(1,"资源");

        private Integer type;
        private String lable;

        public Integer getType() {
            return type;
        }

        public String getLable() {
            return lable;
        }

        InfinityType(Integer type,String lable) {
            this.type = type;
            this.lable = lable;
        }
    }

}
