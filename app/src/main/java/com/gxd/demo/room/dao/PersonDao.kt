package com.gxd.demo.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.gxd.demo.room.table.Person
import com.gxd.demo.room.table.PersonAndClothes
import com.gxd.demo.room.table.PersonMinimal
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface PersonDao : BaseDao<Person> {
    @Query("DELETE FROM t_person")
    fun deleteAllRx(): Completable

    @Query("DELETE FROM t_person")
    fun deleteAll()

    @Query("SELECT * FROM t_person")
    fun getAll(): Observable<List<Person>>

    @Query("SELECT * FROM t_person WHERE p_id = :id")
    fun getPersonById(id: Int): Observable<Person>

    @Query("SELECT * FROM t_person WHERE p_id IN (:ids)")
    fun getPersonListById(vararg ids: Int): Observable<List<Person>>

    @Query("SELECT * FROM t_person WHERE name = :name AND age = :age")
    fun getPerson(name: String, age: Int): Observable<List<Person>>

    /**
     * 查询部分字段可以通过降低I/O开销提高查询速度
     */
    @Query("SELECT p_id,name,age FROM t_person")
    fun getPersonMinimalList(): Observable<List<PersonMinimal>>

    @Transaction
    fun updateAll(personList: List<Person>) {
        deleteAll()
        insert(personList)
    }


    @Transaction
    @Query("SELECT * FROM t_person")
    fun getPersonAndClothesList(): Observable<List<PersonAndClothes>>
}