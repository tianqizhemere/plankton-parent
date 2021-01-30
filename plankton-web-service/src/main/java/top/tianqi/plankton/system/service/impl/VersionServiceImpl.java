package top.tianqi.plankton.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.tianqi.plankton.base.entity.BaseEntity;
import top.tianqi.plankton.common.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.common.constant.Constant;
import top.tianqi.plankton.common.utils.PageResult;
import top.tianqi.plankton.system.entity.Attach;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.entity.VersionInfo;
import top.tianqi.plankton.system.enumeration.AttachDataTypeEnum;
import top.tianqi.plankton.system.mapper.AttachMapper;
import top.tianqi.plankton.system.mapper.VersionMapper;
import top.tianqi.plankton.system.service.AttachService;
import top.tianqi.plankton.system.service.VersionService;

import javax.annotation.Resource;
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
    private AttachMapper attachDao;

    @Resource(name = "attachServiceImpl")
    private AttachService attachService;

    @Override
    public VersionInfo checkVersion(String currentVersion, String model) throws Exception {
        User currentUser = getCurrentUser();
        if (Objects.equals(currentUser.getUserMode(), Constant.USER_MODE_POWERFUL)) {
            Map<String, Object> paramMap = new HashMap<>(3);
            paramMap.put("type", 1);
            paramMap.put("model", model);
            List<VersionInfo> versionInfos = versionMapper.selectByMap(paramMap);
            if (!CollectionUtils.isEmpty(versionInfos)) {
                VersionInfo versionInfo = versionInfos.get(0);
                if (currentVersion != null) {
                    int result = compareVersion(currentVersion, versionInfo.getVersionCode());
                    if (result < 0) {
                        List<Attach> fileList = attachService.getFileList(versionInfo.getId(), AttachDataTypeEnum.N9760.getCode());
                        if (!CollectionUtils.isEmpty(fileList)) {
                            Attach attach = fileList.get(0);
                            versionInfo.setDownloadUrl(attach.getPath());
                        }
                        versionInfo.setIsUpdate(Boolean.TRUE);
                        return versionInfo;
                    }
                    return versionInfo;
                }
            }
        }
        return new VersionInfo();
    }

    @Override
    public VersionInfo getVersionInfo(String version, String model) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("model", model);
        paramMap.put("version_code", version);
        List<VersionInfo> versionInfos = versionMapper.selectByMap(paramMap);
        if (!CollectionUtils.isEmpty(versionInfos)) {
            return versionInfos.get(0);
        }
        return new VersionInfo();
    }

    @Override
    public PageResult getPage(String name, Page<BaseEntity> page) {
        List<VersionInfo> list = versionMapper.findList(name, page);
        return new PageResult(page.getCurrent(), page.getSize(),  page.getTotal() , page.getPages(), list);
    }

    @Override
    public boolean insert(VersionInfo versionInfo) {
        List<Long> versionIds = new ArrayList<>();
        if (versionInfo.getModel() != null) {
            for (String modelId : versionInfo.getModel().split(",")) {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("model", modelId);
                paramMap.put("type", 1);
                List<VersionInfo> versionInfos = versionMapper.selectByMap(paramMap);
                if (!CollectionUtils.isEmpty(versionInfos)) {
                    for (VersionInfo info : versionInfos) {
                        // 之前版本设置为不升级版本
                        info.setType(0);
                        info.setModifyTime(new Date());
                        versionMapper.updateById(info);
                    }
                }
                versionInfo.setModel(modelId.toUpperCase());
                versionInfo.setType(1);
                super.insert(versionInfo);
                versionIds.add(versionInfo.getId());
            }
        }
        if (versionInfo.getAttachIds() != null) {
            for (String attachId : versionInfo.getAttachIds().split(",")) {
                Attach attach = attachDao.selectById(new Long(attachId));
                if (attach != null) {
                    if (!CollectionUtils.isEmpty(versionIds)) {
                        for (Long versionId : versionIds) {
                            attach.setRecordId(versionId);
                            attach.setDataType(AttachDataTypeEnum.N9760.getCode());
                            attachDao.insert(attach);
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0
     * @param clientVersion 客户端版本
     * @param baseVersion 数据库版本
     */
    private static int compareVersion(String clientVersion, String baseVersion) throws Exception {
        if (clientVersion == null || baseVersion == null) {
            throw new Exception("compareVersion error:illegal params.");
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
        diff = (diff != 0) ? diff : clientVersionArray.length - baseVersionArray2.length;
        return diff;
    }

    public static void main(String[] args) throws Exception {
        int i = compareVersion("1.2", "2.1");
        System.out.println(i);

        String result = "1.修复自定义BIX按键无效问题\n2.修复最近任务点击失效".replaceAll("\n", System.getProperty("line.separator"));
        System.out.println(result);
    }
}
