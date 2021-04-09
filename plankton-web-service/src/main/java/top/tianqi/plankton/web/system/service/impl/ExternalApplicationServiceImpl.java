package top.tianqi.plankton.web.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.tianqi.plankton.web.common.service.impl.BaseServiceImpl;
import top.tianqi.plankton.core.system.entity.Attach;
import top.tianqi.plankton.core.system.entity.ExternalApplication;
import top.tianqi.plankton.core.system.enumeration.AttachDataTypeEnum;
import top.tianqi.plankton.core.system.enumeration.VersionTypeEnum;
import top.tianqi.plankton.core.system.mapper.AttachMapper;
import top.tianqi.plankton.core.system.mapper.ExternalApplicationMapper;
import top.tianqi.plankton.web.system.service.ExternalApplicationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 外置应用服务层实现
 *
 * @author Wukh
 * @create 2021-02-01
 */
@Service
public class ExternalApplicationServiceImpl extends BaseServiceImpl<ExternalApplicationMapper, ExternalApplication> implements ExternalApplicationService {

    @Autowired
    private ExternalApplicationMapper externalApplicationMapper;

    @Autowired
    private AttachMapper attachMapper;

    @Override
    public Page<ExternalApplication> getPage(String name, Page<ExternalApplication> page) {
        LambdaQueryWrapper<ExternalApplication> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(name), ExternalApplication::getName, name);
        lambdaQueryWrapper.orderByDesc(ExternalApplication::getModifyTime);
        return externalApplicationMapper.selectPage(page, lambdaQueryWrapper);
    }

    @Override
    public ExternalApplication findByCode(Integer type) {
        if (type == null) return null;
        LambdaQueryWrapper<ExternalApplication> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ExternalApplication::getExternalType, type);
        lambdaQueryWrapper.eq(ExternalApplication::getType, VersionTypeEnum.THE_LATEST_VERSION.getCode());
        lambdaQueryWrapper.orderByDesc(ExternalApplication::getCreateTime);
        return externalApplicationMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public boolean save(ExternalApplication externalApplication) {
        // 覆盖最新的版本
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("type", VersionTypeEnum.THE_LATEST_VERSION.getCode());
        paramMap.put("external_type", externalApplication.getExternalType());
        List<ExternalApplication> externalApplications = externalApplicationMapper.selectByMap(paramMap);
        if (!CollectionUtils.isEmpty(externalApplications)) {
            ExternalApplication baseExternalApplication = externalApplications.get(0);
            baseExternalApplication.setType(VersionTypeEnum.HISTORIC_VERSION.getCode());
            externalApplicationMapper.updateById(baseExternalApplication);
        }
        boolean result = super.save(externalApplication);
        if (StringUtils.isNotBlank(externalApplication.getAttachIds())) {
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

    @Override
    public boolean updateById(ExternalApplication externalApplication) {
        if (StringUtils.isNotBlank(externalApplication.getAttachIds())) {
            LambdaQueryWrapper<Attach> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Attach::getRecordId, externalApplication.getId());
            lambdaQueryWrapper.eq(Attach::getDataType, AttachDataTypeEnum.EXTERNAL_APPLICATION.getCode());
            attachMapper.delete(lambdaQueryWrapper);

            for (String attachId : externalApplication.getAttachIds().split(",")) {
                Attach attach = attachMapper.selectById(attachId);
                if (attach != null) {
                    attach.setRecordId(externalApplication.getId());
                    attachMapper.updateById(attach);
                    externalApplication.setDownloadUrl(attach.getPath());
                }
            }
        }
        return super.updateById(externalApplication);
    }
}
