package com.example.problemset4_tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var gameRecyclerView: RecyclerView
    private lateinit var adapter: CellAdapter
    private var currentTurn: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "Created Main Activity")

        gameRecyclerView = findViewById(R.id.game_recycler_view)
        gameRecyclerView.layoutManager = GridLayoutManager(this, 3)
        findViewById<Button>(R.id.restart_button).setOnClickListener {
            initGame()
        }

        initGame()
    }

    private fun initializeAdapter(): CellAdapter {
        return CellAdapter(List(9) {resources.getString(R.string.empty_cell)})
    }

    private fun initGame()  {
        currentTurn = 0
        adapter = initializeAdapter()
        gameRecyclerView.adapter = adapter
    }

    private inner class CellHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private var cell: String = ""
        private val textView: TextView = itemView.findViewById(R.id.valueTextView);

        init {
            Log.d(TAG, "Cell Holder Init Called")
            itemView.setOnClickListener(this)
        }

        fun bind(value: String) {
            Log.d(TAG, "Binding the value $value to the textView")
            cell = value
            textView.text = cell
        }

        override fun onClick(v: View?) {
            Log.d(TAG, "Clicked a cell")
            if (cell == resources.getString(R.string.empty_cell)) {
                if (currentTurn % 2 == 0) {
                    bind(resources.getString(R.string.x_cell))
                } else {
                    bind(resources.getString(R.string.o_cell))
                }
                currentTurn++
            }
            //Toast.makeText(this, "$cell clicked!", Toast.LENGTH_SHORT).show()
        }

    }

    private inner class CellAdapter(var board: List<String>) : RecyclerView.Adapter<CellHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellHolder {
            Log.d(TAG, "Cell Adapter View Holder Created")
            val view: View = layoutInflater.inflate(R.layout.list_item_cell, parent, false)
            return CellHolder(view)
        }

        override fun onBindViewHolder(holder: CellHolder, position: Int) {
            Log.d(TAG, "Binding data at position: $position")
            val cell = board[position]
            holder.bind(cell)
        }

        override fun getItemCount(): Int {
            return board.size
        }
    }
}