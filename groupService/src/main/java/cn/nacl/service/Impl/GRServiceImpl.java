package cn.nacl.service.Impl;

import cn.nacl.dao.GRDao;
import cn.nacl.domain.adapter.GRAdapter;
import cn.nacl.domain.dto.GRDTO;
import cn.nacl.domain.entity.GR;
import cn.nacl.service.GRService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class GRServiceImpl implements GRService {

    @Resource
    private GRDao grDao;

    @Resource
    private GRAdapter grAdapter;

    @Override
    public GRDTO addUserToGroup(GRDTO grdto) {
        if (grDao.findByGidAndUid(grdto.getGid(), grdto.getUid()) != null) return null;
        return grAdapter.convertGR2GRDTO(grDao.save(grAdapter.convertGRDTO2GR(grdto)));
    }

    @Override
    public void removeUserFromGroup(GRDTO grdto) {
        grDao.removeByGidAndUid(grdto.getGid(), grdto.getUid());
    }

    @Override
    public List<GRDTO> getAllUserFromGroup(GRDTO grdto) {
        List<GR> grs = grDao.findAllByGid(grdto.getGid());
        List<GRDTO> grDTOs = new ArrayList<>();
        for (GR gr : grs) {
            grDTOs.add(grAdapter.convertGR2GRDTO(gr));
        }
        return grDTOs;
    }
}
