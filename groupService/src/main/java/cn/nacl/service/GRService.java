package cn.nacl.service;

import cn.nacl.domain.dto.GRDTO;
import cn.nacl.domain.entity.GR;

import java.util.List;

public interface GRService {
    GRDTO addUserToGroup (GRDTO grdto);

    void removeUserFromGroup (GRDTO grdto);

    List<GRDTO> getAllUserFromGroup (GRDTO grdto);
}
