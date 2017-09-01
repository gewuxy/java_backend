package cn.medcn.user.model;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lixuan on 2017/5/8.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="t_app_menu")
public class AppMenu implements Serializable , Comparable<AppMenu>{
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String menuName;

    private String menuDesc;

    private String permission;

    private Integer sort;

    private String href;

    private String icon;

    private String target;

    private Integer preid;

    private Boolean hide;

    @Transient
    private AppMenu parent;

    @Transient
    private List<AppMenu> items = Lists.newArrayList();


    @Override
    public int compareTo(AppMenu o) {
        if(o.getSort() > this.sort){
            return 1;
        }else if(o.getSort() == o.sort){
            return 0;
        }else{
            return -1;
        }
    }


    public static List<AppMenu> sort(List<AppMenu> list){
        List<AppMenu> menuList = Lists.newArrayList();
        if(list != null && !list.isEmpty()){
            Iterator<AppMenu> iterator = list.iterator();
            while(iterator.hasNext()){
                AppMenu appMenu = iterator.next();
                if(appMenu.getPreid() == 0){
                    menuList.add(appMenu);
                }else{
                    for(AppMenu am:menuList){
                        if(am.getId() == appMenu.getPreid()){
                            am.getItems().add(appMenu);
                        }
                    }
                }
            }

        }
        return menuList;
    }
}
