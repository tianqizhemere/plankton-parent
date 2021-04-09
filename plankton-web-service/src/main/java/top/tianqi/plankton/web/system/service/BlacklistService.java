package top.tianqi.plankton.web.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.tianqi.plankton.web.common.service.BaseService;
import top.tianqi.plankton.core.system.entity.Blacklist;

/**
 * 黑名单服务层接口
 * @author Wukh
 * @create 2021-04-02
 */
public interface BlacklistService extends BaseService<Blacklist> {

    /**
     * 加载数据列表
     * @param ip 请求ip
     * @param uri 请求URI
     * @param method 请求方法
     * @param page 分页对象
     */
    IPage<Blacklist> findPage(String ip, String uri, String method, Page<Blacklist> page);
}
