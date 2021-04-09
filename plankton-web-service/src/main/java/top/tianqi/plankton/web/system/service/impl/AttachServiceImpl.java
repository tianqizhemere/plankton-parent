package top.tianqi.plankton.web.system.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.tianqi.plankton.attach.plugin.StoragePlugin;
import top.tianqi.plankton.web.common.service.impl.BaseServiceImpl;
import top.tianqi.plankton.core.system.entity.Attach;
import top.tianqi.plankton.core.system.mapper.AttachMapper;
import top.tianqi.plankton.web.system.service.AttachService;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigInteger;
import java.util.*;

/**
 * 文件服务层实现
 *
 * @author Wukh
 * @create 2021-01-12
 */
@Service
public class AttachServiceImpl extends BaseServiceImpl<AttachMapper, Attach> implements AttachService {

    @Value("${attachUploadBase}")
    private String uploadBase;

    @Value("${attachPlugin}")
    private String attachPlugin;

    @Value("${fileUploadPath}")
    private String fileUploadPath;

    @Autowired
    private AttachMapper attachMapper;

    @Resource(name = "taskExecutor")
    private TaskExecutor taskExecutor;

    @Resource
    private Map<String, StoragePlugin> storagePluginMap = new HashMap<>();

    @Override
    public List<Attach> uploadFile(Collection<MultipartFile> files, Integer dataType) {
        List<Attach> list = new ArrayList<>();
        for (MultipartFile multipartFile : files) {
            if (multipartFile != null && !multipartFile.isEmpty()) {
                try {
                    String uploadPath = fileUploadPath + DateUtil.format(new Date(), "yyyyMMdd") + "/" + UUID.randomUUID().toString() + "/";
                    String uuid = UUID.randomUUID().toString();
                    String returnSourcePath = uploadPath + uuid + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
                    String sourcePath = uploadBase + returnSourcePath;

                    //创建上传文件目录
                    File destFile = new File(uploadBase + uploadPath);
                    if (!destFile.exists()) {
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
                        attach.setDataType(dataType);
                        attach.setExt(FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
                        attach.setFileName(multipartFile.getName());
                        attach.setFileSize(multipartFile.getSize());
                        attach.setMime("application/zip");
                        attach.setPath(returnSourcePath);
                        attach.setOriginalName(multipartFile.getOriginalFilename());
                        attachMapper.insert(attach);
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
     *
     * @param sourcePath  原文件上传路径
     * @param tempFile    原临时文件
     * @param contentType 原文件类型
     */
    private void addTask(final String sourcePath, final File tempFile, final String contentType) {
        try {
            taskExecutor.execute(() -> {
                StoragePlugin storagePlugin = storagePluginMap.get(attachPlugin);
                if (storagePlugin != null) {
                    try {
                        storagePlugin.upload(sourcePath, tempFile, contentType);
                    } finally {
                        FileUtils.deleteQuietly(tempFile);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(BigInteger recordId, Integer dataType) {
        if (recordId == null) return;
        LambdaQueryWrapper<Attach> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Attach::getDataType, dataType);
        lambdaQueryWrapper.eq(Attach::getRecordId, recordId);
        attachMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public List<Attach> getFileList(Long recordId, Integer dataType) {
        if (recordId == null) return null;
        LambdaQueryWrapper<Attach> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Attach::getRecordId, recordId);
        lambdaQueryWrapper.eq(dataType != null, Attach::getDataType, dataType);
        return attachMapper.selectList(lambdaQueryWrapper);
    }
}
