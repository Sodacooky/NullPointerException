package soda.npe.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import soda.npe.common.entity.Report;
import soda.npe.common.service.ReportService;
import soda.npe.common.mapper.ReportMapper;
import org.springframework.stereotype.Service;

/**
* @author sodac
* @description 针对表【report(用户举报)】的数据库操作Service实现
* @createDate 2023-03-28 23:16:19
*/
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report>
    implements ReportService{

}




