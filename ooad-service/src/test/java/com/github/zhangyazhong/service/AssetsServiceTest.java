package com.github.zhangyazhong.service;

import com.github.zhangyazhong.model.Assets;
import com.github.zhangyazhong.model.AssetsRecord;
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
    
    @Before
    public void setUp() {
        employeeService.login("123456", "123456");
    }
    
    @Test
    public void testGetAssets() {
        List<Assets> assetsList = assetsService.getAssets();
        assetsList.forEach(System.out::println);
    }
    
    @Test
    public void testGetAssetsByEmployeeReceive() {
        List<Assets> assetsList = assetsService.getAssetsByEmployeeReceive("123456");
        assetsList.forEach(System.out::println);
    }
    
    @Test
    public void testGetAssetsStatus() {
        AssetsRecord assetsRecord = assetsService.getAssetsStatus(1);
        if (assetsRecord != null) {
            System.out.println(assetsRecord.getEmployee()
                    + " | " + assetsRecord.getAction().getDescription()
                    + " | " + assetsRecord.getDate());
        } else {
            System.out.println("(null)");
        }
    }
    
    @Test
    public void testBuyAssets() {
        Assets assets = new Assets("iMac", Assets.TYPE_EQUIPMENT);
        assetsService.buyAssets(assets);
        List<Assets> assetsList = assetsService.getAssets();
        assetsList.forEach(System.out::println);
    }
}