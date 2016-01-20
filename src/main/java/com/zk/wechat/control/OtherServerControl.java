package com.zk.wechat.control;

import com.alibaba.fastjson.JSONObject;
import com.zk.wechat.constant.Constant;
import com.zk.wechat.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("otherServerControl")
public class OtherServerControl {

    private Logger logger = LoggerFactory.getLogger(OtherServerControl.class);

    @RequestMapping(value = "doSoming")
    @ResponseBody
    public String doSoming(HttpServletRequest request) {
        String tokenValue = Utils.tokenMap.get(Constant.CORPSECRET);
        String code = request.getParameter("code");
        //String state = request.getParameter("state");
        if (StringUtils.isEmpty(tokenValue) || StringUtils.isEmpty(code)) {
            return null;
        }

        String getUserUrl = Constant.GET_USERID.replace("${tokenValue}", tokenValue).replace("${code}", code);
        JSONObject json = Utils.httpRequest(getUserUrl, "GET", null);
        logger.info(json.toJSONString());
        String userId = json.getString("UserId");
        return userId;
    }
}
