package com.talkortell.bbs.apigate.web.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Producer;
import com.talkortell.bbs.apigate.enums.ImgCodeSceneEnum;
import com.talkortell.bbs.base.common.exception.AppLogicException;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ImgCodeController {
	
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	@Autowired
    private Producer captchaProducer;
	
	@GetMapping("/portal/common/getImgCode")
	public void getImgCode(HttpServletResponse response, String userMobile, String sceneId) {
		if(StringUtils.isBlank(userMobile)) {
			throw new AppLogicException("手机号不能为空");
		}
		String preKey = ImgCodeSceneEnum.getPreKeyByCode(sceneId);
		if(StringUtils.isBlank(preKey)) {
			throw new AppLogicException("无法获取图片验证码");
		}
		
		ServletOutputStream out = null;
		
		try {
			out = response.getOutputStream();
			response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
			response.addHeader("Cache-Control", "post-check=0, pre-check=0");
			response.setHeader("Pragma", "no-cache");
			response.setContentType("image/jpeg");
			String capText = captchaProducer.createText();
			
			ValueOperations<Object, Object> opsForValue = redisTemplate.opsForValue();
			opsForValue.set(preKey+userMobile, capText, 180, TimeUnit.SECONDS);
			
			BufferedImage bi = captchaProducer.createImage(capText);
			ImageIO.write(bi, "jpg", out);
		} catch (IOException e) {
			log.error("====create imgcode error===");
			throw new AppLogicException("图片验证码生成失败");
		} finally {
			IOUtils.closeQuietly(out);
		}
        
	}
}
