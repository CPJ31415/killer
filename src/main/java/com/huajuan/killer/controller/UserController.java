package com.huajuan.killer.controller;

import com.huajuan.killer.controller.viewobject.UserVO;
import com.huajuan.killer.error.BusinessException;
import com.huajuan.killer.error.EmBusinessError;
import com.huajuan.killer.response.CommonReturnType;
import com.huajuan.killer.service.UserService;
import com.huajuan.killer.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping(value = "/login",method = {RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name = "telephone") String telephone,
                                  @RequestParam(name = "password") String password) throws BusinessException, NoSuchAlgorithmException {
        //入参校验
        if(StringUtils.isEmpty(telephone) || StringUtils.isEmpty(password)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "短信验证码不符合");
        }

        //用户登录服务，校验用户登录合法性
        UserModel userModel = userService.validateLogin(telephone, this.EncodeByMd5(password));


        //生成登陆凭证token, UUID, token + redis 存储用户信息
        String uuidToken = UUID.randomUUID().toString();
        uuidToken = uuidToken.replace("-","");

        //建立token和用户登录态的联系
        redisTemplate.opsForValue().set(uuidToken, userModel);
        redisTemplate.expire(uuidToken, 1, TimeUnit.HOURS);
//        this.httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
//        this.httpServletRequest.getSession().setAttribute("LOGIN_USER", userModel);

        //下发token
        return CommonReturnType.create(uuidToken);
    }


    @RequestMapping(value = "/register", method = {RequestMethod.POST}, consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "telephone") String telephone,
                                      @RequestParam(name = "otpCode") String otpCode,
                                      @RequestParam(name = "name") String name,
                                      @RequestParam(name = "age") Integer age,
                                      @RequestParam(name = "gender") Byte gender,
                                      @RequestParam(name = "password") String password) throws BusinessException, NoSuchAlgorithmException {
        String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telephone);
        if (!otpCode.equals(inSessionOtpCode)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "短信验证码不符合");
        }

        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setAge(age);
        userModel.setGender(gender);
        userModel.setTelephone(telephone);
        userModel.setRegisterMode("byPhone");
        userModel.setEncryptPassword(this.EncodeByMd5(password));

        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    public String EncodeByMd5(String str) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();

        String newStr = base64Encoder.encode(md5.digest(str.getBytes(StandardCharsets.UTF_8)));
        return newStr;
    }

    //用户获取otp短信接口
    @RequestMapping(value = "/getotp",method = {RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    //@GetMapping ("/getotp")
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "telephone") String telephone) {
        //生成otp验证
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);

        //OTP验证码同对应手机号绑定， session绑定
        httpServletRequest.getSession().setAttribute(telephone, otpCode);

        System.out.println(otpCode);

        //将OTP验证码通过短信通道发给用户
        return CommonReturnType.create(null);
    }

    @RequestMapping( "/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id") Long id) throws BusinessException {
        //调用service服务获取对应的用户id传给前端
        UserModel userModel = userService.getUserById(id);
        UserVO userVO = convertFromModel((userModel));

        //如果不存在
        if (userVO == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }

        return CommonReturnType.create(userVO);
    }

    private UserVO convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }

}
