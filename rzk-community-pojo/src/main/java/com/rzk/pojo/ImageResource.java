package com.rzk.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 图片资源表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2022-11-03
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("image_resource")
public class ImageResource{
	private static final long serialVersionUID = 1L;
	@TableId(value = "id",type= IdType.AUTO)//需要在实体类的主键属性上添加此句
	private Integer id;

    /**
     * 图片地址
     */
	private String imageUrl;
    /**
     * 地址
     */
	private String url;
    /**
     * 图片类型
     */
	private Integer type;
    /**
     * 图片状态
     */
	private Integer status;
    /**
     * 创建者
     */
	private Date createMaster;
	/**
	 * 组
	 */
	private String groupName;
    /**
     * 修改时间
     */
	@JsonFormat(pattern="yyyy年MM月dd日 HH时mm分ss秒",timezone = "UTC")
	private Date updateTime;
    /**
     * 创建时间
     */
	@JsonFormat(pattern="yyyy年MM月dd日 HH时mm分ss秒",timezone = "UTC")
	private Date createTime;
}