package com.aditd5.bwamov.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aditd5.bwamov.R
import com.aditd5.bwamov.model.Film
import com.bumptech.glide.Glide

class NowPlayingAdapter(private var data: ArrayList<Film>,
                        private var listener:(Film) -> Unit) :
    RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>() {

    private lateinit var contextAdapter: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NowPlayingAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_now_playing,parent,false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: NowPlayingAdapter.ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        private val tvGenre = view.findViewById<TextView>(R.id.tv_genre)
        private val tvRating = view.findViewById<TextView>(R.id.tv_rating)
        private val ivPoster = view.findViewById<ImageView>(R.id.iv_poster)

        fun bindItem(data: Film, listener: (Film) -> Unit, context: Context) {
            tvTitle.setText(data.title)
            tvGenre.setText(data.genre)
            tvRating.setText(data.rating)

            Glide.with(context)
                .load(data.poster)
                .into(ivPoster)

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }

}
