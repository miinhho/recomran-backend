package io.miinhho.recomran.user.model

import io.miinhho.recomran.history.model.PlaceHistory
import io.miinhho.recomran.saved.model.SavedPlace
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class User(
    val id: Long? = null,
    val email: Email,
    val hashedPassword: String,
    val role: UserRole = UserRole.USER,
    val username: Username? = null,
    val image: String? = null,
    val savedPlaces: List<SavedPlace> = emptyList(),
    val historyPlace: List<PlaceHistory> = emptyList()
) : UserDetails {
    fun changePassword(newPassword: String): User {
        return this.copy(hashedPassword = newPassword)
    }

    fun updateProfile(username: Username? = null, image: String? = null): User {
        return this.copy(
            username = username ?: this.username,
            image = image ?: this.image
        )
    }

    fun addSavedPlace(place: SavedPlace): User {
        return this.copy(savedPlaces = savedPlaces + place)
    }

    fun addPlaceHistory(history: PlaceHistory): User {
        return this.copy(historyPlace = historyPlace + history)
    }

    fun isAdmin(): Boolean = role == UserRole.ADMIN

    override fun getUsername(): String = email.value
    override fun getAuthorities(): Collection<GrantedAuthority> =
        listOf(SimpleGrantedAuthority("ROLE_$role"))
    override fun getPassword(): String = hashedPassword
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}