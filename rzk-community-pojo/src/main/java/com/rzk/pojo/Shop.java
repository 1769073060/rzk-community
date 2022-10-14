package com.rzk.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
 
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2022-09-11
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("shop")
public class Shop{
	private static final long serialVersionUID = 1L;

    /**
     * 
     */
	private Integer shopId;
    /**
     * 
     */
	private String shopName;
    /**
     * 
     */
	private String shopIntro;
    /**
     * 
     */
	private String shopPhone;
    /**
     * 
     */
	private String shopAvatar;
    /**
     * 
     */
	private String shopLatitude;
    /**
     * 
     */
	private String shopLongitude;
    /**
     * 
     */
	private Date shopCreatTime;

	private List<ShopImages> shopImages;

	private List<ShopBusiness> shopBusinesses;
}