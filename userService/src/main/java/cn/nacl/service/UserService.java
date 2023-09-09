package cn.nacl.service;

import cn.nacl.domain.dto.UserDTO;
import cn.nacl.domain.vo.UserVO;

import java.util.List;

public interface UserService {
    public UserDTO login (UserDTO userDTO);

    public UserDTO register (UserDTO userDTO);

    public UserDTO getUserByUid (UserDTO userDTO);

    public UserDTO getUserByUserName (UserDTO userDTO);

    public List<UserDTO> getListOfFriend (UserDTO userDTO);
}
