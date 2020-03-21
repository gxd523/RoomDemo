package com.demo.room.bean;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "t_person")
public class Person {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "p_id")
    public int pid;

    /*不修改字段名就不需要加@ColumnInfo注解*/
    public String name;

    public int age;

    @Embedded(prefix = "one")/*实体类中引用其他实体类*/
    public Address address1;
    @Embedded(prefix = "two")/*实体类中引用其他实体类*/
    public Address address2;

    @Ignore
    public float income;
//    /**
//     * 新加字段,用于演示数据库升级
//     */
//    public String phone;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @NonNull
    @Override
    public String toString() {
        return "[" + pid + ", " +
                name + ", " +
                age + "岁, " +
                income + "元" +
                (address1 == null ? ", " : address1.toString()) +
                (address2 == null ? ", " : address2.toString()) + "]";
    }

    public static class Address {
        public String city;
        public String street;

        public Address(String city, String street) {
            this.city = city;
            this.street = street;
        }

        @NonNull
        @Override
        public String toString() {
            return ", " + city + ", " + street;
        }
    }
}
