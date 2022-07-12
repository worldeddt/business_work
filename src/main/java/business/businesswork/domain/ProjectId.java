package business.businesswork.domain;

import javax.persistence.Column;
import java.io.Serializable;

public class ProjectId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name="project_index")
    private long projectIndex;
}
