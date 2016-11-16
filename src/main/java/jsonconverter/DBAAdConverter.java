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

public class DBAAdConverter implements IAdConverter {

    private static DBAAdConverter instance = null;
    private static Gson gson = null;

    public static DBAAdConverter getClassInstance() {
        if (instance == null) {
            instance = new DBAAdConverter();
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
        JsonArray jsonArr = new JsonParser().parse(json).getAsJsonObject().get("ads").getAsJsonArray();

        for (JsonElement e : jsonArr) {
            JsonObject jsonAd = e.getAsJsonObject();

            String title = jsonAd.get("title").getAsString();
            String description = jsonAd.get("description").getAsString();
            String adLink = jsonAd.getAsJsonObject("ad-url").get("href").getAsString();
            String thumbnail = "";
            if (jsonAd.getAsJsonArray("pictures").size() > 0) {
                thumbnail = jsonAd.getAsJsonArray("pictures").get(0).getAsJsonObject().getAsJsonArray("link").get(0).getAsJsonObject().get("href").getAsString();
            }
            double price = jsonAd.get("price").getAsInt();
            SimpleAd sAd = new SimpleAd(title, adLink, thumbnail, price, "DBA");
            sAd.setZipcode(jsonAd.getAsJsonObject("ad-address").get("zip-code").getAsInt());
            sAd.setDescription(description);
            ads.add(sAd);
        }

        return ads;
    }

    @Override
    public String getJsonFromAds(List<SimpleAd> ad) {
        return getGsonInstance().toJson(ad);
    }

}
