package top.tianqi.plankton.util.excel;

import cn.hutool.core.date.DateUtil;
import net.sf.jxls.transformer.XLSTransformer;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;
import top.tianqi.plankton.core.common.utils.PropertiesUtil;

import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Jxls导入导出Excel工具类
 * @author Wukh
 * @create 2021-04-23
 */
public final class JxlsUtils {

    /** excel导出模板路径 */
    public static final String TEMPLATE_PATH = ClassUtils.getDefaultClassLoader().getResource("/templates/export/template").getPath();
    /** excel导出保存路径 */
    public static String SAVE_PATH;

    public static String uploadBase;

    public static String fileUploadPath;

    static {
        PropertiesUtil.readProperties("application.properties");
        uploadBase = PropertiesUtil.getProperty("attachUploadBase");
        fileUploadPath = PropertiesUtil.getProperty("fileUploadPath");
    }

    private JxlsUtils(){ }

    /**
     * 导出 Excel
     *
     * @param source 模板文件路径
     * @param target 目标文件（文件下载时的路径）
     * @param beansMaps
     * @return 下载文件路径
     * @throws Exception
     */
    public static String export(String source, String target, Map<String, Object> beansMaps) throws Exception {
        String fileName = target.substring(0, target.lastIndexOf("."));

        String[] sourceArrays = source.split("\\.");
        source = TEMPLATE_PATH + File.separator + source;
        target = SAVE_PATH + File.separator + sourceArrays[0] + "-" + System.currentTimeMillis() + "." + sourceArrays[1];
        String uploadPath = fileUploadPath + DateUtil.format(new Date(), "yyyyMMdd") + "/" + UUID.randomUUID().toString() + "/";
        String uuid = UUID.randomUUID().toString();
        String returnSourcePath = uploadPath + uuid + "." + fileName;
        String sourcePath = uploadBase + returnSourcePath;
        File file = new File(SAVE_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(source, beansMaps, target);
        return target;
    }

    /**
     * 存储导入模板文件
     *
     * @param file         模板文件对象
     * @param templateName 模板文件名
     * @return 文件存储路径
     */
    public static String uploadTemplate(MultipartFile file, String templateName) throws Exception {
        File targetFile = template(templateName);
        saveFile(file, targetFile);
        return targetFile.getAbsolutePath();
    }

    /**
     * 模板文件对象
     *
     * @param templateName 模板名称
     * @return File 对象
     */
    public static File template(String templateName) {
        return new File(TEMPLATE_PATH, templateName);
    }

    /**
     * 根据模板名称获取模板路径
     *
     * @param templateName
     * @return String
     */
    public static String getTemplatePath(String templateName) {
        return template(templateName).getAbsolutePath();
    }

    /**
     * 文件存储
     *
     * @param file       原始文件对象
     * @param targetFile 文件存储对象
     */
    private static void saveFile(MultipartFile file, File targetFile) throws Exception {
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        file.transferTo(targetFile);
    }

    /**
     * 文件存储
     *
     * @param file 临时文件对象
     * @return String
     */
    public static String uploadTemp(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        File targetFile = new File(SAVE_PATH, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        file.transferTo(targetFile);
        return targetFile.getAbsolutePath();
    }

}
