package com.github.zhangyazhong.service.impl;

import com.github.zhangyazhong.common.hql.query.HqlQuerySafe;
import com.github.zhangyazhong.common.hql.query.HqlQueryStatement;
import com.github.zhangyazhong.common.permission.AssetsPermission;
import com.github.zhangyazhong.common.session.Session;
import com.github.zhangyazhong.dao.EntityDao;
import com.github.zhangyazhong.model.*;
import com.github.zhangyazhong.service.IAssetsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
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
    public Assets findAssets(int id) {
        HqlQueryStatement findAssets = new HqlQuerySafe("Assets").where("id=" + id);
        return assetsDao.find(findAssets);
    }
    
    @Override
    @AssetsPermission({Role.ADMIN, Role.CLIENT})
    public List<Assets> getAssets() {
        HqlQueryStatement getAssets = new HqlQuerySafe("Assets").orderBy(HqlQueryStatement.Order.ASC, "id");
        return assetsDao.query(getAssets);
    }
    
    @Override
    @AssetsPermission({Role.ADMIN, Role.CLIENT})
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
    @AssetsPermission({Role.ADMIN, Role.CLIENT})
    public AssetsRecord getAssetsStatus(int id) {
        HqlQueryStatement findAssets = new HqlQuerySafe("Assets").where("id=" + id);
        return assetsDao.find(findAssets).getStatus();
    }
    
    @Override
    @AssetsPermission({Role.ADMIN, Role.BUYER})
    public Boolean buyAssets(Assets assets) {
        Employee employee = Session.getAttribute("employee");
        Action action = Action.create(Action.BUY);
        AssetsRecord assetsRecord = new AssetsRecord(new Timestamp(System.currentTimeMillis()), employee, assets, action);
        assets.addAssetsRecord(assetsRecord);
        assetsDao.save(assets);
        return true;
    }
    
    @Override
    @AssetsPermission({Role.ADMIN, Role.EMPLOYEE})
    public Boolean receiveAssets(Assets assets) {
        AssetsRecord assetsRecord = getAssetsStatus(assets.getId());
        Employee employee = Session.getAttribute("employee");
        Action action = Action.create(Action.RECEIVE);
        if (assetsRecord != null && assetsRecord.isIdle()) {
            assetsRecord = new AssetsRecord(new Timestamp(System.currentTimeMillis()), employee, assets, action);
            assets.addAssetsRecord(assetsRecord);
            assetsDao.update(assets);
            return true;
        }
        return false;
    }
    
    @Override
    @AssetsPermission({Role.ADMIN, Role.EMPLOYEE})
    public Boolean returnAssets(Assets assets) {
        AssetsRecord assetsRecord = getAssetsStatus(assets.getId());
        Employee employee = Session.getAttribute("employee");
        Action action = Action.create(Action.RETURN);
        if (assetsRecord != null && assetsRecord.isUsing() && assetsRecord.getEmployee().equals(employee)) {
            assetsRecord = new AssetsRecord(new Timestamp(System.currentTimeMillis()), employee, assets, action);
            assets.addAssetsRecord(assetsRecord);
            assetsDao.update(assets);
            return true;
        }
        return false;
    }
    
    @Override
    @AssetsPermission({Role.ADMIN, Role.BUYER})
    public Boolean discardAssets(Assets assets) {
        AssetsRecord assetsRecord = getAssetsStatus(assets.getId());
        Employee employee = Session.getAttribute("employee");
        Action action = Action.create(Action.DISCARD);
        if (assetsRecord != null && assetsRecord.isIdle()) {
            assetsRecord = new AssetsRecord(new Timestamp(System.currentTimeMillis()), employee, assets, action);
            assets.addAssetsRecord(assetsRecord);
            assetsDao.update(assets);
            return true;
        }
        return false;
    }
}
