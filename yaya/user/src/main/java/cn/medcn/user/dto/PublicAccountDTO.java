package cn.medcn.user.dto;

import cn.medcn.common.utils.PinyinUtils;
import cn.medcn.user.model.AppUser;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**公众号id,头像和昵称
 * Created by LiuLP on 2017/4/21.
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PublicAccountDTO implements Comparable<PublicAccountDTO>{
    private Integer id;

    private String nickname;

    private String headimg;
    /**昵称拼音首字母*/
    private char alpha;

    public char getAlpha(){
        return PinyinUtils.getAlpha(this.nickname).toUpperCase().charAt(0);
    }

    public static PublicAccountDTO build(AppUser appUser){
        PublicAccountDTO publicAccountDTO = new PublicAccountDTO();
        if(appUser!=null){
            publicAccountDTO.setId(appUser.getId());
            publicAccountDTO.setNickname(appUser.getNickname());
            publicAccountDTO.setHeadimg(appUser.getHeadimg());
        }
        return publicAccountDTO;
    }

    public static void main(String[] args) {
        List<PublicAccountDTO> list = Lists.newArrayList();

        PublicAccountDTO dto2 = new PublicAccountDTO();
        dto2.setNickname("南方医院普外科");
        list.add(dto2);
        PublicAccountDTO dto = new PublicAccountDTO();
        dto.setNickname("海印南院泌尿科");
        list.add(dto);
        for(PublicAccountDTO pad:list){
            System.out.println(pad.getAlpha()+" - "+pad.getNickname());
        }
        Collections.sort(list);
        for(PublicAccountDTO pad:list){
            System.out.println(pad.getAlpha()+" - "+pad.getNickname());
        }
    }

    @Override
    public int compareTo(PublicAccountDTO o) {
        if(this.getAlpha() < o.getAlpha()){
            return -1;
        }else if(this.getAlpha() > o.getAlpha()){
            return 1;
        }
        return 0;
    }
}
