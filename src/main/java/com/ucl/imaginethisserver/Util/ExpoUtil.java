package com.ucl.imaginethisserver.Util;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DockerClientBuilder;
import com.ucl.imaginethisserver.FigmaComponents.FigmaFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExpoUtil {


    public void publish(FigmaFile figmaFile) {

    }

}
