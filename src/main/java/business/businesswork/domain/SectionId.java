package business.businesswork.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class SectionId implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @Column(name="section_index")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long index;
}
