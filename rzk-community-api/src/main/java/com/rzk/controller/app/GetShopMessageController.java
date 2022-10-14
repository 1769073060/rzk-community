package com.rzk.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzk.pojo.Collect;
import com.rzk.pojo.Shop;
import com.rzk.pojo.ShopBusiness;
import com.rzk.pojo.ShopImages;
import com.rzk.service.ShopBusinessService;
import com.rzk.service.ShopImagesService;
import com.rzk.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetShopMessageController {
    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopImagesService shopImagesService;
    @Autowired
    private ShopBusinessService shopBusinessService;

    @PostMapping("/getMessage/getAllShop")
    public List<Shop> getAllShopMessage() {
        return shopService.list();
    }

    @PostMapping("/getMessage/getShopMessage/{id}")
    public Shop getShopMessageById(@PathVariable Integer id) {
        Shop shop = shopService.getById(id);
        ShopImages shopImages = new ShopImages();
        shopImages.setShopId(id);
        QueryWrapper<ShopImages> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shop_id",shopImages.getShopId());
        shop.setShopImages(shopImagesService.list(queryWrapper));


        ShopBusiness shopBusiness = new ShopBusiness();
        shopBusiness.setShopId(id);
        QueryWrapper<ShopBusiness> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("shop_id",shopImages.getShopId());
        shop.setShopBusinesses(shopBusinessService.list(queryWrapper1));
        return shop;
    }

}
