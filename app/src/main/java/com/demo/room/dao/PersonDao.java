package com.demo.room.dao;

import com.demo.room.bean.Person;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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
    void insert(Person... persons);

    @Query("DELETE FROM t_person")
    void deleteAll();

    /**
     * 一次删除单条数据或多条
     */
    @Delete
    void delete(Person... persons);

    /**
     * 一次更新单条数据或多条
     */
    @Update
    void update(Person... persons);

    @Query("SELECT * FROM t_person")
    List<Person> getAll();

    @Query("SELECT * FROM t_person WHERE p_id= :pid")
    Person getPersonByPid(int pid);

    /**
     * 一次查找多个数据
     */
    @Query("SELECT * FROM t_person WHERE p_id IN (:pidList)")
    List<Person> getPersonListByPids(List<Integer> pidList);

    /**
     * 多个条件查找
     */
    @Query("SELECT * FROM t_person WHERE name = :name AND age = :age")
    Person getPersonByMultiCondition(String name, int age);
}
