package cn.nacl.controller;

import cn.nacl.domain.adapter.UserAdapter;
import cn.nacl.domain.dto.UserDTO;
import cn.nacl.domain.entity.UserInfo;
import cn.nacl.domain.vo.UserVO;
import cn.nacl.service.UserInfoService;
import cn.nacl.service.UserService;
import cn.nacl.utils.result.Result;
import cn.nacl.utils.result.ResultCode;
import cn.nacl.utils.result.ResultFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private UserAdapter userAdapter;

    @PostMapping("/register")
    public Result<UserVO> register (@RequestBody UserVO userVO) {
        UserDTO userDTO = userAdapter.convertUserVO2UserDTO(userVO);
        userDTO = userService.register(userDTO);
        if (userDTO == null) {
            return ResultFactory.getResult(ResultCode.fail, null, "注册失败");
        } else {
            UserInfo userInfo = new UserInfo();
            userInfo.setUid(userDTO.getUid());
            userInfoService.save(userInfo);
            List<UserVO> data = new ArrayList<>();
            data.add(userAdapter.convertUserDTO2UserVO(userDTO));
            return ResultFactory.getResult(ResultCode.success, data, "注册成功");
        }
    }

    @GetMapping("")
    public Result<UserVO> getUser (@RequestBody UserVO userVO) {
        UserDTO userDTO = userAdapter.convertUserVO2UserDTO(userVO);
        if (userDTO.getUid() != null) userDTO = userService.getUserByUid(userDTO);
        else if (userDTO.getUsername() != null) userDTO = userService.getUserByUserName(userDTO);
        else userDTO = null;
        if (userDTO == null) return ResultFactory.getResult(ResultCode.fail, null, "不存在该用户");
        else {
            List<UserVO> data = new ArrayList<>();
            userVO = userAdapter.convertUserDTO2UserVO(userDTO);
            userVO.setUserInfo(userInfoService.getInfoByUid(userVO.getUid()));
            data.add(userVO);
            return ResultFactory.getResult(ResultCode.success, data, "获取成功");
        }
    }

    @GetMapping("/friend")
    public Result<UserVO> getFriend (@RequestBody UserVO userVO) {
        UserDTO userDTO = userAdapter.convertUserVO2UserDTO(userVO);
        List<UserDTO> friends = userService.getListOfFriend(userDTO);
        List<UserVO> data = new ArrayList<>();
        UserInfo userInfo = null;
        for (UserDTO friend : friends) {
            userDTO = userService.getUserByUid(friend);
            if (userDTO != null) {
                userInfo = userInfoService.getInfoByUid(userDTO.getUid());
                userVO = userAdapter.convertUserDTO2UserVO(userDTO);
                userVO.setUserInfo(userInfo);
                data.add(userVO);
            }
        }
        return ResultFactory.getResult(ResultCode.success, data, "成功获取好友");
    }
}
