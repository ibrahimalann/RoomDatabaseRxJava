package alan.software.roomdatabase.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import alan.software.roomdatabase.model.ProductModel;

@Database(entities = {ProductModel.class},version = 2)
public abstract class ProductDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
}
