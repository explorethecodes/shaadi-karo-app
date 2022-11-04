package com.fincare.shaadikaro.ui.home.matches

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fincare.support.images.loadImage
import com.fincare.shaadikaro.R
import com.fincare.shaadikaro.data.network.models.collection.matches.Person
import com.fincare.shaadikaro.databinding.AdapterMatchesBinding
import com.fincare.support.views.hide
import com.fincare.support.views.show

class MatchesAdapter(
    var context : Context, var matches: ArrayList<Person>, var onClickListener : MatchesOnCLickListener
): RecyclerView.Adapter<MatchesAdapter.MatchesViewHolder>() {

    override fun getItemCount() = matches.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MatchesViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_matches,
                parent,
                false
            )
        )

    @SuppressLint("NotifyDataSetChanged")
    fun setMatches(matches: List<Person>) {
        this.matches.clear()
        this.matches.addAll(matches)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addMatches(matches: List<Person>) {
        this.matches.addAll(matches)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateMatches(matches: List<Person>) {
        this.matches.addAll(matches)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearMatches(){
        this.matches.clear()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setSelected(position: Int){
        selectedPosition = position
        notifyDataSetChanged()
    }

    private var selectedPosition = 0
    override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {
        val movie = matches[position]

        if (position==matches.size-1){
            holder.binding.idDivider.hide()
        } else {
            holder.binding.idDivider.show()
        }

        holder.binding.data = movie

        holder.binding.root.setOnClickListener {
            onClickListener.onMatchClickListener(movie,position)
        }

        movie.picture?.large?.let {
            val placeHolder = ContextCompat.getDrawable(context,R.drawable.ic_person)
            holder.binding.idArtistImage.loadImage(it,placeHolder) {
            }
        }
//
//        movie.title?.let {
//            holder.binding.idTitle.text = it
//        }
    }

   inner class MatchesViewHolder(
        val binding: AdapterMatchesBinding
    ) : RecyclerView.ViewHolder(binding.root)
}

interface MatchesOnCLickListener {
    fun onMatchClickListener(person : Person, position: Int)
}