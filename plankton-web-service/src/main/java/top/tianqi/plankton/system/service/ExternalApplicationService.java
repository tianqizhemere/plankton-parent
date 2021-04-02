package top.tianqi.plankton.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.tianqi.plankton.common.service.BaseService;
import top.tianqi.plankton.system.entity.ExternalApplication;

/**
 * 外置应用服务层接口
 *
 * @author Wukh
 * @create 2021-02-01
 */
public interface ExternalApplicationService extends BaseService<ExternalApplication> {


    Page<ExternalApplication> getPage(String name, Page<ExternalApplication> page);

    /**
     * 根据code值获取最新文件信息
     *
     * @param type 文件类型,详见ExternalTypeEnum枚举类
     * @return ExternalApplication
     */
    ExternalApplication findByCode(Integer type);
}
