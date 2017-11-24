package cn.medcn.official.controller;

/**
 * Created by lixuan on 2017/3/6.
 */
public enum MobileCenter {

    HLYY("hlyy","合理用药"),
    YYYIS("yis","合理用药"),
    YYYAOS("yaos","合理用药"),
    YYZS("yyzs","YaYa助手");

    public String title;

    public String label;

    MobileCenter(String title, String label){
        this.title = title;
        this.label = label;
    }
}
