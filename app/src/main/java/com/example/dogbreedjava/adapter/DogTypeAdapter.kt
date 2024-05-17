package com.example.dogbreedjava.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.dogbreedjava.R
import com.example.dogbreedjava.adapter.DogTypeAdapter.DogTypeViewHolder
import com.example.dogbreedjava.listener.DogItemClickListener

/**
 * @Description
 * @Author chenlongwang
 * @CreateTime 2024/05/15
 */
class DogTypeAdapter(private val mContext: Context) : RecyclerView.Adapter<DogTypeViewHolder>() {
    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var dogTypeNames: Array<String?>? = null
    private var dogItemClickListener: DogItemClickListener? = null
    private var dogTypeNameMap: Map<String, List<String>>? = null

    fun setOnItemClickListener(listener: DogItemClickListener?) {
        dogItemClickListener = listener
    }

    fun updateDogNamesChanged(dogTypeNames: Array<String?>) {
        this.dogTypeNames = dogTypeNames
        notifyDataSetChanged()
    }

    fun updateDogNameChanged(
        dogTypeNames: Array<String?>?,
        dogTypeNameMap: Map<String, List<String>>?
    ) {
        this.dogTypeNameMap = dogTypeNameMap
        this.dogTypeNames = dogTypeNames
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogTypeViewHolder {
        val view = mLayoutInflater.inflate(R.layout.item_text, parent, false)
        return DogTypeViewHolder(view)
    }

    override fun onBindViewHolder(holder: DogTypeViewHolder, position: Int) {
        val textView = TextView(mContext)
        textView.text = "HAHAH"
        textView.textSize = 10f
        holder.linearLayout.addView(textView)
        holder.textView.text = dogTypeNames!![position]
        holder.textView.setOnClickListener {
            if (dogItemClickListener != null) {
                dogItemClickListener!!.OnItemClickListener(dogTypeNames!![position], position)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (dogTypeNames == null) 0 else dogTypeNames!!.size
    }

    class DogTypeViewHolder(itemView: View) : ViewHolder(itemView) {
        val textView: TextView
        val linearLayout: LinearLayout

        init {
            textView = itemView.findViewById(R.id.text_dog_type)
            linearLayout = itemView.findViewById(R.id.view_parent)
        }
    }
}
