package com.example.cityecommerceex.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cityecommerceex.R;
import com.example.cityecommerceex.adapters.CartAdapter;
import com.example.cityecommerceex.databinding.ActivityCartBinding;
import com.example.cityecommerceex.model.Product;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

     CartAdapter adapter;
    ActivityCartBinding binding;
     ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


 Cart cart= TinyCartHelper.getCart();


        products=new ArrayList<>();
 for(Map.Entry<Item,Integer> item:cart.getAllItemsWithQty().entrySet())
 {

   Product product=(Product) item.getKey();
   int quantity=item.getValue();
   product.setQuantity(quantity);

   products.add(product);


 }








        adapter=new CartAdapter(this, products, new CartAdapter.CartListener() {
            @Override
            public void onQuantityChanged() {
                binding.subtotal.setText(String.format("INR %.2f",cart.getTotalPrice()));
            }
        });



        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration=new DividerItemDecoration(this,layoutManager.getOrientation());

binding.cartList.setLayoutManager(layoutManager);
binding.cartList.addItemDecoration(itemDecoration);
binding.cartList.setAdapter(adapter);


        binding.subtotal.setText(String.format("INR %.2f",cart.getTotalPrice()));

        binding.continuebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this,CheckoutActivity.class));
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}