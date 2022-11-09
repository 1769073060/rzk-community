package com.rzk.controller.app;


import com.rzk.pojo.ImageResource;
import com.rzk.service.ImageResourceService;
import com.rzk.utils.status.MsgConsts;
import com.rzk.utils.status.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * 图片资源表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2022-11-03
 */
@RestController
@RequestMapping("demo/imageresource")
@Api(tags="图片资源表")
public class ImageResourceController {
    @Autowired
    private ImageResourceService imageResourceService;

    @GetMapping("page")
    @ApiOperation("分页")/**
    @ApiImplicitParams({
        @ApiImplicitParam(name = "PAGE", value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = "LIMIT", value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = "ORDER_FIELD", value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = "ORDER", value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })**/
    public List<ImageResource> page(){
        return imageResourceService.list();
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    public ResponseResult get(@PathVariable("id") Integer id){
        imageResourceService.getById(id);
        return new ResponseResult(MsgConsts.SUCCESS_CODE,null,null);

    }

    @PostMapping
    @ApiOperation("保存")
    public ResponseResult save(@RequestBody ImageResource dto){

        imageResourceService.save(dto);

        return new ResponseResult(MsgConsts.SUCCESS_CODE,null,null);
    }



    @PutMapping
    @ApiOperation("修改")
    public ResponseResult update(@RequestBody ImageResource imageResource){

        imageResourceService.updateById(imageResource);
        return new ResponseResult(MsgConsts.SUCCESS_CODE,null,null);

    }

    @DeleteMapping
    @ApiOperation("删除")
    public ResponseResult delete(@RequestBody Long[] ids){
        imageResourceService.removeById(ids);
        return new ResponseResult(MsgConsts.SUCCESS_CODE,null,null);

    }
}