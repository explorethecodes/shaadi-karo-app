package com.fincare.shaadikaro.ui.persons

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fincare.shaadikaro.R
import com.fincare.shaadikaro.data.local.database.entities.Person
import com.fincare.shaadikaro.data.network.utils.CallInfo
import com.fincare.shaadikaro.data.network.utils.NetworkCallListener
import com.fincare.shaadikaro.data.network.utils.NoInternetException
import com.fincare.shaadikaro.databinding.ActivityPersonsBinding
import com.fincare.shaadikaro.utils.startPhotoActivity
import com.fincare.support.alerts.AlertCallbacks
import com.fincare.support.alerts.noInternetAlert
import com.fincare.support.alerts.somethingWentWrongAlert
import com.fincare.support.views.hide
import com.fincare.support.views.show
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class PersonsActivity : AppCompatActivity(), NetworkCallListener {

    private val viewModel: PersonsViewModel by viewModels()

    lateinit var binding : ActivityPersonsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_persons)
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

        binding.idSwipeRefreshLayout.setOnRefreshListener {
            setIsFetchNeeded(true)
            requestPersons()
        }

        binding.idRefresh.setOnClickListener {
            setIsFetchNeeded(true)
            requestPersons()
        }
    }

    private fun set() {
        persons()
    }

    //---------------------------------------------- PERSONS -------------------------------------------//
    private fun persons() {
        preparePersons()
        requestPersons()
        observePersons()
    }
    private fun preparePersons() {
        personsRecyclerView()
    }
    private fun requestPersons() {
        viewModel.requestPersons(viewModel.personsRequest)
    }
    private fun observePersons() {
        viewModel.persons.observe(this) { persons ->
            if (!persons.isNullOrEmpty()){
                personsResults(true)
                personsAdapter?.setPersons(persons)
            } else {
                personsResults(false)
            }
        }
    }
    private fun updatePerson(person: Person){
        viewModel.updatePerson(person)
    }

    private fun setIsFetchNeeded(isNeeded : Boolean) {
        viewModel.setIsFetchNeeded(isNeeded)
    }

    @SuppressLint("StaticFieldLeak")
    var personsRecyclerView: RecyclerView? = null
    private var personsAdapter: PersonsAdapter? = null
    private fun personsRecyclerView() {
        personsRecyclerView = binding.idRecyclerView
        personsAdapter = PersonsAdapter(this,arrayListOf(), object : PersonsOnCLickListener {
            override fun onPersonClick(person: Person) {
            }

            override fun onPersonPhotoClick(imageUrl: String) {
                startPhotoActivity(imageUrl)
            }

            override fun onPersonAccept(person: Person) {
                updatePerson(person)
            }

            override fun onPersonDecline(person: Person) {
                updatePerson(person)
            }

            override fun onPersonChange(person: Person) {
                updatePerson(person)
            }

        })

        personsRecyclerView?.apply {
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL,false)
            adapter = personsAdapter
        }

//        personsRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(personsRecyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(personsRecyclerView, dx, dy)
//                val linearLayoutManager = personsRecyclerView.layoutManager as LinearLayoutManager?
//                val personsCount = personsAdapter?.itemCount
//                personsCount?.let {
//                    if (personsCount >= 10){
//                        linearLayoutManager?.let { linearLayoutManager ->
//                            val lastCompleteVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
//                            if (lastCompleteVisibleItemPosition == personsCount - 1) {
//
////                                if (! personsRecyclerView.canScrollVertically(1)){
////                                    viewModel.personsData?.getNextPage()?.let {
////                                        viewModel.personsRequest.page = it
////                                        viewModel.personsRequest.callCode = CallCode.MOVIES_LOAD_MORE
////                                        requestPersons()
////                                    }
////                                }
//                            }
//                        }
//                    }
//                }
//            }
//        })

    }

    private fun personsResults(haveResults: Boolean){
        if (haveResults){
            setIsFetchNeeded(false)
            binding.idNoResults.hide()
            binding.idRecyclerView.show()
        } else {
            binding.idNoResults.show()
            binding.idRecyclerView.hide()
        }
    }
    private fun personsProgress(isLoading: Boolean) {
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
        personsProgress(true)
    }

    override fun onNetworkCallSuccess(callInfo: CallInfo) {
        personsProgress(false)
    }

    override fun onNetworkCallFailure(callInfo: CallInfo) {
        personsProgress(false)
        if (callInfo.exception is NoInternetException) {
            noInternetAlert {
                when(it){
                    AlertCallbacks.TRY_AGAIN -> {
                        setIsFetchNeeded(true)
                        requestPersons()
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
                            requestPersons()
                        }
                        AlertCallbacks.QUIT -> onBackPressed()
                    }
                }
            }
        }
    }

    override fun onNetworkCallCancel(callInfo: CallInfo) {
        personsProgress(false)
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