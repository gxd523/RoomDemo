package com.demo.room.bean;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

/**
 * Created by guoxiaodong on 2020/3/21 16:09
 */
public class PersonAndClothes {
    @Embedded
    public Person person;

    @Relation(parentColumn = "p_id",entityColumn = "father_id")
    public List<Clothes> clothesList;
}
