package com.softserve.edu.service.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;

import com.softserve.edu.service.storage.impl.SaveOptions;

public interface FileOperations {

    public Boolean putResourse(InputStream stream, Path path, String resourseName,
            SaveOptions options);

    public InputStream getResourseStream(Path path) throws IOException, FileNotFoundException,
            Exception;

    public URI getResourseURI(Path directory, String fileName);

}
