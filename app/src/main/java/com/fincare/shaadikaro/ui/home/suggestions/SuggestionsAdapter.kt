package com.fincare.shaadikaro.ui.home.suggestions

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fincare.support.images.loadImage
import com.fincare.shaadikaro.R
import com.fincare.shaadikaro.data.local.database.entities.Suggestion
import com.fincare.shaadikaro.databinding.AdapterSuggestionsBinding
import com.fincare.support.views.hide
import com.fincare.support.views.show

class SuggestionsAdapter(
    var context : Context, var suggestions: ArrayList<Suggestion>, var onClickListener : SuggestionsOnCLickListener
): RecyclerView.Adapter<SuggestionsAdapter.SuggestionsViewHolder>() {

    override fun getItemCount() = suggestions.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SuggestionsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_suggestions,
                parent,
                false
            )
        )

    @SuppressLint("NotifyDataSetChanged")
    fun setSuggestions(suggestions: List<Suggestion>) {
        this.suggestions.clear()
        this.suggestions.addAll(suggestions)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addSuggestions(suggestions: List<Suggestion>) {
        this.suggestions.addAll(suggestions)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setSelected(position: Int){
        selectedPosition = position
        notifyDataSetChanged()
    }

    private var selectedPosition = 0
    override fun onBindViewHolder(holder: SuggestionsViewHolder, position: Int) {
        val data = suggestions[position]
        holder.bind(data,position)
    }

    private fun SuggestionsViewHolder.bind(data: Suggestion, position: Int){
        binding.data = data

        if (position==suggestions.size-1){
            binding.idDivider.hide()
        } else {
            binding.idDivider.show()
        }

        binding.root.setOnClickListener {
            onClickListener.onSuggestionClick(data)
        }

        binding.idArtistImage.setOnClickListener {
            data.picture?.large?.let { imageUrl -> onClickListener.onSuggestionPhotoClick(imageUrl) }
        }
        binding.idAccept.setOnClickListener {
            data.hasActionTaken = true
            data.isAccepted = true
            onClickListener.onSuggestionAccept(data)
        }

        binding.idReject.setOnClickListener {
            data.hasActionTaken = true
            data.isAccepted = false
            onClickListener.onSuggestionDecline(data)
        }

        binding.idChange.setOnClickListener {
            data.hasActionTaken = false
            data.isAccepted = false
            onClickListener.onSuggestionChange(data)
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

    inner class SuggestionsViewHolder(
        val binding: AdapterSuggestionsBinding
    ) : RecyclerView.ViewHolder(binding.root)
}

interface SuggestionsOnCLickListener {
    fun onSuggestionClick(suggestion : Suggestion)
    fun onSuggestionPhotoClick(imageUrl : String)
    fun onSuggestionAccept(suggestion : Suggestion)
    fun onSuggestionDecline(suggestion : Suggestion)
    fun onSuggestionChange(suggestion: Suggestion)
}