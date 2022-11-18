package org.aquam.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LiveRowModel {

    // patternId

    private Long liveRowId;
    private int rowNumber;
    private String schema;
    private Boolean isTitleRow;
    private Boolean isInfoRow;
    private Boolean isCrossed;

    public LiveRowModel(Long liveRowId, int rowNumber, String schema,
                        Boolean isTitleRow, Boolean isInfoRow) {
        this.liveRowId = liveRowId;
        this.rowNumber = rowNumber;
        this.schema = schema;
        this.isTitleRow = isTitleRow;
        this.isInfoRow = isInfoRow;
        this.isCrossed = false;
    }
}
