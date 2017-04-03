package com.tomhazell.aidtrackerapp.additem.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.tomhazell.aidtrackerapp.NetworkManager;
import com.tomhazell.aidtrackerapp.R;
import com.tomhazell.aidtrackerapp.additem.AddItemActivity;
import com.tomhazell.aidtrackerapp.additem.Manufacturer;
import com.tomhazell.aidtrackerapp.additem.ManufacturesResponce;
import com.tomhazell.aidtrackerapp.additem.NewItem;
import com.tomhazell.aidtrackerapp.additem.Product;
import com.tomhazell.aidtrackerapp.additem.ProductResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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

    @BindView(R.id.select_product_discription)
    TextInputLayout layoutDiscription;

    @BindView(R.id.select_product_error_creating)
    TextView errorCreating;

    @BindView(R.id.select_product_error_loading)
    Button errorButton;

    @BindView(R.id.select_product_manufacture)
    Spinner manufactureSelect;

    @BindView(R.id.select_product_manufacture_name)
    TextInputLayout manufactureText;

    boolean gotProducts = false;//have we received a list of products
    boolean isCreateingProduct = false;//is the user creating a new product or using an existing one
    boolean hasCreatedProduct = false;

    private List<Product> products;
    private List<Manufacturer> manufacturers;
    private Product selectedProduct;
    private Manufacturer selectedManufacturer;

    private ArrayList<Disposable> disposables = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_additem_selectproduct, container, false);
        ButterKnife.bind(this, v);

        selectProduct.setOnItemSelectedListener(this);
        manufactureSelect.setOnItemSelectedListener(this);
        //create web request
        getProducts();

        return v;
    }

    private void getProducts() {
        NetworkManager.getInstance().getProductService().getAllProducts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Product>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(List<Product> value) {
                        products = value;
                        getManufactures();
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorButton.setVisibility(View.VISIBLE);
                        Log.e(getClass().getSimpleName(), "Failed to get products", e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });

        //TODO get all manafacutures and then create if needed
    }

    void getManufactures() {
        NetworkManager.getInstance().getManufacturesService().getAllManufacturers()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Manufacturer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(List<Manufacturer> value) {
                        manufacturers = value;
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorButton.setVisibility(View.VISIBLE);
                        Log.e(getClass().getSimpleName(), "Failed to get products", e);
                    }

                    @Override
                    public void onComplete() {
                        onGotProducts();
                    }
                });
    }

    void onGotProducts() {
        gotProducts = true;
        viewSwitcher.showNext();//show content not the progressbar

        //create array adapter for products
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item);

        adapter.add("Add a new product");//add default option

        //add all products to it
        for (Product product : products) {
            if (product.getName() != null) {
                adapter.add(product.getName());
            }
        }

        selectProduct.setAdapter(adapter);
        selectProduct.setSelection(0);

        //create array adapter for manafactures
        ArrayAdapter<String> adapterManfact = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item);

        adapterManfact.add("Add a new manufacturer");//add default option

        //add all products to it
        for (Manufacturer manufacturer : manufacturers) {
            if (manufacturer.getName() != null) {
                adapterManfact.add(manufacturer.getName());
            }
        }

        manufactureSelect.setAdapter(adapterManfact);
        manufactureSelect.setSelection(0);

    }

    @OnClick(R.id.select_product_error_loading)
    void setErrorButton() {
        errorButton.setVisibility(View.INVISIBLE);
        getProducts();
    }


    @Override
    public boolean validateDetails() {
        if (gotProducts) {
            if (selectProduct.getSelectedItemPosition() == 0) {//if selectedCampain is null then we are creating one or we have already created one
                boolean errors = false;
                if (isCreateingProduct) {
                    return false;
                }

                //check feilds are correct then send to server
                if (layoutName.getEditText().getText().toString().equals("")) {
                    layoutName.setError("Cant be empty");
                    errors = true;
                }

                if (layoutDiscription.getEditText().getText().toString().equals("")) {
                    layoutDiscription.setError("Cant be empty");
                    errors = true;
                }

                if (selectProduct.getSelectedItemPosition() == 0 && manufactureText.getEditText().getText().toString().equals("")) {
                    manufactureText.setError("Cant be empty");
                    errors = true;
                }

                if (errors) {
                    return false;
                }

                if (hasCreatedProduct && (layoutName.getEditText().getText().toString().equals(selectedProduct.getName()) && layoutDiscription.getEditText().getText().toString().equals(selectedProduct.getDescription()))) {
                    return true;
                }

//                if (selectProduct.getSelectedItemPosition() == 0 && !(selectedManufacturer == null || manufactureSelect.getSelectedItemPosition() == 0 && manufactureText.getEditText().getText().toString().equals(selectedManufacturer.getName()))) {TODO SOSON RIP ME
                if (manufactureSelect.getSelectedItemPosition() == 0 && selectedManufacturer == null) {
                    Manufacturer manufacturer = new Manufacturer();
                    manufacturer.setName(manufactureText.getEditText().getText().toString());
                    createManufacturer(manufacturer);
                    return false;
                }

                //createProduct api call
                Product product = new Product(layoutName.getEditText().getText().toString(), layoutDiscription.getEditText().getText().toString());
                product.setManufacturer(selectedManufacturer);
                product.setmId(selectedManufacturer.getId());
                createProduct(product);
                return false;

            } else {
                return true;
            }
        }

        return false;//check selected item, validate feilds if opeion selected
    }

    private void createManufacturer(Manufacturer manufacturer) {
        errorCreating.setVisibility(View.INVISIBLE);
        isCreateingProduct = true;
        NetworkManager.getInstance().getManufacturesService().createManufacturer(manufacturer)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ManufacturesResponce>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(ManufacturesResponce value) {
                        selectedManufacturer = value.getInfo().get(0);
                        isCreateingProduct = false;
                        validateDetails();
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorCreating.setVisibility(View.VISIBLE);
                        Log.e(getClass().getSimpleName(), "Failed to get Manfactures", e);
                        isCreateingProduct = false;
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void createProduct(Product product) {
        errorCreating.setVisibility(View.INVISIBLE);
        isCreateingProduct = true;
        NetworkManager.getInstance().getProductService().createProduct(product)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProductResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(ProductResponse value) {
                        onProductCreated(value.getInfo().get(0));
                        isCreateingProduct = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorCreating.setVisibility(View.VISIBLE);
                        Log.e(getClass().getSimpleName(), "Failed to get Campains", e);
                        isCreateingProduct = false;
                    }

                    @Override
                    public void onComplete() {
                    }
                });

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

    void onProductCreated(Product product) {
        hasCreatedProduct = true;
        this.selectedProduct = product;
        ((AddItemActivity) getActivity()).onNextClick();
    }

    //for spinner selection
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        if (adapterView.getId() == R.id.select_product_products) {

            if (pos == 0) {
                layoutName.setVisibility(View.VISIBLE);
                layoutDiscription.setVisibility(View.VISIBLE);
                if (manufactureSelect.getSelectedItemPosition() == 0) {
                    manufactureSelect.setVisibility(View.VISIBLE);
                    manufactureText.setVisibility(View.VISIBLE);
                }

            } else {
                layoutName.setVisibility(View.GONE);
                layoutDiscription.setVisibility(View.GONE);
                manufactureSelect.setVisibility(View.GONE);
                manufactureText.setVisibility(View.GONE);
                selectedProduct = products.get(pos - 1);
            }
        } else if (adapterView.getId() == R.id.select_product_manufacture) {
            if (pos == 0) {
                manufactureText.setVisibility(View.VISIBLE);
            } else {
                manufactureText.setVisibility(View.GONE);
                selectedManufacturer = manufacturers.get(pos - 1);
            }
        } else {
            Log.wtf(getClass().getSimpleName(), "Could not identify spinner");
        }
    }

    //for spinner selection
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //do nothing
    }

    @Override
    public void onStop() {
        //make sure all long running requests are stopped
        for (Disposable disposable : disposables) {
            disposable.dispose();
        }
        super.onStop();
    }
}
