package com.rakeshv.bitvavotrading.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class TelegramBotService {

    @Value("${telegram.bot.path}")
    private String scriptPath;

    @Value("${python.path}")
    private String pythonPath;

    public void sendNotification(String message) {
        String line = pythonPath + " " + getPythonPath() + " " + message;
        CommandLine cmd = CommandLine.parse(line);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            PumpStreamHandler streamHandler = new PumpStreamHandler(byteArrayOutputStream);
            DefaultExecutor executor = new DefaultExecutor();
            executor.setStreamHandler(streamHandler);

            executor.execute(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPythonPath() {
        File file = null;
        try {
            file = new File(scriptPath).getAbsoluteFile();
            return file.getAbsolutePath();
        } catch (Exception e) {
            log.error("Unable to locate {}", scriptPath);
        }
        return "";
    }
}
