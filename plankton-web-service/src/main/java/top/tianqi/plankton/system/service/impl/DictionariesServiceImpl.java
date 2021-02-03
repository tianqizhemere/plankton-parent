package top.tianqi.plankton.system.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.common.base.service.impl.BaseServiceImpl;
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
    public List<Dictionaries> findTree() {
        List<Dictionaries> tempDictionaries = new ArrayList<>();
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
     * @param parentDictionaries 父节点列表
     * @param childrens 数据列表
     */
    private void findChildren(List<Dictionaries> parentDictionaries, List<Dictionaries> childrens) {
        for (Dictionaries parent : parentDictionaries) {
            List<Dictionaries> children = new ArrayList<>();
            for (Dictionaries childrenDictionaries : childrens) {
                if (parent.getId() != null && parent.getId().equals(childrenDictionaries.getParentId())) {
                    childrenDictionaries.setParentName(parent.getName());
                    childrenDictionaries.setLevel(parent.getLevel() + 1);
                    children.add(childrenDictionaries);
                }
            }
            parent.setChildren(children.size() == 0 ? null : children);
            findChildren(children, childrens);
        }
    }
}
