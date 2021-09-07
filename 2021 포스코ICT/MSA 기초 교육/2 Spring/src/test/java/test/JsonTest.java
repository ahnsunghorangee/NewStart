package test;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonTest {

    public static void main(String[] args) {
        String jsonString = "{'age':20, 'name':'sunho', 'student':{'age':22,'name':'ahn'}}";

        try{
            // JSONObject: json형식의 매개변수를 json 객체로 만들어준다.
            JSONObject json = new JSONObject(jsonString);
            System.out.println(json);

            // JSON 객체의 get("key") 메서드로 value만을 가져올 수 있다.
            System.out.println("age: "+json.getInt("age"));

            // json안의 객체를 getJSONObject("key") 메서드로 가져올 수 있다.
            System.out.println("student: " + json.getJSONObject("student"));

            // JSON Object 생성 -> key-value 형식
            JSONObject object = new JSONObject();
            object.put("gener", "F");
            object.put("animal",123);
            System.out.println("json 객체 생성: " + object);
        } catch(JSONException e){
            e.printStackTrace();
        }
    }
}
