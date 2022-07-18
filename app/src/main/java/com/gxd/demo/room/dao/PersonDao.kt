package com.gxd.demo.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.gxd.demo.room.data.PersonAndClothes
import com.gxd.demo.room.data.PersonClothes
import com.gxd.demo.room.data.PersonMinimal
import com.gxd.demo.room.table.Clothes
import com.gxd.demo.room.table.Person
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface PersonDao : BaseDao<Person> {
    @Query("DELETE FROM t_person")
    fun deleteAllRx(): Completable

    @Query("DELETE FROM t_person")
    fun deleteAll()

    @Query("SELECT * FROM t_person")
    fun queryAll(): Single<List<Person>>

    @Query("SELECT * FROM t_person WHERE p_id = :id")
    fun queryPersonById(id: Int): Single<Person>

    @Query("SELECT * FROM t_person WHERE p_id IN (:ids)")
    fun queryPersonListById(vararg ids: Int): Single<List<Person>>

    @Query("SELECT * FROM t_person WHERE name = :name AND age = :age")
    fun queryPerson(name: String, age: Int): Single<List<Person>>

    /**
     * 查询部分字段可以通过降低I/O开销提高查询速度
     */
    @Query("SELECT p_id,name,age FROM t_person")
    fun queryPersonMinimalList(): Single<List<PersonMinimal>>

    @Transaction
    fun updateAll(personList: List<Person>) {
        deleteAll()
        insert(personList)
    }

    @Query("SELECT * FROM t_person")
    fun queryPersonAndClothesList(): Single<List<PersonAndClothes>>

    @Query(
        "SELECT t_person.name AS personName, t_clothes.brand AS clothesBrand " +
                "FROM t_person,t_clothes WHERE t_person.p_id = t_clothes.owner_id"
    )
    fun queryIntermediateData(): Single<List<PersonClothes>>

    @Query("SELECT * FROM t_person JOIN t_clothes ON t_person.p_id = t_clothes.owner_id")
    fun queryMultimapData(): Single<Map<Person, List<Clothes>>>

//    @Query("SELECT * FROM fts_person WHERE fts_person MATCH :query")
//    fun search(query: String): Observable<List<PersonFTS>>
}