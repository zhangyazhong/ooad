package com.github.zhangyazhong.service.impl;

import com.github.zhangyazhong.common.hql.query.HqlQuerySafe;
import com.github.zhangyazhong.common.hql.query.HqlQueryStatement;
import com.github.zhangyazhong.dao.EntityDao;
import com.github.zhangyazhong.model.Assets;
import com.github.zhangyazhong.service.IAssetsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangyazhong
 * @version 1.0
 */
@Service
public class AssetsServiceImpl implements IAssetsService {
    @Resource
    private EntityDao<Assets> assetsDao;
    
    @Override
    public List<Assets> getAssets() {
        HqlQueryStatement getAssets = new HqlQuerySafe("Assets").orderBy(HqlQueryStatement.Order.ASC, "id");
        return assetsDao.query(getAssets);
    }
    
    @Override
    public List<Assets> getAssetsByEmployeeReceive(String phone) {
        String sql = "SELECT * FROM (SELECT * FROM (SELECT * FROM `assets_record` WHERE phone='" + phone + "' ORDER BY id DESC) GROUP BY assetsId), `action` WHERE ";
        return null;
    }
}
