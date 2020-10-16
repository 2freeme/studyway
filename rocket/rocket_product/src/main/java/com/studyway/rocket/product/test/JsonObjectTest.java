package com.studyway.rocket.product.test;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020/10/16 23:40
 */
public class JsonObjectTest {
    public static void main(String[] args) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("q","w");
        jsonObject.addProperty("q","w2");
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("aa","bb");
        JsonArray jsonElements = new JsonArray();
        jsonElements.add(jsonObject1);
        jsonObject.add("jsonArray",jsonElements);
        System.out.println(jsonObject.toString());

        System.out.println(new Gson().fromJson(jsonObject,JsonDTO.class)); //没找到的时候就会固定设置不进  {"q":"w2","jsonArray":[{"aa":"bb"}]}
        //    private JonArray jsonArray;            //如果是参数类型错误的话就会报错
        ///private List<JsonDTO.JonArray> jsonArray;      JsonDTO(q=w2, w=null, jsonArray=[JsonDTO.JonArray(aa=bb, cc=null)])
        //不会报错    JsonDTO(q=w2, w=null, jsonArray=[JsonDTO.JonArray(aa=bb, cc=null)])







    }

}
