/**
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 *    Copyright 2014 Edgar Espina
 */
package io.jooby.internal.jetty;

import io.jooby.Jooby;
import io.jooby.Router;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class JettyMultiHandler extends AbstractHandler {
  private final List<Jooby> routers;
  private int bufferSize;
  private long maxRequestSize;

  public JettyMultiHandler(List<Jooby> routers, int bufferSize, long maxRequestSize) {
    this.routers = routers;
    this.bufferSize = bufferSize;
    this.maxRequestSize = maxRequestSize;
  }

  @Override public void handle(String target, Request request, HttpServletRequest servletRequest,
      HttpServletResponse response) {
    for (Router router : routers) {
      JettyContext ctx = new JettyContext(request, router, bufferSize, maxRequestSize);
      Router.Match match = router.match(ctx);
      if (match.matches()) {
        match.execute(ctx);
        return;
      }
    }
  }
}