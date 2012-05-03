/**
 * 
 */
package com.zz91.sms.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.sms.common.ZZSms;
import com.zz91.sms.domain.Gateway;
import com.zz91.sms.dto.ExtResult;
import com.zz91.sms.service.GatewayService;

/**
 * @author root
 * 
 */
@Controller
public class GatewayController extends BaseController {
	
	@Resource
	private GatewayService gatewayService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		return null;
	}
	
	@RequestMapping
	public ModelAndView query(HttpServletRequest request,Map<String, Object>out,Integer enabled){
		
		List<Gateway> list=gatewayService.query(enabled);
		
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView delete(HttpServletRequest requst,Map<String, Object>out,Integer id){
		
		Integer i=gatewayService.remove(id);
		
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView add(HttpServletRequest request,Map<String, Object>out ,Gateway gateway){
		
		Integer i=gatewayService.create(gateway);
		
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
		
	}
	
	@RequestMapping
	public ModelAndView queryOne(HttpServletRequest request,Map<String, Object>out,Integer id){
		
		Gateway gateway=gatewayService.queryOne(id);
		
		return printJson(gateway, out);
	}
	
	@RequestMapping
	public ModelAndView update(HttpServletRequest request,Map<String, Object>out,Gateway gateway){
		
		ExtResult result=new ExtResult();
		if(gateway!=null){
			Integer i=gatewayService.update(gateway);
			if(i!=null && i.intValue()>0){
				result.setSuccess(true);
			}
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView enabledGate(HttpServletRequest request,Map<String, Object>out,Integer id, String code){
		
		gatewayService.enabled(id, code);
		
		ExtResult result=new ExtResult();
		if(id!=null && id.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView disenabledGate(HttpServletRequest request,Map<String, Object>out,Integer id, String code){
		
		gatewayService.disabled(id,code);
		
		ExtResult result=new ExtResult();
		if(id!=null && id.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping
	public ModelAndView balance(HttpServletRequest request,Map<String, Object>out,
			String code){
		
		ZZSms sms = (ZZSms) gatewayService.CACHE_GATEWAY.get(code);
		
		ExtResult result=new ExtResult();
			if (sms != null ) {
				result.setData(sms.balance());
			}else {
				result.setData(0);
			}
			result.setSuccess(true);
		return printJson(result, out);
	}


	
	@SuppressWarnings("static-access")
	@RequestMapping
	public ModelAndView testGateway(HttpServletRequest request,
			Map<String, Object> out, String mobile,String content,String gatewayCode) {

		ZZSms zzSms=(ZZSms) gatewayService.CACHE_GATEWAY.get(gatewayCode);

		ExtResult result = new ExtResult();
		if (zzSms != null ) {
			zzSms.send(mobile, content);
		}
		result.setSuccess(true);
		return printJson(result, out);
	}

}
