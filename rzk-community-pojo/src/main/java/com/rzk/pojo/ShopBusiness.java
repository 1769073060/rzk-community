package com.rzk.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
 
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2022-09-11
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("shop_business")
public class ShopBusiness{
	private static final long serialVersionUID = 1L;

    /**
     * 
     */
	private Integer businessId;
    /**
     * 
     */
	private Integer shopId;
    /**
     * 
     */
	private String shopGoodsImage;
    /**
     * 
     */
	private String shopGoodsTitle;
    /**
     * 
     */
	private BigDecimal shopGoodsPrice;
}