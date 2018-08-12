package com.example.android.instrumentsinventoryapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.instrumentsinventoryapp.R;

public class InstrumentsCursorAdapter extends CursorAdapter {

    public InstrumentsCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        TextView instrumentNameTV = (TextView) view.findViewById(R.id.list_item_product_name);
        TextView instrumentPriceTV = (TextView) view.findViewById(R.id.list_item_price);
        TextView instrumentQuantityTV = (TextView) view.findViewById(R.id.list_item_quantity);
        ImageButton saleButtonTV = (ImageButton) view.findViewById(R.id.list_item_sale_button);

        //Find the column index which we are interested in name price quantity

        int nameColumnIndex = cursor.getColumnIndex(InstrumentsContract.MusicalInstrumentsEntry.COLUMN_INSTRUMENT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(InstrumentsContract.MusicalInstrumentsEntry.COLUMN_INSTRUMENT_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(InstrumentsContract.MusicalInstrumentsEntry.COLUMN_INSTRUMENT_QUANTITY);

        int _id = cursor.getInt(cursor.getColumnIndexOrThrow(InstrumentsContract.MusicalInstrumentsEntry._ID));
        String instrumentName = cursor.getString(nameColumnIndex);
        int instrumentPrice = cursor.getInt(priceColumnIndex);
        int instrumentQuantity = cursor.getInt(quantityColumnIndex);

        instrumentNameTV.setText(instrumentName);
        instrumentPriceTV.setText(Integer.toString(instrumentPrice));
        instrumentQuantityTV.setText(Integer.toString(instrumentQuantity));


        //tu poprawic!!!!!
        final Instrument objectInstrument = new Instrument(_id, instrumentQuantity);
        saleButtonTV.setTag(objectInstrument);

        saleButtonTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Instrument object = (Instrument) v.getTag();
                String klick = InstrumentsContract.MusicalInstrumentsEntry._ID + "= ?";
                String[] selectionArgs = {Integer.toString(object.getmInstrumentId())};
                Uri updateURI = Uri.withAppendedPath(InstrumentsContract.MusicalInstrumentsEntry.CONTENT_URI, Integer.toString(objectInstrument.getmInstrumentId()));
                if (object.getmInstrumentId() > 0) {
                    ContentValues values = new ContentValues();
                    values.put(InstrumentsContract.MusicalInstrumentsEntry.COLUMN_INSTRUMENT_QUANTITY, object.getmInstrumentQuantity() - 1);
                    int sum = context.getContentResolver().update(updateURI, values, klick, selectionArgs);
                }
            }
        });
    }
}
