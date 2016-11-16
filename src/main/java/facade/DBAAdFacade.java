package facade;

import entity.SimpleAd;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import jsonconverter.DBAAdConverter;

public class DBAAdFacade implements IAdFacade {

    private static DBAAdFacade instance = null;

    public static DBAAdFacade getClassInstance() {
        if (instance == null) {
            instance = new DBAAdFacade();
        }
        return instance;
    }

    @Override
    public List<SimpleAd> getRecent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SimpleAd> getRandom() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://api.dba.dk/api/v2/ads/cassearch");
        return DBAAdConverter.getClassInstance().getAdFromJson(target.request(MediaType.APPLICATION_JSON).header("dbaapikey", "087157d7-84d5-4f2b-1d02-08d282f6c857").get(String.class));
    }

    @Override
    public List<SimpleAd> getSpecificAds(String searchText, String priceRange) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://api.dba.dk/api/v2/ads/cassearch?q=" + searchText + "&p=" + priceRange);
        return DBAAdConverter.getClassInstance().getAdFromJson(target.request(MediaType.APPLICATION_JSON).header("dbaapikey", "087157d7-84d5-4f2b-1d02-08d282f6c857").get(String.class));
    }
    
    public static void main(String[] args) {
        for (SimpleAd ad : new DBAAdFacade().getSpecificAds("hej", "")) {
            System.out.println(ad.getPrice());
        }
    }
}
