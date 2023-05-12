package soda.npe.servicemiscellaneous.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import soda.npe.common.entity.Announcement;
import soda.npe.common.mapper.AnnouncementMapper;
import soda.npe.servicemiscellaneous.vo.AddAnnouncementVO;

import java.util.Date;

@Service
public class AnnouncementService extends ServiceImpl<AnnouncementMapper, Announcement> {

    public boolean add(AddAnnouncementVO vo) {
        Announcement announcement = new Announcement();
        announcement.setTitle(vo.getTitle());
        announcement.setText(vo.getText());
        announcement.setTime(new Date());
        return this.save(announcement);
    }

}
