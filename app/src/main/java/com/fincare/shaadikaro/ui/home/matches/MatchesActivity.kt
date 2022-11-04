package com.fincare.shaadikaro.ui.home.matches

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fincare.shaadikaro.R
import com.fincare.shaadikaro.data.network.utils.CallInfo
import com.fincare.shaadikaro.data.network.utils.NetworkCallListener
import com.fincare.shaadikaro.data.network.utils.NoInternetException
import com.fincare.shaadikaro.data.network.models.collection.matches.MatchesData
import com.fincare.shaadikaro.data.network.models.collection.matches.Person
import com.fincare.shaadikaro.databinding.ActivityMatchesBinding
import com.fincare.shaadikaro.store.widgets.alerts.AlertCallbacks
import com.fincare.shaadikaro.store.widgets.alerts.noInternetAlert
import com.fincare.shaadikaro.store.widgets.alerts.somethingWentWrongAlert
import com.fincare.shaadikaro.ui.home.HomeViewModel
import com.fincare.support.views.hide
import com.fincare.support.views.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchesActivity : AppCompatActivity(), NetworkCallListener {

    private val viewModel: HomeViewModel by viewModels()

    lateinit var binding : ActivityMatchesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_matches)
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
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)

        binding.idSwipeRefreshLayout.setOnRefreshListener {
            requestMatches()
        }
    }

    private fun set() {
        matches()
    }

    //---------------------------------------------- MATCHES -------------------------------------------//
    private fun matches() {
        prepareMatches()
        requestMatches()
        observeMatches()
    }
    private fun prepareMatches() {
        matchesRecyclerView()
    }
    private fun requestMatches() {
        viewModel.requestMatches(viewModel.matchesRequest)
    }
    private fun observeMatches() {
        viewModel.matchesResponse.observe(this) { matchesResponse ->
            viewModel.matchesData = MatchesData(matchesResponse)
            viewModel.matchesData?.let {
                if (it.isOk()){
                    if (it.isSuccess()){
                        val matches = it.getMatches()
                        if (!matches.isNullOrEmpty()){
                            matchesResults(true)
                            matchesAdapter?.setMatches(matches)
                        } else {
                            matchesResults(false)
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    var matchesRecyclerView: RecyclerView? = null
    private var matchesAdapter: MatchesAdapter? = null
    private fun matchesRecyclerView() {
        matchesRecyclerView = binding.idRecyclerView
        matchesAdapter = MatchesAdapter(this,arrayListOf(), object : MatchesOnCLickListener {
            override fun onMatchClickListener(person: Person, position: Int) {
            }

        })

        matchesRecyclerView?.apply {
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL,false)
            adapter = matchesAdapter
        }

        matchesRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(matchesRecyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(matchesRecyclerView, dx, dy)
                val linearLayoutManager = matchesRecyclerView.layoutManager as LinearLayoutManager?
                val matchesCount = matchesAdapter?.itemCount
                matchesCount?.let {
                    if (matchesCount >= 20){
                        linearLayoutManager?.let { linearLayoutManager ->
                            val lastCompleteVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
                            if (lastCompleteVisibleItemPosition == matchesCount - 1) {

//                                if (! matchesRecyclerView.canScrollVertically(1)){
//                                    viewModel.matchesData?.getNextPage()?.let {
//                                        viewModel.matchesRequest.page = it
//                                        viewModel.matchesRequest.callCode = CallCode.MOVIES_LOAD_MORE
//                                        requestMatches()
//                                    }
//                                }
                            }
                        }
                    }
                }
            }
        })

    }

    private fun matchesResults(haveResults: Boolean){
        if (haveResults){
            binding.idNoResults.hide()
            binding.idRecyclerView.show()
        } else {
            binding.idNoResults.show()
            binding.idRecyclerView.hide()
        }
    }
    private fun matchesProgress(isLoading: Boolean) {
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
        matchesProgress(true)
    }

    override fun onNetworkCallSuccess(callInfo: CallInfo) {
        matchesProgress(false)
    }

    override fun onNetworkCallFailure(callInfo: CallInfo) {
        matchesProgress(false)
        if (callInfo.exception is NoInternetException) {
            noInternetAlert {
                when(it){
                    AlertCallbacks.TRY_AGAIN -> matches()
                    AlertCallbacks.QUIT -> onBackPressed()
                }
            }
        } else {
            callInfo.exception?.let {
                somethingWentWrongAlert (it){
                    when(it){
                        AlertCallbacks.TRY_AGAIN -> matches()
                        AlertCallbacks.QUIT -> onBackPressed()
                    }
                }
            }
        }
    }

    override fun onNetworkCallCancel(callInfo: CallInfo) {
        matchesProgress(false)
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