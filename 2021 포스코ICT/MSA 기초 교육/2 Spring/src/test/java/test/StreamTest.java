package test;

import test.entity.Student;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamTest {

    static Map<String, Student> map;
    public static void main(String[] args) {
        map = new LinkedHashMap<>();
        map.put("A",new Student("A",10,"sungho"));
        map.put("B",new Student("B",10,"rooney"));
        map.put("C",new Student("C",20,"ronaldo"));
        map.put("D",new Student("D",20,"messi"));
        map.put("E",new Student("E",30,"son"));
        map.put("F",new Student("F",30,"park"));

        List<Student> collect = map.values().stream()
                .filter(data -> data.getAge() == 10)
                .collect(Collectors.toList());

    }

    public void remove(String num){
        map.remove(num);
    }

}
