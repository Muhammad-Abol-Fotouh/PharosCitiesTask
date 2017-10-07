package de.pharos_solutions.pharoscitiestask.views;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.pharos_solutions.pharoscitiestask.R;

/**
 * Created by muhammadkorany on 10/5/17.
 */

public class MapActivity extends Activity {

    @BindView(R.id.map_activity_toolbar) Toolbar toolbar;
    @BindView(R.id.map_activity_image_view_location) ImageView mapImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_map_image);
        ButterKnife.bind(this);
        initialization();
    }

    private void initialization(){
        initializeToolbar();

        if(getIntent().getExtras()!=null){
            if (getIntent().getExtras().getString("city_name")!=null){
                toolbar.setTitle(getIntent().getExtras().getString("city_name"));
            }

            if (getIntent().getExtras().getString("url")!=null){
                Picasso.with(this).load(getIntent().getExtras().getString("url")).into(mapImage);
            }
        }
    }

    private void initializeToolbar() {
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
