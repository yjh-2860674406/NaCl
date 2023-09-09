package cn.nacl.domain.adapter;


import cn.nacl.domain.dto.GRDTO;
import cn.nacl.domain.entity.GR;
import cn.nacl.domain.vo.GRVO;

public interface GRAdapter {
    public GRDTO convertGR2GRDTO(GR gr);

    public GRDTO convertGRVO2GRDTO(GRVO grVO);

    public GRVO convertGRDTO2GRVO(GRDTO grDTO);

    public GR convertGRDTO2GR(GRDTO grDTO);
}
