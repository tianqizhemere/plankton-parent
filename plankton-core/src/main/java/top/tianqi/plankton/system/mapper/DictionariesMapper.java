package top.tianqi.plankton.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import top.tianqi.plankton.system.entity.Dictionary;

import java.util.List;

/**
 * 字典持久层接口
 * @author Wukh
 * @create 2021-01-24
 */
public interface DictionariesMapper extends BaseMapper<Dictionary> {

    /**
     * 分页查询数据字典
     * @param name 字典名称
     * @param page 分页对象
     * @return List<Dictionaries> 数据列表
     */
    List<Dictionary> findPage(@Param("name") String name, @Param("page") Page<Dictionary> page);
}
