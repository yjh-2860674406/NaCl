package cn.nacl.service.client;

import cn.nacl.domain.entity.GRVO;
import cn.nacl.utils.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("group-service")
public interface GroupClient {
    @GetMapping("/group/user")
    public Result<GRVO> getAllUserFromGroup (@RequestParam String gid);
}
