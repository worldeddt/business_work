package business.businesswork.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedDate
    @Column(name = "register_date", nullable = false, updatable = false)
    public LocalDateTime registerDate;

    @LastModifiedDate
    @Column(name = "last_modify_date")
    public LocalDateTime lastModifyDate;

    @Column(name = "delete_date")
    public LocalDateTime deleteDate;
}
