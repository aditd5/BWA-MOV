package com.aditd5.bwamov.home

import android.icu.text.NumberFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aditd5.bwamov.R
import com.aditd5.bwamov.model.Film
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var preferences: com.aditd5.bwamov.utils.Preferences
    private lateinit var mDatabase: DatabaseReference
    private var dataList = ArrayList<Film>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        preferences = com.aditd5.bwamov.utils.Preferences(requireActivity().applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        val tvName = view?.findViewById<TextView>(R.id.tv_name)
        val tvBalance = view?.findViewById<TextView>(R.id.tv_balance)
        val ivProfile = view?.findViewById<ImageView>(R.id.iv_profile)
        val rvNowPlaying = view?.findViewById<RecyclerView>(R.id.rv_now_playing)
        val rvCommingSoon = view?.findViewById<RecyclerView>(R.id.rv_coming_soon)

        tvName?.setText(preferences.getValues("username"))
        if (!preferences.getValues("balance").equals("")) {
            currency(preferences.getValues("balance")!!.toDouble(), tvBalance!!)
        }
        if (ivProfile != null) {
            Glide.with(this)
                .load(preferences.getValues("url"))
                .apply(RequestOptions.circleCropTransform())
                .into(ivProfile)

            rvNowPlaying?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            rvCommingSoon?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

            getData()
        }
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for (getDataSnapshot in dataSnapshot.children) {
                    var film = getDataSnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }

                val rvNowPlaying = view?.findViewById<RecyclerView>(R.id.rv_now_playing)
                val rvCommingSoon = view?.findViewById<RecyclerView>(R.id.rv_coming_soon)

                rvNowPlaying?.adapter = NowPlayingAdapter(dataList) {

                }

               rvCommingSoon?.adapter = ComingSoonAdapter(dataList) {

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context,""+databaseError.message,Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun currency(price: Double, textView: TextView) {
        val localID = Locale("in", "ID")
        val format = NumberFormat.getCurrencyInstance(localID)
        textView.setText(format.format(price))
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DashboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}