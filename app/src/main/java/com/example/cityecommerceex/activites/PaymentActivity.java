package com.example.cityecommerceex.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cityecommerceex.adapters.ProductAdapter;
import com.example.cityecommerceex.databinding.ActivityPaymentBinding;
import com.example.cityecommerceex.model.Product;
import com.example.cityecommerceex.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {



    ActivityPaymentBinding binding;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

  String orderCode=getIntent().getStringExtra("orderCode");

binding.webview.setMixedContentAllowed(true);
binding.webview.loadUrl(Constants.PAYMENT_URL+orderCode);

  getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }



    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}