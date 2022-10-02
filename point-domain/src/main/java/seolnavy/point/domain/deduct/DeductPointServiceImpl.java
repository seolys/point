package seolnavy.point.domain.deduct;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seolnavy.point.domain.deduct.DeductPointCommand.CancelDeductPoint;
import seolnavy.point.domain.deduct.DeductPointCommand.RegisterDeductPoint;
import seolnavy.point.domain.deduct.DeductPointInfo.CancelDeductPointInfo;
import seolnavy.point.domain.deduct.DeductPointInfo.Main;
import seolnavy.point.domain.deduct.exception.DeductPointNotFoundException;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeductPointServiceImpl implements DeductPointService {

	private final DeductPointReader deductPointReader;
	private final DeductPointStore deductPointStore;

	@Transactional
	@Override public Main registerDeductPoint(final RegisterDeductPoint registerDeductPoint) {
		final var saveDeductPoint = deductPointStore.save(registerDeductPoint.toEntity());
		final var savedDeductPointNo = saveDeductPoint.getDeductPointNo();

		deductPointStore.saveAll(registerDeductPoint.toEntityDetails(savedDeductPointNo));
		return DeductPointInfo.Main.of(savedDeductPointNo);
	}

	@Transactional
	@Override public CancelDeductPointInfo cancelDeductPoint(final CancelDeductPoint cancelDeductPoint) {
		final var deductPoint = deductPointReader.findById(cancelDeductPoint.getUserNo(), cancelDeductPoint.getDeductPointNo())
				.orElseThrow(DeductPointNotFoundException::new);
		deductPoint.cancel();
		return CancelDeductPointInfo.of(deductPoint, deductPoint.getDeductPointDetails());
	}

	@Override public boolean existsByDeductUuid(final String deductUuid) {
		return deductPointReader.existsByDeductUuid(deductUuid);
	}

}
