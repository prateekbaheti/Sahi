/**
 * Copyright  2006  V Narayan Raman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * Authors: Dodda Rampradeep and Deepak Lewis
 */
package net.sf.sahi.command;

import net.sf.sahi.request.HttpRequest;
import net.sf.sahi.response.HttpResponse;
import net.sf.sahi.response.SimpleHttpResponse;

import java.io.IOException;

public class CommandInvoker {
    private static final int NORMAL_TERMINATION = 0;
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    public HttpResponse execute(HttpRequest request) throws InterruptedException {
        String command = request.getParameter(RequestConstants.COMMAND);
        boolean isSynchronous = Boolean.toString(true).equals(request.getParameter(RequestConstants.SYNC));
        String exitStatus = executeSystemCommand(command, isSynchronous);
        return new SimpleHttpResponse(exitStatus);
    }

    private String executeSystemCommand(String command, boolean isSynchronous) throws InterruptedException {
        try {
            System.out.println("Executing: "+command);
            Process process = Runtime.getRuntime().exec(command);
            return (isSynchronous) ? getExitStatus(process) : SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAILURE;
        }
    }

    private String getExitStatus(Process process) throws InterruptedException {
        return (NORMAL_TERMINATION == process.waitFor()) ? SUCCESS : FAILURE;
    }
}