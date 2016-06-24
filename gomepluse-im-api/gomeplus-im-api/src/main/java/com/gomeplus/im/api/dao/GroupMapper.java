package com.gomeplus.im.api.dao;


import java.util.List;

import com.gomeplus.im.api.model.Group;

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
