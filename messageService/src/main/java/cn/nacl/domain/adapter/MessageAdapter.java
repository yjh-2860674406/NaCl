package cn.nacl.domain.adapter;

import cn.nacl.domain.dto.MessageDTO;
import cn.nacl.domain.entity.Message;
import cn.nacl.domain.vo.MessageVO;

public interface MessageAdapter {
    public MessageDTO convertMessage2MessageDTO (Message message);
    public MessageDTO convertMessageVO2MessageDTO (MessageVO messageVO);
    public MessageVO convertMessageDTO2MessageVO (MessageDTO messageDTO);
    public Message convertMessageDTO2Message (MessageDTO messageDTO);
}
