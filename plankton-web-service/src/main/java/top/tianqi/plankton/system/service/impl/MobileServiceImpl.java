package top.tianqi.plankton.system.service.impl;

import org.springframework.stereotype.Service;
import top.tianqi.plankton.common.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.system.entity.Mobile;
import top.tianqi.plankton.system.mapper.MobileMapper;
import top.tianqi.plankton.system.service.MobileService;

/**
 * 手机型号服务层实现
 * @author Wukh
 * @create 2021-01-10
 */
@Service(value = "mobileServiceImpl")
public class MobileServiceImpl extends BaseServiceImpl<MobileMapper, Mobile> implements MobileService {
}
