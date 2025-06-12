package com.java.DTO;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@SuppressWarnings("serial")
public class UsuarioDetalleDTO implements UserDetails {

    private final String usuario;
    private final String clave;
    private final int permisos;

    public UsuarioDetalleDTO(String usuario, String clave, int permisos) {
        this.usuario = usuario;
        this.clave = clave;
        this.permisos = permisos;
    }

    public int getPermisos() {
        return permisos;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override public String getPassword() { return clave; }
    @Override public String getUsername() { return usuario; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
