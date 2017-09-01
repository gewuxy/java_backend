package cn.medcn.user.dto;

import cn.medcn.common.utils.PinyinUtils;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Created by lixuan on 2017/5/15.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalDTO implements Serializable, Comparable<HospitalDTO>{

    private String name;

    private char alpha;


    public static List<HospitalDTO> buildList(List<String> list){
        List<HospitalDTO> dtoList = Lists.newArrayList();
        if(list != null && !list.isEmpty()){
            for(String name:list){
                char alpha = PinyinUtils.getFirstAlpha(name).toUpperCase().charAt(0);
                HospitalDTO dto = new HospitalDTO(name, alpha);
                dtoList.add(dto);
            }
        }
        Collections.sort(dtoList);
        return dtoList;
    }

    @Override
    public int compareTo(HospitalDTO o) {
        if(o.getAlpha() > this.alpha){
            return -1;
        }else if(o.getAlpha() < this.alpha){
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) {
        List<HospitalDTO> list = Lists.newArrayList();
        list.add(new HospitalDTO("广州中医院",'G'));
        list.add(new HospitalDTO("福建中医院",'H'));
        list.add(new HospitalDTO("浙江中医院",'Z'));
        Collections.sort(list);
        for(HospitalDTO dto:list){
            System.out.println(dto.getAlpha()+" - "+dto.getName());
        }
    }
}
