package org.jupiter.sdk.chuanglan.gateway.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.jupiter.sdk.chuanglan.api.ChuangLanService;
import org.jupiter.sdk.chuanglan.bean.notice.ReporterCommonNotice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 回调
 * 
 * @author lynn
 */
@Controller
@RequestMapping("notice/chuanglan")
public class ChuangLanNoticeController {
	
	@Resource
	private ChuangLanService chuangLanService;
	
	/**
	 * 普通短信状态回调
	 */
	@ResponseBody
	@RequestMapping("reporter/common")
	public void reporterCommon(@Valid ReporterCommonNotice notice) { 
		chuangLanService.reporterNotice(notice);
	}
}
