package com.whysly.alimseolap1.models;

public class Age {

    int id;
    String age;
    String created_at;
    String updated_at;
    String deleted_at;
    public Age(int id, String age, String created_at, String updated_at, String deleted_at) {
        this.id = id;
        this.age = age;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at =deleted_at;
    }



    public int getId() {
        return id;
    }

    public String getAge() {
        return age;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Age{" +
                "id=" + id +
                ", age='" + age + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", deleted_at='" + deleted_at + '\'' +
                '}';
    }
}