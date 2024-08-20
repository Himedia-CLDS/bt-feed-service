package com.clds.bottletalk.common;


public class JsonResult {

	/** 필드 **/
	private String result; // "success" or "fail"
	private Object apiData; // 성공일때 데이타
	private String message; // 실패일때 실패메세지

	/******************************
	 * 생성자
	 *  -성공: JsonResult(Object apiData)
	 *  -실패: JsonResult(String message)
	 */
	private JsonResult(Object apiData) {
		result = "success";
		this.apiData = apiData;
		message = null;
	}
	private JsonResult(String message) {
		result = "fail";
		apiData = null;
		this.message = message;
	}

	
	/******************************
	 * GetterSetter
	 * setter역할은 생성자가 함
	 * getter메소드만 생성
	 */
	public String getResult() {
		return result;
	}

	public Object getApiData() {
		return apiData;
	}

	public String getMessage() {
		return message;
	}
	
	/***********************************
	 * static으로 new사용하지 않고 사용
	 *  -성공: success(Object data)
	 *  -실패: fail(String message)
	 */
	public static JsonResult success(Object data) {
		return new JsonResult(data);
	}
	public static JsonResult fail(String message) {
		return new JsonResult(message);
	}
}
