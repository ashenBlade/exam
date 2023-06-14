package com.example.data

import CreateItemRequestKt
import ItemServiceGrpcKt
import com.example.core.IItemRepository
import com.example.core.Item
import io.grpc.Channel

class GrpcItemRepository(val channel: Channel): IItemRepository {
    private val service = ItemServiceGrpcKt.ItemServiceCoroutineStub(channel)

    override suspend fun getAllItems(): ArrayList<Item> {
        val response = service.getAllItems(ItemOuterClass.GetAllItemsRequest.getDefaultInstance())
        return ArrayList(response.itemsOrBuilderList.map {
            Item(it.id, it.value)
        })
    }

    override suspend fun createItem(value: String): Item {
        val request = ItemOuterClass.CreateItemRequest.newBuilder()
            .setValue(value)
            .build()
        val response = service.createItem(request)
        return Item(id = response.item.id, value = response.item.value)
    }

    override suspend fun removeItem(id: Int): Item {
        val response = service.removeItem(ItemOuterClass.RemoveItemRequest.newBuilder().setId(id).build())
        return Item(response.deleted.id, response.deleted.value)
    }

    override suspend fun editItem(id: Int, value: String): Item {
        val response = service.editItem(ItemOuterClass.EditItemRequest.newBuilder().setId(id).setValue(value).build())
        return Item(response.item.id, response.item.value)
    }

}