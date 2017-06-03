package com.app.yellowcap.ui.viewbinder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.ServiceEnt;
import com.app.yellowcap.interfaces.onDeleteImage;
import com.app.yellowcap.ui.viewbinders.abstracts.ViewBinder;
import com.app.yellowcap.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 5/23/2017.
 */

public class SelectedJobBinder extends ViewBinder<ServiceEnt> {
    private onDeleteImage onDeleteImage;

    public SelectedJobBinder(onDeleteImage onDeleteImage) {
        super(R.layout.selectedjobs_row_item);
        this.onDeleteImage = onDeleteImage;
    }


    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new SelectedJobsViewHolder(view);
    }

    @Override
    public void bindView(ServiceEnt entity, final int position, int grpPosition, View view, Activity activity) {
        SelectedJobsViewHolder viewHolder = (SelectedJobsViewHolder)view.getTag();
        viewHolder.txtJobselectedtext.setText(entity.getTitle());
        viewHolder.deleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteImage.OnDeleteJobs(position);
            }
        });
    }

    public static class SelectedJobsViewHolder extends BaseViewHolder{
        @BindView(R.id.txt_jobselectedtext)
        AnyTextView txtJobselectedtext;
        @BindView(R.id.delete_text)
        ImageView deleteText;

        SelectedJobsViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
