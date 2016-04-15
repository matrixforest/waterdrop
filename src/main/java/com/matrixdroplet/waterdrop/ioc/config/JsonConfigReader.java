package com.matrixdroplet.waterdrop.ioc.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * Created by li on 2016/4/14.
 */
public class JsonConfigReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonConfigReader.class);
    private Config config;

    public JsonConfigReader() {
        init();
    }

    public void init() {
        InputStream resourceAsStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader tBufferedReader = null;
        try {
//            URL url = this.getClass().getClassLoader().getResource("waterdrop.json");
            resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("waterdrop.json");
//            ByteSource byteSource = Resources.asByteSource(url);
//            String jsonStr=new String(byteSource.read());
            inputStreamReader = new InputStreamReader(resourceAsStream);
            tBufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer tStringBuffer = new StringBuffer();
            String sTempOneLine = new String("");
            while ((sTempOneLine = tBufferedReader.readLine()) != null) {
                tStringBuffer.append(sTempOneLine);
            }
            JSONObject waterdrop = JSON.parseObject(tStringBuffer.toString());
            JSONObject config = waterdrop.getJSONObject("waterdrop");

            //是否开启web支持
            Boolean web = config.getBoolean("web");

            //注解扫描包路径
            JSONArray packageNames = config.getJSONArray("packageNames");
            List<String> packageList = Lists.newArrayList();
            for (Object packageName : packageNames) {
                String pn = ((JSONObject) packageName).getString("packageName");
                packageList.add(pn);
            }

            //视图前后缀
            String view_pref = config.getString("view_pref");
            String view_suffix = config.getString("view_suffix");

            //静态资源
            List<String> staticPathList = Lists.newArrayList();
            JSONArray staticFilePaths = config.getJSONArray("staticFilePaths");
            if (staticFilePaths != null && staticFilePaths.size() > 0) {
                for (Object staticFilePath : staticFilePaths) {
                    String path = ((JSONObject) staticFilePath).getString("staticFilePath");
                    staticPathList.add(path);
                }
            }

            //数据库
            JSONObject database = config.getJSONObject("database");
            Map<String,String> dbMap=Maps.newHashMap();
            dbMap.put("driver",database.getString("driver"));
            dbMap.put("url",database.getString("url"));
            dbMap.put("username",database.getString("username"));
            dbMap.put("password",database.getString("password"));

            this.config = new Config(web, packageList.toArray(new String[packageList.size()]), view_pref, view_suffix, staticPathList.toArray(new String[staticPathList.size()]),dbMap);
        } catch (Exception e) {
            LOGGER.warn("加载配置文件出错");
            e.printStackTrace();
        } finally {
            if (tBufferedReader != null) {
                try {
                    tBufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (resourceAsStream != null) {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Config getConfig() {
        return config;
    }
}
