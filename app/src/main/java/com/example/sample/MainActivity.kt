package com.example.sample

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        taskAdapter = TaskAdapter()
        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputEditText: EditText = findViewById(R.id.inputUsername)
        val addButton: Button = findViewById(R.id.button4)

        addButton.setOnClickListener {
            val taskName = inputEditText.text.toString().trim()

            if (taskName.isNotEmpty()) {
                val task = Task(taskName = taskName)
                taskViewModel.addList(task)
                inputEditText.text.clear()
                Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show()
            }
        }

        taskViewModel.readAllData.observe(this) { tasks ->
            tasks?.let {
                taskAdapter.submitList(tasks)
            }
        }
    }
}
