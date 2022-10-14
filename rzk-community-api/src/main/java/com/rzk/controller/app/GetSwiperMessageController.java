package com.rzk.controller.app;

import com.rzk.pojo.Swiper;
import com.rzk.service.SwiperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetSwiperMessageController {

    @Autowired
    private SwiperService swiperService;

    @PostMapping("/getMessage/getAllSwiperMessage")
    public List<Swiper> getAllSwiperMessage() {
        return swiperService.list();
    }
}
