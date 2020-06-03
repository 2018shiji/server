package com.module.server;

import com.module.server.mBeanInfo.ServerAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 阻塞式IO的服务端
 */
@Component
public class BioServer {

    @Autowired
    @Qualifier("serverMBean")
    private ServerAttribute serverAttribute;
    /** to be module import **/
    //@Autowired private ITaskDispatcher taskDispatcher;

    public void startBioServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(serverAttribute.getPort());
            Socket socket = serverSocket.accept();
            /**
             * 业务处理模块
             */
            InputStream in = socket.getInputStream();
            InputStreamReader inReader = new InputStreamReader(in);
            BufferedReader bufReader = new BufferedReader(inReader);
            System.out.println("i am here here here");

            String line = "";
            StringBuilder content = new StringBuilder();
            while((line = bufReader.readLine()) != null){
                content.append(line);
            }

//            Task task = new Task();
//            task.setContent(content.toString());
//            taskDispatcher.dispatchTask(task);

            String reply = "welcome";
            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out);
            writer.write(reply);
            writer.flush();

            writer.close();
            out.close();
            bufReader.close();
            inReader.close();
            in.close();
            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
    }

}
