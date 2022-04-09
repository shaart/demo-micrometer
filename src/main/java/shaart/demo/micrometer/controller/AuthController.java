package shaart.demo.micrometer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shaart.demo.micrometer.entity.Otp;
import shaart.demo.micrometer.repository.OtpRepository;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Slf4j
@Transactional
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final OtpRepository otpRepository;

  @PostMapping("/generate")
  public OtpGenerationResultDto generateOtp(@Valid @RequestBody GenerateOtpRequestDto request) {
    var phoneNumber = request.getPhone();
    log.info("Request to generate OTP for phone = {}", phoneNumber);

    var otp = Otp.builder()
        .code(generateOtpCode())
        .phone(phoneNumber)
        .build();

    var saved = otpRepository.save(otp);

    UUID otpKey = saved.getId();
    log.info("Generated and sent OTP with id = {} for phone = {}", otpKey, phoneNumber);
    return new OtpGenerationResultDto(otpKey);
  }

  @PostMapping("/confirm")
  public OtpConfirmationResultDto confirmOtp(@Valid @RequestBody ConfirmOtpRequestDto request) {
    var phoneNumber = request.getPhone();
    var otpKey = request.getOtpKey();
    var otpCode = request.getOtpCode();
    log.info("Request to confirm OTP for phone = {}", phoneNumber);

    var sentOtps = otpRepository.findAllByPhone(phoneNumber);
    if (sentOtps.isEmpty()) {
      log.warn("Unknown phone = {}. Cannot confirm OTP", phoneNumber);
      throw new IllegalArgumentException("Unknown phone number");
    }

    var hasMatches = sentOtps.stream()
        .anyMatch(aSentOtp ->
            Objects.equals(aSentOtp.getId(), otpKey)
                && Objects.equals(aSentOtp.getCode(), otpCode)
                && Objects.equals(aSentOtp.getPhone(), phoneNumber));

    if (hasMatches) {
      otpRepository.deleteAllByPhone(phoneNumber);
      UUID sessionId = UUID.randomUUID();
      log.warn("Matched phone and OTP for phone = {}. Created session with id = {}",
          phoneNumber, sessionId);
      return new OtpConfirmationResultDto(sessionId);
    }

    log.warn("No matched phone and OTP for phone = {}, otpKey = {}, otpCode = {}",
        phoneNumber, otpKey, otpCode);
    throw new IllegalArgumentException("Wrong OTP");
  }

  private String generateOtpCode() {
    return String.format("%06d", ThreadLocalRandom.current().nextInt(0, 999_999));
  }
}
