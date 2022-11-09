package com.fincare.shaadikaro.ui.persons

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fincare.support.images.loadImage
import com.fincare.shaadikaro.R
import com.fincare.shaadikaro.data.local.database.entities.Person
import com.fincare.shaadikaro.databinding.AdapterPersonsBinding
import com.fincare.support.views.hide
import com.fincare.support.views.show

class PersonsAdapter(
    var context : Context, var persons: ArrayList<Person>, var onClickListener : PersonsOnCLickListener
): RecyclerView.Adapter<PersonsAdapter.PersonsViewHolder>() {

    override fun getItemCount() = persons.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PersonsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_persons,
                parent,
                false
            )
        )

    @SuppressLint("NotifyDataSetChanged")
    fun setPersons(persons: List<Person>) {
        this.persons.clear()
        this.persons.addAll(persons)
        notifyDataSetChanged()
    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun addPersons(persons: List<Person>) {
//        this.persons.addAll(persons)
//        notifyDataSetChanged()
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    private fun setSelected(position: Int){
//        selectedPosition = position
//        notifyDataSetChanged()
//    }

//    private var selectedPosition = 0
    override fun onBindViewHolder(holder: PersonsViewHolder, position: Int) {
        val data = persons[position]
        holder.bind(data,position)
    }

    private fun PersonsViewHolder.bind(data: Person, position: Int){
        binding.data = data

        if (position==persons.size-1){
            binding.idDivider.hide()
        } else {
            binding.idDivider.show()
        }

        binding.root.setOnClickListener {
            onClickListener.onPersonClick(data)
        }

        binding.idArtistImage.setOnClickListener {
            data.picture?.large?.let { imageUrl -> onClickListener.onPersonPhotoClick(imageUrl) }
        }
        binding.idAccept.setOnClickListener {
            data.hasActionTaken = true
            data.isAccepted = true
            onClickListener.onPersonAccept(data)
        }

        binding.idReject.setOnClickListener {
            data.hasActionTaken = true
            data.isAccepted = false
            onClickListener.onPersonDecline(data)
        }

        binding.idChange.setOnClickListener {
            data.hasActionTaken = false
            data.isAccepted = false
            onClickListener.onPersonChange(data)
        }

        if (data.hasActionTaken){
            binding.idNoActionTaken.hide()
            binding.idActionTaken.show()
            if (data.isAccepted) {
                binding.idAccepted.show()
                binding.idBadge.show()
                binding.idRejected.hide()
            }
            else{
                binding.idRejected.show()
                binding.idAccepted.hide()
                binding.idBadge.hide()
            }
        } else {
            binding.idNoActionTaken.show()
            binding.idBadge.hide()
            binding.idActionTaken.hide()
            binding.idAccepted.hide()
            binding.idRejected.hide()
        }

        data.picture?.large?.let {
            val placeHolder = ContextCompat.getDrawable(context,R.drawable.ic_person)
            binding.idArtistImage.loadImage(it,placeHolder) {
            }
        }
    }

    inner class PersonsViewHolder(
        val binding: AdapterPersonsBinding
    ) : RecyclerView.ViewHolder(binding.root)
}

interface PersonsOnCLickListener {
    fun onPersonClick(person : Person)
    fun onPersonPhotoClick(imageUrl : String)
    fun onPersonAccept(person : Person)
    fun onPersonDecline(person : Person)
    fun onPersonChange(person: Person)
}