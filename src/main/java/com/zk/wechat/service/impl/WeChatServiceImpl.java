package com.zk.wechat.service.impl;

import com.zk.wechat.constant.Constant;
import com.zk.wechat.entity.TextMessage;
import com.zk.wechat.service.WeChatService;
import com.zk.wechat.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * Created by x on 2016/1/18.
 */
@Service(value = "weChatService")
public class WeChatServiceImpl implements WeChatService{

    private Logger logger = LoggerFactory.getLogger(WeChatServiceImpl.class);

    public String processResult(String requestMsg) {
        String result = null;
        Map<String, String> postMap = Utils.parseXML(requestMsg);
        for (Map.Entry<String, String> entry : postMap.entrySet()){
            logger.info(entry.getKey() + "====" + entry.getValue());
        }

        String event = postMap.get(Constant.EVENT);
        event = event == null?null:event.toLowerCase();
        String msgType = postMap.get(Constant.MSGTYPE);
        msgType = msgType == null?null:msgType.toLowerCase();


        if(Constant.MSGTYPE_TEXT.equals(msgType)){
            String message = postMap.get("content");
            TextMessage textMessage = new TextMessage();
            textMessage.setContent("不要和我随便聊天:" + message);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setToUserName(postMap.get("fromusername"));
            textMessage.setFromUserName(Constant.CORPID);
            textMessage.setMsgType("text");
            result = Utils.createXml(textMessage);
        }else if (Constant.MSGTYPE_EVENT.equals(msgType)){
            if(Constant.EVENT_CLICK.equals(event)){
                TextMessage textMessage = new TextMessage();
                textMessage.setContent("卧槽,点我干嘛");
                textMessage.setCreateTime(new Date().getTime());
                textMessage.setToUserName(postMap.get("fromusername"));
                textMessage.setFromUserName(Constant.CORPID);
                textMessage.setMsgType("text");
                result = Utils.createXml(textMessage);
            }else if(Constant.EVENT_LOCATION.equals(event)){
                TextMessage textMessage = new TextMessage();
                textMessage.setContent("你的地址被我获取到了, 小心了");
                textMessage.setCreateTime(new Date().getTime());
                textMessage.setToUserName(postMap.get("fromusername"));
                textMessage.setFromUserName(Constant.CORPID);
                textMessage.setMsgType("text");
                result = Utils.createXml(textMessage);
            }else if(Constant.EVENT_VIEW.equals(event)){
                TextMessage textMessage = new TextMessage();
                textMessage.setContent("你想看什么");
                textMessage.setCreateTime(new Date().getTime());
                textMessage.setToUserName(postMap.get("fromusername"));
                textMessage.setFromUserName(Constant.CORPID);
                textMessage.setMsgType("text");
                result = Utils.createXml(textMessage);
            }
        }
        return result;
    }
}
