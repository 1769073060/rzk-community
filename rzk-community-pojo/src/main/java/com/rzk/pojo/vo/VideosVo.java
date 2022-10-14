package com.rzk.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class VideosVo {
    @Id
    private String id;
    @TableField(value = "user_id")
    private String userId;
    @TableField(value = "video_desc")
    private String videoDesc;
    @TableField(value = "video_path")
    private String videoPath;
    @TableField(value = "video_seconds")
    private Float videoSeconds;
    @TableField(value = "video_width")
    private Integer videoWidth;
    @TableField(value = "video_height")
    private Integer videoHeight;
    @TableField(value = "cover_path")
    private String coverPath;
    @TableField(value = "like_counts")
    private Long likeCounts;
    @TableField(value = "status")
    private Integer status;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "face_image")
    private String faceImage;
    @TableField(value = "nickname")
    private String nickname;

    public String getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }


    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc == null ? null : videoDesc.trim();
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath == null ? null : videoPath.trim();
    }

    public Float getVideoSeconds() {
        return videoSeconds;
    }

    public void setVideoSeconds(Float videoSeconds) {
        this.videoSeconds = videoSeconds;
    }

    public Integer getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(Integer videoWidth) {
        this.videoWidth = videoWidth;
    }

    public Integer getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(Integer videoHeight) {
        this.videoHeight = videoHeight;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath == null ? null : coverPath.trim();
    }

    public Long getLikeCounts() {
        return likeCounts;
    }

    public void setLikeCounts(Long likeCounts) {
        this.likeCounts = likeCounts;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setVideoPath() {
    }
}