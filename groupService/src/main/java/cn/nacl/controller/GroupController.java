package cn.nacl.controller;

import cn.nacl.domain.adapter.GRAdapter;
import cn.nacl.domain.dto.GRDTO;
import cn.nacl.domain.vo.GRVO;
import cn.nacl.service.GRService;
import cn.nacl.utils.result.Result;
import cn.nacl.utils.result.ResultCode;
import cn.nacl.utils.result.ResultFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Resource
    private GRService grService;

    @Resource
    private GRAdapter grAdapter;

    @PostMapping("/user")
    public Result<GRVO> addUserToGroup (@RequestBody GRVO grVO) {
        GRDTO grDTO = grAdapter.convertGRVO2GRDTO(grVO);
        grDTO = grService.addUserToGroup(grDTO);
        if (grDTO == null) return ResultFactory.getResult(ResultCode.fail, null, "进群失败");
        else return ResultFactory.getResult(ResultCode.success, null, "进群成功");
    }

    @GetMapping("/user")
    public Result<GRVO> getAllUserFromGroup (GRVO grVO) {
        GRDTO grDTO = grAdapter.convertGRVO2GRDTO(grVO);
        List<GRVO> data = new ArrayList<>();
        List<GRDTO> grdtoList = grService.getAllUserFromGroup(grDTO);
        for (GRDTO grdto : grdtoList) data.add(grAdapter.convertGRDTO2GRVO(grdto));
        return ResultFactory.getResult(ResultCode.success, data, "获取群成员成功");
    }

    @DeleteMapping("/user")
    public Result<GRVO> removeUserFromGroup (@RequestBody GRVO grVO) {
        GRDTO grDTO = grAdapter.convertGRVO2GRDTO(grVO);
        grService.removeUserFromGroup(grDTO);
        return ResultFactory.getResult(ResultCode.success, null, "退群成功");
    }
}
