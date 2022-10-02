package seolnavy.point.domain.deduct;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import seolnavy.point.domain.deduct.DeductPointCommand.CancelDeductPoint;
import seolnavy.point.domain.deduct.DeductPointCommand.RegisterDeductPoint;
import seolnavy.point.domain.deduct.DeductPointInfo.Main;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeductPointServiceImpl implements DeductPointService {

	private final DeductPointReader deductPointReader;
	private final DeductPointStore deductPointStore;

	@Override public Main registerDeductPoint(final RegisterDeductPoint registerDeductPoint) {
		final var saveDeductPoint = deductPointStore.save(registerDeductPoint.toEntity());
		final var savedDeductPointNo = saveDeductPoint.getDeductPointNo();

		deductPointStore.saveAll(registerDeductPoint.toEntityDetails(savedDeductPointNo));
		return DeductPointInfo.Main.of(savedDeductPointNo);
	}

	@Override public void cancelDeductPoint(final CancelDeductPoint cancelDeductPoint) {

	}

	@Override public boolean existsByDeductUuid(final String deductUuid) {
		return deductPointReader.existsByDeductUuid(deductUuid);
	}

}
