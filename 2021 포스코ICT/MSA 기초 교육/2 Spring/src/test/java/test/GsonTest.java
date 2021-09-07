package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import test.entity.Student;

public class GsonTest {

    public static void main(String[] args) {
        // Test1. JSON -> GSON(Java Object)
        String jsonString="{'age':20, 'name':'Sungho','gender':'F'}";
        // Gson 객체 생성
        Gson gson1 = new GsonBuilder().create();
        // JSON -> GSON(Java Object)
        Student student1 = gson1.fromJson(jsonString, Student.class);
        student1.getInfo();

        // Test2. GSON(Java Object) -> JSON
        Student student2 = new Student("A",20,"ASH");
        // Gson 객체 생성
        Gson gson2 = new GsonBuilder().create();
        // GSON(Java Object) -> JSON
        String json = gson1.toJson(student2);
        System.out.println(json);
    }
}
