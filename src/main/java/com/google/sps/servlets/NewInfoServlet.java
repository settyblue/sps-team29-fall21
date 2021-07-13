package com.google.sps.servlets;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/** Servlet responsible for creating new tasks. */
@WebServlet("/new-analysis")
public class NewTaskServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Sanitize user input to remove HTML tags and JavaScript.
    String title = Jsoup.clean(request.getParameter("title"), Whitelist.none());
    long timestamp = System.currentTimeMillis();
    //Add day!
    //Add Rating/grade

    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    KeyFactory keyFactory = datastore.newKeyFactory().setKind("Analysis");
    FullEntity taskEntity =
        Entity.newBuilder(keyFactory.newKey())
            .set("title", title) //name of company
            .set("day", day)
            .set("timestamp", timestamp)
            .set("rating", rating)
            .build();
    datastore.put(taskEntity);

    response.sendRedirect("/index.html");
  }
}