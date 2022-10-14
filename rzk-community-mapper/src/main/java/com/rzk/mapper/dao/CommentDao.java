package com.rzk.mapper.dao;

 import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rzk.pojo.Comment;
 import com.rzk.pojo.vo.CommentsVO;
 import org.apache.ibatis.annotations.*;

 import java.util.List;

/**
 * 
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2022-09-11
 */
@Mapper
public interface CommentDao extends BaseMapper<Comment> {


 @Select("<script>" +
         "select c.*,u.face_image,u.user_nickname,tu.nickname as toNickName from comment c left join  user u  on c.from_user_id = u.user_id " +
         " left join user tu on c.user_id = tu.user_id "+
         "where 1=1 and c.message_id =#{videoId} order by c.create_time desc "+
         "</script>")
 @Results({
         @Result(property = "id", column = "id"),
         @Result(property = "videoId", column = "video_id"),
         @Result(property = "fromUserId", column = "from_user_id"),
         @Result(property = "createTime", column = "create_time"),
         @Result(property = "comment", column = "comment"),
         @Result(property = "faceImage", column = "face_image"),
         @Result(property = "nickname", column = "nickname"),
 })
 public List<CommentsVO> queryComments(@Param("videoId") String videoId);
}