package cn.nacl.domain.adapter.Impl;

import cn.nacl.domain.adapter.GRAdapter;
import cn.nacl.domain.dto.GRDTO;
import cn.nacl.domain.entity.GR;
import cn.nacl.domain.vo.GRVO;
import org.springframework.stereotype.Component;

@Component
public class GRAdapterImpl implements GRAdapter {
    @Override
    public GRDTO convertGR2GRDTO(GR gr) {
        if (gr == null) return null;
        GRDTO grDTO = new GRDTO();
        grDTO.setGid(gr.getGid());
        grDTO.setUid(gr.getUid());
        grDTO.setGrid(gr.getGrid());
        return grDTO;
    }

    @Override
    public GRDTO convertGRVO2GRDTO(GRVO grVO) {
        if (grVO == null) return null;
        GRDTO grDTO = new GRDTO();
        grDTO.setGid(grVO.getGid());
        grDTO.setUid(grVO.getUid());
        grDTO.setGrid(grVO.getGrid());
        return grDTO;
    }

    @Override
    public GRVO convertGRDTO2GRVO(GRDTO grDTO) {
        if (grDTO == null) return null;
        GRVO grVO = new GRVO();
        grVO.setGid(grDTO.getGid());
        grVO.setUid(grDTO.getUid());
        grVO.setGrid(grDTO.getGrid());
        return grVO;
    }

    @Override
    public GR convertGRDTO2GR(GRDTO grDTO) {
        if (grDTO == null) return null;
        GR gr = new GR();
        if (grDTO.getGid() != null) gr.setGid(grDTO.getGid());
        else gr.setGid(-1L);
        if (grDTO.getUid() != null) gr.setUid(grDTO.getUid());
        else gr.setUid(-1L);
        if (grDTO.getGrid() != null) gr.setUid(grDTO.getGrid());
        return gr;
    }
}
