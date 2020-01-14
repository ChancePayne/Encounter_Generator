package com.cmpayne.dnd5e.screens

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cmpayne.dnd5e.BestiaryListAdapter
import com.cmpayne.dnd5e.BestiaryListInterface
import com.cmpayne.dnd5e.MonsterReturnListener
import com.cmpayne.dnd5e.R
import com.cmpayne.dnd5e.models.Encounter
import com.cmpayne.dnd5e.models.Monster
import com.cmpayne.dnd5e.models.Party
import com.wealthfront.magellan.BaseScreenView
import com.wealthfront.magellan.Screen
import com.wealthfront.magellan.transitions.CircularRevealTransition
import java.lang.NumberFormatException

class EncounterScreen : Screen<EncounterView>(),
    BestiaryListInterface, MonsterReturnListener {
//    lateinit var originalEncounterMonsterList: MutableList<Monster>
//    lateinit var originalEncounterCountList: MutableList<Int>

    companion object {
        const val REQUEST_ORIGINAL = 0
        const val REQUEST_ADJUSTED = 1
    }

    lateinit var originalEncounter: Encounter
    lateinit var adjustedEncounter: Encounter

    override fun returnMonster(monster: Monster, requestCode: Int) {
        when(requestCode) {
            REQUEST_ORIGINAL -> {
                addMonster(originalEncounter, monster)
            }
            REQUEST_ADJUSTED -> {
                addMonster(adjustedEncounter, monster)
            }
        }
        /*if(view != null) {
            view.updateEncouterUi(originalEncounter, adjustedEncounter)
        }*/
    }

    private fun addMonster(
        encounter: Encounter,
        monster: Monster
    ) {
        val index = encounter.monsters.indexOf(monster)
        if (index == -1) {
            encounter.monsters.add(monster)
            encounter.monsterCount.add(1)
        } else {
            encounter.monsterCount[index]++
        }
    }

    override fun monsterSelected(monster: Monster, requestCode: Int) {
//        navigator.goTo(MonsterScreen(monster))
        navigator.overrideTransition(CircularRevealTransition(view.originalListView)).goTo(MonsterScreen(monster))
    }

    override fun monsterLongSelected(monster: Monster) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun monsterQtyChanged(monster: Monster, qty: Int) {
        view.updateEncouterUi(originalEncounter, adjustedEncounter)
    }

    override fun createView(context: Context): EncounterView {
        if (!::originalEncounter.isInitialized) {
            originalEncounter = Encounter(mutableListOf(), mutableListOf(), Party())
        }
        if (!::adjustedEncounter.isInitialized) {
            adjustedEncounter = Encounter(mutableListOf(), mutableListOf(), Party())
        }
        return EncounterView(context)
    }

    override fun onResume(context: Context?) {
        view.showMonsterLists(originalEncounter, adjustedEncounter)
        view.updateEncouterUi(originalEncounter, adjustedEncounter)
    }

    fun findNewMonster(requestCode: Int) {
        navigator.show(BestiaryScreen(this, requestCode))
    }

    fun copyOrigMonsters() {
        adjustedEncounter.monsters.clear()
        adjustedEncounter.monsters.addAll(originalEncounter.monsters)
        adjustedEncounter.monsterCount.clear()
        adjustedEncounter.monsterCount.addAll(originalEncounter.monsterCount)

    }

    fun updatePartyData(
        originalSize: Int,
        originalLevel: Int,
        adjustedSize: Int,
        adjustedLevel: Int
    ) {
        originalEncounter.party.size = originalSize
        originalEncounter.party.level = originalLevel
        adjustedEncounter.party.size = adjustedSize
        adjustedEncounter.party.level = adjustedLevel
        view.updateRatingUi(originalEncounter, adjustedEncounter)
    }
}

class EncounterView(context: Context) : BaseScreenView<EncounterScreen>(context) {
    val originalListView: RecyclerView
    private var originalAdapter: BestiaryListAdapter? = null
    private val adjustedListView: RecyclerView
    private var adjustedAdapter: BestiaryListAdapter? = null

    private val copyButton: Button

    private val inputPartyLayout: ViewGroup
    private val inputPartySize: EditText
    private val inputLevelLayout: ViewGroup
    private val inputPartyLevel: EditText
    private val addOriginalMonsterButton: Button
    private val addAdjustedMonsterButton: Button
    private val ratingOriginal: TextView
    private val ratingAdjusted: TextView
    private val adjInputPartyLayout: ViewGroup
    private val adjInputPartySize: EditText
    private val adjInputLevelLayout: ViewGroup
    private val adjInputPartyLevel: EditText


    init {
        inflate(context, R.layout.encounter_layout, this)

        val partyTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.d("EncounterView", "afterTextChanged")
                updateEncounterData()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("EncounterView", "beforeTextChanged")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("EncounterView", "onTextChanged")
            }

        }

        originalListView = findViewById(R.id.encounter_original_list_view)
        adjustedListView = findViewById(R.id.encounter_adj_list_view)

        inputPartyLayout = findViewById(R.id.encounter_input_party_layout)
        inputPartySize = findViewById(R.id.encounter_input_party_size)
        inputPartySize.addTextChangedListener(partyTextWatcher)
        inputLevelLayout = findViewById(R.id.encounter_input_level_layout)
        inputPartyLevel = findViewById(R.id.encounter_input_party_level)
        inputPartyLevel.addTextChangedListener(partyTextWatcher)
        addOriginalMonsterButton = findViewById(R.id.encounter_add_original_monster_button)
        addOriginalMonsterButton.setOnClickListener {
            screen.findNewMonster(EncounterScreen.REQUEST_ORIGINAL)
        }
        addAdjustedMonsterButton = findViewById(R.id.encounter_add_adjusted_monster_button)
        addAdjustedMonsterButton.setOnClickListener {
            screen.findNewMonster(EncounterScreen.REQUEST_ADJUSTED)
        }
        ratingOriginal = findViewById(R.id.encounter_rating_original)
        ratingAdjusted = findViewById(R.id.encounter_rating_adjusted)
        adjInputPartyLayout = findViewById(R.id.encounter_adj_input_party_layout)
        adjInputPartySize = findViewById(R.id.encounter_adj_input_party_size)
        adjInputPartySize.addTextChangedListener(partyTextWatcher)
        adjInputLevelLayout = findViewById(R.id.encounter_adj_input_level_layout)
        adjInputPartyLevel = findViewById(R.id.encounter_adj_input_party_level)
        adjInputPartyLevel.addTextChangedListener(partyTextWatcher)

        copyButton = findViewById(R.id.encounter_copy_button)
        copyButton.setOnClickListener {
            screen.copyOrigMonsters()
            adjustedAdapter?.refreshData()
        }
    }

    fun showMonsterLists(
        original: Encounter,
        adjusted: Encounter
    ) {
        /*val monsterList = mutableListOf<Monster>()
        val countList = mutableListOf<Int>()

        originalEncounterList.forEach {
            monsterList.add(it.first)
            countList.add(it.second)
        }*/

        originalAdapter =
            BestiaryListAdapter(
                original.monsters,
                screen,
                original.monsterCount,
                true,
                EncounterScreen.REQUEST_ORIGINAL
            )
        originalListView.adapter = originalAdapter

        adjustedAdapter =
            BestiaryListAdapter(
                adjusted.monsters,
                screen,
                adjusted.monsterCount,
                true,
                EncounterScreen.REQUEST_ADJUSTED
            )
        adjustedListView.adapter = adjustedAdapter
    }

    fun updateRatingUi(original: Encounter, adjusted: Encounter) {
        ratingOriginal.text = original.getDifficulty()
        ratingAdjusted.text = adjusted.getDifficulty()
    }

    fun updateEncouterUi(original: Encounter, adjusted: Encounter) {
        inputPartySize.setText(original.party.size.toString())
        inputPartyLevel.setText(original.party.level.toString())
//        ratingOriginal.text = original.getRating()
//        ratingAdjusted.text = adjusted.getRating()
        adjInputPartySize.setText(adjusted.party.size.toString())
        adjInputPartyLevel.setText(adjusted.party.level.toString())
        originalAdapter?.notifyDataSetChanged()
        adjustedAdapter?.notifyDataSetChanged()
    }

    fun updateEncounterData() {
        try {
            screen.updatePartyData(
                inputPartySize.text.toString().toInt(),
                inputPartyLevel.text.toString().toInt(),
                adjInputPartySize.text.toString().toInt(),
                adjInputPartyLevel.text.toString().toInt()
            )
        } catch (e: NumberFormatException) {
            Log.e("EncounterScreen", "numberFormatException")
            e.printStackTrace()
        }
    }
}