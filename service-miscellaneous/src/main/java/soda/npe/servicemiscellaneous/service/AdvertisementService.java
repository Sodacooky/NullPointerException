package soda.npe.servicemiscellaneous.service;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import soda.npe.common.entity.Advertisement;
import soda.npe.common.mapper.AdvertisementMapper;
import soda.npe.servicemiscellaneous.vo.AddAdvertisementVO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Service
public class AdvertisementService extends ServiceImpl<AdvertisementMapper, Advertisement> {

    @Value("${npe.ads-image-path:./ads/}")
    private String adsImagePath;

    public boolean removeAdv(Long advertisementId) {
        //获取信息，用于删除图片
        Advertisement advertisement = this.getById(advertisementId);
        //删除图片
        // - 先检查是否有别的图片也使用了这个文件名
        if (1 == this.count(new LambdaQueryWrapper<Advertisement>().like(Advertisement::getImage, advertisement.getImage()))) {
            FileUtil.del(new File(adsImagePath, advertisement.getImage() + ".jpg"));
        }
        //删除记录
        return this.removeById(advertisementId);
    }

    public boolean addAdv(AddAdvertisementVO vo) {
        //判断图片存在性
        File imageFile = new File(adsImagePath, vo.getImage() + ".jpg");
        if (!imageFile.exists()) return false;
        //填充
        Advertisement advertisement = new Advertisement();
        advertisement.setUrl(vo.getUrl());
        advertisement.setImage(vo.getImage());
        advertisement.setTime(new Date());
        return this.save(advertisement);
    }

    public String uploadImage(InputStream fileInputStream, String type) throws IOException {
        //生成文件名
        String filename = UUID.randomUUID().toString();
        String filenameWithExt = filename + ".jpg";
        //根据类型决定是否转换，并写出到文件
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(adsImagePath, filenameWithExt))) {
            if (type.contains("png")) {
                //png要转换成jpg
                ImgUtil.convert(fileInputStream, "png", fileOutputStream);
            } else {
                //直接写出
                IoUtil.copy(fileInputStream, fileOutputStream);
            }
            //fileInputStream.close();
        }
        return filename;
    }
}
