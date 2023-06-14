package com.example.core

interface IItemRepository {
    suspend fun getAllItems(): ArrayList<Item>
    suspend fun createItem(value: String): Item
    suspend fun removeItem(id: Int): Item
    suspend fun editItem(id: Int, value: String): Item
}