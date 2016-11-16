package facade;

import com.google.gson.Gson;
import controller.AdController;
import entity.SimpleAd;
import java.io.IOException;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import jsonconverter.EbayAdConverter;

public class EbayAdFacade implements IAdFacade {

    private static EbayAdFacade instance = null;

    public static EbayAdFacade getClassInstance() {
        if (instance == null) {
            instance = new EbayAdFacade();
        }
        return instance;
    }

    @Override
    public List<SimpleAd> getRandom() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsByKeywords&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=DanielWi-3Semeste-PRD-c45f64428-bc9a0454&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&keywords=random");
        return EbayAdConverter.getClassInstance().getAdFromJson(target.request(MediaType.APPLICATION_JSON).get(String.class));
    }

    @Override
    public List<SimpleAd> getRecent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SimpleAd> getSpecificAds(String searchText, String priceRange) {
        if (priceRange.length() > 0) {
            String[] minMaxPrice = priceRange.split("-");
            priceRange = "&itemFilter(0).name=MaxPrice&itemFilter(0).value=" + minMaxPrice[1] + "&itemFilter(1).name=MinPrice&itemFilter(1).value=" + minMaxPrice[0];
        }
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsByKeywords&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=DanielWi-3Semeste-PRD-c45f64428-bc9a0454&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&keywords=" + searchText + priceRange);
        return EbayAdConverter.getClassInstance().getAdFromJson(target.request(MediaType.APPLICATION_JSON).get(String.class));
    }

    public static void main(String[] args) {
        System.out.println(new Gson().toJson(AdController.getClassInstance().getSearchResults("hej", "")));
    }

}
