package by.sitko.restapp.api

import com.google.gson.Gson

private val gson = Gson()

data class TransactionResponse(
      val op: String,
      val x: X?
) {
    companion object {
        fun newInstance(content: String) = gson.fromJson(content, TransactionResponse::class.java)
    }
}

data class X(
      val hash: String,
      val inputs: List<Input>,
      val lock_time: Int,
      val `out`: List<Out>,
      val relayed_by: String,
      val size: Int,
      val time: Int,
      val tx_index: Int,
      val ver: Int,
      val vin_sz: Int,
      val vout_sz: Int
)

data class PrevOut(
      val addr: String,
      val n: Int,
      val script: String,
      val spent: Boolean,
      val tx_index: Int,
      val type: Int,
      val value: Int
)

data class Out(
      val addr: String,
      val n: Int,
      val script: String,
      val spent: Boolean,
      val tx_index: Int,
      val type: Int,
      val value: Int
)

data class Input(
      val prev_out: PrevOut,
      val script: String,
      val sequence: Long
)