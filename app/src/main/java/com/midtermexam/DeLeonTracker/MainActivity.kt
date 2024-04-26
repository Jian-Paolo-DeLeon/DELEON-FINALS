package com.midtermexam.DeLeonTracker
//MainActivity
// Import necessary libraries
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

// Declare MainActivity class, inheriting from AppCompatActivity
class MainActivity : AppCompatActivity() {
    // listdata objects
    // Declare lateinit variables for view binding, toolbar, list, edit texts, buttons, search view, and date TextView
    lateinit var binding:ActivityMainBinding
    lateinit var toolbar: MaterialToolbar
    var listcat= arrayListOf<listdata>()
    lateinit var input:EditText // Declare an uninitialized EditText variable for input
    lateinit var input2:EditText // Declare an uninitialized EditText variable for input2
    lateinit var pickDateBtn:Button // Declare an uninitialized Button variable for pickDateBtn
    lateinit var date:TextView // Declare an uninitialized TextView variable for date
    private lateinit var fileHelper: Filehelper // Declare a private lateinit variable for fileHelper of type Filehelper
    lateinit var delete1:Button // Declare an uninitialized Button variable for delete1
    lateinit var searchView: SearchView // Declare an uninitialized SearchView variable for searchView

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) { // Override the onCreate method
        super.onCreate(savedInstanceState) // Call the superclass onCreate method with the saved instance state
        setContentView(R.layout.activity_main) // Set the content view to the activity_main layout
        fileHelper = Filehelper(this) // Initialize the fileHelper variable with a new instance of Filehelper class passing this activity context
        listcat = fileHelper.getTask() // Initialize the listcat variable with the tasks retrieved from fileHelper
        binding= ActivityMainBinding.inflate(layoutInflater) // Inflate the view using view binding
        setContentView(binding.root) // Set the content view to the root view of the inflated layout
        input=findViewById(R.id.input1) // Initialize the input variable with the EditText input1 from the layout
        input2=findViewById(R.id.input2) // Initialize the input2 variable with the EditText input2 from the layout
        toolbar=findViewById(R.id.toolbar) // Initialize the toolbar variable with the MaterialToolbar from the layout
        delete1=findViewById(R.id.button3) // Initialize the delete1 variable with the Button button3 from the layout
        searchView=findViewById(R.id.search) // Initialize the searchView variable with the SearchView search from the layout

        var itdate:String // Declare a variable for storing the selected date as a string

        pickDateBtn = findViewById(R.id.button2) // Initialize the pickDateBtn variable with the Button button2 from the layout
        date = findViewById(R.id.textView6) // Initialize the date variable with the TextView textView6 from the layout
        var dateit:String=""
        var count:Int=0

        pickDateBtn.setOnClickListener {
            count++ // Increment the count variable
            val c = Calendar.getInstance() // Get an instance of Calendar

            val year = c.get(Calendar.YEAR) // Get the current year
            val month = c.get(Calendar.MONTH) // Get the current month
            val day = c.get(Calendar.DAY_OF_MONTH) // Get the current day

            val datePickerDialog = DatePickerDialog( // Create a new DatePickerDialog
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    // Update the date TextView with the selected date
                    date.text =(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    // Format the selected date as a string and store it in itdate variable
                    itdate=(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year).toString()
                    // Store the formatted date string in dateit variable
                    dateit=itdate.toString()


                },

                year,
                month,
                day
            )

            datePickerDialog.show() // Show the DatePickerDialog

        }
        dateit=date.text.toString() // Get the text from the date TextView and store it in dateit variable

        val sdf = SimpleDateFormat("dd/M/yyyy") // Create a SimpleDateFormat object with the specified date format
        val currentDate = sdf.format(Date()) // Format the current date and store it in currentDate variable
        date.setText(currentDate) // Set the text of the date TextView to the formatted current date

        binding.button.setOnClickListener { // Set a click listener for the button inside the binding
            val int_name:String=input.text.toString() // Get the text from the input EditText and store it in int_name variable
            val int_price:String=input2.text.toString() // Get the text from the input2 EditText and store it in int_price variable
//            val k=listcat.get(position)
            if(count>0) // Check if count is greater than 0
            {
                // If count is greater than 0, add a new listdata object with the input values and selected date to the listcat
                listcat.add(listdata("$int_name","₹$int_price","$dateit"))
                fileHelper.saveData(listcat)

            }
            else{
                // If count is not greater than 0, add a new listdata object with the input values and current date to the listcat
                listcat.add(listdata("$int_name","₹$int_price","$currentDate"))
                fileHelper.saveData(listcat) // Save the updated listcat to file
            }
            // Clear the input fields and set the date TextView to the current date
            input.setText("")
            input2.setText("")
            date.setText(currentDate)
            // Set the layout manager and adapter for the recyclerView1
            binding.recycleView1.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true)
            binding.recycleView1.adapter=list_adapter(this,listcat)

        }
        // Set the layout manager and adapter for the recyclerView1
        binding.recycleView1.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true)
        binding.recycleView1.adapter=list_adapter(this,listcat)



        delete1.setOnClickListener { // Set a click listener for the delete1 button
            listcat.clear() // Clear the listcat
            // Set the layout manager and adapter for the recyclerView1
            binding.recycleView1.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true)
            binding.recycleView1.adapter=list_adapter(this,listcat)
            fileHelper.saveData(listcat) // Save the updated listcat to file
        }


        // Set a query text listener for the searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Call filterList function with the new text
                filterList(newText)
                return true
            }
        })


        // Set the overflow icon of the toolbar to the delete icon
        toolbar.overflowIcon=AppCompatResources.getDrawable(this,R.drawable.baseline_delete_24)

        // Define a function to delete all items from the list
        fun deleteOn(){
            listcat.clear() // Clear the listcat
            // Set the layout manager and adapter for the recyclerView1
            binding.recycleView1.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true)
            binding.recycleView1.adapter=list_adapter(this,listcat)
            fileHelper.saveData(listcat) // Save the updated listcat to file
            // Show a toast message indicating that all data are cleared
            Toast.makeText(this, "All data are cleared", Toast.LENGTH_SHORT).show()
        }
        // Set a click listener for the toolbar menu items
        toolbar.setOnMenuItemClickListener{item ->
            when(item.itemId){
                R.id.delete->deleteOn() // Call deleteOn function when delete menu item is clicked
            }
            return@setOnMenuItemClickListener true // Return true to indicate that the menu item click event has been consumed

        }


    }

    // Define a function to filter the list based on the given query
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