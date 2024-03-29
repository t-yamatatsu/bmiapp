package com.example.bmiapp

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_history.*
import java.util.List

//履歴タブ
class TabHistoryFragment: Fragment() {
    var adapter : ViewAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        return view
    }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         val rv  = recycler_view
         val savedDataList: List<PersonalDataModel> = loadSavedData()
         adapter = ViewAdapter(savedDataList)
         //itemごとの枠線
         val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
         val llm = LinearLayoutManager(this.context)
         rv.addItemDecoration(itemDecoration)
         rv.setHasFixedSize(true)
         rv.setLayoutManager(llm)
         rv.setAdapter(adapter)
    }
    /**
     *履歴タブを開いた時に、保存されている履歴を再取得してrecyclerViewに反映
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        val lastValue = userVisibleHint
        super.setUserVisibleHint(isVisibleToUser)
        if (!lastValue && isVisibleToUser) {
            adapter?.refresh(loadSavedData())

        }
    }

    /**
     * SharedPreferencesからjsonデータを取得して履歴リストを返す
     */
    private fun loadSavedData(): List<PersonalDataModel> {
        val dataStore: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context)
        val savedData = dataStore.getString("History", "[]")
        val savedDataList: List<PersonalDataModel> = listAdapter.fromJson(savedData) as List<PersonalDataModel>
        return savedDataList
    }
}

