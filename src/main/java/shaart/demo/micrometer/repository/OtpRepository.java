package shaart.demo.micrometer.repository;

import org.springframework.data.repository.CrudRepository;
import shaart.demo.micrometer.entity.Otp;

import java.util.List;
import java.util.UUID;

public interface OtpRepository extends CrudRepository<Otp, UUID> {

  List<Otp> findAllByPhone(String phone);

  void deleteAllByPhone(String phone);
}
