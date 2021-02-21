package com.ucl.imaginethisserver.Service;

import com.ucl.imaginethisserver.CustomExceptions.NotFoundException;
import com.ucl.imaginethisserver.FigmaComponents.FigmaFile;
import com.ucl.imaginethisserver.Util.Authentication;

import java.util.List;

public interface GenerationService {

    boolean buildProject(String projectID, Authentication auth, List<String> wireframeList);

    java.io.File downloadProject(String projectID);

    FigmaFile getFigmaFile(String projectID, Authentication auth);

}
