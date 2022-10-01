package seolnavy.point.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity<PK extends Serializable> implements Persistable<PK>, Serializable {

	@CreatedDate
	@Column(name = "CREATED_DATE", nullable = false)
	private LocalDateTime createdDate; // 등록일

	@LastModifiedDate
	@Column(name = "UPDATED_DATE", nullable = false)
	private LocalDateTime updatedDate; // 수정일

	@Override
	public boolean isNew() {
		return Objects.isNull(createdDate);
	}

}