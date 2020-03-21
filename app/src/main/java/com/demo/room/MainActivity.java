package com.demo.room;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.demo.room.bean.Clothes;
import com.demo.room.bean.Person;
import com.demo.room.dao.ClothesDao;
import com.demo.room.dao.PersonDao;
import com.demo.room.databinding.ActivityMainBinding;
import com.demo.room.manager.MyDataBaseManager;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends Activity {
    private ActivityMainBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        PersonDao personDao = MyDataBaseManager.dataBase.getPersonDao();
        personDao.deleteAll().subscribeOn(Schedulers.io()).subscribe();
        personDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Person>>() {
                               @Override
                               public void onSubscribe(Disposable d) {
                               }

                               @Override
                               public void onNext(List<Person> personList) {
                                   dataBinding.personTv.setText("");
                                   for (Person person : personList) {
                                       dataBinding.personTv.append(person.toString() + "\n");
                                   }
                               }

                               @Override
                               public void onError(Throwable e) {
                               }

                               @Override
                               public void onComplete() {
                               }
                           }
                );
        final ClothesDao clothesDao = MyDataBaseManager.dataBase.getClothesDao();
        clothesDao.deleteAll().subscribeOn(Schedulers.io()).subscribe();
        clothesDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Clothes>>() {
                               @Override
                               public void onSubscribe(Disposable d) {
                               }

                               @Override
                               public void onNext(List<Clothes> clothesList) {
                                   dataBinding.clothesTv.setText("");
                                   for (Clothes clothes : clothesList) {
                                       dataBinding.clothesTv.append(clothes.toString() + "\n");
                                   }
                               }

                               @Override
                               public void onError(Throwable e) {
                               }

                               @Override
                               public void onComplete() {
                               }
                           }
                );

        dataBinding.addPersonBtn.setOnClickListener(v -> {
            Person gxd = new Person("gxd", 25);
            gxd.address1 = new Person.Address("杭州", "乔司街道");
            gxd.address2 = new Person.Address("伊宁", "艾兰木巴格街道");
            personDao.insert(gxd)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                                   @Override
                                   public void onSubscribe(Disposable d) {
                                   }

                                   @Override
                                   public void onComplete() {
                                       Log.d("gxd", "personDao-->成功插入一条数据");
                                   }

                                   @Override
                                   public void onError(Throwable e) {
                                   }
                               }
                    );
        });

        dataBinding.addClothesBtn.setOnClickListener(v ->
                personDao.getPersonByMultiCondition("gxd", 25)
                        .subscribeOn(Schedulers.io())
                        .flatMapCompletable(person ->
                                clothesDao.insert(new Clothes("red", "uniqlo", "xl", person.pid)))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                                       @Override
                                       public void onSubscribe(Disposable d) {
                                       }

                                       @Override
                                       public void onComplete() {
                                           Log.d("gxd", "clothesDao-->成功插入一条数据");
                                       }

                                       @Override
                                       public void onError(Throwable e) {
                                       }
                                   }
                        )
        );
    }
}
