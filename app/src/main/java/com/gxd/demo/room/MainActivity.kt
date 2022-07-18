package com.gxd.demo.room

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
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

    fun addPerson(view: View) {
        personDao.insertRx(
            Person(
                name = "aaa",
                age = 35,
                firstAddress = Address("China", "Hangzhou")
            )
        ).doSubscribe("addPerson")
    }

    fun deleteAllPerson(view: View) {
        personDao.deleteAllRx().doSubscribe("deleteAllPerson")
    }

    fun updatePerson(view: View) {
        personDao.getAll()
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

    fun getPersonMinimal(view: View) {
        personDao.getPersonMinimalList().doSubscribe("getPersonMinimal")
    }

    fun updateAllPerson(view: View) {
        val personList = emptyList<Person>().toMutableList()
        repeat(5) { index ->
            personList += Person(name = "person-$index", age = 30 + index, firstAddress = Address(country = "address-$index"))
        }
        Completable.create { emitter ->
            personDao.updateAll(personList)
            emitter.onComplete()
        }.doSubscribe("updateAllPerson")
    }

    fun addClothes(view: View) {
        personDao.getAll()
            .subscribeOn(Schedulers.io())
            .flatMap { personList ->
                val randomIndex = personList.indices.random()
                val ownerId = personList[randomIndex].id
                Clothes(
                    "gxg-$randomIndex", "yellow-$randomIndex", ownerId, "L"
                ).let(clothesDao::insertRx)
            }
            .doSubscribe("addClothes")
    }

    fun getPersonAndClothesList(view: View) {
        personDao.getPersonAndClothesList().doSubscribe("getPersonAndClothesList")
    }

    fun getPersonFstList(view: View) {
//        personDao.search("gxd")
    }

    private fun Completable.doSubscribe(methodName: String) {
        subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onComplete() {
                    Log.d("glog", "$methodName...onComplete")
                }

                override fun onError(e: Throwable) {
                    Log.d("glog", "$methodName...onError", e)
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
                    Log.d("glog", "onSuccess...$t")
                }

                override fun onError(e: Throwable) {
                    Log.d("glog", "$methodName...onError", e)
                }
            })
    }
}