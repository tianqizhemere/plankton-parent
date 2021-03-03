package top.tianqi.plankton.admin.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import top.tianqi.plankton.admin.vo.LoginStatisticsVO;

import java.util.List;

/**
 * @author Wukh
 * @create 2021-03-03
 */
public interface LoginStatisticsMapper extends BaseMapper<LoginStatisticsVO> {

    List<Integer> findCount(@Param(Constants.WRAPPER) QueryWrapper<LoginStatisticsVO> queryWrapper);
}
