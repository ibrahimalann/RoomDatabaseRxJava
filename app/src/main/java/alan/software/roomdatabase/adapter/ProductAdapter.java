package alan.software.roomdatabase.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import alan.software.roomdatabase.databinding.RecyclerviewRowBinding;
import alan.software.roomdatabase.model.ProductModel;
import alan.software.roomdatabase.view.AddActivity;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    List<ProductModel> productModels;

    public ProductAdapter(List<ProductModel> productModels) {
        this.productModels = productModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerviewRowBinding recyclerviewRowBinding=RecyclerviewRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(recyclerviewRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.productNameText.setText(productModels.get(position).productName);
        holder.binding.productPriceText.setText(String.valueOf(productModels.get(position).price));
        holder.binding.productStockText.setText(String.valueOf(productModels.get(position).stockAmount));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.itemView.getContext(), AddActivity.class);
                intent.putExtra("dataId",productModels.get(holder.getAdapterPosition()));
                intent.putExtra("info","old");
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return productModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerviewRowBinding binding;
        public ViewHolder(RecyclerviewRowBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
