package top.tianqi.plankton.system.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import top.tianqi.plankton.system.entity.ExceptionLog;

/**
 * 操作日志异常信息持久层接口
 * @author Wukh
 * @create 2021-01-08
 */
public interface ExceptionLogMapper extends BaseMapper<ExceptionLog> {
    Page<ExceptionLog> findPage(Page<ExceptionLog> page,@Param(Constants.WRAPPER) QueryWrapper<ExceptionLog> queryWrapper);
}
