package com.google.sps.servlets;

import java.io.FileReader;
import com.opencsv.CSVReader;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/sentiment")
public class SentimentAnalysisServlet extends HttpServlet {
  public float readDataLineByLine(String file){
    try {
        // Create an object of filereader
        // class with CSV file as a parameter.
        FileReader filereader = new FileReader(file);
        // create csvReader object passing
        // file reader as a parameter
        CSVReader csvReader = new CSVReader(filereader);
        String[] nextRecord;
        float score = 0;
        int linecount = 0;
        // we are going to read data line by line
        while ((nextRecord = csvReader.readNext()) != null) {
            String str = nextRecord[0];
            try{
                score += getSentimentScore(str);
            }catch(Exception e){
                continue;
            }
            linecount += 1;
        }
        return score/linecount;
    }
    catch (Exception e) {
        e.printStackTrace();
    }
    return 0;
  }
  public float getSentimentScore(String tweet){
    float score = 0;
    Document doc =
        Document.newBuilder().setContent(tweet).setType(Document.Type.PLAIN_TEXT).build();
    try{
        LanguageServiceClient languageService = LanguageServiceClient.create();
        Sentiment sentiment = languageService.analyzeSentiment(doc).getDocumentSentiment();
        score = sentiment.getScore();
        languageService.close();
    }catch(Exception e){
        return 0;
    }
    return score;
  }
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //String message = request.getParameter("message");
    float avgScore = readDataLineByLine("/home/rghosh/software-product-sprint/portfolio/src/data2.csv");
    response.setContentType("text/html;");
    response.getWriter().println("<h1>Sentiment Analysis</h1>");
    //response.getWriter().println("<p>Scores: " + scores + "</p>");
    response.getWriter().println("<p>Avg Sentiment analysis score for Amazon Product: " + avgScore + "</p>");
    response.getWriter().println("<p><a href=\"/\">Back</a></p>");
  }
}
