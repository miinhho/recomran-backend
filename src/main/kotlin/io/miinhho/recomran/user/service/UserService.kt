package io.miinhho.recomran.user.service

import io.miinhho.recomran.user.exception.UserAccessDeniedException
import io.miinhho.recomran.user.exception.UserNotFoundException
import io.miinhho.recomran.user.model.User
import io.miinhho.recomran.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    @Transactional
    fun changePassword(user: User, password: String) {
        val user = user.changePassword(password)
        userRepository.save(user)
    }

    @Transactional
    fun changeEmail(user: User, email: String) {
        val user = user.changeEmail(email)
        userRepository.save(user)
    }

    @Transactional
    fun changeUsername(user: User, username: String) {
        val user = user.changeUsername(username)
        userRepository.save(user)
    }

    @Transactional
    fun deleteUser(requester: User, targetId: Long) {
        val targetUser = userRepository.findById(targetId)
            ?: throw UserNotFoundException(targetId)
        if (targetUser.id != requester.id || !requester.isAdmin()) {
            throw UserAccessDeniedException()
        }
        userRepository.delete(targetId)
    }
}