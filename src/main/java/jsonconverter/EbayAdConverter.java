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

    @Override
    public List<SimpleAd> getAdFromJson(String json) {
        List<SimpleAd> ads = new ArrayList();
        JsonArray jsonArr = new JsonParser().parse(json).getAsJsonObject().get("findItemsByKeywordsResponse").getAsJsonArray().get(0).getAsJsonObject().get("searchResult").getAsJsonArray().get(0).getAsJsonObject().get("item").getAsJsonArray();

        for (JsonElement e : jsonArr) {
            JsonObject jsonAd = e.getAsJsonObject();

            String title = jsonAd.get("title").getAsString();
            String adLink = jsonAd.get("viewItemURL").getAsString();
            String thumbnail = "";
            if (jsonAd.get("galleryURL") != null) {
                thumbnail = jsonAd.get("galleryURL").getAsString();
            }
            double price = jsonAd.getAsJsonArray("sellingStatus").get(0).getAsJsonObject().getAsJsonArray("currentPrice").get(0).getAsJsonObject().get("__value__").getAsDouble();
            SimpleAd sAd = new SimpleAd(title, adLink, thumbnail, price);
            ads.add(sAd);
        }

        return ads;
    }

    @Override
    public String getJsonFromAds(List<SimpleAd> ad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
