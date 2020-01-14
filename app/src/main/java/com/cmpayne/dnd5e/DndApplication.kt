package com.cmpayne.dnd5e

import android.app.Application
import android.util.Log
import com.cmpayne.dnd5e.models.Monster
import com.cmpayne.dnd5e.models.Monsters
import com.cmpayne.dnd5e.models.MonstersDeserilizer
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import kotlin.system.measureNanoTime

class DndApplication : Application() {

    var disposable: Disposable
    val monsters = mutableListOf<Monster>()

    init {
        val obsGetMonsters: Observable<Monsters> = Observable.create { emitter ->
            Log.d("ApplicationRX", "Loading Monsters: " + (measureNanoTime {
//                if (!::monsters.isInitialized) {
                    val jsonString =
                        Utils.inputStreamToString(assets.open("bestiary/bestiary-mm.json"))

                    emitter.onNext(
                        GsonBuilder().registerTypeAdapter(
                            Monsters::class.java,
                            MonstersDeserilizer()
                        )
                            .create()
                            .fromJson(jsonString, Monsters::class.java)
                    )
//                }
                emitter.onComplete()
            } / 1000000f).toString())
        }
        disposable = obsGetMonsters
            .subscribeOn(Schedulers.computation())
            .subscribe { newMonsters ->
                Log.d("ApplicationRX", "Monsters loaded")
                monsters.addAll(newMonsters.monsters)
            }
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onTerminate() {
        disposable.dispose()
        super.onTerminate()
    }
}