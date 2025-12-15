package br.com.wss.filters;

import br.com.wss.barbersync.entities.Account;
import br.com.wss.barbersync.repositories.AccountRepository;
import br.com.wss.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;


@AllArgsConstructor
@Component
public class JwtToken {

    private final JwtEncoder encoder;

    private final JwtDecoder decode;

    private final HttpServletRequest request;

    private final AccountRepository accountRepository;

    private static final long JWT_TOKEN_VALIDITY = (60 * 60 * 24 * 2);

    public String genereteToken(Account account){
        try {
            Instant now = Instant.now();

            // Garante que não é null (correção anterior)
            var scopes = account.getRole() != null ? account.getRole() : "USER";

            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("wss")
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(JWT_TOKEN_VALIDITY))
                    .subject(account.getUid())
                    .claim("scope", scopes)
                    .build();

            // IMPORTANTE: Definir o algoritmo no cabeçalho
            var encoderParameters = JwtEncoderParameters.from(
                    JwsHeader.with(MacAlgorithm.HS256).build(),
                    claims
            );

            return encoder.encode(encoderParameters).getTokenValue();

        } catch (Exception e) {
            e.printStackTrace(); // Mantenha isso para debugar se der erro
            throw new RuntimeException("Erro ao gerar token: " + e.getMessage());
        }
    }

//    public String genereteToken(Account account){
//        try {
//            Instant now = Instant.now();
//            long expire = 300L;
//
//            var scopes = account.getRole();
//
//            JwtClaimsSet claims = JwtClaimsSet.builder()
//                    .issuer("wss")
//                    .issuedAt(now)
//                    .expiresAt(now.plusSeconds(JWT_TOKEN_VALIDITY))
//                    .subject(account.getUid())
//                    .claim("scope", scopes)
//                    .build();
//
//            return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Não foi possível autenticar o usuário.");
//        }
//    }

    public UserTokenDetails getUserDetails() {
        final String requestTokenHeader = request.getHeader("Authorization");

        String jwtToken = null;

        if (requestTokenHeader != null) {
            if (requestTokenHeader.startsWith("Bearer "))
                jwtToken = requestTokenHeader.substring(7);
            else
                throw new BusinessException(HttpStatus.UNAUTHORIZED, "Invalid JWT Token!");

            final Jwt jwt = decodeTokenString(jwtToken);

            Account account = new Account();
            String id = jwt.getSubject();
            if (!id.isBlank()) {
                account = accountRepository.findById(id)
                        .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Usuário não encontrado!"));
                if (account.getDeleted() != null && Boolean.TRUE.equals(account.getDeleted())){
                    throw new BusinessException(HttpStatus.NOT_FOUND, "Usuário " + account.getUid() + " foi deletado");
                }
            }   
            return new UserTokenDetails(account.getName(), account, jwtToken);
        } else
            return new UserTokenDetails("anonymousUser", null, null);
    }

    public String getToken() {
        return request.getHeader("Authorization");
    }

    public Jwt decodeTokenString(String tokenString) {
        try {
            return decode.decode(tokenString);
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "Invalid JWT Token!");
        }
    }

    @AllArgsConstructor
    @Getter
    public class UserTokenDetails {
        private String username;
        private Account account;
        private String jwtToken;
    }
}
