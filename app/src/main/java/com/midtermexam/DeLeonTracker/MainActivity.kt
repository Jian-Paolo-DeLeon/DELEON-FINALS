package com.midtermexam.DeLeonTracker
//MainActivity
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import com.midtermexam.DeLeonTracker.databinding.ActivityMainBinding
import com.midtermexam.project.Filehelper
import com.google.android.material.appbar.MaterialToolbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var toolbar: MaterialToolbar
    var listcat= arrayListOf<listdata>()
    lateinit var input:EditText
    lateinit var input2:EditText
    lateinit var pickDateBtn:Button
    lateinit var date:TextView
    private lateinit var fileHelper: Filehelper
    lateinit var delete1:Button
    lateinit var searchView: SearchView





    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fileHelper = Filehelper(this)
        listcat = fileHelper.getTask()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        input=findViewById(R.id.input1)
        input2=findViewById(R.id.input2)
        toolbar=findViewById(R.id.toolbar)
        delete1=findViewById(R.id.button3)
        searchView=findViewById(R.id.search)



//        listcat.add(listdata("Tanmoy","666"))
//        listcat.add(listdata("Rahul","667"))
//        listcat.add(listdata("Sumit","668"))
//        listcat.add(listdata("Hament","669"))
//        listcat.add(listdata("Hament","669"))
//        listcat.add(listdata("Hament","669"))
//        listcat.add(listdata("Hament","669"))
//        listcat.add(listdata("Hament","669"))
//        listcat.add(listdata("Hament","669"))


        var itdate:String

        pickDateBtn = findViewById(R.id.button2)
        date = findViewById(R.id.textView6)
        var dateit:String=""
        var count:Int=0

        pickDateBtn.setOnClickListener {
            count++
            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { view, year, monthOfYear, dayOfMonth ->

                    date.text =(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)

                    itdate=(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year).toString()

                    dateit=itdate.toString()


                },

                year,
                month,
                day
            )

            datePickerDialog.show()

        }
        dateit=date.text.toString()

        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        date.setText(currentDate)

        binding.button.setOnClickListener {
            val int_name:String=input.text.toString()
            val int_price:String=input2.text.toString()
//            val k=listcat.get(position)
            if(count>0)
            {
                listcat.add(listdata("$int_name","₹$int_price","$dateit"))
                fileHelper.saveData(listcat)

            }
            else{
                listcat.add(listdata("$int_name","₹$int_price","$currentDate"))
                fileHelper.saveData(listcat)
            }

            input.setText("")
            input2.setText("")
            date.setText(currentDate)
            binding.recycleView1.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true)
            binding.recycleView1.adapter=list_adapter(this,listcat)

        }
        binding.recycleView1.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true)
        binding.recycleView1.adapter=list_adapter(this,listcat)



        delete1.setOnClickListener {
            listcat.clear()
            binding.recycleView1.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true)
            binding.recycleView1.adapter=list_adapter(this,listcat)
            fileHelper.saveData(listcat)
        }








        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })





        toolbar.overflowIcon=AppCompatResources.getDrawable(this,R.drawable.baseline_delete_24)


        fun deleteOn(){
            listcat.clear()
            binding.recycleView1.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true)
            binding.recycleView1.adapter=list_adapter(this,listcat)
            fileHelper.saveData(listcat)
            Toast.makeText(this, "All data are cleared", Toast.LENGTH_SHORT).show()
        }
        toolbar.setOnMenuItemClickListener{item ->
            when(item.itemId){
                R.id.delete->deleteOn()
            }
            return@setOnMenuItemClickListener true

        }





    }





    fun filterList(query: String?) {
        val filteredList = ArrayList<listdata>()

        if (query.isNullOrBlank()) {
            filteredList.addAll(listcat)
        } else {
            val filterPattern = query.toLowerCase(Locale.ROOT)
            for (item in listcat) {
                if (item.item_name.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                    filteredList.add(item)
                }
            }
        }

        (binding.recycleView1.adapter as list_adapter).updateList(filteredList)
    }
    }





