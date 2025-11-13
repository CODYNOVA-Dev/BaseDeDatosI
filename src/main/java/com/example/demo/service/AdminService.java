package com.example.demo.service;

import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public List<Admin> getAll() {
        return adminRepository.findAll();
    }

    public Admin getById(Integer id) {
        return adminRepository.findById(id).orElse(null);
    }

    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

    public void delete(Integer id) {
        adminRepository.deleteById(id);
    }

    public Admin update(Integer id, Admin admin) {
        Admin existing = getById(id);
        if (existing != null) {
            existing.setCorreoAdmin(admin.getCorreoAdmin());
            existing.setContraseñaAdmin(admin.getContraseñaAdmin());
            return adminRepository.save(existing);
        }
        return null;
    }

    // 🔐 MÉTODO ESPECIAL PARA LOGIN
    public Admin login(String correo, String contraseña) {
        return adminRepository.findByCorreoAdminAndContraseñaAdmin(correo, contraseña)
                .orElse(null);
    }

    // Verificar si existe el correo
    public boolean existsByCorreo(String correo) {
        return adminRepository.findByCorreoAdmin(correo).isPresent();
    }
}