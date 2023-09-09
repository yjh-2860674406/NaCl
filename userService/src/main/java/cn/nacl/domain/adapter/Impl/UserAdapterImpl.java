package cn.nacl.domain.adapter.Impl;

import cn.nacl.domain.adapter.UserAdapter;
import cn.nacl.domain.dto.UserDTO;
import cn.nacl.domain.entity.User;
import cn.nacl.domain.vo.UserVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;


@ToString
@Setter
@Getter
@Component
public class UserAdapterImpl implements UserAdapter {
    @Override
    public UserDTO convertUser2UserDTO (User user) {
        if (user == null) return null;
        UserDTO userDTO = new UserDTO();
        userDTO.setUid(user.getUid());
        userDTO.setPassword(user.getPassword());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        return userDTO;
    }

    @Override
    public UserDTO convertUserVO2UserDTO(UserVO userVO) {
        if (userVO == null) return null;
        UserDTO userDTO = new UserDTO();
        userDTO.setUid(userVO.getUid());
        userDTO.setPassword(userVO.getPassword());
        userDTO.setUsername(userVO.getUsername());
        userDTO.setEmail(userVO.getEmail());
        userDTO.setPhone(userVO.getPhone());
        return userDTO;
    }

    @Override
    public UserVO convertUserDTO2UserVO(UserDTO userDTO) {
        if (userDTO == null) return null;
        UserVO userVO = new UserVO();
        userVO.setUid(userDTO.getUid());
        userVO.setUsername(userDTO.getUsername());
        userVO.setEmail(userDTO.getEmail());
        userVO.setPhone(userDTO.getPhone());
        return userVO;
    }

    @Override
    public User convertUserDTO2User(UserDTO userDTO) {
        if (userDTO == null) return null;
        User user = new User();
        if (userDTO.getUid() == null) user.setUid(0L);
        else user.setUid(userDTO.getUid());
        if (userDTO.getPassword() == null) user.setPassword("");
        else user.setPassword(userDTO.getPassword());
        if (userDTO.getUsername() == null) user.setUsername("");
        else user.setUsername(userDTO.getUsername());
        if (userDTO.getEmail() == null) user.setEmail("");
        else user.setEmail(userDTO.getEmail());
        if (userDTO.getPhone() == null) user.setPhone("");
        else user.setPhone(userDTO.getPhone());
        return user;
    }
}
