package com.cmpayne.dnd5e.screens

import android.content.Context
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.cmpayne.dnd5e.*
import com.cmpayne.dnd5e.models.Monster
import com.wealthfront.magellan.BaseScreenView
import com.wealthfront.magellan.Screen
import com.wealthfront.magellan.transitions.CircularRevealTransition

class BestiaryScreen(
    val monsterReturnListener: MonsterReturnListener? = null,
    val requestCode: Int
) : Screen<BestiaryView>(),
    BestiaryListInterface {
    override fun monsterLongSelected(monster: Monster) {
//        Log.d("BestiaryScreen", "monsterLongSelected")
//        navigator.goTo(MonsterScreen(monster))
        navigator.overrideTransition(CircularRevealTransition(view.listView)).goTo(MonsterScreen(monster))
    }

    override fun monsterQtyChanged(monster: Monster, qty: Int) {
        Log.d("BestiaryScreen", "monsterQtyChanged not used")
    }

    override fun monsterSelected(monster: Monster, requestCode: Int) {
        monsterReturnListener?.returnMonster(monster, requestCode)
    }

//    lateinit var monsters: Monsters

    override fun createView(context: Context): BestiaryView {
        /*if(!::monsters.isInitialized) {
            val jsonString =
                Utils.inputStreamToString(activity.assets.open("bestiary/bestiary-mm.json"))

            monsters =
                GsonBuilder().registerTypeAdapter(Monsters::class.java, MonstersDeserilizer())
                    .create()
                    .fromJson(jsonString, Monsters::class.java)
        }*/

        return BestiaryView(context)
    }

    override fun onResume(context: Context?) {
        view.showMonsterList((activity.application as DndApplication).monsters, requestCode)
    }

    override fun getTitle(context: Context?): String {
        return "Bestiary"
    }
}

class BestiaryView(context: Context) : BaseScreenView<BestiaryScreen>(context) {
    val listView: RecyclerView
    var adapter: BestiaryListAdapter? = null

    init {
        inflate(context, R.layout.bestiary_layout, this)
        listView = this.findViewById(R.id.bestiary_list_view)
        this.findViewById<SearchView>(R.id.bestiary_search_view).setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("BestiarySearch", "not implemented") //To change body of created functions use File | Settings | File Templates.
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { adapter?.filterData(it) }
                return true
            }
        })
    }

    fun showMonsterList(monsters: MutableList<Monster>, requestCode: Int) {
        adapter = BestiaryListAdapter(
            monsters,
            screen,
            requestCode = requestCode
        )
        listView.adapter = adapter
    }


}

