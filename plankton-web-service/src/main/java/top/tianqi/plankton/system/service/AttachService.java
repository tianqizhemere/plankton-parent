package top.tianqi.plankton.system.service;

import org.springframework.web.multipart.MultipartFile;
import top.tianqi.plankton.common.base.service.BaseService;
import top.tianqi.plankton.system.entity.Attach;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

/**
 * 文件服务层接口
 * @author Wukh
 * @create 2021-01-12
 */
public interface AttachService extends BaseService<Attach> {

    /**
     * 上传文件
     * @param values
     * @param dataType
     * @return
     */
    List<Attach> uploadFile(Collection<MultipartFile> values, Integer dataType);


    List<Attach> uploadImage(Collection<MultipartFile> values, Integer dataType);

    /**
     * 删除文件
     * @param recordId 记录ID
     * @param dataType 数据标识来源
     */
    void delete(BigInteger recordId, Integer dataType);

    List<Attach> getFileList(Long recordId, Integer dataType);
}
