package top.tianqi.plankton.web.system.service.impl;

import cn.hutool.core.date.DateUtil;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.tianqi.plankton.attach.plugin.StoragePlugin;
import top.tianqi.plankton.config.ServerConfig;
import top.tianqi.plankton.core.common.exception.BusinessException;
import top.tianqi.plankton.core.system.entity.Attach;
import top.tianqi.plankton.core.system.mapper.AttachMapper;
import top.tianqi.plankton.web.common.service.impl.BaseServiceImpl;
import top.tianqi.plankton.web.system.service.AttachService;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigInteger;
import java.net.ConnectException;
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

    /** 转换文件后缀名 */
    public static final String FILE_SUFFIX_PDF = ".pdf";

    /** OpenOffice连接地址 */
    private static final String OPEN_OFFICE_URL = "127.0.0.1";

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

    @Override
    public String transitionFile(String srcPath) throws ConnectException {
        // 完整路径
        String filePath = uploadBase + srcPath;
        File inputFile = new File(filePath);
        if (!inputFile.exists()) {
            throw new BusinessException("源文件不存在！");
        }
        // 输出路径
        String desPath = srcPath.substring(0, srcPath.lastIndexOf(".")) + FILE_SUFFIX_PDF;
        // 输出文件目录是否存在
        File outputFile = new File(uploadBase + desPath);
        if (outputFile.exists()) {
            return desPath;
        }
        // 连接openOffice服务
        OpenOfficeConnection connection = new SocketOpenOfficeConnection(OPEN_OFFICE_URL, 8100);
        connection.connect();
        // 转换word到PDF
        DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
        try {
            converter.convert(inputFile, outputFile);
            // 关闭连接
            connection.disconnect();
            return ServerConfig.getUrl() + desPath;
        } catch (IllegalArgumentException e) {
            connection.disconnect();
            e.printStackTrace();
        }
        return null;
    }
}
