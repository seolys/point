package seolnavy.point.infra.earn;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import seolnavy.point.domain.earn.EarnPoint;

public interface EarnPointRepository extends JpaRepository<EarnPoint, Long> {

	boolean existsByEarnUuid(String earnUuid);

	/**
	 * 유효 포인트 조회
	 * @param userNo
	 * @param expirationDate 만료일이 기준일 또는 기준일 이후
	 * @param remainPoint 기준금액 이상
	 * @return
	 */
	List<EarnPoint> findActivePointsByUserNoAndExpirationDateGreaterThanEqualAndRemainPointGreaterThanOrderByEarnPointNoAsc(
			Long userNo, LocalDate expirationDate, Long remainPoint);
}
