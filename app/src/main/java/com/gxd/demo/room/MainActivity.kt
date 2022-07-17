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
import io.reactivex.Observable
import io.reactivex.Observer
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
        personDao.insert(
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
        Person(id = 1, name = "xxxx", age = 999, firstAddress = Address(country = "eee"))
            .let(personDao::update).doSubscribe("updatePerson")
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
        val personList = emptyList<Clothes>().toMutableList()
        repeat(5) { index ->
            personList += Clothes("gxg-$index", "yellow-$index", 4, "L")
        }
        clothesDao.insertRx(personList).doSubscribe("addClothes")
    }

    fun getPersonAndClothesList(view: View) {
        personDao.getPersonAndClothesList().doSubscribe("getPersonAndClothesList")
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

    private fun <T : Any> Observable<T>.doSubscribe(methodName: String) {
        subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<T> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: T) {
                    Log.d("glog", "MainActivity.onNext...$t")
                }

                override fun onComplete() {
                    Log.d("glog", "$methodName...onComplete")
                }

                override fun onError(e: Throwable) {
                    Log.d("glog", "$methodName...onError", e)
                }
            })
    }
}