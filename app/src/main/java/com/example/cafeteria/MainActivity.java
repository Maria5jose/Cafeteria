package com.example.cafeteria;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.cafeteria.R;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void increment(View view) {
        if (quantity == 10) {
            Toast.makeText(this,   "You cannot have more than 10 coffees", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity + 1;
        displayQuantity(quantity);
    }
    public void decrement(View view){
        if(quantity == 1){
            Toast.makeText(this, "You cannot have less than 1 coffes", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity =quantity -1;
        displayQuantity(quantity);

    }


    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        CheckBox whippedCreamCheckBox = (CheckBox)  findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        boolean hasChocolate = chocolateCheckBox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage  = (createOrderSummary(price,  hasWhippedCream,  hasChocolate, name));

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order for  " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage );
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }

       private int calculatePrice(boolean addWippedCream, boolean addChocolate) {
           int basePrice = 5;
           if(addWippedCream){
               basePrice = basePrice +1;
           }
           if (addChocolate){
               basePrice = basePrice +2;
           }
           return quantity*basePrice;

    }

    private String createOrderSummary (int price, boolean addWhippedCream, boolean addChocolate, String name){

        String priceMessage ="Name: "  + name;
        priceMessage = priceMessage + "\nAdd whipped Cream?: "+ addWhippedCream;
        priceMessage = priceMessage + "\nAdd Chocolate?: "+ addChocolate;
        priceMessage = priceMessage + "\nQuantity: "+ quantity;
        priceMessage = priceMessage + "\nTotal: $ "+ price;
        priceMessage= priceMessage + "\nThank you!";
        return  priceMessage;
    }

        /**
         * This method displays the given quantity value on the screen.
         */
        private void displayQuantity(int numberOfCoffees){
            TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
            quantityTextView.setText("" + numberOfCoffees);
    }

    private void displayPrice(int number){
            TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
            priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    private void displayMessage(String message){
            TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
            priceTextView.setText(message);
    }
}