package com.rzk.pojo.wxserver;



import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * @PackageName : com.rzk.pojo
 * @FileName : Image
 * @Description :
 * @Author : rzk
 * @CreateTime : 24/1/2022 上午12:52
 * @Version : v1.0
 */
public class Image implements Serializable {
    @XmlElement(name = "MediaId")
    private String mediaId;

    public String getMediaId() {
        return mediaId;
    }

    @XmlTransient
    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public Image() {
    }

    public Image(String mediaId) {
        this.mediaId = mediaId;
    }

    @Override
    public String toString() {
        return "Image{" +
                "mediaId='" + mediaId + '\'' +
                '}';
    }
}
