package cn.nacl.domain.adapter;

import cn.nacl.domain.dto.UserDTO;
import cn.nacl.domain.entity.User;
import cn.nacl.domain.vo.UserVO;

public interface UserAdapter {
    public UserDTO convertUser2UserDTO (User user);
    public UserDTO convertUserVO2UserDTO (UserVO userVO);
    public UserVO convertUserDTO2UserVO (UserDTO userDTO);
    public User convertUserDTO2User (UserDTO userDTO);
}
