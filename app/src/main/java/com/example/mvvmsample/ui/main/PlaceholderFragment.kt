package com.example.mvvmsample.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmsample.R
import com.example.mvvmsample.Utils.Status
import com.example.mvvmsample.db.User
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
        pageViewModel.getUsers(false)?.observe(this, Observer {
            Log.d("Placeholder",it.status.toString())
            when (it?.status) {
                Status.SUCCESS -> {
                    stopLoading(it.status)
                    setUpViews(it.data)
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

    private fun setUpViews(data: List<User>?) {

    }

    private fun startLoading() {
        progressbar.visibility = View.VISIBLE
    }

    private fun stopLoading(status: Status) {
        progressbar.visibility = View.GONE
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}