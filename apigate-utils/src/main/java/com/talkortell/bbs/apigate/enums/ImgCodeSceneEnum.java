package com.talkortell.bbs.apigate.enums;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

@Getter
public enum ImgCodeSceneEnum {
	login("login","LOGIN:"),
	FINDPASSWORD("find","FIND:");
	
	private String code;
	private String preKey;
	
	ImgCodeSceneEnum(String code, String preKey) {
		this.code = code;
		this.preKey = preKey;
	}
	
	public static String getPreKeyByCode(String code) {
		if(StringUtils.isBlank(code)) {
			return "";
		}
		for(ImgCodeSceneEnum item : ImgCodeSceneEnum.values()) {
			if(item.getCode().equalsIgnoreCase(code)) {
				return item.getPreKey();
			}
		}
		return "";
	}
}
