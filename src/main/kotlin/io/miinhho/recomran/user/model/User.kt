package io.miinhho.recomran.user.model

import io.miinhho.recomran.history.model.PlaceHistory
import io.miinhho.recomran.saved.model.SavedPlace
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class User(
    val id: Long? = null,
    val email: Email,
    val hashedPassword: Password,
    val role: UserRole = UserRole.USER,
    val username: Username? = null,
    val image: String? = null,
    val savedPlaces: List<SavedPlace> = emptyList(),
    val historyPlace: List<PlaceHistory> = emptyList()
) : UserDetails {
    fun changePassword(password: String): User {
        val newPassword = Password.from(password)
        return this.copy(hashedPassword = newPassword)
    }

    fun changeEmail(email: String): User {
        val newEmail = Email.from(email)
        return this.copy(email = newEmail)
    }

    fun changeUsername(username: String): User {
        val newUsername = Username.from(username)
        return this.copy(username = newUsername)
    }

    fun isAdmin(): Boolean = role == UserRole.ADMIN

    override fun getUsername(): String = email.value
    override fun getAuthorities(): Collection<GrantedAuthority> =
        listOf(SimpleGrantedAuthority("ROLE_$role"))
    override fun getPassword(): String = hashedPassword.value
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}