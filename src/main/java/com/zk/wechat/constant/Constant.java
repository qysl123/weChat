package com.zk.wechat.constant;

/**
 * com.zk.wechat.constant.Constant
 *
 * @author: Administrator
 * @Date: 2016/1/16
 * @Time: 13:20
 */
public class Constant {

    public static final String CORPID = "wxefdc7ec2f68519f3";
    public static final String CORPSECRET = "voXQ5tLI3UH_bXWH3bwqi94mWkhYffRqqpqr93-_2mB0ht4Vll-TV4-I80nrG-Hv";
    public static final String TOKEN = "QsRua4tRBkhyMBay";
    public static final String ENCODINGAESKEY = "Kf9Es3VuNHNXiTkJbLbabrBaVBDhTwGCRUPfCsKHRzh";
    public static final String AGENTID = "3";


    public static final String SIGNATURE = "msg_signature";
    public static final String TIMESTAMP = "timestamp";
    public static final String ECHOSTR = "echostr";
    public static final String NONCE = "nonce";
    public static final String BTYPE_VIEW = "view";

    public static final String MSGTYPE = "msgtype";
    public static final String MSGTYPE_EVENT = "event";
    public static final String MSGTYPE_TEXT = "text";

    public static final String EVENT = "event";
    public static final String EVENT_LOCATION = "location";
    public static final String EVENT_CLICK = "click";
    public static final String EVENT_VIEW = "view";

    public static final String TOKENURL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=${corpid}&corpsecret=${corpsecret}";
    public static final String CREATE_MENU = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token=${tokenValue}&agentid=&{agentid}";
    public static final String AUTH = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=${corpid}&redirect_uri=${redirectUrl}&response_type=code&scope=snsapi_base&state=abcABC000#wechat_redirect";

    public static final String GET_ACCESSTOKEN = TOKENURL.replace("${corpid}", CORPID).replace("${corpsecret}", CORPSECRET);
    public static final String GET_CREATE_MENU = CREATE_MENU.replace("&{agentid}", AGENTID);
}
