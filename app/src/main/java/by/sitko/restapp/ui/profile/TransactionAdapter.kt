package by.sitko.restapp.ui.profile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.sitko.restapp.R
import by.sitko.restapp.api.TransactionResponse
import by.sitko.restapp.ui.profile.TransactionAdapter.TransactionHolder
import kotlinx.android.synthetic.main.item_view_transaction.view.*

class TransactionAdapter : RecyclerView.Adapter<TransactionHolder>() {

    private val data = mutableListOf<TransactionResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
          TransactionHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view_transaction, parent, false))

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        holder.populate(data.get(position))
    }

    fun update(transactions: List<TransactionResponse>) {
        data.clear()
        data.addAll(transactions)
        notifyDataSetChanged()
    }

    inner class TransactionHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun populate(item: TransactionResponse) {
            with(itemView) {
                transactionName.text = "index: ${item.x!!.tx_index}"
                transactionPrice.text = item.x.size.toString()
            }
        }
    }
}