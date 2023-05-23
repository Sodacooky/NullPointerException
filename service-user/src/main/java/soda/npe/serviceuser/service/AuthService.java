package soda.npe.serviceuser.service;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import soda.npe.common.entity.GlobalData;
import soda.npe.common.entity.UserAuthentication;
import soda.npe.common.entity.UserInfo;
import soda.npe.common.entity.UserNotice;
import soda.npe.common.mapper.GlobalDataMapper;
import soda.npe.common.mapper.UserAuthenticationMapper;
import soda.npe.common.mapper.UserInfoMapper;
import soda.npe.common.mapper.UserNoticeMapper;
import soda.npe.common.utils.JwtAuthUtil;
import soda.npe.common.utils.MailUtil;
import soda.npe.serviceuser.vo.RegisterVO;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;

@Service
public class AuthService extends ServiceImpl<UserAuthenticationMapper, UserAuthentication> {

    @Resource
    private JwtAuthUtil jwtAuthUtil;

    @Resource
    private MailUtil mailUtil;

    @Resource
    private GlobalDataMapper globalDataMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserNoticeMapper userNoticeMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    public boolean tryLogin(String email, String password) {
        return this.getOne(new LambdaQueryWrapper<UserAuthentication>()
                .eq(UserAuthentication::getEmail, email)
                .eq(UserAuthentication::getPassword, password)) != null;
    }

    public String doLogin(String email, String password) {
        UserAuthentication loginUser = this.getOne(new LambdaQueryWrapper<UserAuthentication>()
                .eq(UserAuthentication::getEmail, email)
                .eq(UserAuthentication::getPassword, password));
        //判断是否被封禁
        if (userInfoMapper.selectById(loginUser.getId()).getIsBanned() != 0) return null;
        //生成JWT
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("userId", loginUser.getId());
        return jwtAuthUtil.createToken(payload);
    }

    public boolean doLogout(String token) {
        //由于没有经过gateway，所以此时token可能没有效果，我们先判断一波
        if (jwtAuthUtil.validation(token)) {
            //然后移除
            return jwtAuthUtil.revoke(token);
        } else return false;
    }

    public String adminDoLogin(String password) {
        //从数据库读取密码并判断
        GlobalData adminPassword = globalDataMapper.selectById("adminPassword");
        if (!password.equals(adminPassword.getContent())) return null;
        //生成管理员的jwt
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("userId", "admin");
        return jwtAuthUtil.createToken(payload);
    }

    public boolean doRegister(RegisterVO registerVO) {
        //此处默认数据都通过了校验
        //填充UserInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setNickname(registerVO.getNickname());
        userInfo.setDescription(null);
        userInfo.setAvatar("default");
        userInfo.setRegisterTime(new Date());
        userInfo.setIsBanned(1); //注册的账号先冻结
        if (userInfoMapper.insert(userInfo) != 1) return false;
        //得到刚刚填充的UserInfo所分配的ID
        Long userId = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getNickname, registerVO.getNickname())).getId();
        //填充UserAuthentication
        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setId(userId);
        userAuthentication.setEmail(registerVO.getEmail());
        userAuthentication.setPassword(registerVO.getPassword());
        //生成激活账户所需使用的magic串
        String magic = UUID.randomUUID(true).toString(true);
        //添加一条Redis记录，带有激活账户时使用的magic串，用户需要在一天内激活账户
        stringRedisTemplate.opsForValue().set(magic, userId.toString(), Duration.ofDays(1));
        //将激活链接发送到邮箱，异步地
        //从数据库读取网站的主机名，构建校验URL
        GlobalData host = globalDataMapper.selectById("host");
        final String title = "点击链接，完成NullPointerException注册验证";
        // - http(s)://aaa.bbb:xxx/registerVerify?magic=ccc
        String content = "点击该链接进行验证：" + host.getContent() + "/registerVerify?magic=" + magic;
        mailUtil.sendEmailAsync(registerVO.getEmail(), title, content);
        return this.save(userAuthentication);
    }

    public boolean doRegisterVerify(String magic) {
        //判断是否存在
        if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(magic))) return false;
        //激活账户
        String userIdStr = stringRedisTemplate.opsForValue().get(magic);
        Assert.notNull(userIdStr, "userIdStr一定不是null");
        Long userId = Long.parseLong(userIdStr);
        UserInfo updateInfo = new UserInfo();
        updateInfo.setId(userId);
        updateInfo.setIsBanned(0);
        //发送欢迎消息
        UserNotice userNotice = new UserNotice();
        userNotice.setGoalUserId(updateInfo.getId());
        userNotice.setType("system");
        userNotice.setTitle("欢迎来到NullPointerException~");
        userNotice.setText("社区将因你的到来而改变吗？拭目以待吧！");
        userNotice.setTime(new Date());
        userNotice.setIsRead(0);
        userNoticeMapper.insert(userNotice);//ignore result
        //redis中的记录将在一天内删除
        return userInfoMapper.updateById(updateInfo) == 1;
    }

    public boolean isEmailUsed(String email) {
        //假定邮箱格式已经被校验
        return this.getOne(
                new LambdaQueryWrapper<UserAuthentication>()
                        .eq(UserAuthentication::getEmail, email)) != null;
    }

    public boolean isNicknameUsed(String nickname) {
        return this.userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getNickname, nickname)) != null;
    }

    public boolean hasLogin(String token) {
        return jwtAuthUtil.validation(token);
    }

    public boolean isPasswordCorrect(Long userId, String password) {
        return this.getOne(new LambdaQueryWrapper<UserAuthentication>()
                .eq(UserAuthentication::getId, userId)
                .eq(UserAuthentication::getPassword, password)) != null;
    }

    public boolean updatePassword(Long userId, String newPassword) {
        //更新
        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setId(userId);
        userAuthentication.setPassword(newPassword);
        return updateById(userAuthentication);
    }
}
