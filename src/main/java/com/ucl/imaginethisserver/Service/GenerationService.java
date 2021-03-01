package com.ucl.imaginethisserver.Service;

import com.ucl.imaginethisserver.FigmaComponents.FigmaFile;
import com.ucl.imaginethisserver.Util.Authentication;

import java.io.File;
import java.util.List;

public interface GenerationService {

    boolean buildProject(String projectID, Authentication auth, List<String> wireframeList, boolean publish);

    File downloadProject(String projectID);

    FigmaFile getFigmaFile(String projectID, Authentication auth);

}
