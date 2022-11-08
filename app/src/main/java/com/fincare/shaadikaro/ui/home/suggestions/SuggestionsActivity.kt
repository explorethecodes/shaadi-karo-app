package com.fincare.shaadikaro.ui.home.suggestions

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fincare.shaadikaro.R
import com.fincare.shaadikaro.data.local.database.entities.Suggestion
import com.fincare.shaadikaro.data.network.utils.CallInfo
import com.fincare.shaadikaro.data.network.utils.NetworkCallListener
import com.fincare.shaadikaro.data.network.utils.NoInternetException
import com.fincare.shaadikaro.databinding.ActivitySuggestionsBinding
import com.fincare.shaadikaro.store.widgets.alerts.AlertCallbacks
import com.fincare.shaadikaro.store.widgets.alerts.noInternetAlert
import com.fincare.shaadikaro.store.widgets.alerts.somethingWentWrongAlert
import com.fincare.shaadikaro.ui.home.HomeViewModel
import com.fincare.shaadikaro.utils.startPhotoActivity
import com.fincare.support.display.nightMode
import com.fincare.support.views.hide
import com.fincare.support.views.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuggestionsActivity : AppCompatActivity(), NetworkCallListener {

    private val viewModel: HomeViewModel by viewModels()

    lateinit var binding : ActivitySuggestionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_suggestions)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        create()
    }

    private fun create() {
        init()
        set()
    }

    @SuppressLint("RestrictedApi")
    private fun init() {
        viewModel.networkCallListener = this

        nightMode(false)

        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)

        binding.idSwipeRefreshLayout.setOnRefreshListener {
            setIsFetchNeeded(true)
            requestSuggestions()
        }

        binding.idRefresh.setOnClickListener {
            setIsFetchNeeded(true)
            requestSuggestions()
        }
    }

    private fun set() {
        suggestions()
    }

    //---------------------------------------------- MATCHES -------------------------------------------//
    private fun suggestions() {
        prepareSuggestions()
        requestSuggestions()
        observeSuggestions()
    }
    private fun prepareSuggestions() {
        suggestionsRecyclerView()
    }
    private fun requestSuggestions() {
        viewModel.requestSuggestions(viewModel.suggestionsRequest)
    }
    private fun observeSuggestions() {
        viewModel.suggestions.observe(this) { suggestions ->
            if (!suggestions.isNullOrEmpty()){
                suggestionsResults(true)
                suggestionsAdapter?.setSuggestions(suggestions)
            } else {
                suggestionsResults(false)
            }
        }
    }
    private fun updateSuggestion(suggestion: Suggestion){
        viewModel.updateSuggestion(suggestion)
    }

    private fun setIsFetchNeeded(isNeeded : Boolean) {
        viewModel.setIsFetchNeeded(isNeeded)
    }

    @SuppressLint("StaticFieldLeak")
    var suggestionsRecyclerView: RecyclerView? = null
    private var suggestionsAdapter: SuggestionsAdapter? = null
    private fun suggestionsRecyclerView() {
        suggestionsRecyclerView = binding.idRecyclerView
        suggestionsAdapter = SuggestionsAdapter(this,arrayListOf(), object : SuggestionsOnCLickListener {
            override fun onSuggestionClick(suggestion: Suggestion) {
            }

            override fun onSuggestionPhotoClick(imageUrl: String) {
                startPhotoActivity(imageUrl)
            }

            override fun onSuggestionAccept(suggestion: Suggestion) {
                updateSuggestion(suggestion)
            }

            override fun onSuggestionDecline(suggestion: Suggestion) {
                updateSuggestion(suggestion)
            }

            override fun onSuggestionChange(suggestion: Suggestion) {
                updateSuggestion(suggestion)
            }

        })

        suggestionsRecyclerView?.apply {
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL,false)
            adapter = suggestionsAdapter
        }

        suggestionsRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(suggestionsRecyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(suggestionsRecyclerView, dx, dy)
                val linearLayoutManager = suggestionsRecyclerView.layoutManager as LinearLayoutManager?
                val suggestionsCount = suggestionsAdapter?.itemCount
                suggestionsCount?.let {
                    if (suggestionsCount >= 20){
                        linearLayoutManager?.let { linearLayoutManager ->
                            val lastCompleteVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
                            if (lastCompleteVisibleItemPosition == suggestionsCount - 1) {

//                                if (! suggestionsRecyclerView.canScrollVertically(1)){
//                                    viewModel.suggestionsData?.getNextPage()?.let {
//                                        viewModel.suggestionsRequest.page = it
//                                        viewModel.suggestionsRequest.callCode = CallCode.MOVIES_LOAD_MORE
//                                        requestSuggestions()
//                                    }
//                                }
                            }
                        }
                    }
                }
            }
        })

    }

    private fun suggestionsResults(haveResults: Boolean){
        if (haveResults){
            setIsFetchNeeded(false)
            binding.idNoResults.hide()
            binding.idRecyclerView.show()
        } else {
            binding.idNoResults.show()
            binding.idRecyclerView.hide()
        }
    }
    private fun suggestionsProgress(isLoading: Boolean) {
        if (isLoading){
            binding.idRecyclerView.hide()
            binding.idProgressBar.show()
            binding.idNoResults.hide()
        } else{
            binding.idRecyclerView.show()
            binding.idProgressBar.hide()
            binding.idSwipeRefreshLayout.isRefreshing = false
        }
    }

    //----------------------------------------------- NETWORK --------------------------------------------//
    override fun onNetworkCallStarted(callInfo: CallInfo) {
        suggestionsProgress(true)
    }

    override fun onNetworkCallSuccess(callInfo: CallInfo) {
        suggestionsProgress(false)
    }

    override fun onNetworkCallFailure(callInfo: CallInfo) {
        suggestionsProgress(false)
        if (callInfo.exception is NoInternetException) {
            noInternetAlert {
                when(it){
                    AlertCallbacks.TRY_AGAIN -> {
                        setIsFetchNeeded(true)
                        requestSuggestions()
                    }
                    AlertCallbacks.QUIT -> onBackPressed()
                }
            }
        } else {
            callInfo.exception?.let {
                somethingWentWrongAlert (it){
                    when(it){
                        AlertCallbacks.TRY_AGAIN -> {
                            setIsFetchNeeded(true)
                            requestSuggestions()
                        }
                        AlertCallbacks.QUIT -> onBackPressed()
                    }
                }
            }
        }
    }

    override fun onNetworkCallCancel(callInfo: CallInfo) {
        suggestionsProgress(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}