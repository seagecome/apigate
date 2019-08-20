package com.talkortell.bbs.apigate.reference.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.talkortell.bbs.ups.api.IUserOuterService;

@FeignClient(name="feignLoginService", url="http://localhost:8080/")
public interface IFeignLoginService extends IUserOuterService {

}
