package com.demo.room.bean;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

/**
 * Created by guoxiaodong on 2020/3/21 16:09
 */
public class PersonAndClothes {
    @Embedded
    public Person person;

    @Relation(parentColumn = "p_id", entityColumn = "father_id")
    public List<Clothes> clothesList;
}
