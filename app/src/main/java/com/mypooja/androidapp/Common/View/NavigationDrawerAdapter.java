package com.mypooja.androidapp.Common.View;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mypooja.androidapp.R;

import java.util.List;

/**
 * Created by poliveira on 24/10/2014.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder> {

    private List<NavigationItem> mData;
    private NavigationDrawerCallbacks mNavigationDrawerCallbacks;
    private int mSelectedPosition;
    private int mTouchedPosition = -1;

    public NavigationDrawerAdapter(List<NavigationItem> data, int selPos) {
        mData = data;
        mSelectedPosition = selPos;
    }

    public NavigationDrawerCallbacks getNavigationDrawerCallbacks() {
        return mNavigationDrawerCallbacks;
    }

    public void setNavigationDrawerCallbacks(NavigationDrawerCallbacks navigationDrawerCallbacks) {
        mNavigationDrawerCallbacks = navigationDrawerCallbacks;
    }

    @Override
    public NavigationDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_row, viewGroup, false);
        final ViewHolder viewholder = new ViewHolder(v);

        /*viewholder.itemView.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {

               switch (event.getAction()) {
                   case MotionEvent.ACTION_DOWN:
                       touchPosition(viewholder.getAdapterPosition());
                       return false;
                   case MotionEvent.ACTION_CANCEL:
                       touchPosition(-1);
                       return false;
                   case MotionEvent.ACTION_MOVE:
                       return false;
                   case MotionEvent.ACTION_UP:
                       touchPosition(-1);
                       return false;
               }
               return true;
           }
        });*/

        viewholder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //selectPosition(viewholder.getAdapterPosition());
               if (mNavigationDrawerCallbacks != null)
                   mNavigationDrawerCallbacks.onNavigationDrawerItemSelected(mData.get(viewholder.getAdapterPosition()).GetId());//viewholder.getAdapterPosition());
           }
        });

        return viewholder;
    }

    @Override
    public void onBindViewHolder(NavigationDrawerAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.textView.setText(mData.get(i).getText());
        mData.get(i).getDrawable().setBounds(0, 0, 32, 32);
        viewHolder.textView.setCompoundDrawables(mData.get(i).getDrawable(), null, null, null);
        if (mData.get(i).addSeparatorBefore())
            viewHolder.separator.setVisibility(View.VISIBLE);
        else
            viewHolder.separator.setVisibility(View.INVISIBLE);

        //TODO: selected menu position, change layout accordingly
        if (mSelectedPosition == i || mTouchedPosition == i) {
            viewHolder.itemView.setBackgroundColor(viewHolder.itemView.getContext().getResources().getColor(R.color.grey_shade_1));
            viewHolder.textView.setTextColor(viewHolder.itemView.getContext().getResources().getColor(R.color.primary_color_dark));
            if (mData.get(i).addSeparatorBefore())
                viewHolder.separator.setBackgroundColor(Color.TRANSPARENT);

            mData.get(i).getDrawableSelected().setBounds(0, 0, 32, 32);
            viewHolder.textView.setCompoundDrawables(mData.get(i).getDrawableSelected(), null, null, null);
        } else {
            viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
            viewHolder.textView.setTextColor(viewHolder.itemView.getContext().getResources().getColor(R.color.TextPrimaryColor));
        }
    }

    private void touchPosition(int position) {
        int lastPosition = mTouchedPosition;
        mTouchedPosition = position;
        if (lastPosition >= 0)
            notifyItemChanged(lastPosition);
        if (position >= 0)
            notifyItemChanged(position);
    }

    public void selectPosition(int position) {
        int lastPosition = mSelectedPosition;
        mSelectedPosition = position;
        notifyItemChanged(lastPosition);
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public View separator;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_name);
            separator = (View) itemView.findViewById(R.id.separator);
        }
    }
}
