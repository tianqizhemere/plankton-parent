package top.tianqi.plankton.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;
import top.tianqi.plankton.system.entity.Dictionaries;

import java.util.List;

/**
 * 字典持久层接口
 * @author Wukh
 * @create 2021-01-24
 */
public interface DictionariesMapper extends BaseMapper<Dictionaries> {

    List<Dictionaries> findPage(@Param("name") String name, @Param("page") Page<Dictionaries> page);
}
