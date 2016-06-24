package com.gomeplus.im.api.service.impl;

import com.gomeplus.im.api.dao.CreateDatabaseMapper;
import com.gomeplus.im.api.request.response.ResultModel;
import com.gomeplus.im.api.service.CreateDatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangshikai on 2016/6/23.
 */
@Service
public class CreateDatabaseServiceImpl implements CreateDatabaseService{
    private final Logger logger = LoggerFactory.getLogger(CreateDatabaseServiceImpl.class);

    @Autowired
    private CreateDatabaseMapper createDatabaseMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResultModel<Object> createDatabase(Map<String,Object> parm) {
        createDatabaseMapper.createDatabase(parm);
        createDatabaseMapper.createContactsTbl(parm);
        createDatabaseMapper.createFavoritesTbl(parm);
        createDatabaseMapper.createFriendGroupTbl(parm);
        createDatabaseMapper.createFriendTbl(parm);
        createDatabaseMapper.createUserTbl(parm);
        logger.info("createDatabase success,databaseName:{}",parm.toString());
        return new ResultModel<Object>(ResultModel.RESULT_OK, "创建成功",new HashMap<>());
    }
}
