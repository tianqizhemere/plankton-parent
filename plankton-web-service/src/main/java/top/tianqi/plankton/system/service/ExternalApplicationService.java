package top.tianqi.plankton.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import top.tianqi.plankton.common.base.service.BaseService;
import top.tianqi.plankton.common.utils.PageResult;
import top.tianqi.plankton.system.entity.ExternalApplication;

/**
 * 外置应用服务层接口
 * @author Wukh
 * @create 2021-02-01
 */
public interface ExternalApplicationService extends BaseService<ExternalApplication> {


    PageResult getPage(String name, Page<ExternalApplication> page);

    /**
     * 根据code值获取最新文件信息
     * @param code code值
     * @return ExternalApplication
     */
    ExternalApplication findByCode(Integer code);
}
