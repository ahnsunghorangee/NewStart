package test.entity;

public class Student {

    private String id;
    private int age;
    private String name;

    public Student(String id, int age, String name){
        this.age = age;
        this.name = name;
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void getInfo(){
        System.out.println(id+" "+ "이름은: "+this.name+"이고, 나이는 "+this.age+"입니다.");
    }
}
