package site.metacoding.springlogin.web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.springlogin.domain.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JoinReqDto {

    @Pattern(regexp = "[a-zA-Z1-9]{4,20}", message = "유저네임은 한글이 들어갈 수 없습니다.")
    @Size(min = 4, max = 20)
    @NotBlank // @NotNull, @NotEmpty 두개의 조합
    private String username;

    @Size(min = 4, max = 20)
    @NotBlank
    private String password;

    @Size(min = 10, max = 300)
    @NotBlank
    private String addr;

    public User toEntity() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAddr(addr);
        return user;
    }
}