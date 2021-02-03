package top.tianqi.plankton.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import top.tianqi.plankton.system.entity.Dictionaries;

import java.util.List;

/**
 * 字典持久层接口
 * @author Wukh
 * @create 2021-01-24
 */
public interface DictionariesMapper extends BaseMapper<Dictionaries> {

    /**
     * 分页查询数据字典
     * @param name
     * @param page
     * @return
     */
    List<Dictionaries> findPage(@Param("name") String name, @Param("page") Page<Dictionaries> page);
}
