package com.github.zhangyazhong.service;

import com.github.zhangyazhong.model.Assets;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangyazhong
 * @version 1.0
 */
public class AssetsServiceTest extends BaseTest {
    @Resource(name = "assetsServiceImpl")
    private IAssetsService assetsService;
    
    @Test
    public void testGetAssets() {
        List<Assets> assetsList = assetsService.getAssets();
        assetsList.forEach(System.out::println);
    }
}