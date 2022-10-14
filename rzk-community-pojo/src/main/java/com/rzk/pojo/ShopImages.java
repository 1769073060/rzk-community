package com.rzk.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
 
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2022-09-11
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("shop_images")
public class ShopImages{
	private static final long serialVersionUID = 1L;

    /**
     * 
     */
	private Integer shopDetailId;
    /**
     * 
     */
	private Integer shopId;
    /**
     * 
     */
	private String shopImages;
}