package com.github.zhangyazhong.service;

import com.github.zhangyazhong.model.Assets;
import com.github.zhangyazhong.model.AssetsRecord;
import com.github.zhangyazhong.model.Employee;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangyazhong
 * @version 1.0
 */
public class AssetsServiceTest extends BaseTest {
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
    public void findAssets() {
        Assets dbAssets = assetsService.findAssets(1);
        Assets stdAssets = new Assets(1, "ThinkPad", Assets.TYPE_EQUIPMENT);
        assert dbAssets.equals(stdAssets);
    }
    
    @Test
    public void testGetAssets() {
        List<Assets> dbAssetsList = assetsService.getAssets();
        List<Assets> stdAssetsList = Lists.newArrayList(
                new Assets(1, "ThinkPad", Assets.TYPE_EQUIPMENT),
                new Assets(2, "Macbook Air", Assets.TYPE_EQUIPMENT),
                new Assets(8, "Samsung Note 7", Assets.TYPE_EQUIPMENT)
        );
        assert dbAssetsList.size() == stdAssetsList.size();
        for (int i = 0; i < dbAssetsList.size(); i++) {
            assert dbAssetsList.get(i).equals(stdAssetsList.get(i));
        }
    }
    
    @Test
    public void testGetAssetsByEmployeeReceive() {
        List<Assets> dbAssetsList = assetsService.getAssetsByEmployeeReceive(employee.getPhone());
        List<Assets> stdAssetsList = Lists.newArrayList(
                new Assets(2, "Macbook Air", Assets.TYPE_EQUIPMENT)
        );
        assert dbAssetsList.size() == stdAssetsList.size();
        for (int i = 0; i < dbAssetsList.size(); i++) {
            assert dbAssetsList.get(i).equals(stdAssetsList.get(i));
        }
    }
    
    @Test
    public void testGetAssetsStatus() {
        int testIdleAssetsId = 1;
        int testUsingAssetsId = 2;
        int testScrappedAssetsId = 8;
        AssetsRecord idleStatus = assetsService.getAssetsStatus(testIdleAssetsId);
        AssetsRecord usingStatus = assetsService.getAssetsStatus(testUsingAssetsId);
        AssetsRecord scrappedStatus = assetsService.getAssetsStatus(testScrappedAssetsId);
        assert idleStatus.isIdle();
        assert usingStatus.isUsing();
        assert scrappedStatus.isScrapped();
    }
    
    @Test
    public void testBuyAssets() {
        Assets assets = new Assets("Samsung Note 6", Assets.TYPE_EQUIPMENT);
        assetsService.buyAssets(assets);
        assert assetsService.findAssets(assets.getId()).equals(assets);
    }
    
    @Test
    public void testReceiveAssets() {
        int testIdleAssetsId = 1;
        assert assetsService.getAssetsStatus(testIdleAssetsId).isIdle();
        Assets assets = assetsService.findAssets(testIdleAssetsId);
        assert assetsService.receiveAssets(assets);
        assert assetsService.getAssetsStatus(testIdleAssetsId).isUsing();
        List<Assets> assetsList = assetsService.getAssetsByEmployeeReceive(employee.getPhone());
        assert assetsList.get(assetsList.size() - 1).equals(assets);
    }
    
    @Test
    public void testReturnAssets() {
        int testUsingAssetsId = 2;
        assert assetsService.getAssetsStatus(testUsingAssetsId).isUsing();
        Assets assets = assetsService.findAssets(testUsingAssetsId);
        assert assetsService.returnAssets(assets);
        assert assetsService.getAssetsStatus(testUsingAssetsId).isIdle();
    }
    
    @Test
    public void testDiscardAssets() {
        int testIdleAssetsId = 1;
        assert assetsService.getAssetsStatus(testIdleAssetsId).isIdle();
        Assets assets = assetsService.findAssets(testIdleAssetsId);
        assert assetsService.discardAssets(assets);
        assert assetsService.getAssetsStatus(testIdleAssetsId).isScrapped();
    }
}