package top.tianqi.plankton.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.common.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.system.entity.Dictionary;
import top.tianqi.plankton.system.mapper.DictionariesMapper;
import top.tianqi.plankton.system.service.DictionariesService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典服务层实现
 * @author Wukh
 * @create 2021-01-24
 */
@Service
public class DictionariesServiceImpl extends BaseServiceImpl<DictionariesMapper, Dictionary> implements DictionariesService {

    @Autowired
    private DictionariesMapper dictionariesMapper;

    @Override
    public List<Dictionary> findTree() {
        List<Dictionary> tempDictionaries = new ArrayList<>();
        List<Dictionary> dictionaries = dictionariesMapper.selectList(null);
        for (Dictionary dictionary : dictionaries) {
            if (dictionary.getParentId() == null || dictionary.getParentId() == 0) {
                dictionary.setLevel(0);
                tempDictionaries.add(dictionary);
            }
        }
        findChildren(tempDictionaries, dictionaries);
        return tempDictionaries;
    }

    @Override
    public List<String> findNameById(String dictId) {
        if (!StringUtils.isNotBlank(dictId)) {
            return null;
        }
        LambdaQueryWrapper<Dictionary> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.or().eq(StringUtils.isNotBlank(dictId), Dictionary::getParentId, dictId).or().eq(StringUtils.isNotBlank(dictId), Dictionary::getId, dictId);
        List<Dictionary> dictionaries = dictionariesMapper.selectList(lambdaQueryWrapper);
        return dictionaries.stream().map(Dictionary::getName).collect(Collectors.toList());
    }


    /**
     * 寻找子节点对象
     * @param parentDictionaries 父节点列表
     * @param childrens 数据列表
     */
    private void findChildren(List<Dictionary> parentDictionaries, List<Dictionary> childrens) {
        for (Dictionary parent : parentDictionaries) {
            List<Dictionary> children = new ArrayList<>();
            for (Dictionary childrenDictionaries : childrens) {
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
