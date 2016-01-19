package com;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilderFactory;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

public class Sample {

	public static void main(String[] args) throws Exception {
		String sToken = "QDG6eK";
		String sCorpID = "wx5823bf96d3bd56c7";
		String sEncodingAESKey = "jWmYm7qr5nMoAUwZRjGtBxmz3KA1tkAj3ykkR6q2B2C";

		WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);
		/*
		------------ʹ��ʾ��һ����֤�ص�URL---------------
		*��ҵ�����ص�ģʽʱ����ҵ�Ż�����֤url����һ��get���� 
		��������֤ʱ����ҵ�յ���������
		* GET /cgi-bin/wxpush?msg_signature=5c45ff5e21c57e6ad56bac8758b79b1d9ac89fd3&timestamp=1409659589&nonce=263014780&echostr=P9nAzCzyDtyTWESHep1vC5X9xho%2FqYX3Zpb4yKa9SKld1DsH3Iyt3tP3zNdtp%2B4RPcs8TgAE7OaBO%2BFZXvnaqQ%3D%3D 
		* HTTP/1.1 Host: qy.weixin.qq.com

		���յ�������ʱ����ҵӦ		1.������Get����Ĳ�����������Ϣ��ǩ��(msg_signature)��ʱ���(timestamp)��������ִ�(nonce)�Լ�����ƽ̨���͹�������������ַ���(echostr),
		��һ��ע����URL���롣
		2.��֤��Ϣ��ǩ������ȷ�� 
		3. ���ܳ�echostrԭ�ģ���ԭ�ĵ���Get�����response�����ظ�����ƽ̨
		��2��3�������ù���ƽ̨�ṩ�Ŀ⺯��VerifyURL��ʵ�֡�

		*/
		// ������url�ϵĲ���ֵ���£�
		// String sVerifyMsgSig = HttpUtils.ParseUrl("msg_signature");
		String sVerifyMsgSig = "5c45ff5e21c57e6ad56bac8758b79b1d9ac89fd3";
		// String sVerifyTimeStamp = HttpUtils.ParseUrl("timestamp");
		String sVerifyTimeStamp = "1409659589";
		// String sVerifyNonce = HttpUtils.ParseUrl("nonce");
		String sVerifyNonce = "263014780";
		// String sVerifyEchoStr = HttpUtils.ParseUrl("echostr");
		String sVerifyEchoStr = "P9nAzCzyDtyTWESHep1vC5X9xho/qYX3Zpb4yKa9SKld1DsH3Iyt3tP3zNdtp+4RPcs8TgAE7OaBO+FZXvnaqQ==";
		String sEchoStr; //��Ҫ���ص�����
		try {
			sEchoStr = wxcpt.VerifyURL(sVerifyMsgSig, sVerifyTimeStamp,
					sVerifyNonce, sVerifyEchoStr);
			System.out.println("verifyurl echostr: " + sEchoStr);
			// ��֤URL�ɹ�����sEchoStr����
			// HttpUtils.SetResponse(sEchoStr);
		} catch (Exception e) {
			//��֤URLʧ�ܣ�����ԭ����鿴�쳣
			e.printStackTrace();
		}

		/*
		------------ʹ��ʾ���������û��ظ�����Ϣ����---------------
		�û��ظ���Ϣ���ߵ���¼���Ӧʱ����ҵ���յ��ص���Ϣ������Ϣ�Ǿ�������ƽ̨����֮���������post��ʽ���͸���ҵ�����ĸ�ʽ��ο��ٷ��ĵ�
		������ҵ�յ�����ƽ̨�Ļص���Ϣ���£�
		POST /cgi-bin/wxpush? msg_signature=477715d11cdb4164915debcba66cb864d751f3e6&timestamp=1409659813&nonce=1372623149 HTTP/1.1
		Host: qy.weixin.qq.com
		Content-Length: 613
		<xml>		<ToUserName><![CDATA[wx5823bf96d3bd56c7]]></ToUserName><Encrypt><![CDATA[RypEvHKD8QQKFhvQ6QleEB4J58tiPdvo+rtK1I9qca6aM/wvqnLSV5zEPeusUiX5L5X/0lWfrf0QADHHhGd3QczcdCUpj911L3vg3W/sYYvuJTs3TUUkSUXxaccAS0qhxchrRYt66wiSpGLYL42aM6A8dTT+6k4aSknmPj48kzJs8qLjvd4Xgpue06DOdnLxAUHzM6+kDZ+HMZfJYuR+LtwGc2hgf5gsijff0ekUNXZiqATP7PF5mZxZ3Izoun1s4zG4LUMnvw2r+KqCKIw+3IQH03v+BCA9nMELNqbSf6tiWSrXJB3LAVGUcallcrw8V2t9EL4EhzJWrQUax5wLVMNS0+rUPA3k22Ncx4XXZS9o0MBH27Bo6BpNelZpS+/uh9KsNlY6bHCmJU9p8g7m3fVKn28H3KDYA5Pl/T8Z1ptDAVe0lXdQ2YoyyH2uyPIGHBZZIs2pDBS8R07+qN+E7Q==]]></Encrypt>
		<AgentID><![CDATA[218]]></AgentID>
		</xml>

		��ҵ�յ�post����֮��Ӧ��		1.������url�ϵĲ�����������Ϣ��ǩ��(msg_signature)��ʱ���(timestamp)�Լ�������ִ�(nonce)
		2.��֤��Ϣ��ǩ������ȷ�ԡ�
		3.��post��������ݽ���xml����������<Encrypt>��ǩ�����ݽ��н��ܣ����ܳ��������ļ����û��ظ���Ϣ�����ģ����ĸ�ʽ��ο��ٷ��ĵ�
		��2��3�������ù���ƽ̨�ṩ�Ŀ⺯��DecryptMsg��ʵ�֡�
		*/
		// String sReqMsgSig = HttpUtils.ParseUrl("msg_signature");
		String sReqMsgSig = "477715d11cdb4164915debcba66cb864d751f3e6";
		// String sReqTimeStamp = HttpUtils.ParseUrl("timestamp");
		String sReqTimeStamp = "1409659813";
		// String sReqNonce = HttpUtils.ParseUrl("nonce");
		String sReqNonce = "1372623149";
		// post�������������
		// sReqData = HttpUtils.PostData();
		String sReqData = "<xml><ToUserName><![CDATA[wx5823bf96d3bd56c7]]></ToUserName><Encrypt><![CDATA[RypEvHKD8QQKFhvQ6QleEB4J58tiPdvo+rtK1I9qca6aM/wvqnLSV5zEPeusUiX5L5X/0lWfrf0QADHHhGd3QczcdCUpj911L3vg3W/sYYvuJTs3TUUkSUXxaccAS0qhxchrRYt66wiSpGLYL42aM6A8dTT+6k4aSknmPj48kzJs8qLjvd4Xgpue06DOdnLxAUHzM6+kDZ+HMZfJYuR+LtwGc2hgf5gsijff0ekUNXZiqATP7PF5mZxZ3Izoun1s4zG4LUMnvw2r+KqCKIw+3IQH03v+BCA9nMELNqbSf6tiWSrXJB3LAVGUcallcrw8V2t9EL4EhzJWrQUax5wLVMNS0+rUPA3k22Ncx4XXZS9o0MBH27Bo6BpNelZpS+/uh9KsNlY6bHCmJU9p8g7m3fVKn28H3KDYA5Pl/T8Z1ptDAVe0lXdQ2YoyyH2uyPIGHBZZIs2pDBS8R07+qN+E7Q==]]></Encrypt><AgentID><![CDATA[218]]></AgentID></xml>";

		try {
			String sMsg = wxcpt.DecryptMsg(sReqMsgSig, sReqTimeStamp, sReqNonce, sReqData);
			System.out.println("after decrypt msg: " + sMsg);
			// TODO: ����������xml��ǩ�����ݽ��д���
			// For example:
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(sMsg);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);

			Element root = document.getDocumentElement();
			NodeList nodelist1 = root.getElementsByTagName("Content");
			String Content = nodelist1.item(0).getTextContent();
			System.out.println("Content��" + Content);
			
		} catch (Exception e) {
			// TODO
			// ����ʧ�ܣ�ʧ��ԭ����鿴�쳣
			e.printStackTrace();
		}

	/*
		------------ʹ��ʾ��������ҵ�ظ��û���Ϣ�ļ���---------------
		��ҵ�����ظ��û�����ϢҲ��Ҫ���м��ܣ�����ƴ�ӳ����ĸ�ʽ��xml����
		������ҵ��Ҫ�ظ��û����������£�
		<xml>
		<ToUserName><![CDATA[mycreate]]></ToUserName>
		<FromUserName><![CDATA[wx5823bf96d3bd56c7]]></FromUserName>
		<CreateTime>1348831860</CreateTime>
		<MsgType><![CDATA[text]]></MsgType>
		<Content><![CDATA[this is a test]]></Content>
		<MsgId>1234567890123456</MsgId>
		<AgentID>128</AgentID>
		</xml>

		Ϊ�˽��˶����Ļظ����û�����ҵӦ��			1.�Լ�����ʱ��ʱ���(timestamp),������ִ�(nonce)�Ա�������Ϣ��ǩ����Ҳ����ֱ���ôӹ���ƽ̨��post url�Ͻ������Ķ�Ӧֵ��
		2.�����ļ��ܵõ����ġ�	3.�����ģ�����1���ɵ�timestamp,nonce����ҵ�ڹ���ƽ̨�趨��token������Ϣ��ǩ����			4.�����ģ���Ϣ��ǩ����ʱ�����������ִ�ƴ�ӳ�xml��ʽ���ַ��������͸���ҵ��
		����2��3��4�������ù���ƽ̨�ṩ�Ŀ⺯��EncryptMsg��ʵ�֡�
		*/
		String sRespData = "<xml><ToUserName><![CDATA[mycreate]]></ToUserName><FromUserName><![CDATA[wx5823bf96d3bd56c7]]></FromUserName><CreateTime>1348831860</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[this is a test]]></Content><MsgId>1234567890123456</MsgId><AgentID>128</AgentID></xml>";
		try{
			String sEncryptMsg = wxcpt.EncryptMsg(sRespData, sReqTimeStamp, sReqNonce);
			System.out.println("after encrypt sEncrytMsg: " + sEncryptMsg);
			// ���ܳɹ�
			// TODO:
			// HttpUtils.SetResponse(sEncryptMsg);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			// ����ʧ��
		}

	}
}
