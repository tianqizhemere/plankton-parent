package top.tianqi.plankton.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.common.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.common.utils.PageResult;
import top.tianqi.plankton.system.entity.Attach;
import top.tianqi.plankton.system.entity.ExternalApplication;
import top.tianqi.plankton.system.mapper.AttachMapper;
import top.tianqi.plankton.system.mapper.ExternalApplicationMapper;
import top.tianqi.plankton.system.service.ExternalApplicationService;

import java.util.List;

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
    public PageResult getPage(String name, Page<ExternalApplication> page) {
        List<ExternalApplication> list = externalApplicationMapper.findPage(name, page);
        return new PageResult(page.getCurrent(), page.getSize(),  page.getTotal() , page.getPages(), list);
    }

    @Override
    public ExternalApplication findByCode(Integer code) {
        return externalApplicationMapper.findByCode(code);
    }

    @Override
    public boolean insert(ExternalApplication externalApplication) {
        boolean result = super.insert(externalApplication);
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
