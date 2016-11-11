package facade;

import entity.SimpleAd;
import java.util.List;

public interface IAdFacade {

    List<SimpleAd> getRandom();

    List<SimpleAd> getRecent();

    List<SimpleAd> getSpecificAds(String searchText, String priceRange);
}
