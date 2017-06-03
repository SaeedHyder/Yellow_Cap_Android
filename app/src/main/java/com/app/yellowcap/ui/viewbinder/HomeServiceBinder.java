package com.app.yellowcap.ui.viewbinder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.app.yellowcap.R;
import com.app.yellowcap.activities.DockActivity;
import com.app.yellowcap.entities.ServiceEnt;
import com.app.yellowcap.ui.viewbinders.abstracts.ViewBinder;
import com.app.yellowcap.ui.views.AnyTextView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 6/3/2017.
 */

public class HomeServiceBinder extends ViewBinder<ServiceEnt> {
    DockActivity context;
    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public HomeServiceBinder(DockActivity activity) {
        super(R.layout.row_item_home);
        context = activity;
    }

    @Override
    public void bindView(ServiceEnt entity, int position, int grpPosition, View view, Activity activity) {
        ViewHolder holder = (ViewHolder)view.getTag();
        holder.txtServiceName.setText(entity.getTitle());
        Picasso.with(context)
                .load(entity.getServiceImage())
                .placeholder(R.drawable.ac)
                .into(holder.imgService);
    }

    public static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.img_service)
        ImageView imgService;
        @BindView(R.id.txt_service_name)
        AnyTextView txtServiceName;

       public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
