package cn.nacl.controller;

import cn.nacl.domain.adapter.MessageAdapter;
import cn.nacl.domain.dto.MessageDTO;
import cn.nacl.domain.entity.GRVO;
import cn.nacl.domain.vo.MessageVO;
import cn.nacl.service.MessageService;
import cn.nacl.service.client.GroupClient;
import cn.nacl.utils.result.Result;
import cn.nacl.utils.result.ResultCode;
import cn.nacl.utils.result.ResultFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/msg")
public class MessageController {

    @Resource
    private MessageService messageService;

    @Resource
    private MessageAdapter messageAdapter;

    @Resource
    private GroupClient groupClient;

    /**
     * 发送消息
     * @param messageVO 消息数据
     * @return 发送结果，是否成功
     */
    @PostMapping("")
    public Result<MessageVO> sendMsg (@RequestBody MessageVO messageVO) {
        MessageDTO messageDTO = messageAdapter.convertMessageVO2MessageDTO(messageVO);
        // VO -> DTO
        if (messageDTO == null) return ResultFactory.getResult(ResultCode.fail, null, "消息格式错误");
        List<GRVO> grvoList = null;
        if (messageDTO.getRuid() == null) {
            // 如果是群发消息，则获取群成员
            GRVO grvo = new GRVO(); grvo.setGid(messageDTO.getRgid());
            grvoList = groupClient.getAllUserFromGroup(grvo.getGid().toString()).getData();
        }
        messageDTO = messageService.saveMessageOnCache(messageDTO, grvoList);
        // 添加消息进缓存中
        messageService.sendMessage(messageDTO, grvoList);
        // 发送消息: 通知对应需要接收的用户
        messageVO = messageAdapter.convertMessageDTO2MessageVO(messageDTO);
        // DTO -> VO
        List<MessageVO> data = new ArrayList<>();
        data.add(messageVO);
        // 返回结果
        return ResultFactory.getResult(ResultCode.success, data, "发送成功");
        // 无论如何，只要消息被存储就算发送成功
    }

    /**
     * 发送所有未读消息
     * @param messageVO 要获取未读消息的接收用户 uid
     * @return 返回结果
     */
    @GetMapping("")
    public Result<MessageVO> sendUnRead (MessageVO messageVO) {
        MessageDTO messageDTO = messageAdapter.convertMessageVO2MessageDTO(messageVO);
        // VO -> DTO
        if (messageDTO == null) return ResultFactory.getResult(ResultCode.fail, null, "请求有误");
        List<MessageDTO> unReadOnCache = messageService.getUnReadOnCache(messageDTO);
        // 获取缓存中的未读数据
        for (MessageDTO unRead : unReadOnCache) {
            unRead.setRuid(messageDTO.getRuid());
            messageService.sendMessage(unRead, null);
        }
        // 逐个发送到已登陆的用户上
        if (messageService.detectUnReadOnDB(messageDTO)) {
            // 如果数据库中存在未读消息
            List<MessageDTO> unReadOnDB = messageService.getUnReadOnDB(messageDTO);
            // 获取数据中的未读消息
            for (MessageDTO unRead : unReadOnDB) {
                // 逐个存入缓存中并发送到已登录用户上
                unRead.setSuid(messageVO.getSuid());
                messageService.saveMessageOnCache(unRead, null);
                messageService.sendMessage(unRead, null);
            }
        }
        return ResultFactory.getResult(ResultCode.success, null, "获取未读信息成功");
        // 返回结果
    }
}
