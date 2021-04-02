package top.tianqi.plankton.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.common.service.impl.BaseServiceImpl;
import top.tianqi.plankton.system.entity.Blacklist;
import top.tianqi.plankton.system.mapper.BlacklistMapper;
import top.tianqi.plankton.system.service.BlacklistService;

/**
 * @author Wukh
 * @create 2021-04-02
 */
@Service
public class BlacklistServiceImpl extends BaseServiceImpl<BlacklistMapper, Blacklist> implements BlacklistService {

    @Autowired
    private BlacklistMapper blacklistMapper;

    @Override
    public IPage<Blacklist> findPage(String ip, String uri, String method, Page<Blacklist> page) {
        QueryWrapper<Blacklist> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(ip), "b.ip", ip);
        queryWrapper.eq(StringUtils.isNotBlank(uri), "b.uri", uri);
        queryWrapper.eq(StringUtils.isNotBlank(method), "b.method", method);
        return blacklistMapper.selectPage(page, queryWrapper);
    }
}
