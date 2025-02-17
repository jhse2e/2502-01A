package app.sync.domain.user.api;

import app.sync.domain.user.dto.request.UserAuthenticateDto;
import app.sync.domain.user.dto.request.UserCreateDto;
import app.sync.domain.user.dto.request.UserEmailCodeAuthenticateDto;
import app.sync.domain.user.dto.request.UserEmailCodeCreateDto;
import app.sync.domain.user.dto.response.UserAuthenticateResultDto;
import app.sync.domain.user.dto.response.UserCreateResultDto;
import app.sync.domain.user.dto.response.UserEmailCodeAuthenticateResultDto;
import app.sync.domain.user.dto.response.UserEmailCodeCreateResultDto;
import app.sync.domain.user.service.UserService;
import app.sync.global.api.ServerResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;

    @Operation(summary = "회원 생성", description = "")
    @PostMapping(value = {"/api/users"})
    public ResponseEntity<ServerResponse<UserCreateResultDto>> createUser(
        @RequestBody(required = true) UserCreateDto userDto
    ) {
        ServerResponse<UserCreateResultDto> response = ServerResponse.ok(userService.createUser(userDto));

        return new ResponseEntity<>(response, response.status());
    }

    @Operation(summary = "회원 인증 메일 생성", description = "")
    @PostMapping(value = {"/api/users/email-code"})
    public ResponseEntity<ServerResponse<UserEmailCodeCreateResultDto>> createUserEmailCode(
        @RequestBody(required = true) UserEmailCodeCreateDto userDto
    ) {
        ServerResponse<UserEmailCodeCreateResultDto> response = ServerResponse.ok(userService.createUserEmailCode(userDto));

        return new ResponseEntity<>(response, response.status());
    }

    @Operation(summary = "회원 인증", description = "")
    @PostMapping(value = {"/api/users/authenticate"})
    public ResponseEntity<ServerResponse<UserAuthenticateResultDto>> authenticateUser(
        @RequestBody(required = true) UserAuthenticateDto userDto
    ) {
        ServerResponse<UserAuthenticateResultDto> response = ServerResponse.ok(userService.authenticateUser(userDto));

        return new ResponseEntity<>(response, response.status());
    }

    @Operation(summary = "회원 인증 메일 번호 확인", description = "")
    @PostMapping(value = {"/api/users/email-code/authenticate"})
    public ResponseEntity<ServerResponse<UserEmailCodeAuthenticateResultDto>> authenticateUserEmailCode(
        @RequestBody(required = true) UserEmailCodeAuthenticateDto userDto
    ) {
        ServerResponse<UserEmailCodeAuthenticateResultDto> response = ServerResponse.ok(userService.authenticateUserEmailCode(userDto));

        return new ResponseEntity<>(response, response.status());
    }
}