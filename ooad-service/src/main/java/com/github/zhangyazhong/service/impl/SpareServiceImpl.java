package com.github.zhangyazhong.service.impl;

import com.github.zhangyazhong.common.hql.query.HqlQuerySafe;
import com.github.zhangyazhong.common.hql.query.HqlQueryStatement;
import com.github.zhangyazhong.common.permission.SparePermission;
import com.github.zhangyazhong.common.session.Session;
import com.github.zhangyazhong.dao.EntityDao;
import com.github.zhangyazhong.model.*;
import com.github.zhangyazhong.service.ISpareService;
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
public class SpareServiceImpl implements ISpareService {
    @Resource
    private EntityDao<Spare> spareDao;
    
    @Override
    public Spare findSpare(int id) {
        HqlQueryStatement findSpare = new HqlQuerySafe("Spare").where("id=" + id);
        return spareDao.find(findSpare);
    }
    
    @Override
    @SparePermission({Role.ADMIN, Role.CLIENT})
    public List<Spare> getSpare() {
        HqlQueryStatement getSpare = new HqlQuerySafe("Spare").orderBy(HqlQueryStatement.Order.ASC, "id");
        return spareDao.query(getSpare);
    }
    
    @Override
    @SparePermission({Role.ADMIN, Role.CLIENT})
    public SpareRecord getSpareStatus(int id) {
        HqlQueryStatement findSpare = new HqlQuerySafe("Spare").where("id=" + id);
        return spareDao.find(findSpare).getStatus();
    }
    
    @Override
    @SparePermission({Role.ADMIN, Role.CLIENT})
    public List<SpareRecord> getSpareHistory(int id) {
        HqlQueryStatement findSpare = new HqlQuerySafe("Spare").where("id=" + id);
        return spareDao.find(findSpare).getSpareRecordList();
    }
    
    @Override
    @SparePermission({Role.ADMIN, Role.BUYER})
    public Boolean buySpare(Spare spare) {
        Employee employee = Session.getAttribute("employee");
        Action action = Action.create(Action.BUY);
        SpareRecord spareRecord = new SpareRecord(new Timestamp(System.currentTimeMillis()), employee, spare, null, action);
        spare.addSpareRecord(spareRecord);
        spareDao.save(spare);
        return true;
    }
    
    @Override
    @SparePermission({Role.ADMIN, Role.EMPLOYEE})
    public Boolean installSpare(Spare spare, Assets assets) {
        SpareRecord spareRecord = getSpareStatus(spare.getId());
        Employee employee = Session.getAttribute("employee");
        Action action = Action.create(Action.INSTALL);
        if (spareRecord != null && spareRecord.isIdle() && assets.getStatus().isUsing() && assets.getStatus().getEmployee().equals(employee)) {
            spareRecord = new SpareRecord(new Timestamp(System.currentTimeMillis()), employee, spare, assets, action);
            spare.addSpareRecord(spareRecord);
            spareDao.update(spare);
            return true;
        }
        return false;
    }
    
    @Override
    @SparePermission({Role.ADMIN, Role.EMPLOYEE})
    public Boolean returnSpare(Spare spare) {
        SpareRecord spareRecord = getSpareStatus(spare.getId());
        Employee employee = Session.getAttribute("employee");
        Action action = Action.create(Action.RETURN);
        if (spareRecord != null && spareRecord.isUsing() && spareRecord.getEmployee().equals(employee)) {
            spareRecord = new SpareRecord(new Timestamp(System.currentTimeMillis()), employee, spare, null, action);
            spare.addSpareRecord(spareRecord);
            spareDao.update(spare);
            return true;
        }
        return false;
    }
    
    @Override
    @SparePermission({Role.ADMIN, Role.BUYER})
    public Boolean discardSpare(Spare spare) {
        SpareRecord spareRecord = getSpareStatus(spare.getId());
        Employee employee = Session.getAttribute("employee");
        Action action = Action.create(Action.DISCARD);
        if (spareRecord != null && spareRecord.isIdle()) {
            spareRecord = new SpareRecord(new Timestamp(System.currentTimeMillis()), employee, spare, null, action);
            spare.addSpareRecord(spareRecord);
            spareDao.update(spare);
            return true;
        }
        return false;
    }
}
