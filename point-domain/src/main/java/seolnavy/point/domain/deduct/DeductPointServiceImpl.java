package seolnavy.point.domain.deduct;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import seolnavy.point.domain.deduct.DeductPointCommand.CancelDeductPoint;
import seolnavy.point.domain.deduct.DeductPointCommand.RegisterDeductPoint;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeductPointServiceImpl implements DeductPointService {

	private final DeductPointReader deductPointReader;
	private final DeductPointStore deductPointStore;

	@Override public void registerDeductPoint(final RegisterDeductPoint registerDeductPoint) {

	}

	@Override public void cancelDeductPoint(final CancelDeductPoint cancelDeductPoint) {

	}

}
