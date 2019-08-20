package com.talkortell.bbs.apigate.web.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
public class KaptchaConfig {
	@Bean
	public DefaultKaptcha getDefaultKaptcha() {
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		Properties properties = new Properties();
		// 图片边框
		properties.setProperty("kaptcha.border", "no");
		// 边框颜色
		properties.setProperty("kaptcha.border.color", "105,179,90");
		// 字体
		properties.setProperty("kaptcha.textproducer.font.names", "Courier, Arial	");
		// 字体颜色
		properties.setProperty("kaptcha.textproducer.font.color", "0,0,0");
		// 字体大小
		properties.setProperty("kaptcha.textproducer.font.size", "30");
		// 验证码长度
		properties.setProperty("kaptcha.textproducer.char.length", "4");
		// 验证码取值范围
		properties.setProperty("kaptcha.textproducer.char.string", "0123456789ABCEFGHIJKLMNOPQRSTUVWXYZ");
		// 图片宽
		properties.setProperty("kaptcha.image.width", "176");
		// 背景颜色渐变 开始颜色
		properties.setProperty("kaptcha.background.clear.from", "228,226,226");
		// 背景颜色渐变  结束颜色
		properties.setProperty("kaptcha.background.clear.to", "228,226,226");
		// 干扰 颜色
		properties.setProperty("kaptcha.noise.color", "153,153,153");
		
		Config config = new Config(properties);
		defaultKaptcha.setConfig(config);

		return defaultKaptcha;
	}
}
