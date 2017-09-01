package cn.medcn.transfer.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixuan on 2017/2/9.
 */
public class KeyValuePair implements Serializable{

    private String key;

    private String value;

    public KeyValuePair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public static String toJSON(List<KeyValuePair> keyValuePairList){
        if(keyValuePairList == null){
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int index = 0;
        for(KeyValuePair keyValuePair : keyValuePairList){
            builder.append("{\"key\":\""+keyValuePair.getKey()+"\",").append("\"value\":\""+keyValuePair.getValue()+"\"}");
            builder.append(index < keyValuePairList.size() - 1?",":"");
            index ++;
        }
        builder.append("]");
        return builder.toString();
    }

    public static void main(String[] args) {
        List<KeyValuePair> list = new ArrayList<>();
        KeyValuePair keyValuePair = new KeyValuePair("A", "hahaha");
        KeyValuePair keyValuePair1 = new KeyValuePair("B", "hehehehe");
        KeyValuePair keyValuePair2 = new KeyValuePair("C", "hohohoho");
        list.add(keyValuePair);
        list.add(keyValuePair1);
        list.add(keyValuePair2);
        System.out.println(toJSON(list));
    }
}
