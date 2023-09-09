package cn.nacl.service.Impl;

import cn.nacl.dao.RelationDao;
import cn.nacl.dao.UserDao;
import cn.nacl.domain.adapter.UserAdapter;
import cn.nacl.domain.dto.UserDTO;
import cn.nacl.domain.entity.Relation;
import cn.nacl.domain.entity.Role;
import cn.nacl.domain.entity.User;
import cn.nacl.domain.entity.UserRole;
import cn.nacl.service.UserService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private RelationDao relationDao;

    @Resource
    private UserDao userDao;

    @Resource
    private UserAdapter userAdapter;

    @Resource
    private RoleClient roleClient;

    @Override
    public UserDTO login(UserDTO userDTO) {
        userDTO.calculatePassWord();
        User user = userDao.findByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword());
        return userAdapter.convertUser2UserDTO(user);
    }

    @Override
    public UserDTO register(UserDTO userDTO) {
        User user = userDao.findByUsername(userDTO.getUsername());
        if (user != null && user.getIsUsed()) return null;
        user = userDao.findByPhone(userDTO.getPhone());
        if (user != null && user.getIsUsed()) return null;
        user = userDao.findByEmail(userDTO.getEmail());
        if (user != null && user.getIsUsed()) return null;
        userDTO.calculatePassWord();
        user = userAdapter.convertUserDTO2User(userDTO);
        Role role = new Role(); role.setRname("user"); UserRole userRole = new UserRole(); userRole.setUid(user.getUid()); userRole.setRid(role);
        roleClient.addUserRole(userRole);
        userDao.save(user);
        return userAdapter.convertUser2UserDTO(userDao.findByUsername(user.getUsername()));
    }

    @Override
    public UserDTO getUserByUid(UserDTO userDTO) {
        User user = userDao.findByUid(userDTO.getUid());
        if (user.getIsUsed()) return userAdapter.convertUser2UserDTO(user);
        else return null;
    }

    @Override
    public UserDTO getUserByUserName(UserDTO userDTO) {
        User user = userDao.findByUsername(userDTO.getUsername());
        if (user.getIsUsed()) return userAdapter.convertUser2UserDTO(user);
        else return null;
    }

    @Override
    public List<UserDTO> getListOfFriend(UserDTO userDTO) {
        List<Relation> relations = relationDao.findAllByUid1(userDTO.getUid());
        List<UserDTO> friends = new ArrayList<>();
        for (Relation relation : relations) {
            userDTO = new UserDTO();
            userDTO.setUid(relation.getUid2());
            friends.add(userDTO);
        }
        return friends;
    }
}
