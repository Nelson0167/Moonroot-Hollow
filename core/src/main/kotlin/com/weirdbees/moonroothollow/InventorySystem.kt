package com.weirdbees.moonroothollow

data class Item(
    val id: String,
    var quantity: Int = 1
)

class InventorySystem {
    private val items = mutableMapOf<String, Item>()

    init {
        println("InventorySystem Initialized.")
    }

    fun addItem(id: String, amount: Int = 1) {
        val existingItem = items[id]
        if (existingItem != null) {
            existingItem.quantity += amount
        } else {
            items[id] = Item(id, amount)
        }
    }

    fun removeItem(id: String, amount: Int = 1): Boolean {
        val item = items[id] ?: return false
        return if (item.quantity >= amount) {
            item.quantity -= amount
            if (item.quantity == 0) {
                items.remove(id)
            }
            true
        } else {
            false
        }
    }

    fun getItemCount(id: String): Int {
        return items[id]?.quantity ?: 0
    }

    fun printInventory() {
        println("Your Shit:")
        for ((_, item) in items) {
            println("${item.id}: ${item.quantity}")
        }
    }
}
