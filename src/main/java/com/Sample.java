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
		StringBuffer buffer = new StringBuffer();
		buffer.append("{'status':0,'result':{'location':{'lng':116.32298703399,'lat':39.983424051248},'formatted_address':'北京市海淀区中关村大街27号1101-08室','business':'中关村,人民大学,苏州街','addressComponent':{'city':'北京市','country':'中国','direction':'附近','distance':'7','district':'海淀区','province':'北京市','street':'中关村大街','street_number':'27号1101-08室','country_code':0},'pois':[{'addr':'北京北京海淀海淀区中关村大街27号（地铁海淀黄庄站A1','cp':'NavInfo','direction':'内','distance':'0','name':'北京远景国际公寓(中关村店)','poiType':'房地产','point':{'x':116.3229458916,'y':39.983610361549},'tag':'房地产','tel':'','uid':'35a08504cb51b1138733049d','zip':''},{'addr':'海淀区中关村北大街27号','cp':'NavInfo','direction':'附近','distance':'25','name':'中关村大厦','poiType':'房地产','point':{'x':116.32285606105,'y':39.983568897877},'tag':'房地产;写字楼','tel':'','uid':'06d2dffdaef1b7ef88f15d04','zip':''},{'addr':'中关村大街29','cp':'NavInfo','direction':'北','distance':'62','name':'海淀医院激光整形美容部','poiType':'医疗','point':{'x':116.32317046798,'y':39.983016046485},'tag':'医疗;专科医院','tel':'','uid':'b1c556e81f27cb71b4265502','zip':''},{'addr':'中关村大街27号中关村大厦1层','cp':'NavInfo','direction':'附近','distance':'1','name':'中国人民财产保险中关村营业部','poiType':'金融','point':{'x':116.32298182382,'y':39.983416864194},'tag':'金融;投资理财','tel':'','uid':'060f5e53137d20d7081cc779','zip':''},{'addr':'北京市海淀区','cp':'NavInfo','direction':'东北','distance':'58','name':'北京市海淀医院-输血科','poiType':'医疗','point':{'x':116.322685383,'y':39.983092063819},'tag':'医疗;其他','tel':'','uid':'cf405905b6d82eb9b55f1e89','zip':''},{'addr':'北京市海淀区中关村大街27号中关村大厦二层','cp':'NavInfo','direction':'附近','distance':'0','name':'眉州东坡酒楼(中关村店)','poiType':'美食','point':{'x':116.32298182382,'y':39.983423774823},'tag':'美食','tel':'','uid':'2c0bd6c57dbdd3b342ab9a8c','zip':''},{'addr':'北京市海淀区中关村大街29号（海淀黄庄路口）','cp':'NavInfo','direction':'东北','distance':'223','name':'海淀医院','poiType':'医疗','point':{'x':116.32199368776,'y':39.982083099537},'tag':'医疗;综合医院','tel':'','uid':'fa01e9371a040053774ff1ca','zip':''},{'addr':'北京市海淀区中关村大街28号','cp':'NavInfo','direction':'西北','distance':'229','name':'海淀剧院','poiType':'休闲娱乐','point':{'x':116.32476945179,'y':39.982622137118},'tag':'休闲娱乐;电影院','tel':'','uid':'edd64ce1a6d799913ee231b3','zip':''},{'addr':'海淀黄庄地铁站旁','cp':'NavInfo','direction':'西北','distance':'375','name':'中发电子市场(中关村大街)','poiType':'购物','point':{'x':116.32529945204,'y':39.981537146849},'tag':'购物;家电数码','tel':'','uid':'69130523db34c811725e8047','zip':''},{'addr':'北京市海淀区知春路128号','cp':'NavInfo','direction':'西北','distance':'434','name':'泛亚大厦','poiType':'房地产','point':{'x':116.32600013033,'y':39.981516414381},'tag':'房地产;写字楼','tel':'','uid':'d24e48ebb9991cc9afee7ade','zip':''}],'poiRegions':[],'sematic_description':'北京远景国际公寓(中关村店)内0米','cityCode':131}}");
		int end = buffer.lastIndexOf("}") == buffer.length()?buffer.length():buffer.lastIndexOf("}")+1;

		System.out.println(buffer.substring(buffer.indexOf("{"), end));
	}
}
