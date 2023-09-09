package cn.nacl.service;

import cn.nacl.dao.MessageDao;
import cn.nacl.domain.dto.MessageDTO;
import cn.nacl.domain.entity.GRVO;
import cn.nacl.domain.entity.Message;

import java.util.List;


public interface MessageService {
    public MessageDTO saveMessageOnCache (MessageDTO messageDTO, List<GRVO> grvoList);

    public boolean setReadOnCacheAndSaveOnDB (MessageDTO messageDTO);

    public List<MessageDTO> getUnReadOnCache (MessageDTO messageDTO);

    public boolean detectUnReadOnDB (MessageDTO messageDTO);

    public void setReadAndSaveOnDB (MessageDTO messageDTO);

    public List<MessageDTO> getUnReadOnDB (MessageDTO messageDTO);

    public void sendMessage (MessageDTO messageDTO, List<GRVO> grvoList);
}
