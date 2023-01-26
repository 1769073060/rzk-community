package com.rzk.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * @PackageName : com.rzk.util
 * @FileName : IPUtils
 * @Description : 根据IP查询位置
 * @Author : rzk
 * @CreateTime : 2022年 11月 13日 下午8:40
 * @Version : 1.0.0
 */
@Slf4j
public class IPUtils {

    @Value("${ipText.text}")
    private String dbPath;

    /**
     * 获取IP地址
     * @param ip
     * @return
     */
    public String getIpAddr(String ip) {
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        log.info("ip"+ip);
        return ip;
    }



    public String getIpAddress(String ip) {
        ip = getIpAddr(ip);
        log.info("ip"+ip);

        // 1、从 dbPath 加载整个 xdb 到内存。
        byte[] cBuff;
        try {

            //InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("excleTemplate/test.xlsx");
            //cBuff = Searcher.loadContentFromFile(dbPath);

            cBuff = Searcher.loadContentFromFile("/opt/jar/ip2region.xdb");
            //cBuff = Searcher.loadContentFromFile("C:\\Users\\ASUS\\Desktop\\公众号\\公众号\\wxserver\\src\\main\\resources\\ip2region.xdb");

        } catch (Exception e) {
            log.info("failed to load content from `%s`: %s\n", dbPath, e);
            return null;
        }

        // 2、使用上述的 cBuff 创建一个完全基于内存的查询对象。
        Searcher searcher;
        try {
            searcher = Searcher.newWithBuffer(cBuff);
        } catch (Exception e) {
            log.info("failed to create content cached searcher: %s\n", e);
            return null;
        }

        // 3、查询
        try {
            long sTime = System.nanoTime();
            String region = searcher.search(ip);
            long cost = TimeUnit.NANOSECONDS.toMicros((long) (System.nanoTime() - sTime));
            System.out.printf("{region: %s, ioCount: %d, took: %d μs}\n", region, searcher.getIOCount(), cost);
            return region;
        } catch (Exception e) {
            System.out.printf("failed to search(%s): %s\n", ip, e);
        }
        // 4、关闭资源 - 该 searcher 对象可以安全用于并发，等整个服务关闭的时候再关闭 searcher
        // searcher.close();

        // 备注：并发使用，用整个 xdb 数据缓存创建的查询对象可以安全的用于并发，也就是你可以把这个 searcher 对象做成全局对象去跨线程访问。
        return null;
    }
    /**
     * InputStream转Byte[]
     * @param inputStream
     * @return
     * @throws Exception
     */
    public byte[] InputStreamToBytes(InputStream inputStream) throws Exception{
        int bufferSize = 1024;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[bufferSize];
        int count = -1;
        while((count = inputStream.read(data,0,bufferSize))!= -1){
            outputStream.write(data,0,count);
            data = null;
        }

        return outputStream.toByteArray();
    }

    /**
     * 获取详细地址
     * @param region
     * @return
     */
    public String getRegion(String region){
        if (!StringUtils.isEmpty(region)){
            String replace = region.replace("|", ",");
            String[] replaceList = replace.split(",");
            if (replaceList.length > 0){
                if ("中国".equals(replaceList[0])){
                    return replaceList[2]+replaceList[3]+replaceList[4];
                }
            }
        }
        return null;
    }

    /**
     * 获取省
     * @param region
     * @return
     */
    public String getProvince(String region){
        if (!StringUtils.isEmpty(region)){
            String replace = region.replace("|", ",");
            String[] replaceList = replace.split(",");
            if (replaceList.length > 0){
                if ("中国".equals(replaceList[0])){
                    return replaceList[2];
                }
            }
        }
        return null;
    }
    /**
     * 获取市
     * @param region
     * @return
     */
    public String getCity(String region){
        if (!StringUtils.isEmpty(region)){
            String replace = region.replace("|", ",");
            String[] replaceList = replace.split(",");
            if (replaceList.length > 0){
                if ("中国".equals(replaceList[0])){
                    return replaceList[3];
                }
            }
        }
        return null;
    }


    /**
     * 获取ISP
     * @param region
     * @return
     */
    public String getISP(String region){
        if (!StringUtils.isEmpty(region)){
            String replace = region.replace("|", ",");
            String[] replaceList = replace.split(",");
            if (replaceList.length > 0){
                if ("中国".equals(replaceList[0])){
                    return replaceList[4];
                }
            }
        }
        return null;
    }


    public static void main(String[] args) {
     }
}
