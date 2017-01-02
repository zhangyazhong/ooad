package com.github.zhangyazhong.service;

import com.github.zhangyazhong.model.*;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangyazhong
 * @version 1.0
 */
public class SpareServiceTest extends BaseTest {
    @Resource
    private ISpareService spareService;
    @Resource
    private IAssetsService assetsService;
    @Resource
    private IEmployeeService employeeService;
    private static Employee employee;
    
    @Before
    public void setUp() {
        employee = employeeService.login("13061991332", "123456");
    }
    
    @Test
    public void findSpare() {
        Spare dbSpare = spareService.findSpare(1);
        Spare stdSpare = new Spare(1, "显示屏");
        assert dbSpare.equals(stdSpare);
    }
    
    @Test
    public void testGetSpare() {
        List<Spare> dbSpareList = spareService.getSpare();
        List<Spare> stdSpareList = Lists.newArrayList(
                new Spare(1, "显示屏"),
                new Spare(2, "鼠标"),
                new Spare(3, "音响")
        );
        assert dbSpareList.size() == stdSpareList.size();
        for (int i = 0; i < dbSpareList.size(); i++) {
            assert dbSpareList.get(i).equals(stdSpareList.get(i));
        }
    }
    
    @Test
    public void testGetSpareStatus() {
        int testIdleSpareId = 1;
        int testUsingSpareId = 2;
        int testScrappedSpareId = 3;
        SpareRecord idleStatus = spareService.getSpareStatus(testIdleSpareId);
        SpareRecord usingStatus = spareService.getSpareStatus(testUsingSpareId);
        SpareRecord scrappedStatus = spareService.getSpareStatus(testScrappedSpareId);
        assert idleStatus.isIdle();
        assert usingStatus.isUsing();
        assert scrappedStatus.isScrapped();
    }
    
    @Test
    public void testGetSpareHistory() {
        int testSpareId = 2;
        List<SpareRecord> dbSpareRecordList = spareService.getSpareHistory(testSpareId);
        List<SpareRecord> stdSpareRecordList = Lists.newArrayList(
                new SpareRecord(null, employee, new Spare(testSpareId, null), null, Action.create(Action.BUY)),
                new SpareRecord(null, employee, new Spare(testSpareId, null), new Assets(1, null, null), Action.create(Action.INSTALL))
        );
        assert dbSpareRecordList.size() == stdSpareRecordList.size();
        for (int i = 0; i < dbSpareRecordList.size(); i++) {
            SpareRecord dbSpareRecord = dbSpareRecordList.get(i);
            SpareRecord stdSpareRecord = stdSpareRecordList.get(i);
            assert dbSpareRecord.getEmployee().getId() == stdSpareRecord.getEmployee().getId();
            assert dbSpareRecord.getSpare().getId() == stdSpareRecord.getSpare().getId();
            assert dbSpareRecord.getAction().getId() == stdSpareRecord.getAction().getId();
        }
    }
    
    @Test
    public void testBuySpare() {
        Spare spare = new Spare("机械键盘");
        spareService.buySpare(spare);
        assert spareService.findSpare(spare.getId()).equals(spare);
    }
    
    @Test
    public void testInstallSpare() {
        int testIdleSpare = 1;
        int testUsingAssets = 2;
        Spare spare = spareService.findSpare(testIdleSpare);
        Assets assets = assetsService.findAssets(testUsingAssets);
        assert spareService.installSpare(spare, assets);
        assert spareService.getSpareStatus(testIdleSpare).isUsing();
    }
    
    @Test
    public void testReturnSpare() {
        int testUsingSpare = 2;
        Spare spare = spareService.findSpare(testUsingSpare);
        assert spareService.returnSpare(spare);
        assert spareService.getSpareStatus(testUsingSpare).isIdle();
    }
    
    @Test
    public void testDiscardSpare() {
        int testIdleSpare = 1;
        Spare spare = spareService.findSpare(testIdleSpare);
        assert spareService.discardSpare(spare);
        assert spareService.getSpareStatus(testIdleSpare).isScrapped();
    }
}
