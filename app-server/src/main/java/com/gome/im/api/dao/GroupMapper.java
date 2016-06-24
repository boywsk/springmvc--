package com.gome.im.api.dao;

import com.gome.im.api.db.model.Group;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wangshikai on 2016/2/22.
 */
public interface GroupMapper {

    public void saveGroup(Group group);

    public void updateGroup(Group group);

    public Group getGroup(String groupId);

    public List<Group> listGroup(long uid);

    public void delGroup(String groupId);
}
