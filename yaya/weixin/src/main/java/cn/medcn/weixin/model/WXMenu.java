package cn.medcn.weixin.model;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Created by lixuan on 2017/7/28.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_wx_menu")
public class WXMenu {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    /**父ID*/
    private Integer parentId;
    /**在父级中的排序号*/
    private Integer sort;
    /**按钮类型*/
    private String type;
    /**按钮名称*/
    private String name;
    /**按钮对应的事件的key*/
    //private String key;
    /**view类型按钮的跳转url*/
    private String url;
    /**包含的子按钮集合*/
    @Transient
    private List<WXMenu> sub_button = Lists.newArrayList();


    public enum BUTTON_TYPE{
        CLICK("click"),//用户点击拉取消息
        VIEW("view"),//用户点击链接跳转
        SCANCODE_PUSH("scancode_push"),//用户扫描二维码
        SCANCODE_WAITMSG("scancode_waitmsg"),//扫码并提示消息接受中
        PIC_SYSPHTOTO("pic_sysphoto"),//弹出系统拍照发图的事件
        PIC_PHOTO_OR_ALBUM("pic_photo_or_album"),//弹出拍照或者相册发图的事件推送
        PIC_WEIXIN("pic_weixin"),//弹出微信相册发图器的事件推送
        LOCATION_SELECT("location_select");//弹出地理位置选择器的事件推送

        private String type;

        public String getType(){
            return this.type;
        }

        public void setType(String type){
            this.type = type;
        }

        BUTTON_TYPE(String type){
            this.type = type;
        }
    }
}
