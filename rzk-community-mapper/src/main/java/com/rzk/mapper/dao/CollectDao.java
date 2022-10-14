package com.rzk.mapper.dao;

 import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rzk.pojo.Collect;
import org.apache.ibatis.annotations.Mapper;

 import java.util.List;

/**
 * 
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2022-09-11
 */
@Mapper
public interface CollectDao extends BaseMapper<Collect> {
 List<Collect> getAllCollectionMessageByUserId(Integer id);

}