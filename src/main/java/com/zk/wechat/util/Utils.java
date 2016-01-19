package com.zk.wechat.util;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.zk.wechat.entity.TextMessage;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.zk.wechat.util.Utils
 *
 * @author: Administrator
 * @Date: 2016/1/16
 * @Time: 13:20
 */
public class Utils {

    private static Logger logger = LoggerFactory.getLogger(Utils.class);

    public static Map<String, String> tokenMap = new HashMap<String, String>();

    public static String parseResultString(InputStream inputStream) {
        String text = null;

        if (inputStream == null) {
            return text;
        }

        try {
            text = IOUtils.toString(inputStream);
        } catch (IOException e) {
            logger.error("InputStream 转换成 String 出错!", e);
        }
        return text;
    }

    public static String postData(HttpServletRequest request) {
        String result = null;
        if (request == null) {
            return result;
        }

        try {
            result = parseResultString(request.getInputStream());
        } catch (IOException e) {
            logger.error("获取post data 出错!", e);
        }
        return result;
    }

    public static JSONObject httpRequest(String requestUrl, String type, String param) {
        logger.info("httpRequest>>>>>>");
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST） 
            httpUrlConn.setRequestMethod(type);

            if ("GET".equalsIgnoreCase(type))
                httpUrlConn.connect();

            // 当有数据需要提交时 
            if (null != param) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码 
                outputStream.write(param.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串 
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源 
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            logger.error("发起https请求并获取结果出错：" + ce.getMessage(), ce);
        } catch (Exception e) {
            logger.error("发起https请求并获取结果出错：" + e.getMessage(), e);
        }
        logger.info("httpRequest<<<<<<");
        return jsonObject;
    }

    public static Map<String, String> parseXML(String request){
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
        InputStream inputStream = null;
        try {
            // 从request中取得输入流
            inputStream = new ByteArrayInputStream(request.getBytes("UTF-8"));
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            List<Element> elementList = root.elements();

            // 遍历所有子节点
            for (Element e : elementList)
                map.put(e.getName().toLowerCase(), e.getText());
        } catch (UnsupportedEncodingException e) {
            logger.error("解析XML失败", e);
        } catch (DocumentException e) {
            logger.error("解析XML失败", e);
        } finally {
            try {
                // 释放资源
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            inputStream = null;
        }
        return map;
    }

    public static String createXml(TextMessage textMessage){
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    /**
     * 扩展xstream，使其支持CDATA块
     *
     * @date 2013-05-19
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                @SuppressWarnings("unchecked")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });
}
