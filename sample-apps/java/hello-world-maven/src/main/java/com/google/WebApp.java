// Copyright 2022 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.google;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebApp {
    private static Logger LOGGER = LoggerFactory.getLogger(WebApp.class);

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new HttpHandler() {
            public void handle( HttpExchange httpExchange ) throws IOException {
                LOGGER.debug("Request received");
                LOGGER.trace("Requestor: " + httpExchange.getRemoteAddress().getHostString().toString());

                byte[] response = "Hello, World!".getBytes();
                httpExchange.sendResponseHeaders(200, response.length);
                try (OutputStream os = httpExchange.getResponseBody()) {

                    os.write(response);
                }
            }
        });

        LOGGER.info("Listening at http://localhost:" + port);

        server.start();
    }
}
