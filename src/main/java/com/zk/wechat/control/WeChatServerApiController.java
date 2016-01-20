package com.zk.wechat.control;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.alibaba.fastjson.JSONObject;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.thoughtworks.xstream.XStream;
import com.zk.wechat.constant.Constant;
import com.zk.wechat.entity.TextMessage;
import com.zk.wechat.service.WeChatService;
import com.zk.wechat.util.Button;
import com.zk.wechat.util.Menu;
import com.zk.wechat.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * 微信服务端接口
 * 
 * @author zhanglg 2015年7月7日 上午11:27:35
 */
@Controller
@RequestMapping("weChatServerApi")
public class WeChatServerApiController{

	private Logger logger = LoggerFactory.getLogger(WeChatServerApiController.class);

	@Autowired
	private WeChatService weChatService;

	/**
	 * 获取企业号后台 token
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "getAccessToken", method = RequestMethod.GET)
	@ResponseBody
	public String getAccessToken() throws UnsupportedEncodingException {
		try {
			String url = Constant.GET_ACCESSTOKEN;
			URL realUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			// 连接超时
			conn.setConnectTimeout(25000);
			// 读取超时 --服务器响应比较慢,增大时间
			conn.setReadTimeout(25000);
			HttpURLConnection.setFollowRedirects(true);
			// 请求方式
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0");
			conn.setRequestProperty("Referer", "https://api.weixin.qq.com/");
			conn.connect();
			// 获取URLConnection对象对应的输出流
			String result = Utils.parseResultString(conn.getInputStream());
			JSONObject json = JSONObject.parseObject(result);
			String accessToken = json.getString("access_token");
			if (!StringUtils.isEmpty(accessToken)) {
				logger.info(json.toJSONString());
				Utils.tokenMap.put(Constant.CORPSECRET, accessToken);
				return accessToken;
			}
		} catch (Exception e) {
			logger.error("获取accessToken出错!", e);
		}
		return "";
	}

	@RequestMapping(value = "/queryAccessToken", method = RequestMethod.GET)
	public @ResponseBody String queryAccessToken() throws UnsupportedEncodingException {
		try {
			return Utils.tokenMap.get(Constant.CORPSECRET);
		} catch (Exception e) {
			logger.error("获取accessToken出错!", e);
		}
		return null;
	}

	/**
	 * 企业号验证签名方法
	 * @param request
	 */
	@RequestMapping(value = "core", method = RequestMethod.GET)
	@ResponseBody
	public String applyGet(HttpServletRequest request) {
		// 微信加密签名
		String signature = URLDecoder.decode(request.getParameter(Constant.SIGNATURE));
		// 时间戳
		String timestamp = URLDecoder.decode(request.getParameter(Constant.TIMESTAMP));
		// 随机数/
		String nonce = URLDecoder.decode(request.getParameter(Constant.NONCE));
		// 随机字符串
		String echostr = URLDecoder.decode(request.getParameter(Constant.ECHOSTR));

		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		WXBizMsgCrypt wxcpt;
        try {
        	System.out.println("token:"+Constant.TOKEN+"\n;  encodekey:"+Constant.ENCODINGAESKEY+" \n; corpid:"+Constant.CORPID);
            wxcpt = new WXBizMsgCrypt(Constant.TOKEN, Constant.ENCODINGAESKEY, Constant.CORPID);
            String sEchoStr = wxcpt.VerifyURL(signature, timestamp, nonce, echostr);
			logger.info(sEchoStr);
            // 验证URL成功，将sEchoStr返回
            return sEchoStr;
        } catch (AesException e) {
            logger.error("微信验证回调URL出错，错误信息：", e);
        }

		return "";
	}

	/**
	 * 企业号验证签名方法
	 * @param request
	 */
	@RequestMapping(value = "core", method = RequestMethod.POST)
	public void processEvent(HttpServletRequest request, HttpServletResponse response) {
		String encryptMsg = null;
		// 微信加密签名
		String signature = URLDecoder.decode(request.getParameter(Constant.SIGNATURE));
		// 时间戳
		String timestamp = URLDecoder.decode(request.getParameter(Constant.TIMESTAMP));
		// 随机数/
		String nonce = URLDecoder.decode(request.getParameter(Constant.NONCE));

		String postData = Utils.postData(request);

		try {
			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(Constant.TOKEN, Constant.ENCODINGAESKEY, Constant.CORPID);
			//解密
			String sMsg = wxcpt.DecryptMsg(signature, timestamp, nonce, postData);
			//处理消息
			String respMessage = weChatService.processResult(sMsg);
			logger.info("处理结果:"+ respMessage);
			//加密
			encryptMsg = wxcpt.EncryptMsg(respMessage, timestamp, nonce);

			PrintWriter out =response.getWriter();
			out.write(encryptMsg);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error("处理消息失败", e);
		}
	}

	/**
	 * push主菜单
	 */
	@RequestMapping(value = "/applyMainMenu", method = RequestMethod.GET)
	@ResponseBody
	public String applyMenu() {
		String result = "";
		try {
			String tokenValue = Utils.tokenMap.get(Constant.CORPSECRET);
			String url = Constant.GET_CREATE_MENU.replace("${tokenValue}", tokenValue);

			Button btn = new Button();
			btn.setName("网页");

			Button btn01 = new Button();
			btn01.setName("哈哈");
			btn01.setType(Constant.BTYPE_VIEW);
			String comment = "http://sameal4.6655.la:13741/index.jsp";
			String commentUrl = Constant.AUTH.replace("${corpid}", Constant.CORPID).replace("${redirectUrl}", URLEncoder.encode(comment));
			btn01.setUrl(commentUrl);

			Button btn02 = new Button();
			btn02.setName("嘿嘿");
			btn02.setType(Constant.BTYPE_VIEW);
			btn02.setUrl(commentUrl);

			btn.setSub_button(new Button[]{btn01, btn02});

			Button bb = new Button();
			bb.setName("控制器");

			Button bb01 = new Button();
			bb01.setName("测试");
			bb01.setType(Constant.BTYPE_VIEW);
			String url01 = "http://sameal4.6655.la:13741/otherServerControl/doSoming";
			String authUrl01 = Constant.AUTH.replace("${corpid}", Constant.CORPID).replace("${redirectUrl}", URLEncoder.encode(url01));
			bb01.setUrl(authUrl01);
			bb.setSub_button(new Button[]{bb01});

			Menu menu = new Menu();
			menu.setButton(new Button[]{btn, bb});

			String btnStr = JSONObject.toJSONString(menu).toString();

			JSONObject json = Utils.httpRequest(url, "POST", btnStr);

			for (Map.Entry<String, Object> entry: json.entrySet()) {
				logger.info(entry.getKey() + "====" + entry.getValue());
            }
			result = json.toJSONString();
		} catch (Exception e) {
			logger.error("创建自定义菜单失败", e);
		}
		return result;
	}

	@RequestMapping(value = "/getMenu", method = RequestMethod.GET)
	@ResponseBody
	public String getMenu(){
		String s = "https://qyapi.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN&agentid=AGENTID";
		String url = s.replace("ACCESS_TOKEN", Utils.tokenMap.get(Constant.CORPSECRET)).replace("AGENTID", Constant.AGENTID);
		JSONObject json = Utils.httpRequest(url, "GET", null);
		return json.toJSONString();
	}
}