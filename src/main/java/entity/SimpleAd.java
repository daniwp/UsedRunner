package entity;

public class SimpleAd {

    private String title;
    private String dealer;
    private String adLink;
    private String thumbnail;
    private double price;

    public SimpleAd() {
    }

    public SimpleAd(String title, String adLink, String thumbnail, double price, String dealer) {
        this.title = title;
        this.adLink = adLink;
        this.thumbnail = thumbnail;
        this.price = price;
        this.dealer = dealer;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdLink() {
        return adLink;
    }

    public void setAdLink(String adLink) {
        this.adLink = adLink;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
