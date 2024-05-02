package ai.grayducks.grayducksservice.repository.entities.recipe

import jakarta.persistence.Column
import java.time.Instant


open class TrackableObject(
    @Column(nullable = false)
     var createdBy: String,

    @Column(nullable = false)
     var createdOn: Instant,

    @Column(nullable = false)
     var modifiedBy: String,

    @Column(nullable = false)
    var modifiedOn: Instant,
    ) {
}
