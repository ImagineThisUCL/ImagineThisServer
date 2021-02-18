package com.ucl.imaginethisserver.Service;

import com.ucl.imaginethisserver.CustomExceptions.NotFoundException;
import com.ucl.imaginethisserver.DAO.FigmaFile;
import com.ucl.imaginethisserver.Util.Authentication;

import java.util.List;

public interface GenerationService {

    void buildProject(String projectID, Authentication auth, List<String> wireframeList);

    java.io.File downloadProject(String projectID) throws NotFoundException;

    FigmaFile getFigmaFile(String projectID, Authentication auth);

}