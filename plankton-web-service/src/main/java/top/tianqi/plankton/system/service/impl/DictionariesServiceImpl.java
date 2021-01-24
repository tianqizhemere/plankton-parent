package top.tianqi.plankton.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.common.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.common.utils.DateUtils;
import top.tianqi.plankton.common.utils.PageResult;
import top.tianqi.plankton.system.entity.Dictionaries;
import top.tianqi.plankton.system.mapper.DictionariesMapper;
import top.tianqi.plankton.system.service.DictionariesService;

import java.util.List;

/**
 * 字典服务层实现
 * @author Wukh
 * @create 2021-01-24
 */
@Service(value = "dictionariesServiceImpl")
public class DictionariesServiceImpl extends BaseServiceImpl<DictionariesMapper, Dictionaries> implements DictionariesService {

    @Autowired
    private DictionariesMapper dictionariesMapper;

    @Override
    public PageResult getPage(String name, Page<Dictionaries> page) {
        List<Dictionaries> list = dictionariesMapper.findPage(name, page);
        return new PageResult(page.getCurrent(), page.getSize(),  page.getTotal() , page.getPages(), list);
    }
}
