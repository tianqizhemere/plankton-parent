package top.tianqi.plankton.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.tianqi.plankton.common.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.common.constant.Constant;
import top.tianqi.plankton.common.exception.BusinessException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
                // 获取服务器最新版本
                QueryWrapper<VersionInfo> newVersionWrapper = new QueryWrapper<>();
                newVersionWrapper.lambda().eq(VersionInfo::getType, 1).eq(VersionInfo::getModel, model);
                List<VersionInfo> versionInfos = versionMapper.selectList(newVersionWrapper);
                if (!CollectionUtils.isEmpty(versionInfos)) {
                    // 最新版本
                    VersionInfo newVersionInfo = versionInfos.get(0);
                    // 数据库最大版本号
                    String newStartVersion = newVersionInfo.getVersionCode().substring(0, 1);
                    // 客户端最大版本号
                    String startVersion = currentVersion.substring(0, 1);
                    BigDecimal newStartVersionNum = new BigDecimal(newStartVersion);
                    BigDecimal startVersionNum = new BigDecimal(startVersion);
                    if(newStartVersionNum.compareTo(startVersionNum) == 1){
                        // 返回最新版本
                        newStartVersionNum = newStartVersionNum.add(new BigDecimal("0.0"));
                        newVersionWrapper = new QueryWrapper<>();
                        newVersionWrapper.lambda().eq(VersionInfo::getVersionCode, newStartVersionNum).eq(VersionInfo::getModel, model);
                        versionInfos = versionMapper.selectList(newVersionWrapper);
                        if (!CollectionUtils.isEmpty(versionInfos)) {
                            // 最新版本
                            VersionInfo baseVersion = versionInfos.get(0);
                            baseVersion.setMaxVersion(Boolean.TRUE);
                            baseVersion.setIsUpdate(Boolean.TRUE);
                            return baseVersion;
                        }
                    }
                }
                //客户端版本号
                BigDecimal clientVersion = new BigDecimal(currentVersion );
                // 更新提升0.1个大版本号
                BigDecimal promoteVersion = new BigDecimal("0.1");
                promoteVersion = clientVersion.add(promoteVersion);
                // 检测是否有小版本更新
                QueryWrapper<VersionInfo> checkVersionWrapper = new QueryWrapper<>();
                checkVersionWrapper.lambda().eq(VersionInfo::getVersionCode, promoteVersion).eq(VersionInfo::getModel, model);
                List<VersionInfo> list = versionMapper.selectList(checkVersionWrapper);
                if (!CollectionUtils.isEmpty(list)) {
                    VersionInfo versionInfo = list.get(0);
                    // 检测是否为大版本更新, 获取客户端版本号和数据库版本号的第一位型号进行对比
                    String clientVersionStart = clientVersion.toString().substring(0, 1);
                    String updateCodeStart = promoteVersion.toString().substring(0, 1);
                    if (!Objects.equals(updateCodeStart, clientVersionStart) && promoteVersion.toString().substring(2).equals("0")){
                        versionInfo.setMaxVersion(Boolean.TRUE);
                        versionInfo.setIsUpdate(Boolean.TRUE);
                        return versionInfo;
                    }
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
        LambdaQueryWrapper<VersionInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(VersionInfo::getModel, model).eq(VersionInfo::getVersionCode, version);
        List<VersionInfo> versionInfos = versionMapper.selectList(lambdaQueryWrapper);
        if (!CollectionUtils.isEmpty(versionInfos)) {
            return versionInfos.get(0);
        }
        return new VersionInfo();
    }

    @Override
    public Page<VersionInfo> getPage(String name, List<String> modelNames, Page<VersionInfo> page) {
        LambdaQueryWrapper<VersionInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(!StringUtils.isEmpty(name), VersionInfo::getVersionCode, name);
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
        // 版本型号主键id
        List<Long> versionIds = new ArrayList<>();
        if (versionInfo.getModel() != null) {
            for (String model : versionInfo.getModel().split(",")) {
                LambdaQueryWrapper<VersionInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(VersionInfo::getModel, model);
                lambdaQueryWrapper.eq(VersionInfo::getType, VersionTypeEnum.THE_LATEST_VERSION.getCode());
                VersionInfo baseVersionInfo = versionMapper.selectOne(lambdaQueryWrapper);
                if (baseVersionInfo != null) {
                    // 之前版本设置为历史版本  0-
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
                            VersionInfo baseVersion = versionMapper.selectById(versionId);
                            attach.setRecordId(versionId);
                            attach.setDataType(AttachDataTypeEnum.MODEL_APPLICATION.getCode());
                            attachMapper.insert(attach);
                            baseVersion.setDownloadUrl(attach.getPath());
                            versionMapper.updateById(baseVersion);
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

    /**
     * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0
     * @param clientVersion 客户端版本
     * @param baseVersion 数据库版本
     */
    private static int compareVersion(String clientVersion, String baseVersion) throws BusinessException {
        if (clientVersion == null || baseVersion == null) {
            throw new BusinessException("compareVersion error:illegal params.");
        }
        String[] clientVersionArray = clientVersion.split("\\.");
        String[] baseVersionArray2 = baseVersion.split("\\.");
        int idx = 0;
        int minLength = Math.min(clientVersionArray.length, baseVersionArray2.length);//取最小长度值
        int diff = 0;
        while (idx < minLength
                && (diff = clientVersionArray[idx].length() - baseVersionArray2[idx].length()) == 0//先比较长度
                && (diff = clientVersionArray[idx].compareTo(baseVersionArray2[idx])) == 0) {//再比较字符
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : clientVersionArray.length - baseVersionArray2.length;
        return diff;
    }
}
