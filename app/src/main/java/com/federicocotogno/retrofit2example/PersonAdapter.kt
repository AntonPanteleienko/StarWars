package com.federicocotogno.retrofit2example

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.federicocotogno.retrofit2example.api.Wars
import com.federicocotogno.retrofit2example.databinding.HelpItemBinding
import java.util.ArrayList

class PersonAdapter : RecyclerView.Adapter<PersonAdapter.PersonHolder>() {
    val personList = ArrayList<Wars>()

    class PersonHolder(item: View) : RecyclerView.ViewHolder(item) {

        val binding = HelpItemBinding.bind(item)

        fun bind(person: Wars) = with(binding) {
            name.text = person.name
            name.text = person.birth_year
            name.text = person.created
            name.text = person.edited
            name.text = person.eye_color
            name.text = person.films.toString()
            name.text = person.gender
            name.text = person.hair_color
            name.text = person.height
            name.text = person.homeworld
            name.text = person.mass
            name.text = person.skin_color
            name.text = person.species.toString()
            name.text = person.starships.toString()
            name.text = person.vehicles.toString()


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.help_item,parent,false)
        return PersonHolder(view)
    }

    override fun onBindViewHolder(holder: PersonHolder, position: Int) {
        holder.bind(personList[position])
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    fun addPerson(person: Wars) {
        personList.add(person)
        notifyDataSetChanged()
    }
}