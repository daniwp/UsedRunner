package jsonconverter;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.SimpleAd;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class EbayAdConverter implements IAdConverter {

    private static EbayAdConverter instance = null;
    private static Gson gson = null;

    public static EbayAdConverter getClassInstance() {
        if (instance == null) {
            instance = new EbayAdConverter();
        }
        return instance;
    }

    public static Gson getGsonInstance() {
        if (gson == null) {
            gson = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        }
        return gson;
    }

    public double getRate() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://quote.yahoo.com/d/quotes.csv?s=USDDKK=X&f=l1&e=.csv");
        return Double.parseDouble(target.request(MediaType.TEXT_HTML).get(String.class));
    }

    @Override
    public List<SimpleAd> getAdFromJson(String json) {
        List<SimpleAd> ads = new ArrayList();
        double rate = getRate();
        JsonArray jsonArr = new JsonParser().parse(json).getAsJsonObject().getAsJsonArray("findItemsByKeywordsResponse").get(0).getAsJsonObject().getAsJsonArray("searchResult").get(0).getAsJsonObject().getAsJsonArray("item");

        for (JsonElement e : jsonArr) {
            JsonObject jsonAd = e.getAsJsonObject();

            String title = jsonAd.get("title").getAsString();
            String adLink = jsonAd.get("viewItemURL").getAsString();
            String thumbnail = "";
            if (jsonAd.get("galleryURL") != null) {
                thumbnail = jsonAd.get("galleryURL").getAsString();
            }
            double price = jsonAd.getAsJsonArray("sellingStatus").get(0).getAsJsonObject().getAsJsonArray("currentPrice").get(0).getAsJsonObject().get("__value__").getAsDouble();
            price *= rate;
            SimpleAd sAd = new SimpleAd(title, adLink, thumbnail, price, "EBAY");
            ads.add(sAd);
        }

        return ads;
    }

    @Override
    public String getJsonFromAds(List<SimpleAd> ad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void main(String[] args) {

    }
}
