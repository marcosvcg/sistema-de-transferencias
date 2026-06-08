package com.marcosvcg.handsonhorizon.controller;

import com.marcosvcg.handsonhorizon.model.dto.LoginRequestDTO;
import com.marcosvcg.handsonhorizon.model.dto.LoginResponseDTO;
import com.marcosvcg.handsonhorizon.model.entities.Pessoa;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Login e logout de usuários")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Operation(summary = "Login", description = "Autentica com e-mail e senha.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO dto,
            HttpServletRequest request
    ) {
        Authentication autenticacao = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.senha())
        );

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(autenticacao);

        HttpSession session = request.getSession(true);
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                context
        );

        Pessoa pessoa = (Pessoa) autenticacao.getPrincipal();
        return ResponseEntity.ok(LoginResponseDTO.from(pessoa));
    }

    @Operation(summary = "Logout", description = "Encerra a sessão do usuário autenticado.")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return ResponseEntity.ok("{\"mensagem\": \"Logout realizado com sucesso!\"}");
    }

    @Operation(summary = "Sessão atual", description = "Retorna os dados do usuário autenticado.")
    @GetMapping("/me")
    public ResponseEntity<LoginResponseDTO> me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Pessoa pessoa = (Pessoa) auth.getPrincipal();
        return ResponseEntity.ok(LoginResponseDTO.from(pessoa));
    }
}
