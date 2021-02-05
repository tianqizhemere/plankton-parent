package top.tianqi.plankton.system.service.impl;


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
import top.tianqi.plankton.system.mapper.AttachMapper;
import top.tianqi.plankton.system.mapper.VersionMapper;
import top.tianqi.plankton.system.service.AttachService;
import top.tianqi.plankton.system.service.VersionService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 版本检测服务层实现
 * @author Wukh
 * @create 2021-01-08
 */
@Service(value = "versionServiceImpl")
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
                        List<Attach> fileList = attachService.getFileList(versionInfo.getId(), AttachDataTypeEnum.N9760.getCode());
                        if (!CollectionUtils.isEmpty(fileList)) {
                            Attach attach = fileList.get(0);
                            versionInfo.setDownloadUrl(attach.getPath());
                            versionMapper.selectById(versionInfo);
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
    public Page<VersionInfo>  getPage(String name, String dictId, Page<VersionInfo> page) {
        List<VersionInfo> list = versionMapper.findList(name, dictId, page);
        page.setRecords(list);
        return page;
    }

    @Override
    public Integer checkIsExist(String model, String versionCode) {
        return versionMapper.checkIsExist(model, versionCode);
    }

    @Override
    public boolean save(VersionInfo versionInfo) {
        List<Long> versionIds = new ArrayList<>();
        if (versionInfo.getModel() != null) {
            for (String modelId : versionInfo.getModel().split(",")) {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("model", modelId);
                paramMap.put("type", 1);
                List<VersionInfo> versionInfos = versionMapper.selectByMap(paramMap);
                if (!CollectionUtils.isEmpty(versionInfos)) {
                    for (VersionInfo info : versionInfos) {
                        // 之前版本设置为历史版本
                        info.setType(0);
                        info.setModifyTime(new Date());
                        versionMapper.updateById(info);
                    }
                }
                versionInfo.setModel(modelId.toUpperCase());
                versionInfo.setType(1);
                super.save(versionInfo);
                versionIds.add(versionInfo.getId());
            }
        }
        if (!StringUtils.isEmpty(versionInfo.getAttachIds())) {
            for (String attachId : versionInfo.getAttachIds().split(",")) {
                Attach attach = attachMapper.selectById(new Long(attachId));
                if (attach != null) {
                    if (!CollectionUtils.isEmpty(versionIds)) {
                        for (Long versionId : versionIds) {
                            attach.setRecordId(versionId);
                            attach.setDataType(AttachDataTypeEnum.N9760.getCode());
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

    /**
     * 对比字符串版本号的大小，返回1则clientVersion大于baseVersion，返回-1则clientVersion小于baseVersion，返回0则clientVersion等于baseVersion
     * @param clientVersion 客户端版本
     * @param baseVersion 数据库版本
     */
    private static Map<String, Object> compareVersion(String clientVersion, String baseVersion) throws Exception {
        Map<String, Object> paramMap = new HashMap<>();

        if (clientVersion == null || baseVersion == null) {
            throw new BusinessException("compareVersion error:illegal params.");
        }
        String[] clientVersionArray = clientVersion.split("\\.");//注意此处为正则匹配，不能用.；
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
        if (diff != 0){
            // 大版本更新
            // 数据表是否为大版本更新
            paramMap.put("bigVersionFlag", true);
            paramMap.put("result", diff);
        } else {
            diff = clientVersionArray.length - baseVersionArray2.length;
            // 小版本更新
            paramMap.put("bigVersionFlag", false);
            paramMap.put("result", diff);
        }
        return paramMap;
    }
}
