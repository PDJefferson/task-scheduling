package services;

import domain.Data;

public interface FileRepository {

    Data readFromFile(String filePath);

}
