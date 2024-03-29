package com.gxd.demo.room

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.gxd.demo.room.data.Color.Companion.randomColor
import com.gxd.demo.room.database.AppDatabase
import com.gxd.demo.room.table.Address
import com.gxd.demo.room.table.Clothes
import com.gxd.demo.room.table.Person
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : Activity() {
    private val personDao by lazy { AppDatabase.getInstance(this).personDao() }
    private val clothesDao by lazy { AppDatabase.getInstance(this).clothesDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun addPerson() {
        personDao.insertRx(
            Person(
                name = "aaa",
                age = 35,
                firstAddress = Address("China", "Hangzhou")
            )
        ).doSubscribe("addPerson")
    }

    fun deleteAllPerson() {
        personDao.deleteAllRx().doSubscribe("deleteAllPerson")
    }

    fun updatePerson() {
        personDao.queryAll()
            .subscribeOn(Schedulers.io())
            .filter { it.isNotEmpty() }
            .toSingle()
            .flatMap { personList ->
                val randomIndex = personList.indices.random()
                val personId = personList[randomIndex].id
                Person(
                    id = personId, name = "xxxx", age = 999, firstAddress = Address(country = "eee")
                ).let(personDao::update)
            }
            .doSubscribe("updatePerson")
    }

    fun queryPersonMinimal() {
        personDao.queryPersonMinimalList().doSubscribe("queryPersonMinimal")
    }

    fun updateAllPerson() {
        val personList = emptyList<Person>().toMutableList()
        repeat(5) { index ->
            personList += Person(name = "person-$index", age = 30 + index, firstAddress = Address(country = "address-$index"))
        }
        Completable.create { emitter ->
            personDao.updateAll(personList)
            emitter.onComplete()
        }.doSubscribe("updateAllPerson")
    }

    fun addClothes() {
        personDao.queryAll()
            .subscribeOn(Schedulers.io())
            .flatMap { personList ->
                val randomIndex = personList.indices.random()
                val ownerId = personList[randomIndex].id
                Clothes(
                    "gxg-$randomIndex", "${randomColor()}-$randomIndex", ownerId, "L"
                ).let(clothesDao::insertRx)
            }
            .doSubscribe("addClothes")
    }

    fun queryPersonAndClothesList() {
        personDao.queryPersonAndClothesList().doSubscribe("queryPersonAndClothesList")
    }

    fun queryPersonFstList() {
//        personDao.search("gxd")
    }

    private fun Completable.doSubscribe(methodName: String) {
        subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onComplete() {
                    Log.d("ggg", "$methodName...onComplete")
                }

                override fun onError(e: Throwable) {
                    Log.d("ggg", "$methodName...onError", e)
                }
            })
    }

    private fun <T : Any> Single<T>.doSubscribe(methodName: String) {
        subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<T> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onSuccess(t: T) {
                    Log.d("ggg", "onSuccess...$t")
                }

                override fun onError(e: Throwable) {
                    Log.d("ggg", "$methodName...onError", e)
                }
            })
    }

    fun queryIntermediateData() {
        personDao.queryIntermediateData().doSubscribe("queryIntermediateData")
    }

    fun queryMultimapData() {
        personDao.queryMultimapData().doSubscribe("queryMultimapData")
    }
}