package seolnavy.point.domain.user;

public interface UserReader {

	User findUserById(Long userNo);
	
	Long getRemainPoint(Long userNo);

}
