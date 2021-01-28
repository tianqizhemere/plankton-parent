package top.tianqi.plankton.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.common.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.common.utils.PageResult;
import top.tianqi.plankton.system.entity.Dictionaries;
import top.tianqi.plankton.system.mapper.DictionariesMapper;
import top.tianqi.plankton.system.service.DictionariesService;

import java.util.ArrayList;
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

    @Override
    public List<Dictionaries> findTree() {
        List<Dictionaries> tempDictionaries = new ArrayList<>();;
        List<Dictionaries> dictionaries = dictionariesMapper.selectList(null);
        for (Dictionaries dictionary : dictionaries) {
            if (dictionary.getParentId() == null || dictionary.getParentId() == 0) {
                dictionary.setLevel(0);
                tempDictionaries.add(dictionary);
            }
        }
        findChildren(tempDictionaries, dictionaries);
        return tempDictionaries;
    }


    /**
     * 寻找子节点对象
     * @param tempDictionaries 父节点列表
     * @param dictionaries 数据列表
     */
    private void findChildren(List<Dictionaries> tempDictionaries, List<Dictionaries> dictionaries) {
        for (Dictionaries temp : tempDictionaries) {
            List<Dictionaries> children = new ArrayList<>();
            for (Dictionaries dept : dictionaries) {
                if (temp.getId() != null && temp.getId().equals(dept.getParentId())) {
                    dept.setParentName(dept.getName());
                    dept.setLevel(temp.getLevel() + 1);
                    children.add(dept);
                }
            }
            temp.setChildren(children);
            findChildren(children, dictionaries);
        }
    }
}
