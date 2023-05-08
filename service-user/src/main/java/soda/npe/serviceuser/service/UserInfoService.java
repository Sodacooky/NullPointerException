package soda.npe.serviceuser.service;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import soda.npe.common.constant.DBConstant;
import soda.npe.common.entity.UserInfo;
import soda.npe.common.mapper.UserInfoMapper;
import soda.npe.serviceuser.vo.ModifyUserVO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public class UserInfoService extends ServiceImpl<UserInfoMapper, UserInfo> {

    @Value("${npe.avatar-path:./avatar/}")
    private String avatarPath;

    public List<UserInfo> searchByRegisterTime(String keyword, Integer page, Boolean isAsc) {
        return this.list(
                new LambdaQueryWrapper<UserInfo>()
                        .like(UserInfo::getNickname, keyword)
                        .or().like(UserInfo::getId, keyword)
                        .or().like(UserInfo::getDescription, keyword)
                        .orderBy(true, isAsc, UserInfo::getRegisterTime)
                        .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE));
    }

    public boolean isNicknameUsed(String nickname, Long userId) {
        UserInfo found = this.getOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getNickname, nickname));
        if (found == null) return false;
        return found.getId().longValue() != userId;
    }

    public boolean updateAvatar(Long userId, InputStream fileInputStream, String type) throws IOException {
        //生成文件名
        String filename = UUID.randomUUID().toString();
        String filenameWithExt = filename + ".jpg";
        //根据类型决定是否转换，并写出到文件
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(avatarPath, filenameWithExt))) {
            if (type.contains("png")) {
                //png要转换成jpg
                ImgUtil.convert(fileInputStream, "png", fileOutputStream);
            } else {
                //直接写出
                IoUtil.copy(fileInputStream, fileOutputStream);
            }
            //fileInputStream.close();
        }
        //更新用户表中的头像文件名
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userId);
        userInfo.setAvatar(filename);
        return this.updateById(userInfo);
    }

    public boolean updateInfo(Long userId, String nickname, String description) {
        //控制器中已经做了一些校验工作，这里就不做了
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userId);
        userInfo.setNickname(nickname);
        userInfo.setDescription(description);
        return this.updateById(userInfo);
    }

    public boolean adminUpdate(ModifyUserVO vo) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(vo.getId());
        userInfo.setNickname(vo.getNickname());
        userInfo.setDescription(vo.getDescription());
        userInfo.setAvatar(vo.getAvatar());
        return this.updateById(userInfo);
    }

    public boolean doBan(Long userId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userId);
        userInfo.setIsBanned(1);
        return this.updateById(userInfo);
    }

    public boolean doUnban(Long userId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userId);
        userInfo.setIsBanned(0);
        return this.updateById(userInfo);
    }
}
