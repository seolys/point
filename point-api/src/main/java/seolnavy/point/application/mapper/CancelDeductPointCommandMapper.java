package seolnavy.point.application.mapper;

import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import seolnavy.point.domain.deduct.DeductPointInfo.CancelDeductPointInfo;
import seolnavy.point.domain.deduct.DeductPointInfo.DeductPointDetailInfo;
import seolnavy.point.domain.earn.EarnPointCommand.RestorePoint;
import seolnavy.point.domain.earn.EarnPointCommand.RestorePointInfo;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CancelDeductPointCommandMapper {

	public static RestorePoint of(final CancelDeductPointInfo cancelDeductPointInfo) {
		final var restorePointInfos = cancelDeductPointInfo.getDeductPointDetailInfos()
				.stream()
				.map(CancelDeductPointCommandMapper::of)
				.collect(Collectors.toList());
		return RestorePoint.of(restorePointInfos);
	}

	public static RestorePointInfo of(final DeductPointDetailInfo deductPointDetailInfo) {
		return RestorePointInfo.of(deductPointDetailInfo.getEarnPointNo(), deductPointDetailInfo.getPoint());
	}

}
