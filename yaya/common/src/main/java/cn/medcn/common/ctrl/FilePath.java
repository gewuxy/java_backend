package cn.medcn.common.ctrl;

/**
 * Created by lixuan on 2017/5/19.
 */
/**
 * 文件上传下载路径
 */
public enum FilePath{
    EXCEL("excels"),//excel文件存放目录
    LICENCE("licence"),//许可证图片存放路径
    PORTRAIT("headimg"),//头像图片存放路径
    TEMP("temp"),//模板文件存放路径
    QRCODE("qrcode"),//用户二维码存放路径
    DATA("data"),//数据中心文件
    ARTICLE("article"),//新闻 内容
    COURSE("course"),//ppt图片语音
    COURSE_COVER("course/template"),// 讲本封面
    GOODS("goods"),// 象城礼品图片存放路径
    BACKGROUND_IMAGE("background/image"),// 背景图片存放路径
    BACKGROUND_MUSIC("background/music"),// 背景音乐存放路径
    OTHERS("others");

    public String path;

    FilePath(String path){
        this.path = path;
    }
}