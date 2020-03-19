package com.demo.room;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.demo.room.bean.Clothes;
import com.demo.room.bean.Person;
import com.demo.room.dao.ClothesDao;
import com.demo.room.dao.PersonDao;
import com.demo.room.manager.MyDataBaseManager;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PersonDao personDao = MyDataBaseManager.dataBase.getPersonDao();
        personDao.deleteAll();
        ClothesDao clothesDao = MyDataBaseManager.dataBase.getClothesDao();
        clothesDao.deleteAll();

        Person gxd = new Person("gxd", 25);
        gxd.address1 = new Person.Address("hangzhou", "qiaosi");
        gxd.address2 = new Person.Address("yining", "ailanmubage");
        personDao.insert(gxd);
        personDao.insert(new Person("gxt", 22));

        printPerson(personDao);

        clothesDao.insert(new Clothes("red", "uniqlo", "xl", personDao.getPersonByMultiCondition("gxd", 25).pid));
        clothesDao.insert(new Clothes("red", "uniqlo", "s", personDao.getPersonByMultiCondition("gxd", 25).pid));
        clothesDao.insert(new Clothes("red", "mind_bridge", "xs", personDao.getPersonByMultiCondition("gxt", 22).pid));

        printClothes(clothesDao);

//        personDao.delete(personDao.getPersonByMultiCondition("gxd", 25));

        printPerson(personDao);
        printClothes(clothesDao);
    }

    private void printPerson(PersonDao personDao) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Person person : personDao.getAll()) {
            stringBuilder.append(person.toString());
        }
        Log.d("gxd", stringBuilder.toString());
    }

    private void printClothes(ClothesDao clothesDao) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Clothes clothes : clothesDao.getAll()) {
            stringBuilder.append(clothes.toString());
        }
        Log.d("gxd", stringBuilder.toString());
    }
}
