package top.tianqi.plankton.attach.plugin;

import top.tianqi.plankton.attach.vo.FileInfo;

import java.io.File;
import java.util.List;

/**
 * @author Wukh
 * @create 2021-01-12
 */
public interface StoragePlugin {

    /**
     * 文件上传
     * @param path 上传路径
     * @param file 上传文件
     * @param contentType 文件类型
     */
    public void upload(String path, File file, String contentType);

    /**
     * 获取文件二进制
     * @param fileUrl
     * @return
     */
    byte[] download(String fileUrl);

    /**
     * 获取访问URL
     * @param path 上传路径
     * @return 访问URL
     */
    public String getUrl(String path);

    /**
     * 文件浏览
     * @param path 浏览路径
     * @return 文件信息
     */
    public List<FileInfo> browser(String path);
}
