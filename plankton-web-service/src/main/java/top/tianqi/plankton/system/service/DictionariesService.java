package top.tianqi.plankton.system.service;

import top.tianqi.plankton.common.base.service.BaseService;
import top.tianqi.plankton.system.entity.Dictionaries;

import java.util.List;

/**
 * 字典服务层接口
 * @author Wukh
 * @create 2021-01-24
 */
public interface DictionariesService extends BaseService<Dictionaries> {

    /**
     * 获取树形结构数字字典
     * @return List<Dictionaries>
     */
    List<Dictionaries> findTree();

}
