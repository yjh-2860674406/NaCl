package cn.nacl.domain.adapter.Impl;

import cn.nacl.domain.adapter.MessageAdapter;
import cn.nacl.domain.dto.MessageDTO;
import cn.nacl.domain.entity.Message;
import cn.nacl.domain.vo.MessageVO;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class MessageAdapterImpl implements MessageAdapter {
    @Override
    public MessageDTO convertMessage2MessageDTO(Message message) {
        if (message == null) return null;
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMid(message.getMid());
        messageDTO.setMsg(message.getMsg());
        messageDTO.setSdate(message.getSdate());
        messageDTO.setSuid(message.getSuid());
        messageDTO.setRgid(message.getRgid());
        messageDTO.setRuid(message.getRuid());
        return messageDTO;
    }

    @Override
    public MessageDTO convertMessageVO2MessageDTO(MessageVO messageVO) {
        if (messageVO == null) return null;
        if (messageVO.getRuid() == null && messageVO.getRgid() == null) return null;
        if (messageVO.getRuid() != null && messageVO.getRgid() != null) return null;
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMsg(messageVO.getMsg());
        messageDTO.setSdate(new Timestamp(System.currentTimeMillis()));
        messageDTO.setSuid(messageVO.getSuid());
        messageDTO.setRgid(messageVO.getRgid());
        messageDTO.setRuid(messageVO.getRuid());
        return messageDTO;
    }

    @Override
    public MessageVO convertMessageDTO2MessageVO(MessageDTO messageDTO) {
        if (messageDTO == null) return null;
        MessageVO messageVO = new MessageVO();
        messageVO.setMid(messageDTO.getMid());
        messageVO.setMsg(messageDTO.getMsg());
        messageVO.setSdate(messageDTO.getSdate());
        messageVO.setSuid(messageDTO.getSuid());
        messageVO.setRgid(messageDTO.getRgid());
        messageVO.setRuid(messageDTO.getRuid());
        return messageVO;
    }

    @Override
    public Message convertMessageDTO2Message(MessageDTO messageDTO) {
        if (messageDTO == null) return null;
        Message message = new Message();
        if (messageDTO.getMid() != null) message.setMid(messageDTO.getMid());
        message.setMsg(messageDTO.getMsg());
        message.setSdate(messageDTO.getSdate());
        message.setSuid(messageDTO.getSuid());
        message.setRgid(messageDTO.getRgid());
        message.setRuid(messageDTO.getRuid());
        message.setHadRead(false);
        if (message.getRgid() == null) message.setRgid(-1L);
        if (message.getRuid() == null) message.setRuid(-1L);
        return message;
    }
}
