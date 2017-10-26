package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixuan on 2017/10/25.
 */
@Entity
@Table(name = "t_csp_course_category")
@Data
@NoArgsConstructor
public class CourseCategory implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    protected Integer id;
    //英文名
    protected String nameEn;
    //中文名
    protected String nameCn;
    //深度 1表示大栏目 2表示子栏目
    protected Integer depth;

    protected Integer parentId;

    @Transient
    protected List<CourseCategory> categoryList = new ArrayList<>();

    @Transient
    protected CourseCategory parent;

    public void addSub(CourseCategory category){
        this.categoryList.add(category);
    }

    public enum CategoryDepth{
        root(1),
        sub(2);

        public int depth;

        CategoryDepth(int depth){
            this.depth = depth;
        }
    }
}
