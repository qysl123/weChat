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
            logger.error("InputStream ת���� String ����!", e);
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
            logger.error("��ȡpost data ����!", e);
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
            // ��������ʽ��GET/POST�� 
            httpUrlConn.setRequestMethod(type);

            if ("GET".equalsIgnoreCase(type))
                httpUrlConn.connect();

            // ����������Ҫ�ύʱ 
            if (null != param) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // ע������ʽ����ֹ�������� 
                outputStream.write(param.getBytes("UTF-8"));
                outputStream.close();
            }

            // �����ص�������ת�����ַ��� 
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // �ͷ���Դ 
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            logger.error("����https���󲢻�ȡ�������" + ce.getMessage(), ce);
        } catch (Exception e) {
            logger.error("����https���󲢻�ȡ�������" + e.getMessage(), e);
        }
        logger.info("httpRequest<<<<<<");
        return jsonObject;
    }

    public static Map<String, String> parseXML(String request){
        // ����������洢��HashMap��
        Map<String, String> map = new HashMap<String, String>();
        InputStream inputStream = null;
        try {
            // ��request��ȡ��������
            inputStream = new ByteArrayInputStream(request.getBytes("UTF-8"));
            // ��ȡ������
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            // �õ�xml��Ԫ��
            Element root = document.getRootElement();
            // �õ���Ԫ�ص������ӽڵ�
            List<Element> elementList = root.elements();

            // ���������ӽڵ�
            for (Element e : elementList)
                map.put(e.getName().toLowerCase(), e.getText());
        } catch (UnsupportedEncodingException e) {
            logger.error("����XMLʧ��", e);
        } catch (DocumentException e) {
            logger.error("����XMLʧ��", e);
        } finally {
            try {
                // �ͷ���Դ
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
     * ��չxstream��ʹ��֧��CDATA��
     *
     * @date 2013-05-19
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // ������xml�ڵ��ת��������CDATA���
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
