package hacathon2k16.drugdealer.model;

/**
 * Created by vlastachu on 19/11/16.
 */

public class Drug {
    DrugKind drugKind;
    Store store;
    long price;

    public Drug(DrugKind drugKind, Store store, long price) {
        this.drugKind = drugKind;
        this.store = store;
        this.price = price;
    }

    public DrugKind getDrugKind() {
        return drugKind;
    }

    public Store getStore() {
        return store;
    }

    public long getPrice() {
        return price;
    }
}
