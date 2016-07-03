package com.example.vegard.drawapplication;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends Activity {

    private DrawingView drawingView;


    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawingView = (DrawingView)findViewById(R.id.drawing);
    }

    public void handleClick(View v){
        setContentView(R.layout.fragment_test);
        Log.i("lol", "handleClick");

        ShapeDrawable rect = new ShapeDrawable(new OvalShape());
        rect.setIntrinsicHeight(50);
        rect.setIntrinsicWidth(50);
        rect.getPaint().setColor(Color.GREEN);

        ImageView imageView = new ImageView(this);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);
        imageView.setLayoutParams(layoutParams);

        imageView.setTranslationX(100);
        imageView.setTranslationY(100+i);

        imageView.setImageDrawable(rect);

        LinearLayout frame = (LinearLayout)findViewById(R.id.frameLinearLayout);

        frame.addView(imageView);

        TextView textView = new TextView(this);
        textView.setTranslationX(100);
        textView.setTranslationY(100 + i);
        i = i+50;
        textView.setText("Yo!");
        frame.addView(textView);

        final PdfDocument document = new PdfDocument();
        writePDFDocument(document);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void writePDFDocument(final PdfDocument pdfDocument) {

        // crate a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(500, 500, 1).create();

        // start a page
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        View contentView = (View)findViewById(R.id.drawing);

        // draw view on the page
        int measureWidth = View.MeasureSpec.makeMeasureSpec(page.getCanvas().getWidth(), View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(page.getCanvas().getHeight(), View.MeasureSpec.EXACTLY);
        contentView.measure(measureWidth, measuredHeight);
        contentView.layout(0, 0, page.getCanvas().getWidth(), page.getCanvas().getHeight());
        contentView.draw(page.getCanvas());

        // finish the page
        pdfDocument.finishPage(page);

        Log.i("info","Pdf created");
    }
}
