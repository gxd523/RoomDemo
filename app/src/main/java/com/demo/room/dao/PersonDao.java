package com.demo.room.dao;

import com.demo.room.bean.Person;
import com.demo.room.bean.PersonAndClothes;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public abstract class PersonDao implements BaseDao<Person> {
    @Query("DELETE FROM t_person")
    public abstract Completable deleteAll();

    @Query("SELECT * FROM t_person")
    public abstract Observable<List<Person>> getAll();

    @Query("SELECT * FROM t_person WHERE p_id= :pid")
    abstract Single<Person> getPersonByPid(int pid);

    /**
     * 一次查找多个数据
     */
    @Query("SELECT * FROM t_person WHERE p_id IN (:pidList)")
    abstract Single<List<Person>> getPersonListByPids(List<Integer> pidList);

    /**
     * 多个条件查找
     */
    @Query("SELECT * FROM t_person WHERE name = :name AND age = :age")
    public abstract Single<Person> getPersonByMultiCondition(String name, int age);

    @Insert
    abstract void insert1(Person... psersons);

    @Query("DELETE FROM t_person")
    abstract void deleteAll1();

    @Transaction
    public void deleteAllAndInsert(Person person) {
        deleteAll1();
        insert1(person);
    }

    @Query("SELECT * FROM t_person")
    public abstract Single<List<PersonAndClothes>> getPersonAndRelatedClothes();
}
