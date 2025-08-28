package com.iboot.studio.web.controller;

import com.iboot.studio.common.constant.R;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.chanjar.weixin.cp.api.WxCpDepartmentService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpUserService;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import me.chanjar.weixin.cp.bean.WxCpUser;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.iboot.studio.common.constant.Const.SERVER_API_PATH;

@RestController
@RequestMapping(SERVER_API_PATH + "/wx/cp")
@RequiredArgsConstructor
public class WxCpController {
	private final WxCpService wxCpService;

	/**
	 * 同步企业微信部门
	 *
	 * @return
	 */
	@SneakyThrows
	@PostMapping("/sync/dept")
	public R<List<WxCpDepart>> syncDept() {
		WxCpDepartmentService departmentService = wxCpService.getDepartmentService();
		List<WxCpDepart> departList = departmentService.list(null);
		return R.success(departList);
	}

	/**
	 * 同步企业微信用户
	 *
	 * @return
	 */
	@SneakyThrows
	@PostMapping("/sync/user")
	public R<List<WxCpUser>> syncUser() {
		WxCpUserService userService = wxCpService.getUserService();
		List<WxCpUser> wxCpUsers = userService.listByDepartment(1L, true, 0);
		return R.success(wxCpUsers);
	}

	/**
	 * 发送消息
	 *
	 * @return
	 */
	@SneakyThrows
	@PostMapping("/send/msg")
	public R<String> sendMsg() {
		WxCpMessage wxCpMessage = WxCpMessage
				.TEXT()
				.toUser("TangShuCheng|iboot-ai|JiaYi")
				.content("这是text消息")
				.build();
		wxCpService.getMessageService().send(wxCpMessage);


		String md = """
				实时新增用户反馈<font color="warning">132例</font>，请相关同事注意。
				>类型:<font color="comment">用户反馈</font>
				>普通用户反馈:<font color="comment">117例</font>
				>VIP用户反馈:<font color="info">15例</font>
				""";

		wxCpMessage = WxCpMessage
				.MARKDOWN()
				.toUser("TangShuCheng|iboot-ai|JiaYi")
				.content(md)
				.build();
		wxCpService.getMessageService().send(wxCpMessage);
		return R.success();
	}
}
