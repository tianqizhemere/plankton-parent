package top.tianqi.plankton.system.service.impl;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.tianqi.plankton.common.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.system.entity.Attach;
import top.tianqi.plankton.system.mapper.AttachDao;
import top.tianqi.plankton.attach.plugin.StoragePlugin;
import top.tianqi.plankton.system.service.AttachService;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigInteger;
import java.util.*;

/**
 * 文件服务层实现
 * @author Wukh
 * @create 2021-01-12
 */
@Service(value = "attachServiceImpl")
public class AttachServiceImpl extends BaseServiceImpl<AttachDao, Attach> implements AttachService {

    @Value("${attachUploadBase}")
    private String uploadBase;

    @Value("${attachPlugin}")
    private String attachPlugin;

    @Value("${imageUploadPath}")
    private String imageUploadPath;

    @Value("${fileUploadPath}")
    private String fileUploadPath;

    @Autowired
    private AttachDao attachDao;

    @Resource(name = "taskExecutor")
    private TaskExecutor taskExecutor;

    @Resource
    private Map<String, StoragePlugin> storagePluginMap = new HashMap<String, StoragePlugin>();

    @Override
    public List<Attach> uploadFile(Collection<MultipartFile> files, Integer dataType) {
        List<Attach> list = new ArrayList<Attach>();
        for (MultipartFile multipartFile : files) {
            if (multipartFile != null && !multipartFile.isEmpty()) {
                try {
                    String uploadPath = fileUploadPath + DateUtil.format(new Date(), "yyyyMMdd") + "/" + UUID.randomUUID().toString() + "/";
                    String uuid = UUID.randomUUID().toString();
                    String returnSourcePath  = uploadPath + uuid + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
                    String sourcePath = uploadBase + returnSourcePath;

                    //创建上传文件目录
                    File destFile = new File(uploadBase + uploadPath);
                    if(!destFile.exists()){
                        destFile.mkdirs();
                    }
                    StoragePlugin storagePlugin = storagePluginMap.get(attachPlugin);
                    if (storagePlugin != null) {
                        File tempFile = new File(System.getProperty("java.io.tmpdir") + "/upload_" + UUID.randomUUID() + ".tmp");
                        if (!tempFile.getParentFile().exists()) {
                            tempFile.getParentFile().mkdirs();
                        }
                        multipartFile.transferTo(tempFile);
                        addTask(sourcePath, tempFile, multipartFile.getContentType());
                        Attach attach = new Attach();
                        attach.setCreateTime(new Date());
                        attach.setModifyTime(new Date());
                        attach.setDataType(dataType);
                        attach.setExt(FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
                        attach.setFileName(multipartFile.getName());
                        attach.setFileSize(multipartFile.getSize());
                        attach.setMime("image");
                        attach.setPath(returnSourcePath);
                        attach.setOriginalName(multipartFile.getOriginalFilename());
                        attachDao.insert(attach);
                        //attach.setId(id);
                        list.add(attach);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    /**
     * 添加文件处理任务
     * @param sourcePath 原文件上传路径
     * @param tempFile 原临时文件
     * @param contentType 原文件类型
     */
    private void addTask(final String sourcePath, final File tempFile, final String contentType) {
        try {
            taskExecutor.execute(new Runnable() {
                public void run() {
                    StoragePlugin storagePlugin = storagePluginMap.get(attachPlugin);
                    if (storagePlugin != null) {
                        try {
                            storagePlugin.upload(sourcePath, tempFile, contentType);
                        } finally {
                            FileUtils.deleteQuietly(tempFile);
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Attach> uploadImage(Collection<MultipartFile> values, Integer dataType) {

        return null;
    }

    @Override
    public void delete(BigInteger recordId, Integer dataType) {

    }

    @Override
    public List<Attach> getFileList(Long recordId, Integer dataType) {
        return attachDao.findList(recordId, String.valueOf(dataType));
    }
}
