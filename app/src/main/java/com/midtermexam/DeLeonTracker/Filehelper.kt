package com.midtermexam.project

import com.midtermexam.DeLeonTracker.listdata
import com.google.gson.Gson;
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.reflect.TypeToken


class Filehelper {
    lateinit var taskdata: SharedPreferences
    lateinit var gson: Gson

    constructor(context: Context){
        taskdata = context.getSharedPreferences("SaveDataS",Context.MODE_PRIVATE)
        gson = Gson()
    }


    public fun saveData(task:ArrayList<listdata>):Unit{
        val editor = taskdata.edit()
        editor.putString("item_name",gson.toJson(task))
        editor.apply()
    }
    public fun getTask(): ArrayList<listdata> {
        val taskString = taskdata.getString("item_name", null)
        if (taskString != null) {
            val taskListType = object : TypeToken<ArrayList<listdata>>() {}.type
            val tasks: ArrayList<listdata> = gson.fromJson(taskString, taskListType)
            return tasks
        } else {
            return ArrayList()
        }
    }




}

