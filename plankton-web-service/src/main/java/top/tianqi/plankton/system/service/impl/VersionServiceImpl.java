package top.tianqi.plankton.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.tianqi.plankton.common.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.common.constant.Constant;
import top.tianqi.plankton.system.entity.Attach;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.entity.VersionInfo;
import top.tianqi.plankton.system.enumeration.AttachDataTypeEnum;
import top.tianqi.plankton.system.enumeration.VersionTypeEnum;
import top.tianqi.plankton.system.mapper.AttachMapper;
import top.tianqi.plankton.system.mapper.VersionMapper;
import top.tianqi.plankton.system.service.AttachService;
import top.tianqi.plankton.system.service.VersionService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 版本检测服务层实现
 * @author Wukh
 * @create 2021-01-08
 */
@Service
public class VersionServiceImpl extends BaseServiceImpl<VersionMapper, VersionInfo> implements VersionService {

    @Autowired
    private VersionMapper versionMapper;

    @Autowired
    private AttachMapper attachMapper;

    @Resource(name = "attachServiceImpl")
    private AttachService attachService;

    @Override
    public VersionInfo checkVersion(String currentVersion, String model){
        User currentUser = getCurrentUser();
        // 会员用户才可以更新版本
        if (Objects.equals(currentUser.getUserMode(), Constant.USER_MODE_POWERFUL)) {
            if (currentVersion != null) {
                BigDecimal versionF = new BigDecimal(currentVersion);
                // 更新提升0.1个版本号
                BigDecimal updateCode = new BigDecimal("0.1");
                // 更新的版本号
                BigDecimal code = versionF.add(updateCode);
                // 是否为大版本更新
                String versionStart = versionF.toString().substring(0, 1);
                String codeStart = code.toString().substring(0, 1);
                if (!Objects.equals(codeStart, versionStart) && code.toString().substring(2).equals("0")){
                    return new VersionInfo();
                }
                // 验证是否有最新版本
                List<VersionInfo> list = versionMapper.checkVersion(code.toString(), model);
                if (!CollectionUtils.isEmpty(list)) {
                    VersionInfo versionInfo = list.get(0);
                    if (versionInfo.getDownloadUrl() == null) {
                        List<Attach> fileList = attachService.getFileList(versionInfo.getId(), AttachDataTypeEnum.MODEL_APPLICATION.getCode());
                        if (!CollectionUtils.isEmpty(fileList)) {
                            Attach attach = fileList.get(0);
                            versionInfo.setDownloadUrl(attach.getPath());
                            versionMapper.updateById(versionInfo);
                        }
                    }
                    versionInfo.setIsUpdate(Boolean.TRUE);
                    return versionInfo;
                }
                return new VersionInfo();
            }
        }
        return new VersionInfo();
    }

    @Override
    public VersionInfo getVersionInfo(String version, String model) {
        Map<String, Object> paramMap = new HashMap<>(2);
        paramMap.put("model", model);
        paramMap.put("version_code", version);
        List<VersionInfo> versionInfos = versionMapper.selectByMap(paramMap);
        if (!CollectionUtils.isEmpty(versionInfos)) {
            return versionInfos.get(0);
        }
        return new VersionInfo();
    }

    @Override
    public Page<VersionInfo> getPage(String name, List<String> modelNames, Page<VersionInfo> page) {
        LambdaQueryWrapper<VersionInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(!StringUtils.isEmpty(name), VersionInfo::getModel, name);
        lambdaQueryWrapper.in(!CollectionUtils.isEmpty(modelNames), VersionInfo::getModel, modelNames);
        lambdaQueryWrapper.orderByDesc(VersionInfo::getCreateTime);
        return versionMapper.selectPage(page, lambdaQueryWrapper);
    }

    @Override
    public Integer checkIsExist(String model, String versionCode) {
        LambdaQueryWrapper<VersionInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(!StringUtils.isEmpty(model), VersionInfo::getModel, model);
        lambdaQueryWrapper.eq(!StringUtils.isEmpty(versionCode), VersionInfo::getVersionCode, versionCode);
        return versionMapper.selectCount(lambdaQueryWrapper);
    }

    @Override
    public boolean save(VersionInfo versionInfo) {
        // 更新型号主键id
        List<Long> versionIds = new ArrayList<>();
        if (versionInfo.getModel() != null) {
            for (String model : versionInfo.getModel().split(",")) {
                LambdaQueryWrapper<VersionInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(VersionInfo::getModel, model);
                lambdaQueryWrapper.eq(VersionInfo::getType, VersionTypeEnum.THE_LATEST_VERSION.getCode());
                VersionInfo baseVersionInfo = versionMapper.selectOne(lambdaQueryWrapper);
                if (baseVersionInfo != null) {
                    // 之前版本设置为历史版本
                    baseVersionInfo.setType(VersionTypeEnum.HISTORIC_VERSION.getCode());
                    versionMapper.updateById(baseVersionInfo);
                }
                versionInfo.setModel(model.toUpperCase());
                versionInfo.setType(VersionTypeEnum.THE_LATEST_VERSION.getCode());
                super.save(versionInfo);
                versionIds.add(versionInfo.getId());
            }
        }
        // 关联附件
        if (!StringUtils.isEmpty(versionInfo.getAttachIds())) {
            for (String attachId : versionInfo.getAttachIds().split(",")) {
                Attach attach = attachMapper.selectById(new Long(attachId));
                if (attach != null) {
                    if (!CollectionUtils.isEmpty(versionIds)) {
                        for (Long versionId : versionIds) {
                            attach.setRecordId(versionId);
                            attach.setDataType(AttachDataTypeEnum.MODEL_APPLICATION.getCode());
                            attachMapper.insert(attach);
                            versionInfo.setDownloadUrl(attach.getPath());
                            versionMapper.selectById(versionInfo);
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean updateById(VersionInfo versionInfo) {
        if (!StringUtils.isEmpty(versionInfo.getAttachIds())) {
            // 先清除之前关联的附件
            List<Attach> fileList = attachMapper.findList(versionInfo.getId(), String.valueOf(AttachDataTypeEnum.MODEL_APPLICATION.getCode()));
            if (!CollectionUtils.isEmpty(fileList)) {
                List<Long> attachIds = fileList.stream().map(Attach::getId).collect(Collectors.toList());
                attachMapper.deleteBatchIds(attachIds);
            }
            for (String attachId : versionInfo.getAttachIds().split(",")) {
                Attach attach = attachMapper.selectById(new Long(attachId));
                if (attach != null) {
                    attach.setRecordId(versionInfo.getId());
                    attach.setDataType(AttachDataTypeEnum.MODEL_APPLICATION.getCode());
                    attachMapper.insert(attach);
                    versionInfo.setDownloadUrl(attach.getPath());
                }
            }
        }
        return super.updateById(versionInfo);
    }
}
