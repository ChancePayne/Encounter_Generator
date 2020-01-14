package com.cmpayne.dnd5e.screens

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cmpayne.dnd5e.R
import com.cmpayne.dnd5e.models.Monster
import com.wealthfront.magellan.BaseScreenView
import com.wealthfront.magellan.Screen

class MonsterScreen(val monster: Monster): Screen<MonsterView>() {
    override fun createView(context: Context): MonsterView {
        return MonsterView(context)
    }

    override fun onResume(context: Context?) {
        view.displayMonsterStats(monster)
    }

    override fun getTitle(context: Context?): String {
        return monster.name
    }
}

class MonsterView(context: Context) : BaseScreenView<MonsterScreen>(context) {

    val monsterDescView: TextView
    val monsterDivDesc: View
    val monsterLayoutAc: ViewGroup
    val monsterAcView: TextView
    val monsterLayoutHp: ViewGroup
    val monsterHpView: TextView
    val monsterLayoutSpd: ViewGroup
    val monsterSpdView: TextView
    val monsterDivStat: View
    val monsterLayoutStat: ViewGroup
    val monsterStatStr: TextView
    val monsterStatDex: TextView
    val monsterStatCon: TextView
    val monsterStatInt: TextView
    val monsterStatWis: TextView
    val monsterStatCha: TextView
    val monsterDivSense: View
    val monsterLayoutSkill: ViewGroup
    val monsterSkillView: TextView
    val monsterLayoutSense: ViewGroup
    val monsterSenseView: TextView
    val monsterLayoutLang: ViewGroup
    val monsterLangView: TextView
    val monsterLayoutChall: ViewGroup
    val monsterChallView: TextView
    val monsterDivTrait: View
    val monsterLayoutTraits: ViewGroup
    val monsterLayoutSpell: ViewGroup
    val monsterSpellHeaderView: TextView
    val monsterSpellListView: TextView
    val monsterDivActions: View
    val monsterLayoutActions: ViewGroup


    init {
        inflate(context, R.layout.monster_layout, this)

        monsterDescView = findViewById(R.id.monster_desc_view)
        monsterDivDesc = findViewById(R.id.monster_div_desc)
        monsterLayoutAc = findViewById(R.id.monster_layout_ac)
        monsterAcView = findViewById(R.id.monster_ac_view)
        monsterLayoutHp = findViewById(R.id.monster_layout_hp)
        monsterHpView = findViewById(R.id.monster_hp_view)
        monsterLayoutSpd = findViewById(R.id.monster_layout_spd)
        monsterSpdView = findViewById(R.id.monster_spd_view)
        monsterDivStat = findViewById(R.id.monster_div_stat)
        monsterLayoutStat = findViewById(R.id.monster_layout_stat)
        monsterStatStr = findViewById(R.id.monster_stat_str)
        monsterStatDex = findViewById(R.id.monster_stat_dex)
        monsterStatCon = findViewById(R.id.monster_stat_con)
        monsterStatInt = findViewById(R.id.monster_stat_int)
        monsterStatWis = findViewById(R.id.monster_stat_wis)
        monsterStatCha = findViewById(R.id.monster_stat_cha)
        monsterDivSense = findViewById(R.id.monster_div_sense)
        monsterLayoutSkill = findViewById(R.id.monster_layout_skill)
        monsterSkillView = findViewById(R.id.monster_skill_view)
        monsterLayoutSense = findViewById(R.id.monster_layout_sense)
        monsterSenseView = findViewById(R.id.monster_sense_view)
        monsterLayoutLang = findViewById(R.id.monster_layout_lang)
        monsterLangView = findViewById(R.id.monster_lang_view)
        monsterLayoutChall = findViewById(R.id.monster_layout_chall)
        monsterChallView = findViewById(R.id.monster_chall_view)
        monsterDivTrait = findViewById(R.id.monster_div_trait)
        monsterLayoutTraits = findViewById(R.id.monster_layout_traits)
        monsterLayoutSpell = findViewById(R.id.monster_layout_spell)
        monsterSpellHeaderView = findViewById(R.id.monster_spell_header_view)
        monsterSpellListView = findViewById(R.id.monster_spell_list_view)
        monsterDivActions = findViewById(R.id.monster_div_actions)
        monsterLayoutActions = findViewById(R.id.monster_layout_actions)
    }

    public fun displayMonsterStats(monster: Monster) {
        // TODO: improve description format
        monsterDescView.text = "${monster.size} ${monster.typeObj.type} (${monster.typeObj.tags.toString()}, ${monster.alignmentList[0].good})"
        monsterAcView.text = "${monster.armorClass.rating} ${monster.armorClass.from}"
        monsterHpView.text = "${monster.hp}"
        // TODO: improve speed format
        monsterSpdView.text = monster.speedObj.walk.toString()
        monsterStatStr.text = monster.str.toString()
        monsterStatDex.text = monster.dex.toString()
        monsterStatCon.text = monster.con.toString()
        monsterStatInt.text = monster.int.toString()
        monsterStatWis.text = monster.wis.toString()
        monsterStatCha.text = monster.cha.toString()
        monsterSkillView.text = monster.skillList.toString()
        monsterSenseView.text = "passive Perception ${monster.passive}"
        monsterLangView.text = if (monster.languageTags == null) {
            "none"
        } else {
            monster.languageTags.toString()
        }
        monsterChallView.text = monster.challenge.toString()
        // TODO: Spells missing from monster class
        monsterSpellHeaderView.text = "TBD"
        monsterSpellListView.text = "TBD"
    }
}