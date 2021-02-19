package top.tianqi.plankton.system.service;

import top.tianqi.plankton.common.base.service.BaseService;
import top.tianqi.plankton.system.entity.Dictionary;

import java.util.List;

/**
 * 字典服务层接口
 * @author Wukh
 * @create 2021-01-24
 */
public interface DictionariesService extends BaseService<Dictionary> {

    /**
     * 获取树形结构数字字典
     * @return List<Dictionaries>
     */
    List<Dictionary> findTree();

    /**
     * 根据字典id查询名称列表
     * @param dictId 字典唯一标识
     * @return List<String> 名称列表
     */
    List<String> findNameById(String dictId);
}
