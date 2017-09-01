package cn.medcn.meet.dao;

import cn.medcn.meet.dto.MeetInfoDTO;
import cn.medcn.user.model.Favorite;
import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by LiuLP on 2017/5/18.
 */
public interface MeetFavoriteDAO extends Mapper<Favorite> {

    List<MeetInfoDTO> findMeetFavorite(Map<String, Object> params);
}
