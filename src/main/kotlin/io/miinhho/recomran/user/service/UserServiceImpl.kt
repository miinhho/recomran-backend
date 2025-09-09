package io.miinhho.recomran.user.service

import io.miinhho.recomran.user.exception.UserIllegalEmailException
import io.miinhho.recomran.user.exception.UserIllegalNameException
import io.miinhho.recomran.user.model.Email
import io.miinhho.recomran.user.model.User
import io.miinhho.recomran.user.model.Username
import io.miinhho.recomran.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {
    @Transactional
    override fun changePassword(user: User, newPassword: String) {
        val user = user.changePassword(newPassword)
        userRepository.save(user)
    }

    @Transactional
    override fun changeEmail(user: User, newEmail: String) {
        try {
            val newEmail = Email(newEmail)
            val user = user.changeEmail(newEmail)
            userRepository.save(user)
        } catch (e: IllegalArgumentException) {
            throw UserIllegalEmailException(e)
        }
    }

    @Transactional
    override fun changeUsername(user: User, newUsername: String) {
        try {
            val username = Username(newUsername)
            val user = user.updateProfile(username = username)
            userRepository.save(user)
        } catch (e: IllegalArgumentException) {
            throw UserIllegalNameException(e)
        }
    }

}