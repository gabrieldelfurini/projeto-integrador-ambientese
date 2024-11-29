package com.ambientese.grupo5.Services.UsuarioService;

import com.ambientese.grupo5.Model.FuncionarioModel;
import com.ambientese.grupo5.Model.UsuarioModel;
import com.ambientese.grupo5.Repository.FuncionarioRepository;
import com.ambientese.grupo5.Repository.UsuarioRepository;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private JWTUtil jwtUtil;

    public boolean authenticate(String login, String password) {
        UsuarioModel user = userRepository.findByLogin(login);
        if (user == null) {
            FuncionarioModel funcionarioModel = funcionarioRepository.findByEmail(login);
            if (funcionarioModel != null) {
                user = funcionarioModel.getUsuario();
            }
        }
        return user != null && BCrypt.checkpw(password, user.getPassword());
    }

    public String login(String login, String plainTextPassword) {
        if (authenticate(login, plainTextPassword)) {
            UsuarioModel user = userRepository.findByLogin(login);
            if (user == null) {
                FuncionarioModel funcionarioModel = funcionarioRepository.findByEmail(login);
                user = funcionarioModel.getUsuario();
            }
            FuncionarioModel funcionario = funcionarioRepository.findByUsuarioId(user.getId());
            
            String cargo = (funcionario != null) ? funcionario.getCargo().getDescricao() : "Admin";
            return jwtUtil.generateToken(login, user.getIsAdmin(), cargo);
        }
        return null;
    }

    public DecodedJWT validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    public ResponseEntity<String> forgotPassword(String email) {
        FuncionarioModel funcionario = funcionarioRepository.findByEmail(email);
        if (funcionario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email não encontrado");
        }
        UsuarioModel user = funcionario.getUsuario();
        String code = generateRecoveryCode();
        user.setRecoveryCode(code);
        userRepository.save(user);

        sendRecoveryEmail(email, code);

        return ResponseEntity.status(HttpStatus.OK).body("Email de recuperação enviado com sucesso");
    }

    private String generateRecoveryCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    private void sendRecoveryEmail(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Código de recuperação de senha");
        message.setText("Seu código para recuperação da senha: " + code);
        mailSender.send(message);
    }

    public ResponseEntity<String> resetPassword(String email, String recoveryCode, String newPassword) {
        FuncionarioModel funcionario = funcionarioRepository.findByEmail(email);
        if (funcionario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email não encontrado");
        }
        UsuarioModel user = funcionario.getUsuario();

        if (!recoveryCode.equals(user.getRecoveryCode())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código de recuperação inválido");
        }

        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        user.setPassword(hashedPassword);
        user.setRecoveryCode(null); // Clear the recovery code after successful reset
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body("Senha alterada com sucesso");
    }
}
