package alan.software.roomdatabase.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ProductModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "product_name")
    public String productName;

    @ColumnInfo(name = "price")
    public int price;

    @ColumnInfo(name = "stock_amount")
    public int stockAmount;

    public ProductModel(String productName, int price, int stockAmount) {
        this.productName = productName;
        this.price = price;
        this.stockAmount = stockAmount;
    }
}
