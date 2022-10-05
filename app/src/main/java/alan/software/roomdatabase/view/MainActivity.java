package alan.software.roomdatabase.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import alan.software.roomdatabase.R;
import alan.software.roomdatabase.adapter.ProductAdapter;
import alan.software.roomdatabase.database.ProductDao;
import alan.software.roomdatabase.database.ProductDatabase;
import alan.software.roomdatabase.databinding.ActivityMainBinding;
import alan.software.roomdatabase.model.ProductModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    ProductAdapter productAdapter;
    ProductDao productDao;
    ProductDatabase productDatabase;
    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        productDatabase= Room.databaseBuilder(getApplicationContext(),ProductDatabase.class,"Products").allowMainThreadQueries().build();
        productDao=productDatabase.productDao();

        compositeDisposable.add(productDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(MainActivity.this::getData));


    }
    private void getData(List<ProductModel> productModels){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        productAdapter=new ProductAdapter(productModels);
        binding.recyclerView.setAdapter(productAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() ==R.id.add){
            Intent intent=new Intent(MainActivity.this,AddActivity.class);
            intent.putExtra("info","new");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}