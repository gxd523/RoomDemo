package com.demo.room.bean;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        primaryKeys = {"brand", "color"}/*复合主键*/,
        foreignKeys = @ForeignKey(/*外键*/
                entity = Person.class,
                parentColumns = "p_id",
                childColumns = "father_id",
                onUpdate = ForeignKey.CASCADE,/*person表的一条数据的pid更新时会更新fatherId*/
                onDelete = ForeignKey.CASCADE/*person表的一条数据的被删除时会删除与pid相同的fatherId的数据*/
        )
)
public class Clothes {
    @NonNull/*复合主键的字段要加*/
    public String color;

    @NonNull/*复合主键的字段要加*/
    public String brand;

    public String size;

    @ColumnInfo(name = "father_id")
    public int fatherId;

    public Clothes(@NonNull String color, @NonNull String brand, String size, int fatherId) {
        this.color = color;
        this.brand = brand;
        this.size = size;
        this.fatherId = fatherId;
    }

    @NonNull
    @Override
    public String toString() {
        return " \nclothes-----------\nbrand-->" + brand +
                "\ncolor-->" + color +
                "\nsize-->" + size +
                "\nfatherId-->" + fatherId;
    }
}
