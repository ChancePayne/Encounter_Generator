package com.cmpayne.dnd5e

import android.os.Bundle
import com.cmpayne.dnd5e.screens.EncounterScreen

import com.wealthfront.magellan.Navigator
import com.wealthfront.magellan.support.SingleActivity

class MainActivity : SingleActivity() {
    override fun createNavigator(): Navigator {
        return Navigator.withRoot(EncounterScreen()).loggingEnabled(true).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
/*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bestiary_menu, menu)

//        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val searchView: SearchView? = searchItem?.actionView as SearchView

//        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        return super.onCreateOptionsMenu(menu)
        getNavigator().onCreateOptionsMenu(menu)
        return true
    }*/
}
