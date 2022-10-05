package alan.software.roomdatabase.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import alan.software.roomdatabase.model.ProductModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface ProductDao {
    @Query("select * from ProductModel")
    Flowable<List<ProductModel>> getAll();

    @Insert
    Completable insert(ProductModel productModel);

    @Delete
    Completable delete(ProductModel productModel);

    @Update
    Completable update(ProductModel productModel);
}
