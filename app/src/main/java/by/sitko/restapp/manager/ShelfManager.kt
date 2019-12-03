package by.zmitrocc.shop.manager

data class ShelfModel(
      val number: String,
      val address: String
)

data class ShelfSizeModel(
      val sizeId: Int,
      val address: String
)

@Throws
fun String?.toShelfModels(): MutableList<ShelfModel> {
    if (this == null) return mutableListOf()

    val result = mutableListOf<ShelfModel>()

    replace("\"", "")
          .replace("{", "")
          .replace("}", "")
          .split(",").forEach {
              val number = it.split(":").getOrNull(0) ?: return@forEach
              if (number.toIntOrNull() == null) return@forEach
              val shelf = it.split(":").getOrNull(1) ?: return@forEach
              if (shelf.isBlank()) return@forEach
              result.add(ShelfModel(number, shelf))
          }

    return result.toMutableList()
}

fun List<ShelfModel>.toBodyString(): String? {
    if (isEmpty()) return null

    return this.joinToString(
          prefix = "{",
          postfix = "}",
          separator = ", ",
          transform = {
              StringBuilder()
//                    .append("\"")
                    .append(it.number)
//                    .append("\"")
                    .append(":")
//                    .append("\"")
                    .append(it.address)
//                    .append("\"")
                    .toString()
          }
    )
}

fun List<ShelfSizeModel>.toBodyCollected(orderId:Int): String? {
    if (isEmpty()) return null
    val result = StringBuilder()
    result.append("{")
    result.append('"')
    result.append("checked")
    result.append('"')
    result.append(':')
    result.append('{')

    this.forEachIndexed { index, shelfModel ->
        result.append('"')
        result.append("${index.plus(1)}")
        result.append('"')
        result.append(":")
        result.append("{")
        result.append('"')
        result.append("size_id")
        result.append('"')
        result.append(":")
        result.append(shelfModel.sizeId)
        result.append(",")
        result.append('"')
        result.append("place")
        result.append('"')
        result.append(":")
        result.append('"')
        result.append(shelfModel.address)
        result.append('"')
        result.append("}")
        if (index != this.size - 1) result.append(",")
    }

    result.append("}")
    result.append(",")
    result.append('"')
    result.append("order_id")
    result.append('"')
    result.append(":")
    result.append(orderId)

    result.append("}")

    return result.toString()
}