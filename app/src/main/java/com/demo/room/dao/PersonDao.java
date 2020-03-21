package com.demo.room.dao;

import com.demo.room.bean.Person;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface PersonDao {
    /**
     * 一次插入单条数据或多条
     * OnConflictStrategy.REPLACE:替换旧数据
     * OnConflictStrategy.ROLLBACK:回滚事务
     * OnConflictStrategy.ABORT:终止事务崩溃
     * OnConflictStrategy.FAIL:事务失败
     * OnConflictStrategy.IGNORE:忽略冲突
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    Completable insert(Person... persons);

    @Query("DELETE FROM t_person")
    Completable deleteAll();

    /**
     * 一次删除单条数据或多条
     */
    @Delete
    Completable delete(Person... persons);

    /**
     * 一次更新单条数据或多条
     */
    @Update
    Completable update(Person... persons);

    @Query("SELECT * FROM t_person")
    Observable<List<Person>> getAll();

    @Query("SELECT * FROM t_person WHERE p_id= :pid")
    Single<Person> getPersonByPid(int pid);

    /**
     * 一次查找多个数据
     */
    @Query("SELECT * FROM t_person WHERE p_id IN (:pidList)")
    Single<List<Person>> getPersonListByPids(List<Integer> pidList);

    /**
     * 多个条件查找
     */
    @Query("SELECT * FROM t_person WHERE name = :name AND age = :age")
    Single<Person> getPersonByMultiCondition(String name, int age);
}
