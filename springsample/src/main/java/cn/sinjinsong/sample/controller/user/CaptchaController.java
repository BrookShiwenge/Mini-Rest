package cn.sinjinsong.sample.controller.user;


import cn.sinjinsong.sample.domain.dto.captcha.CaptchaDTO;
import cn.sinjinsong.sample.domain.dto.captcha.CaptchaVO;
import cn.sinjinsong.sample.properties.AuthenticationProperties;
import cn.sinjinsong.sample.security.verification.VerificationManager;
import cn.sinjinsong.sample.util.CaptchaUtil;
import cn.sinjinsong.sample.util.UUIDUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by SinjinSong on 2017/4/27.
 */
@RestController
@RequestMapping("/captchas")
@Api(value = "captchas", description = "图片验证码")
@Slf4j
public class CaptchaController {
    @Autowired
    private VerificationManager verificationManager;
    @Autowired
    private AuthenticationProperties authenticationProperties;
    
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "获取验证码，在登录之前请求", response = CaptchaVO.class)
    public CaptchaVO getCaptcha() throws IOException {
        String uuid = UUIDUtil.uuid();
        CaptchaDTO captchaDTO = CaptchaUtil.createRandomCode();
        //保存
        verificationManager.createVerificationCode(uuid, captchaDTO.getValue(), authenticationProperties.getCaptchaExpireTime());
        log.info("uuid:{}", uuid);
        log.info("value:{}", captchaDTO);
        //返回图片
        return new CaptchaVO(captchaDTO.getImage(),uuid);
    }
}
