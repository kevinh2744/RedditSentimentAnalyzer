package RedditSentimentAnalyzer;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StockHistoryAnalyzer {

    public static List<HistoricalQuote> getHist(String ticker, Date d1, Date d2) {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();

        // gets all quotes from this date to present
        from.setTime(d1);
        to.setTime(d2);

        Stock stock;
        try {
            stock = YahooFinance.get(ticker);
            List<HistoricalQuote> stockHistQuotes = stock.getHistory(from, to, Interval.DAILY);
            return stockHistQuotes;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
