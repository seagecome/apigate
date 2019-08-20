package com.talkortell.bbs.apigate.reference.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talkortell.bbs.base.common.resp.BaseResponse;
import com.talkortell.bbs.ups.api.dto.UserFullInfoDTO;
import com.talkortell.bbs.ups.api.dto.req.QueryUserFullInfoRequest;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginManager {
	@Autowired
	private IFeignLoginService feignLoginService;
	
	public BaseResponse<UserFullInfoDTO> queryUserFullInfo(QueryUserFullInfoRequest queryUserFullInfoRequest){
		BaseResponse<UserFullInfoDTO> resp = feignLoginService.queryUserFullInfo(queryUserFullInfoRequest);
		log.info("===resp==={}", resp);
		return resp;
	}
}
