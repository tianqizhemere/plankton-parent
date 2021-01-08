package top.tianqi.plankton.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.tianqi.plankton.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.system.entity.Mobile;
import top.tianqi.plankton.system.entity.VersionInfo;
import top.tianqi.plankton.system.mapper.VersionDao;
import top.tianqi.plankton.system.service.VersionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 版本检测服务层实现
 * @author Wukh
 * @create 2021-01-08
 */
@Service(value = "versionServiceImpl")
public class VersionServiceImpl extends BaseServiceImpl<VersionDao, VersionInfo> implements VersionService {

    @Autowired
    private VersionDao versionDao;

    @Override
    public VersionInfo checkVersion(Mobile mobile) throws Exception {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("type", 1);
        List<VersionInfo> versionInfos = versionDao.selectByMap(paramMap);
        if (!CollectionUtils.isEmpty(versionInfos)) {
            VersionInfo versionInfo = versionInfos.get(0);
            if (mobile.getVersionCode() != null) {
                int result = compareVersion(mobile.getVersionCode(), versionInfo.getVersionCode());
                if (result < 1) {

                }
            }
        }
        return new VersionInfo();
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
    }
}
