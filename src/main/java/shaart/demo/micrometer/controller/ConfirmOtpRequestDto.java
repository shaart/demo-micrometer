package shaart.demo.micrometer.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shaart.demo.micrometer.util.Consts;

import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmOtpRequestDto {

  @NotNull
  @Pattern(regexp = Consts.PHONE_REGEX)
  public String phone;

  @NotNull
  public UUID otpKey;

  @NotNull
  public String otpCode;
}
