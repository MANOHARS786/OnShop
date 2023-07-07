package com.example.cityecommerceex.activites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cityecommerceex.adapters.CategoryAdapter;
import com.example.cityecommerceex.adapters.ProductAdapter;

import com.example.cityecommerceex.databinding.ActivityMainBinding;
import com.example.cityecommerceex.model.Category;
import com.example.cityecommerceex.model.Product;
import com.example.cityecommerceex.utils.Constants;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {



  CategoryAdapter categoryAdapter;

    ActivityMainBinding binding;
    ArrayList<Category> categories;



    ProductAdapter productAdapter;
    ArrayList<Product> products;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("query", text.toString());
                startActivity(intent);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });



        initCategories();
initProducts();
initSlider();




    }

    void getCategories()
    {
         RequestQueue queue= Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.GET, Constants.GET_CATEGORIES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject mainObj= new JSONObject(response);
                    if(mainObj.getString("status").equals("success"))
                    {
                        JSONArray categoriesArray=mainObj.getJSONArray("categories");
                        for(int i=0;i<categoriesArray.length();i++)
                        {
                            JSONObject object=categoriesArray.getJSONObject(i);
                            Category category=new Category(

                             object.getString("name"),
                                   Constants.CATEGORIES_IMAGE_URL+ object.getString("icon"),
                                    object.getString("color"),
                                    object.getString("brief"),
                                    object.getInt("id")


                            );
                            categories.add(category);

                        }
                        categoryAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        //Do nothing
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

       queue.add(request);
    }

    private void initSlider() {

      getRecentOffers();





    }

    void initCategories()
    {
        categories=new ArrayList<Category>();



        categoryAdapter=new CategoryAdapter(this,categories);

        getCategories();
        GridLayoutManager layoutManager=new GridLayoutManager(this,4);

        binding.CategoriesList.setLayoutManager(layoutManager);

        binding.CategoriesList.setAdapter(categoryAdapter);

    }


    void initProducts()
    {



        products=new ArrayList<>();








        productAdapter=new ProductAdapter(this,products);
        getRecentProducts();


        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        binding.ProductList.setLayoutManager(layoutManager);
        binding.ProductList.setAdapter(productAdapter);


    }


    void getRecentProducts()
    {

        RequestQueue queue=Volley.newRequestQueue(this);
        String url=Constants.GET_PRODUCTS_URL+"?count=8";
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);


             if(object.getString("status").equals("success"))
             {
                  JSONArray productArray=object.getJSONArray("products");
                  for(int i=0;i<productArray.length();i++)
                  {
                      JSONObject childObj=productArray.getJSONObject(i);
                      Product product=new Product(

                              childObj.getString("name"),
                              Constants.PRODUCTS_IMAGE_URL+childObj.getString("image"),
                              childObj.getString("status"),
                              childObj.getDouble("price"),
                              childObj.getDouble("price_discount"),
                              childObj.getInt("stock"),
                              childObj.getInt("id")





                      );
                      products.add(product);
                  }
                  productAdapter.notifyDataSetChanged();

             }





                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {






            }
        });

 queue.add(request);

    }



    void getRecentOffers()
    {
        RequestQueue queue=Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.GET,Constants.GET_OFFERS_URL, response -> {


            try {
                JSONObject object =new JSONObject(response);

                if(object.getString("status").equals("success"))
                {
                     JSONArray offerArray=object.getJSONArray("news_infos");
                     for(int i=0;i<offerArray.length();i++)
                     {
                         JSONObject childObj=offerArray.getJSONObject(i);
                         binding.carousel.addData(new CarouselItem(
                                 Constants.NEWS_IMAGE_URL+childObj.getString("image"),
                                 childObj.getString("title")
                         ));


                     }


                }



            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        },error -> {});





        queue.add(request);
    }





}