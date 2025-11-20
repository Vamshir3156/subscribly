package com.example.subscribly.dto;

public class AuthResponse {

    private String token;
    private String role;
    private Long tenantId;

    public AuthResponse(String token, String role, Long tenantId) {
        this.token = token;
        this.role = role;
        this.tenantId = tenantId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}
