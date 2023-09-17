package nl.louis.filecapdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileMetaData {

    private String filename;
    private long size;
    private String mimetype;
}
