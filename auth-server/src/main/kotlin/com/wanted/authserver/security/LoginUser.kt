package com.wanted.authserver.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class LoginUser(
    private val userId: Long,
): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return mutableListOf<GrantedAuthority>(SimpleGrantedAuthority("ROLE_USER"))
    }

    override fun getPassword(): String {
        return ""
    }

    override fun getUsername(): String {
        return userId.toString()
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}