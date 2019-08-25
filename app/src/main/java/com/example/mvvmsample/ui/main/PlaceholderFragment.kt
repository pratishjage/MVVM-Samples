package com.example.mvvmsample.ui.main

import android.content.Intent
import android.location.Address
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmsample.R
import com.example.mvvmsample.Utils.AppConstants.ADDRESS
import com.example.mvvmsample.Utils.AppConstants.LAT
import com.example.mvvmsample.Utils.AppConstants.LNG
import com.example.mvvmsample.Utils.AppConstants.NAME
import com.example.mvvmsample.Utils.Status
import com.example.mvvmsample.db.User
import com.example.mvvmsample.ui.Adapters.UsersAdapter
import com.example.mvvmsample.ui.MapsActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.failure_layout.*
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*  pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
              setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)


          }*/

        pageViewModel = activity?.run {
            ViewModelProviders.of(this)[PageViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        /* val textView: TextView = root.findViewById(R.id.section_label)
         pageViewModel.text.observe(this, Observer<String> {
             textView.text = it
         })*/
        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = UsersAdapter(this.requireContext(), {
            val userBundle = Bundle()
            userBundle.putString(NAME, it.name)
            userBundle.putString(LAT, it.address?.geo?.lat)
            userBundle.putString(LNG, it.address?.geo?.lng)
            userBundle.putString(ADDRESS, Gson().toJson(it.address))
            val intent = Intent(activity, MapsActivity::class.java)
            intent.putExtras(userBundle)
            startActivity(intent)
        }, {
            pageViewModel.updateUser(it)
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        pageViewModel.getUsers(false)?.observe(this, Observer {
            Log.d("Placeholder", it.status.toString())
            when (it?.status) {
                Status.SUCCESS -> {
                    stopLoading(it.status)
                    it.data?.let {
                        val filter = it.filter { it.isFavourite == arguments?.getBoolean(IS_FAV) }
                        adapter.setUsers(filter)
                    }
                }
                Status.LOADING -> {
                    startLoading()
                }
                Status.ERROR -> {
                    stopLoading(it.status)

                }
            }
        })
        return root
    }


    private fun startLoading() {
        recyclerview.visibility = View.GONE
        failure_layout.visibility = View.GONE
        progressbar.visibility = View.VISIBLE

    }

    private fun stopLoading(status: Status) {
        progressbar.visibility = View.GONE

        when (status) {
            Status.SUCCESS -> {
                failure_layout.visibility = View.GONE
                recyclerview.visibility = View.VISIBLE
            }
            Status.ERROR -> {
                failure_layout.visibility = View.VISIBLE
                recyclerview.visibility = View.GONE
                retry_btn.setOnClickListener {
                    pageViewModel.getUsers()
                }
            }
        }
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val IS_FAV = "is_fav"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(isFav: Boolean): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_FAV, isFav)
                }
            }
        }
    }
}