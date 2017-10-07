package de.pharos_solutions.pharoscitiestask.views;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import de.pharos_solutions.pharoscitiestask.R;
import de.pharos_solutions.pharoscitiestask.models.CityModel;

/**
 * Created by muhammadkorany on 10/5/17.
 */

public class CityRowItem extends LinearLayout {

    private TextView cityName;
    private ImageView cityLocationImage;
    private Context context;
    private LayoutInflater layoutInflater;
    private String cityLocation;

    public CityRowItem(Context context, CityModel cityModel) {
        super(context);
        this.context = context;
        initialization();
    }


    private void initialization(){
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.item_city_row, this, true);

        cityName = (TextView)findViewById(R.id.grid_cell_text_view_city_name);
        cityLocationImage = (ImageView)findViewById(R.id.grid_cell_image_view_city_location);
    }


    public void setData(final CityModel cityModel){
        cityName.setText(cityModel.getName() + ", " + cityModel.getCountry());

        if (cityModel.getCoord().getLat()!=null && !cityModel.getCoord().getLat().equals(null)
                && !cityModel.getCoord().getLat().equals("")
                && cityModel.getCoord().getLon()!=null && !cityModel.getCoord().getLon().equals(null)
                && !cityModel.getCoord().getLon().equals("")) {

            cityLocation = "http://maps.google.com/maps/api/staticmap?center=" +
                    cityModel.getCoord().getLat() + "," + cityModel.getCoord().getLon() + "&zoom=16&size=200x200&sensor=false";

            Picasso.with(context).load(cityLocation).into(cityLocationImage);
        }

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapActivity.class);
                intent.putExtra("city_name" , cityModel.getName() + ", " + cityModel.getCountry());
                intent.putExtra("url" , cityLocation);
                context.startActivity(intent);
            }
        });
    }
}
