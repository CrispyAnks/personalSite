package com.sei.gamerknew.controllers;

import com.sei.gamerknew.entities.Bio;
import com.sei.gamerknew.entities.Result;
import com.sei.gamerknew.entities.User;
import com.sei.gamerknew.services.BioService;
import com.sei.gamerknew.services.UserService;
import com.sei.gamerknew.utils.GaUtils;
import com.sei.gamerknew.utils.GetToken;
import com.sei.gamerknew.utils.MD5Util;
import com.sei.gamerknew.utils.ThreadLocalUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private BioService bioService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/mypage/{id}")
    public Result<User> userInfo(){
        Map<String, Object> map = ThreadLocalUtil.get();
        User user = userService.findByUserId((String)map.get("id"));
        user.complete(bioService.getBio(user.getBioid()));
        return Result.success(user);
    }

    @PostMapping("/login")
    public Result<String> loginCheck(String username, String password){
        User loginUser = userService.findByUserName(username);
        if(loginUser == null){
            return Result.error("username invalid");
        } else if (MD5Util.verifyPassword(password, loginUser.getPassword())) {
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getUserid());
            claims.put("username", loginUser.getUsername());
            String token = GetToken.genToken(claims);

            ValueOperations<String, String> oprations = stringRedisTemplate.opsForValue();
            oprations.set(token, token, 12, TimeUnit.HOURS);

            return Result.success(token);
        }
        return Result.error("wrong password");
    }

    @PostMapping("/signup")
    public Result SignUp(@RequestBody @Validated(User.Add.class) User user){
        if(userService.findByUserName(user.getUsername()) == null){
            String id;
            while (true){
                id = GaUtils.geneId();
                if(userService.findByUserId(id) == null){
                    break;
                }
            }
            user.setUserid(id);
            user.setBioid("bio" + user.getUserid());
            userService.register(user);
            return Result.success();
        }else {
            return Result.error("username invalid");
        }
    }

    @PutMapping("/update")
    public Result updateUser(@RequestBody @Validated(User.Update.class) User user){
        userService.updateUser(user);
        bioService.updateBio(user);
        return Result.success();
    }

    @PatchMapping("/upload")
    public Result uploadAva(@RequestParam @URL String imgsrc){
        bioService.uploadAva(imgsrc);
        return Result.success();
    }

    @GetMapping("/logout")
    public Result logOut(@RequestHeader("Authorization") String token){
        stringRedisTemplate.opsForValue().getOperations().delete(token);
        System.out.println(stringRedisTemplate.opsForValue().get(token));
        return Result.success();
    }

}
