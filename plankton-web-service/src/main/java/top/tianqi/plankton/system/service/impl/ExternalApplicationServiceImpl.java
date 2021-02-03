package top.tianqi.plankton.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.tianqi.plankton.common.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.system.entity.Attach;
import top.tianqi.plankton.system.entity.ExternalApplication;
import top.tianqi.plankton.system.enumeration.VersionTypeEnum;
import top.tianqi.plankton.system.mapper.AttachMapper;
import top.tianqi.plankton.system.mapper.ExternalApplicationMapper;
import top.tianqi.plankton.system.service.ExternalApplicationService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 外置应用服务层实现
 * @author Wukh
 * @create 2021-02-01
 */
@Service(value = "externalApplicationServiceImpl")
public class ExternalApplicationServiceImpl extends BaseServiceImpl<ExternalApplicationMapper, ExternalApplication> implements ExternalApplicationService {

    @Autowired
    private ExternalApplicationMapper externalApplicationMapper;

    @Autowired
    private AttachMapper attachMapper;

    @Override
    public Page<ExternalApplication> getPage(String name, Page<ExternalApplication> page) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (name != null && name != "") {
            queryWrapper.like("name", name);
        }
        return externalApplicationMapper.selectPage(page, queryWrapper);
    }

    @Override
    public ExternalApplication findByCode(Integer code) {
        return externalApplicationMapper.findByCode(code);
    }

    @Override
    public boolean save(ExternalApplication externalApplication) {
        // 覆盖旧的版本
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("type", VersionTypeEnum.THE_LATEST_VERSION.getCode());
        paramMap.put("external_type", externalApplication.getExternalType());
        List<ExternalApplication> externalApplications = externalApplicationMapper.selectByMap(paramMap);
        if (!CollectionUtils.isEmpty(externalApplications)) {
            ExternalApplication baseExternalApplication = externalApplications.get(0);
            baseExternalApplication.setType(VersionTypeEnum.HISTORIC_VERSION.getCode());
            baseExternalApplication.setModifyTime(new Date());
            externalApplicationMapper.updateById(baseExternalApplication);
        }
        boolean result = super.save(externalApplication);
        if (externalApplication.getAttachIds() != null) {
            for (String attachId : externalApplication.getAttachIds().split(",")) {
                Attach attach = attachMapper.selectById(attachId);
                if (attach != null) {
                    attach.setRecordId(externalApplication.getId());
                    attachMapper.updateById(attach);
                    externalApplication.setDownloadUrl(attach.getPath());
                    externalApplicationMapper.updateById(externalApplication);
                }
            }
        }
        return result;
    }
}
