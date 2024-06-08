package com.salas.jorge.laboratoriocalificado03

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.salas.jorge.laboratoriocalificado03.databinding.ItemTeacherBinding
import com.salas.jorge.laboratoriocalificado03.model.Teacher

class TeacherAdapter(
    private val teachers: List<Teacher>,
    private val onItemClick: (Teacher) -> Unit,
    private val onItemLongClick: (Teacher) -> Unit
) : RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder>() {

    inner class TeacherViewHolder(private val binding: ItemTeacherBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(teacher: Teacher) {
            binding.firstName.text = teacher.name
            binding.lastName.text = teacher.last_name
            Picasso.get().load(teacher.image_url).into(binding.photo)

            binding.root.setOnClickListener {
                onItemClick(teacher)
            }
            binding.root.setOnLongClickListener {
                onItemLongClick(teacher)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val binding = ItemTeacherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeacherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        holder.bind(teachers[position])
    }

    override fun getItemCount(): Int = teachers.size
}
