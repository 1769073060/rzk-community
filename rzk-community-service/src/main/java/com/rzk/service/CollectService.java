package com.rzk.service;


import com.rzk.pojo.Collect;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2022-09-11
 */
public interface CollectService extends IService<Collect> {

    public List<Collect> getAllCollectionMessageByUserId(Integer userId);
}