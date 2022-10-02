package seolnavy.point.application.mapper;

import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import seolnavy.point.domain.deduct.DeductPointCommand.DeductPointRequest;
import seolnavy.point.domain.deduct.DeductPointCommand.RegisterDeductPoint;
import seolnavy.point.domain.deduct.DeductPointCommand.RegisterDeductPointDetail;
import seolnavy.point.domain.earn.EarnPointCommand.DeductPoint;
import seolnavy.point.domain.earn.EarnPointInfo.DeductPointResult;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeductPointCommandMapper {

	public static DeductPoint of(final DeductPointRequest command) {
		return DeductPoint.of(command.getUserNo(), command.getDeductPoint());
	}

	public static RegisterDeductPoint of(final DeductPointRequest command, final List<DeductPointResult> deductPointResults) {
		return RegisterDeductPoint.of(command.getDeductUuid(), command.getUserNo(), command.getDeductPoint(), DeductPointCommandMapper.of(deductPointResults));
	}

	private static List<RegisterDeductPointDetail> of(final List<DeductPointResult> deductPointResults) {
		return deductPointResults.stream()
				.map(DeductPointCommandMapper::of)
				.collect(toList());
	}

	private static RegisterDeductPointDetail of(final DeductPointResult deductPointResult) {
		return RegisterDeductPointDetail.of(deductPointResult.getEarnPointNo(), deductPointResult.getDeductedPoint());
	}

}
