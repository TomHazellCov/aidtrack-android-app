package com.tomhazell.aidtrackerapp.additem.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.ViewSwitcher;

import com.tomhazell.aidtrackerapp.R;
import com.tomhazell.aidtrackerapp.additem.AddItemActivity;
import com.tomhazell.aidtrackerapp.additem.NewItem;
import com.tomhazell.aidtrackerapp.additem.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tom Hazell on 27/02/2017.
 * <p>
 * The flow is going to be get all products from API, then if they select one allow it to continue
 * if not they allow them to input stuff, when they hit next save product details then call on next click from activity
 * ...
 */
public class SelectProductIntroductonFragment extends Fragment implements ValidatedFragment, AdapterView.OnItemSelectedListener {

    @BindView(R.id.select_product_viewswitcher)
    ViewSwitcher viewSwitcher;

    @BindView(R.id.select_product_name)
    TextInputLayout layoutName;

    @BindView(R.id.select_product_products)
    Spinner selectProduct;

    @BindView(R.id.select_product_type)
    TextInputLayout layoutType;


    boolean gotProducts = false;//have we received a list of products
    boolean isCreateingProduct = true;//is the user creating a new product or using an existing one
    boolean hasCreatedProduct = false;

    private List<Product> products;
    private Product selectedProduct;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_additem_intro, container, false);
        ButterKnife.bind(this, v);

        selectProduct.setOnItemSelectedListener(this);

        //create web request (mock out from now)
        List<Product> prods = new ArrayList<>();
        prods.add(new Product("Name", "type"));
        prods.add(new Product("Name1", "type1"));
        onGotProducts(prods);

        return v;
    }

    void onGotProducts(List<Product> products) {
        gotProducts = true;
        this.products = products;
        viewSwitcher.showNext();//show content not the progressbar

        //create array adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item);

        adapter.add("Add a new product");//add default option

        //add all products to it
        for (Product product : products) {
            adapter.add(product.getName());
        }

        selectProduct.setAdapter(adapter);
        selectProduct.setSelection(0);
    }

    @Override
    public boolean validateDetails() {
        if (gotProducts) {
            if (isCreateingProduct){
                if (hasCreatedProduct){
                    return true;
                }

                //check feilds are correct then send to server
                if (layoutName.getEditText().getText().toString().equals("")) {
                    layoutName.setError("Cant be empty");
                    return false;
                }

                if (layoutType.getEditText().getText().toString().equals("")) {
                    layoutType.setError("Cant be empty");
                    return false;
                }

                //createProduct api call, the call onproductCreated
                return false;

            }else{
                return true;
            }
        }

        return false;//check selected item, validate feilds if opeion selected
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public NewItem addDataToModel(NewItem newItem) {
        newItem.setProduct(selectedProduct);
        return newItem;
    }

    @Override
    public String getTitle() {
        return "Select Product";
    }

    void onProductCreated(Product product){
        hasCreatedProduct = true;
        this.selectedProduct = product;
        ((AddItemActivity) getActivity()).onNextClick();
    }

    //for spinner selection
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        if (pos == 0){
            isCreateingProduct = true;
            layoutName.setVisibility(View.VISIBLE);
            layoutType.setVisibility(View.VISIBLE);
        }else{
            isCreateingProduct = false;
            layoutName.setVisibility(View.INVISIBLE);
            layoutType.setVisibility(View.INVISIBLE);
        }
    }

    //for spinner selection
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //do nothing
    }
}
