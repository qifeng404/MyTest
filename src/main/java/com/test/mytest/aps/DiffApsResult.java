package com.test.mytest.aps;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.alibaba.fastjson.JSONObject;

import difflib.Delta;
import difflib.DiffRow;
import difflib.DiffRow.Tag;
import difflib.DiffRowGenerator;
import difflib.DiffUtils;
import difflib.Patch;

public class DiffApsResult {
    
   private static String filePublish = "/Users/qifeng/Downloads/定位相关知识/性能数据对比/100000_publish.log";
   private static String filePre = "/Users/qifeng/Downloads/定位相关知识/性能数据对比/100000_pre.log";
   
   private static String fileDiff = "/Users/qifeng/Downloads/定位相关知识/性能数据对比/diffResult_10w.log";
   
   private static Map<String,String> mapPublish = new HashMap<String,String>();
   private static Map<String,String> mapPre = new HashMap<String,String>();
   
   private static Map<String,String> mapReq = new HashMap<String,String>();
   
   
   //改造消耗的时间比线上时间短的接口个数（意味成功）
   private static Integer sucCount = 0;
   //改造消耗的时间比线上时间长的接口个数（意味失败）
   private static Integer failCount = 0;
   
   private static Integer equaCount = 0;
   
   //线上和预发返回结果至少有一个为空个数
   private static Integer respEmpCount = 0;
   
   //线上返回结果为异常个数
   private static Integer pubErrorCount = 0;
   //预发返回结果为异常个数
   private static Integer preErrorCount = 0;
   
   //线上相同请求返回结果不同个数
   private static Integer pubDiffCount = 0;
   //预发相同请求返回结果不同个数
   private static Integer preDiffCount = 0;
   
   //线上相同请求返回结果不同个数
   private static Integer pubEquCount = 0;
   //预发相同请求返回结果不同个数
   private static Integer preEquCount = 0;
   
   private static Integer diffRetypeCount = 0;
   
   private static Map<String,Integer> mapPublishTime = new HashMap<String,Integer>();
   private static Map<String,Integer> mapPreTime = new HashMap<String,Integer>();
 
   
   
   private static int diffNum = 0;
   
   private static File file = null;
   
   static{
       file = new File(fileDiff);  
   }
   

   private static String cenxPath = "/Cell_Req/DRA/cenx";
   private static String cenyPath = "/Cell_Req/DRA/ceny";
   private static String radiusPath = "/Cell_Req/DRA/radius";
   private static String retypePath = "/Cell_Req/DRA/retype";

   private static String cenxPath1 ="/location/cenx";
   private static String cenyPath1 ="/location/ceny";
   private static String radiusPath1 ="/location/radius";
   private static String retypePath1 = "/location/retype";
   
    public static void main(String[] args) throws Exception {
        System. out .println( " 内存信息 :" + toMemoryInfo());
        parseResult();
        //testCompare();
        
    }
    
    /**
     * 
     * 获取当前 jvm 的内存信息
     * @return
     * 
     */

    public static String toMemoryInfo() {

        Runtime currRuntime = Runtime.getRuntime();

        int nFreeMemory = (int) (currRuntime.freeMemory() / 1024 / 1024);

        int nTotalMemory = (int) (currRuntime.totalMemory() / 1024 / 1024);

        return nFreeMemory + "M/" + nTotalMemory + "M(free/total)";

    }
   
   
    private static void parseResult() throws IOException, DocumentException {
        FileUtils.write(file,"diff日志开始\n"); 
        FileUtils.write(file,"==========" + "\n", true);
        
        List<String> publish = FileUtils.readLines(new File(filePublish)); 
        int publishSize = publish.size();
        fillMap(publish, mapPublish, mapPublishTime, 1);
        publish.clear();
        
        List<String> pre = FileUtils.readLines(new File(filePre));
        int preSize = pre.size();
        fillMap(pre, mapPre, mapPreTime, 2);        
        pre.clear();
        
        testRespXmlDiff();
        
        FileUtils.write(file,"生产网日志数" + publishSize + "\n", true);
        FileUtils.write(file,"预发日志数" + preSize + "\n", true);
        FileUtils.write(file,"生产网有效结果数" + mapPublish.size() + "\n", true);
        FileUtils.write(file,"预发测试代码有效结果数" + mapPre.size() + "\n", true);
        FileUtils.write(file,"diff对比完成,优化时间成功的接口个数:" + sucCount + "\n", true);
        FileUtils.write(file,"diff对比完成,优化时间失败的接口个数:" + failCount + "\n", true);
        FileUtils.write(file,"diff对比完成,优化时间相等的接口个数:" + equaCount + "\n", true);
        FileUtils.write(file,"diff对比完成,结果不一致个数为:" + diffNum + "\n", true);
        FileUtils.write(file,"diff对比完成,结果至少有一个为空个数为:" + respEmpCount + "\n", true);
        FileUtils.write(file,"diff对比完成,线上日志返回异常个数为:" + pubErrorCount + "\n", true);
        FileUtils.write(file,"diff对比完成,预发日志返回异常个数为:" + preErrorCount + "\n", true);
        FileUtils.write(file,"diff对比完成,线上相同请求返回结果不同个数为:" + pubDiffCount + "\n", true);
        FileUtils.write(file,"diff对比完成,预发相同请求返回结果不同个数为:" + preDiffCount + "\n", true);
        FileUtils.write(file,"diff对比完成,线上相同请求返回结果相同个数为:" + pubEquCount + "\n", true);
        FileUtils.write(file,"diff对比完成,预发相同请求返回结果相同个数为:" + preEquCount + "\n", true);
        FileUtils.write(file,"diff对比完成,返回结果retype不同个数为:" + diffRetypeCount + "\n", true);
    }

    private static void testRespXmlDiff() throws IOException, DocumentException{
        //针对预发和线上相同req对应的不同resp的解析xml结果，重点diff cenx、cen、radius三个字段
        for(String req : mapPublish.keySet()){
            String publish = mapPublish.get(req);
            String pre = mapPre.get(req);
            
            if(StringUtils.isBlank(publish) || StringUtils.isBlank(pre)){
                respEmpCount ++;
                diffNum ++;
                FileUtils.write(file,"diff change: " + diffNum  + "\n", true);
                FileUtils.write(file,"返回结果线上和预发至少有一个为空" + req + "\n", true);
                FileUtils.write(file,"publish-> " + publish + "\n", true);  
                FileUtils.write(file,"pre->     " + pre + "\n", true); 
                FileUtils.write(file,"==========" + "\n", true);
                continue;
            }
            
            Integer pubTime = mapPublishTime.get(req);
            Integer yufaTime = mapPreTime.get(req);
            //判断新旧代码接口的消耗时间
            if(pubTime > yufaTime){
                sucCount ++;
            }else if(pubTime < yufaTime){
                failCount ++;
            }else{
                equaCount ++;
            }
            //判断返回内容不一致，记录日志
            if(!publish.equals(pre)){
                if(!parseXmlDiff(publish, pre)){
                    diffNum ++; 
                    FileUtils.write(file,"diff change: " + diffNum  + "\n", true);
                    FileUtils.write(file,"req-> " + mapReq.get(req)  + "\n", true);  
                    FileUtils.write(file,"publish-> " + publish + "\n", true);  
                    FileUtils.write(file,"pre->     " + pre + "\n", true); 
                    FileUtils.write(file,"==========" + "\n", true);
                }   
            }
        }            
    }

    private static boolean parseXmlDiff(String publish, String pre) throws IOException, DocumentException {
        //ERROR:的情况的就不能对比了，直接返回error
        if(publish.startsWith("ERROR:") || pre.startsWith("ERROR:")){
            return false;
        }
        try {
            String cenx,ceny,radius,cenx1,ceny1,radius1,retype = null,retype1 = null;
            if(publish.contains("<?xml version=\"1.0\"")){
                // 转换为xml进行解析
                Document pubDocument = DocumentHelper.parseText(publish);
                if (pubDocument == null) {
                    return false;
                }
                Node node = pubDocument.selectSingleNode(cenxPath1);
                if(node != null){
                    cenx = pubDocument.selectSingleNode(cenxPath1).getText();
                    ceny = pubDocument.selectSingleNode(cenyPath1).getText();
                    radius = pubDocument.selectSingleNode(radiusPath1).getText();
                    //retype = pubDocument.selectSingleNode(retypePath1).getText();
                    
                }else{
                    cenx = pubDocument.selectSingleNode(cenxPath).getText();
                    ceny = pubDocument.selectSingleNode(cenyPath).getText();
                    radius = pubDocument.selectSingleNode(radiusPath).getText();
                    retype = pubDocument.selectSingleNode(retypePath).getText();
                }
            }else{
                //json格式转换解析
                JSONObject obj = JSONObject.parseObject(publish);
                if(obj == null){
                    return false;
                }
                JSONObject location = obj.getJSONObject("location");
                cenx = location.getString("cenx");
                ceny = location.getString("ceny");
                radius = location.getString("radius"); 
                retype = obj.getString("retype");
            }
            
            if(pre.contains("<?xml version=\"1.0\"")){
                // 转换为xml进行解析
                Document preDocument = DocumentHelper.parseText(pre);
                if (preDocument == null) {
                    return false;
                }
                Node node = preDocument.selectSingleNode(cenxPath1);
                if(node != null){
                    cenx1 = preDocument.selectSingleNode(cenxPath1).getText();
                    ceny1 = preDocument.selectSingleNode(cenyPath1).getText();
                    radius1 = preDocument.selectSingleNode(radiusPath1).getText();
                    //retype1 = preDocument.selectSingleNode(retypePath1).getText();
                }else{
                    cenx1 = preDocument.selectSingleNode(cenxPath).getText();
                    ceny1 = preDocument.selectSingleNode(cenyPath).getText();
                    radius1 = preDocument.selectSingleNode(radiusPath).getText();
                    retype1 = preDocument.selectSingleNode(retypePath).getText();
                }
            }else{
                //json格式转换解析
                JSONObject obj = JSONObject.parseObject(pre);
                if(obj == null){
                    return false;
                }
                JSONObject location = obj.getJSONObject("location");
                cenx1 = location.getString("cenx");
                ceny1 = location.getString("ceny");
                radius1 = location.getString("radius");   
                retype1 = obj.getString("retype");
            }

            if(StringUtils.isBlank(retype) && StringUtils.isBlank(retype1)){
                if (cenx.equals(cenx1) && ceny.equals(ceny1) && radius.equals(radius1)) {
                    return true;
                }else{
                    //计算经纬度逐渐的距离，距离小于10m，认为近似相等
                    //Double dis = distance(Double.parseDouble(cenx1), Double.parseDouble(ceny1), Double.parseDouble(cenx), Double.parseDouble(ceny));
                    //FileUtils.write(file, "distance-> " + dis + "\n", true);
                    //return dis >= 10.0 ? false : true;
                    FileUtils.write(file, "publish cenx-> " + cenx + ";ceny-> " + ceny + ";radius-> " + radius + ";retype-> " + retype + "\n", true);
                    FileUtils.write(file, "pre     cenx-> " + cenx1 + ";ceny-> " + ceny1 + ";radius-> " + radius1 + ";retype-> " + retype1 + "\n", true);
                    return false;
                }
            }else if(retype.equals(retype1)){
                //采用算法相同的情况下，才比较经纬度合理
                if (cenx.equals(cenx1) && ceny.equals(ceny1) && radius.equals(radius1)) {
                    return true;
                }else{
                    //计算经纬度逐渐的距离，距离小于10m，认为近似相等
                    //Double dis = distance(Double.parseDouble(cenx1), Double.parseDouble(ceny1), Double.parseDouble(cenx), Double.parseDouble(ceny));
                    //FileUtils.write(file, "distance-> " + dis + "\n", true);
                    //return dis >= 10.0 ? false : true;
                    FileUtils.write(file, "publish cenx-> " + cenx + ";ceny-> " + ceny + ";radius-> " + radius + ";retype-> " + retype + "\n", true);
                    FileUtils.write(file, "pre     cenx-> " + cenx1 + ";ceny-> " + ceny1 + ";radius-> " + radius1 + ";retype-> " + retype1 + "\n", true);
                    return false;
                }
            }else {
                diffRetypeCount ++;
                FileUtils.write(file, "publish cenx-> " + cenx + ";ceny-> " + ceny + ";radius-> " + radius + ";retype-> " + retype + "\n", true);
                FileUtils.write(file, "pre     cenx-> " + cenx1 + ";ceny-> " + ceny1 + ";radius-> " + radius1 + ";retype-> " + retype1 + "\n", true);
                return false;
            }
        } catch (Exception e) {
            System.out.println(publish);
            System.out.println(pre);
            e.printStackTrace();
            return false;
        }
    }

    
    private static final double RC = 6378137.0;
    private static final double RJ = 6356725.0;

    public static double distance(double lng, double lat, double lng1,
            double lat1) {
        double rlat = lat * Math.PI / 180.0;
        double rlon = lng * Math.PI / 180.0;
        double rlat1 = lat1 * Math.PI / 180.0;
        double rlon1 = lng1 * Math.PI / 180.0;
        double Ec = RJ + (RC - RJ) * (90.0 - lat) / 90.0;
        double Ed = Ec * Math.cos(rlat);
        double dx = (rlon1 - rlon) * Ed;
        double dy = (rlat1 - rlat) * Ec;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private static void fillMap(List<String> publish, Map<String, String> map, Map<String, Integer> mapTime, Integer flag) {
        for(String line : publish){
            String[] logs = line.split("#");
            String timeInfo = logs[0];
            //解析时间总体信息，获取接口总耗时时间
            String[] times = timeInfo.split(" ", -1);
            Integer time = Integer.parseInt(times[2]);  
            //String ip = logs[1];
            String req = logs[2]/*.replaceAll("<appname>[\\w\\W]*</appname>", "")*/.replaceAll("<serverip>[\\w\\W]*</serverip>", "")/*.replaceAll("<license>[\\w\\W]*</license>", "")*/.replaceAll("\\s+", " ");
            String resp = logs[3].replace("resp:", "").trim();
            if(logs.length >= 5 && (logs[4].endsWith("</Cell_Req>") || logs[4].endsWith("}"))){
                resp = resp + "#"  + logs[4];
            }
            if(logs.length >= 6 && (logs[5].endsWith("</Cell_Req>") || logs[5].endsWith("}"))){
                resp = resp + "#"  + logs[5];
            }
            mapReq.put(req, logs[2]);
            if (!map.containsKey(req)) {
                map.put(req, resp);
                mapTime.put(req, time);
                if (resp.startsWith("ERROR:")) {
                    if (flag == 1) {
                        pubErrorCount++;
                    } else {
                        preErrorCount++;
                    }
                }
            } else {
                if (resp.startsWith("ERROR:")) {
                    if (flag == 1) {
                        pubErrorCount++;
                    } else {
                        preErrorCount++;
                    }
                }
                // 比较相同请求后的多次返回结果不一致，打印出来
                if (!map.get(req).equals(resp)) {
                    // FileUtils.write(file,"flag" + flag + " req:" + req + "出现相同请求返回结果不一致的情况 " + "\n", true);
                    // FileUtils.write(file,"old-> " + map.get(req) + "\n", true);
                    // FileUtils.write(file,"new-> " + resp + "\n", true);
                    System.out.println("flag" + flag + " req:" + mapReq.get(req) + "出现相同请求返回结果不一致的情况 ");
                    System.out.println("old-> " + map.get(req));
                    System.out.println("new-> " + resp);
                    if (flag == 1) {
                        pubDiffCount++;
                    } else {
                        preDiffCount++;
                    }
                }else{
                    if (flag == 1) {
                        pubEquCount++;
                    } else {
                        preEquCount++;
                    }
                }
            }         
        }       
    }

    private static void testCompare() throws IOException{
        List<String> publish = FileUtils.readLines(new File(filePublish));  
        List<String> pre = FileUtils.readLines(new File(filePre));  
        
        Patch patch = DiffUtils.diff(publish, pre);  
  
        for (Delta delta : patch.getDeltas()) {  
            List<?> listOld = delta.getOriginal().getLines();
            List<?> listNew = delta.getRevised().getLines();
            for (Object object : listNew) { 
                int pos = listNew.indexOf(object);
                System.out.println(listOld.get(pos));  
                System.out.println(object);  
            }  
        }  
          
        DiffRowGenerator.Builder builder = new DiffRowGenerator.Builder();  
        builder.showInlineDiffs(false);  
        DiffRowGenerator generator = builder.build();  
        for (Delta delta :  patch.getDeltas()) {  
            List<DiffRow> generateDiffRows = generator.generateDiffRows((List<String>)delta.getOriginal().getLines(), (List<String>)delta.getRevised().getLines());   
            for (DiffRow row : generateDiffRows) {  
                Tag tag = row.getTag();  
                if (tag == Tag.INSERT) {  
                    System.out.println("Insert: ");  
                    System.out.println("new-> " + row.getNewLine());  
                    System.out.println("");  
                } else if (tag == Tag.CHANGE) {  
                    System.out.println("change: ");  
                    System.out.println("old-> " + row.getOldLine());  
                    System.out.println("new-> " + row.getNewLine());  
                    System.out.println("");  
                } else if (tag == Tag.DELETE) {  
                    System.out.println("delete: ");  
                    System.out.println("old-> " + row.getOldLine());  
                    System.out.println("");  
                } else if (tag == Tag.EQUAL) {  
                    System.out.println("equal: ");  
                    System.out.println("old-> " +  row.getOldLine());  
                    System.out.println("new-> " +  row.getNewLine());  
                    System.out.println("");  
                } else {  
                    throw new IllegalStateException("Unknown pattern tag: " + tag);  
                }  
            }  
        } 
        
    }

}
