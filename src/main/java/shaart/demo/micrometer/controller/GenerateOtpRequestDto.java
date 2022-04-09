package shaart.demo.micrometer.controller;

import lombok.Data;
import shaart.demo.micrometer.util.Consts;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class GenerateOtpRequestDto {

  @NotNull
  @Pattern(regexp = Consts.PHONE_REGEX)
  private String phone;
}
