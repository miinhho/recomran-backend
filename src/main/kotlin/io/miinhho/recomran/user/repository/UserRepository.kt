package io.miinhho.recomran.user.repository

import io.miinhho.recomran.common.repository.CommonRepository
import io.miinhho.recomran.user.model.Email
import io.miinhho.recomran.user.model.User

interface UserRepository : CommonRepository<User> {
    fun findById(id: Long): User?
    fun findByEmail(email: Email): User?
}