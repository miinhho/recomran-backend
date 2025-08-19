package io.miinhho.recomran.common.repository

interface CommonRepository<T> {
    fun save(entity: T): T
    fun delete(id: Long)
}