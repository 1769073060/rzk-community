package com.rzk.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rzk.mapper.dao.*;
import com.rzk.pojo.Comment;
import com.rzk.pojo.SearchRecords;
import com.rzk.pojo.Videos;
import com.rzk.pojo.vo.CommentsVO;
import com.rzk.pojo.vo.VideosVo;
import com.rzk.service.VideosService;
import com.rzk.utils.BadWordUtils;
import com.rzk.utils.PagedResult;
import com.rzk.utils.TimeAgoUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @Auther 刘瑞涛
 * @Date 2020-03-11 23:28
 **/
@Transactional
@Service
public class VideosServiceImpl implements VideosService {


    @Resource
    private SearchRecordsMapper searchRecordsMapper;
    @Resource
    private VideosVoMapper videosVoMapper;
    @Resource
    private UserDao usersMapper;

    @Resource
    private VideosMapper videosMapper;
    @Resource
    private CommentDao commentsMapper;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String insertVideos(Videos videos) {
        String id = UUID.randomUUID().toString();
        videos.setId(id);
        videosMapper.insertSelective(videos);
        return id;
    }

    @Override
    public String updateVideos(String videoId, String coverPath) {
        return null;
    }

//    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
//    public String updateVideos(String videoId, String coverPath) {
//
//        Videos videos = new Videos();
//        videos.setId(videoId);
//        videos.setCoverPath(coverPath);
//        videosMapper.updateByPrimaryKeySelective(videos);
//        return null;
//    }

    /**
     * 分页与查询视频列表
     *
     * @param video
     * @param isSaveRecord 1为保存视频，0为不保存
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PagedResult getAllVideosAndUsers(Videos video, Integer isSaveRecord, Integer pageNum, Integer pageSize) {
        String desc = video.getVideoDesc();
        String userId = video.getUserId();
        //  保存热搜
        if (isSaveRecord != null && isSaveRecord == 1) {
            SearchRecords records = new SearchRecords();
            String id = UUID.randomUUID().toString();
            records.setId(id);
            records.setContent(desc);
            searchRecordsMapper.insert(records);
        }

        PageHelper.startPage(pageNum, pageSize);
        List<VideosVo> videosVo = videosVoMapper.selectVideosAndUsers(desc, userId);
        PageInfo<VideosVo> pageList = new PageInfo<>(videosVo);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(pageNum);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(videosVo);
        pagedResult.setRecords(pageList.getTotal());
        return pagedResult;
    }

    @Override
    public PagedResult selectMyLikeVideos(String userId, Integer pages, Integer pageSize) {
        PageHelper.startPage(pages, pageSize);
        List<VideosVo> videosVo = videosVoMapper.selectMyLikeVideos(userId);
        PageInfo<VideosVo> pageList = new PageInfo<>(videosVo);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(pages);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(videosVo);
        pagedResult.setRecords(pageList.getTotal());
        return pagedResult;
    }

    @Override
    public PagedResult selectFollowVideos(String userId, Integer pages, Integer pageSize) {
        PageHelper.startPage(pages, pageSize);
        List<VideosVo> videosVo = videosVoMapper.selectFollowVideos(userId);
        PageInfo<VideosVo> pageList = new PageInfo<>(videosVo);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(pages);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(videosVo);
        pagedResult.setRecords(pageList.getTotal());
        return pagedResult;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<String> getHotselect() {

        return searchRecordsMapper.getHotselect();
    }

    @Override
    public void userLikeVideos(String userId, String videoId, String videoUserId) {

    }

    @Override
    public void userDislikeVideos(String userId, String videoId, String videoUserId) {

    }

    @Override
    public PagedResult getAllComments(String videoId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);

        List<CommentsVO> list = commentsMapper.queryComments(videoId);

        for (CommentsVO c : list) {
            String timeAgo = TimeAgoUtils.format(c.getCreateTime());
            //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            //System.out.println(simpleDateFormat.format(date));
            c.setTimeAgoStr(timeAgo);
        }

        PageInfo<CommentsVO> pageList = new PageInfo<>(list);

        PagedResult grid = new PagedResult();
        grid.setTotal(pageList.getPages());
        grid.setRows(list);
        grid.setPage(page);
        grid.setRecords(pageList.getTotal());

        return grid;
    }

    @Override
    public void saveComment(Comment comment) throws Exception {

    }


}
