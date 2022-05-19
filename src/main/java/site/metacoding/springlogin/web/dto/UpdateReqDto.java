package site.metacoding.springlogin.web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateReqDto {

    @Size(min = 4, max = 20)
    @NotBlank
    private String password;

    @Size(min = 10, max = 300)
    @NotBlank
    private String addr;
}
