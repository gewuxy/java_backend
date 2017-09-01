package cn.medcn.user.dao;

import cn.medcn.user.model.Group;
import com.github.abel533.mapper.Mapper;

import java.util.List;

/**
 * Created by LiuLP on 2017/5/16/016.
 */
public interface GroupDAO extends Mapper<Group>{

    List<Group> findGroupList(Group group);
}
