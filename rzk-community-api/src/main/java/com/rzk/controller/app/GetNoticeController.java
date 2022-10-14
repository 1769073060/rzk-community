package com.rzk.controller.app;

import com.rzk.pojo.Notice;
import com.rzk.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetNoticeController {


    @Autowired
    private NoticeService noticeService;

    @PostMapping("/getMessage/getAllNoticeMessage")

    public List<Notice> getAllNoticeMessage() {
        return noticeService.list();
    }

}
