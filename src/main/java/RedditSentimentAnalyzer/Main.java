package RedditSentimentAnalyzer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.util.*;

import org.apache.log4j.BasicConfigurator;
import yahoofinance.histquotes.HistoricalQuote;

import static RedditSentimentAnalyzer.SentimentAnalyzer.*;

public class Main {

    public static void printTickerInfo(String ticker, int sentiment, Date d1, Date d2){

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

        String sentimentStr = "NULL";

        if (sentiment == 0) {
            sentimentStr = "Very negative";
        } else if (sentiment == 1){
            sentimentStr = "Negative";
        } else if (sentiment == 2){
            sentimentStr = "Neutral";
        } else if (sentiment == 3){
            sentimentStr = "Positive";
        } else if (sentiment == 4){
            sentimentStr = "Very Positive";
        }

        System.out.println("\n" + "Between " + sdformat.format(d1) + " and " + sdformat.format(d2) +
                ": The mean sentiment of " + ticker +  " was " + sentiment + ": '" + sentimentStr + "'.");
    }

    public static void main(String[] args) {

        //Initialize log4j system
        BasicConfigurator.configure();

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        TickerAnalysis analysis = new TickerAnalysis();

        List<String> tickers = Arrays.asList("TSLA", "NVDA", "FB", "AMD", "AAPL", "GME", "SDC", "NOK", "BBBY",
                "BB", "TGT", "KO", "WFC", "CVS", "GM");

        for (String ticker : tickers) {
            try {
                //EDIT DATE INFO HERE
                Date d1 = sdformat.parse("2021-11-01");
                Date d2 = sdformat.parse("2021-12-31");
                Date d3 = sdformat.parse("2022-02-28");

                int sentiment = analysis.getAverageSentimentOfTicker(ticker, d1, d2);

                printTickerInfo(ticker, sentiment, d1, d2);

                List<HistoricalQuote> appleHistQuotes = StockHistoryAnalyzer.getHist(ticker, d2, d3);
                BigDecimal reddit = appleHistQuotes.get(0).getClose();
                BigDecimal current = appleHistQuotes.get(appleHistQuotes.size() - 1).getClose();
                BigDecimal difference = current.subtract(reddit);

                System.out.println("\nThe difference between the current price and the price at the" +
                        "time of the last reddit post is " + difference.toPlainString());

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

}

