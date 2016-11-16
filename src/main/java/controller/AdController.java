package controller;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.SimpleAd;
import facade.DBAAdFacade;
import facade.EbayAdFacade;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdController implements IAdController {

    private static Gson gson = null;
    private static AdController instance = null;

    public static AdController getClassInstance() {
        if (instance == null) {
            instance = new AdController();
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
    public String getSearchResults(String search, String price) {

        List<SimpleAd> ebay = EbayAdFacade.getClassInstance().getSpecificAds(search, price);
        List<SimpleAd> dba = DBAAdFacade.getClassInstance().getSpecificAds(search, price);

        List<SimpleAd> results = new ArrayList(ebay);
        results.addAll(dba);

        Collections.shuffle(results);

        return getGsonInstance().toJson(results);
    }
}
