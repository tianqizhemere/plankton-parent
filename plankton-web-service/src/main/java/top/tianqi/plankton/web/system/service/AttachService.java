package top.tianqi.plankton.web.system.service;

import org.springframework.web.multipart.MultipartFile;
import top.tianqi.plankton.web.common.service.BaseService;
import top.tianqi.plankton.core.system.entity.Attach;

import java.math.BigInteger;
import java.net.ConnectException;
import java.util.Collection;
import java.util.List;

/**
 * 文件服务层接口
 *
 * @author Wukh
 * @create 2021-01-12
 */
public interface AttachService extends BaseService<Attach> {

    /**
     * 上传文件
     *
     * @param values
     * @param dataType
     * @return
     */
    List<Attach> uploadFile(Collection<MultipartFile> values, Integer dataType);

    /**
     * 删除文件
     *
     * @param recordId 记录ID
     * @param dataType 数据标识来源
     */
    void delete(BigInteger recordId, Integer dataType);

    /**
     * 根据关联id和文件类型获取文件列表
     *
     * @param recordId 数据关联id
     * @param dataType 文件类型
     * @return List<Attach> 文件列表
     */
    List<Attach> getFileList(Long recordId, Integer dataType);

    /**
     * 文件转换 word转PDF
     * @param srcPath 文件路径
     * @return 转换后的完整路径
     */
    String transitionFile(String srcPath) throws ConnectException;
}
