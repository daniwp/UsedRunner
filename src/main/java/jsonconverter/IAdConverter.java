package jsonconverter;

import entity.SimpleAd;
import java.util.List;

public interface IAdConverter {

    List<SimpleAd> getAdFromJson(String json);

    String getJsonFromAds(List<SimpleAd> ad);
}
