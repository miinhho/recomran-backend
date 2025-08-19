package io.miinhho.recomran.user

import com.fasterxml.jackson.annotation.JsonIgnore
import io.miinhho.recomran.common.entity.BaseTimeEntity
import io.miinhho.recomran.history.PlaceHistory
import io.miinhho.recomran.saved.SavedPlace
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "user")
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var id: Long? = null,

    @Column(unique = true)
    var email: String,

    @JsonIgnore
    var password: String,

    @Enumerated(EnumType.STRING)
    var role: UserRole = UserRole.USER,

    var username: String? = null,

    var image: String? = null,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var savedPlaces: MutableList<SavedPlace> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var historyPlace: MutableList<PlaceHistory> = mutableListOf(),
) : BaseTimeEntity(), UserDetails {
    /**
     * 유저의 username 이 아닌 email 을 반환합니다.
     *
     * username 은 중복이 허용되지만 email 은 unique 한 값으로 설정되었기 때문입니다.
     */
    override fun getUsername(): String = email
    override fun getPassword(): String = password
    override fun getAuthorities(): Collection<GrantedAuthority> =
        listOf(SimpleGrantedAuthority("ROLE_$role"))
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}

enum class UserRole {
    USER, ADMIN;
}