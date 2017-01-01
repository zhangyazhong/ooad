package com.github.zhangyazhong.service.impl;

import com.github.zhangyazhong.common.hql.query.HqlQuerySafe;
import com.github.zhangyazhong.common.hql.query.HqlQueryStatement;
import com.github.zhangyazhong.dao.EntityDao;
import com.github.zhangyazhong.model.Action;
import com.github.zhangyazhong.model.Assets;
import com.github.zhangyazhong.model.AssetsRecord;
import com.github.zhangyazhong.service.IAssetsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangyazhong
 * @version 1.0
 */
@Service
@Transactional
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
        String sql = String.format("SELECT assets.* " +
                "FROM " +
                "    ( " +
                "        SELECT " +
                "            * " +
                "        FROM " +
                "            assets_record " +
                "        WHERE " +
                "            assets_record.id IN ( " +
                "                SELECT " +
                "                    MAX(assets_record.id) " +
                "                FROM " +
                "                    `assets_record`, " +
                "                    `employee` " +
                "                WHERE " +
                "                    assets_record.employeeId = employee.id " +
                "                AND phone = '%s' " +
                "                GROUP BY " +
                "                    assets_record.assetsId " +
                "            ) " +
                "    ) AS record, " +
                "    `action` AS action, " +
                "    `assets` AS assets " +
                "WHERE " +
                "    action.description = '%s' " +
                "AND record.actionId = action.id " +
                "AND record.assetsId = assets.id", phone, Action.RECEIVE);
        return assetsDao.query(sql, Assets.class);
    }
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public AssetsRecord getAssetsStatus(int id) {
        HqlQueryStatement findAssets = new HqlQuerySafe("Assets").where("id=" + id);
        return assetsDao.find(findAssets).getStatus();
    }
}
