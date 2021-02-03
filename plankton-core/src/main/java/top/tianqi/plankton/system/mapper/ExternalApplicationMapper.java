package top.tianqi.plankton.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import top.tianqi.plankton.system.entity.ExternalApplication;

import java.util.List;

/**
 * 外置应用持久层接口
 * @author Wukh
 * @create 2021-02-01
 */
public interface ExternalApplicationMapper extends BaseMapper<ExternalApplication> {

    List<ExternalApplication> findPage(@Param("name") String name, @Param("page") Page<ExternalApplication> page);

    ExternalApplication findByCode(Integer code);
}
