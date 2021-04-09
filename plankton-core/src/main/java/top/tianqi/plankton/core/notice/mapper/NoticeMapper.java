package top.tianqi.plankton.core.notice.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import top.tianqi.plankton.core.notice.entity.Notice;

import java.util.List;

/**
 * 通知Mapper
 * @author Wukh
 * @create 2021-02-21
 */
public interface NoticeMapper extends BaseMapper<Notice> {

    /**
     * 查询用户接收到的通知
     * @param queryWrapper 查询条件
     * @return List<Notice> 通知列表
     */
    List<Notice> findUserNotice(@Param(Constants.WRAPPER) QueryWrapper<Notice> queryWrapper);
}
