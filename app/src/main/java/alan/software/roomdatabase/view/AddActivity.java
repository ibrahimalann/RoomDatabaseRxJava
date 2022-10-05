package alan.software.roomdatabase.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import alan.software.roomdatabase.database.ProductDao;
import alan.software.roomdatabase.database.ProductDatabase;
import alan.software.roomdatabase.databinding.ActivityAddBinding;
import alan.software.roomdatabase.model.ProductModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddActivity extends AppCompatActivity {

    private ActivityAddBinding binding;

    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    private ProductModel productModel;
    private ProductDao productDao;
    private ProductDatabase productDatabase;

    private int price;
    private int stock;
    private String productName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        productDatabase= Room.databaseBuilder(getApplicationContext(),ProductDatabase.class,"Products").allowMainThreadQueries().build();
        productDao=productDatabase.productDao();

        Intent intent=getIntent();
        String info=intent.getStringExtra("info");
        if (info.equals("new")){
            binding.updateButton.setVisibility(View.GONE);
            binding.deleteButton.setVisibility(View.GONE);
        }else{
            binding.saveButton.setVisibility(View.GONE);
            productModel=(ProductModel) intent.getSerializableExtra("dataId");
            binding.productText.setText(productModel.productName);
            binding.priceText.setText(String.valueOf(productModel.price));
            binding.stockText.setText(String.valueOf(productModel.stockAmount));
        }

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
            }
        });
        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
            }
        });
        binding.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
    }

    private void setData(){
        productName=binding.productText.getText().toString();
        price=Integer.valueOf(binding.priceText.getText().toString());
        stock=Integer.valueOf(binding.stockText.getText().toString());
        productModel=new ProductModel(productName,price,stock);
        compositeDisposable.add(productDao.insert(productModel).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(AddActivity.this::handleResponse));
    }
    private void deleteData(){
        compositeDisposable.add(productDao.delete(productModel).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(AddActivity.this::handleResponse));
    }
    private void update(){
        productModel.productName=binding.productText.getText().toString();
        productModel.price=Integer.valueOf(binding.priceText.getText().toString());
        productModel.stockAmount=Integer.valueOf(binding.stockText.getText().toString());
        compositeDisposable.add(productDao.update(productModel).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(AddActivity.this::handleResponse));
    }

    private void handleResponse(){
        Intent intent=new Intent(AddActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}