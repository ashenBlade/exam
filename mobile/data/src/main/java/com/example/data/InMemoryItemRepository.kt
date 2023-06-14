package com.example.data

import com.example.core.IItemRepository
import com.example.core.Item

class InMemoryItemRepository: IItemRepository {
    val items = arrayListOf<Item>()
    override suspend fun getAllItems(): ArrayList<Item> {
        return items;
    }

    override suspend fun createItem(value: String): Item {
        val id = items.size + 1
        val item = Item(id, value)
        items.add(item)
        return item
    }

    override suspend fun removeItem(id: Int): Item {
        var found = items.find {
            it.id == id
        }
        if (found != null) {
            items.remove(found)
            return found
        }
        throw Exception("Нет такого айтема")
    }

    override suspend fun editItem(id: Int, value: String): Item {
        val found = items.find {
            it.id == id
        }
        if (found != null) {
            return found
        }
        throw Exception("Нет такого айтема")
    }
}