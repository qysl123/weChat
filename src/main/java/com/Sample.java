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
		buffer.append("{'status':0,'result':{'location':{'lng':116.32298703399,'lat':39.983424051248},'formatted_address':'�����к������йش���27��1101-08��','business':'�йش�,�����ѧ,���ݽ�','addressComponent':{'city':'������','country':'�й�','direction':'����','distance':'7','district':'������','province':'������','street':'�йش���','street_number':'27��1101-08��','country_code':0},'pois':[{'addr':'�����������������йش���27�ţ����������ׯվA1','cp':'NavInfo','direction':'��','distance':'0','name':'����Զ�����ʹ�Ԣ(�йش��)','poiType':'���ز�','point':{'x':116.3229458916,'y':39.983610361549},'tag':'���ز�','tel':'','uid':'35a08504cb51b1138733049d','zip':''},{'addr':'�������йش山���27��','cp':'NavInfo','direction':'����','distance':'25','name':'�йش����','poiType':'���ز�','point':{'x':116.32285606105,'y':39.983568897877},'tag':'���ز�;д��¥','tel':'','uid':'06d2dffdaef1b7ef88f15d04','zip':''},{'addr':'�йش���29','cp':'NavInfo','direction':'��','distance':'62','name':'����ҽԺ�����������ݲ�','poiType':'ҽ��','point':{'x':116.32317046798,'y':39.983016046485},'tag':'ҽ��;ר��ҽԺ','tel':'','uid':'b1c556e81f27cb71b4265502','zip':''},{'addr':'�йش���27���йش����1��','cp':'NavInfo','direction':'����','distance':'1','name':'�й�����Ʋ������йش�Ӫҵ��','poiType':'����','point':{'x':116.32298182382,'y':39.983416864194},'tag':'����;Ͷ�����','tel':'','uid':'060f5e53137d20d7081cc779','zip':''},{'addr':'�����к�����','cp':'NavInfo','direction':'����','distance':'58','name':'�����к���ҽԺ-��Ѫ��','poiType':'ҽ��','point':{'x':116.322685383,'y':39.983092063819},'tag':'ҽ��;����','tel':'','uid':'cf405905b6d82eb9b55f1e89','zip':''},{'addr':'�����к������йش���27���йش���ö���','cp':'NavInfo','direction':'����','distance':'0','name':'ü�ݶ��¾�¥(�йش��)','poiType':'��ʳ','point':{'x':116.32298182382,'y':39.983423774823},'tag':'��ʳ','tel':'','uid':'2c0bd6c57dbdd3b342ab9a8c','zip':''},{'addr':'�����к������йش���29�ţ������ׯ·�ڣ�','cp':'NavInfo','direction':'����','distance':'223','name':'����ҽԺ','poiType':'ҽ��','point':{'x':116.32199368776,'y':39.982083099537},'tag':'ҽ��;�ۺ�ҽԺ','tel':'','uid':'fa01e9371a040053774ff1ca','zip':''},{'addr':'�����к������йش���28��','cp':'NavInfo','direction':'����','distance':'229','name':'�����Ժ','poiType':'��������','point':{'x':116.32476945179,'y':39.982622137118},'tag':'��������;��ӰԺ','tel':'','uid':'edd64ce1a6d799913ee231b3','zip':''},{'addr':'�����ׯ����վ��','cp':'NavInfo','direction':'����','distance':'375','name':'�з������г�(�йش���)','poiType':'����','point':{'x':116.32529945204,'y':39.981537146849},'tag':'����;�ҵ�����','tel':'','uid':'69130523db34c811725e8047','zip':''},{'addr':'�����к�����֪��·128��','cp':'NavInfo','direction':'����','distance':'434','name':'���Ǵ���','poiType':'���ز�','point':{'x':116.32600013033,'y':39.981516414381},'tag':'���ز�;д��¥','tel':'','uid':'d24e48ebb9991cc9afee7ade','zip':''}],'poiRegions':[],'sematic_description':'����Զ�����ʹ�Ԣ(�йش��)��0��','cityCode':131}}");
		int end = buffer.lastIndexOf("}") == buffer.length()?buffer.length():buffer.lastIndexOf("}")+1;

		System.out.println(buffer.substring(buffer.indexOf("{"), end));
	}
}
